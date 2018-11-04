package Tanks3D;

import Tanks3D.FastMath.FastMath;
import Tanks3D.Object.SpawnPoint;
import Tanks3D.Object.Wall.BreakableWall;
import Tanks3D.Object.Wall.Wall;
import Tanks3D.Object.Wall.WallSlice;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


//remove
import java.util.Random;

//Store the list of immobile objects and print them to a screen.
public class Level {
    public ArrayList<Wall> wallObjects;
    public Point.Double mapCenter;
    private double mapWidth;
    private double mapHeight;
    private SpawnPoint player1Spawn;
    private SpawnPoint player2Spawn;
    private int floorColor;
    private int ceilColor;

    //remove
    Random rand;

    //Read the data file and translate it into objects. Save the player spawn-points to construct the player objects.
    public Level(String levelFile) {
        //remove
        rand = new Random();
        //Read the data file and create all of the map objects.
        parseDataFile(levelFile);
    }

    //Read the json objects from the file and create them. Walls are stored in the 'wallObjects' ArrayList.
    private void parseDataFile(String levelFile) {
        //remove
        wallObjects = new ArrayList<>();
        player1Spawn = new SpawnPoint(new Point2D.Double(0, 0), 180, 1);
        player2Spawn = new SpawnPoint(new Point2D.Double(0, 0), 0, 2);

        floorColor = new Color(0x803700).getRGB();
        ceilColor = new Color(0).getRGB();

        wallObjects.add(new BreakableWall(new Point2D.Double(3, 3), new Point2D.Double(-3, 3), 500));
        wallObjects.add(new BreakableWall(new Point2D.Double(-3, 3), new Point2D.Double(-3, -3), 500));
        wallObjects.add(new BreakableWall(new Point2D.Double(-3, -3), new Point2D.Double(3, -3), 500));
        wallObjects.add(new BreakableWall(new Point2D.Double(3, -3), new Point2D.Double(3, 3), 500));

        mapCenter = new Point.Double(0, 0);
        mapWidth = 10;
        mapHeight = 10;
    }

    public double getMapWidth() { return mapWidth; }
    public double getMapHeight() { return mapHeight; }

    public SpawnPoint getPlayer1Spawn() {
        return player1Spawn;
    }
    public SpawnPoint getPlayer2Spawn() {
        return player2Spawn;
    }

    //Fill the given buffer with the slices of wall that needs to be drawn.
    public void calculateBuffer(WallSlice[] wallBuffer, Camera camera) {
        //The angle between each ray.
        double rayAngle = camera.FOV/wallBuffer.length;
        //The angle of the first ray.
        double currentRay = -camera.FOV/2;
        //The rotated points of the current wall.
        Point2D.Double point1;
        Point2D.Double point2;

        //Iterate through each ray and determine which wall to draw.
        for(int i = 0; i < wallBuffer.length; i++) {
            wallBuffer[i] = null;

            for(Wall wall : wallObjects) {
                //Copy the points of the wall.
                Line2D.Double line = wall.getLine();

                //Rotate the wall so that the ray is facing along the y axis.
                FastMath.rotate(line, camera.position, currentRay - camera.angle);
                FastMath.translate(line, -camera.position.x, -camera.position.y);

                //The distance between the camera and the point on the wall that the ray hit.
                double dist = FastMath.getYIntercept(line);

                //If this wall is visible and is closer to the camera than walls previously checked, save it in the buffer.
                if(dist > 0 && (wallBuffer[i] == null || wallBuffer[i].distToCamera > dist))
                    wallBuffer[i] = new WallSlice(wall, null, null, currentRay, dist);
            }

            //Move on to the next ray.
            currentRay += rayAngle;
        }
    }

    public void draw(WallSlice[] wallBuffer, BufferedImage canvas) {
        double fixedDistance;
        int onscreenHeight;
        int wallStart;
        int wallEnd;

        //Iterate through the wall slices and draw them.
        for(int i = 0; i < canvas.getWidth(); i++) {

            if(wallBuffer[i] == null) {
                wallStart = canvas.getHeight()/2;
                wallEnd = wallStart;
            }
            else {
                //The fixed distance between the wall slice and the camera removing the fish-eye effect.
                fixedDistance = wallBuffer[i].distToCamera * FastMath.cos(wallBuffer[i].rayAngle);
                //Calculate the height of the wall on the screen.
                onscreenHeight = (int) Math.round((wallBuffer[i].wall.getHeight() / fixedDistance));
                //Calculate the height where the wall starts.
                wallStart = canvas.getHeight()/2 - onscreenHeight/2;

                if(wallStart < 0)
                    wallStart = 0;

                //Calculate the height where the wall ends.
                wallEnd = wallStart + onscreenHeight;

                if(wallEnd > canvas.getHeight())
                    wallEnd = canvas.getHeight();

                //Draw the walls.
                for (int j = wallStart; j < wallEnd; j++)
                        canvas.setRGB(i, j, 255);
            }

            //Draw the ceiling.
            for(int j = 0; j < wallStart; j++)
                canvas.setRGB(i, j, ceilColor);

            //Draw the floor.
            for(int j = wallEnd; j < canvas.getHeight(); j++)
                canvas.setRGB(i, j, floorColor);
        }
    }
}