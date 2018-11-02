package Tanks3D;

import Tanks3D.FastMath.FastMath;
import Tanks3D.FastMath.Point;
import Tanks3D.Object.SpawnPoint;
import Tanks3D.Object.Wall.Wall;
import Tanks3D.Object.Wall.WallSlice;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


//remove
import java.util.Random;

//Store the list of immobile objects and print them to a screen.
public class Level {
    private SpawnPoint player1Spawn;
    private SpawnPoint player2Spawn;
    private ArrayList<Wall> wallObjects;

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
        wallObjects = new ArrayList<>();
        player1Spawn = new SpawnPoint(new Point(0, 0), 180, 1);
        player2Spawn = new SpawnPoint(new Point(0, 0), 0, 2);


        //remove
        wallObjects.add(new Wall(new Point(3, 3), new Point(-3, 3), 500));
        wallObjects.add(new Wall(new Point(-3, 3), new Point(-3, -3), 500));
        wallObjects.add(new Wall(new Point(-3, -3), new Point(3, -3), 500));
        wallObjects.add(new Wall(new Point(3, -3), new Point(3, 3), 500));
    }

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
        Point p1;
        Point p2;

        //Iterate through each ray and determine which wall to draw.
        for(int i = 0; i < wallBuffer.length; i++) {
            wallBuffer[i] = null;

            for(Wall wall : wallObjects) {
                //Copy the points of the wall.
                p1 = wall.getPoint1();
                p2 = wall.getPoint2();

                //Rotate the points so that the ray is parallel to the y axis.
                FastMath.rotatePoint(p1, camera.position, currentRay - camera.angle);
                FastMath.rotatePoint(p2, camera.position, currentRay - camera.angle);

                p1.subtract(camera.position);
                p2.subtract(camera.position);

                //If the points are on opposite sides of the y axis, the wall intersects with the ray.
                if(p1.x > 0 && p2.x < 0 || p1.x < 0 && p2.x > 0) {
                    //Modified the linear equation y = mx + b. b is the y coordinate of the intersection, and x is always 0.
                    double b = p1.y - ((p2.y - p1.y)/(p2.x - p1.x) * p1.x);

                    //If this wall is closer to the camera than walls previously checked, save it in the buffer.
                    if(b > 0 && (wallBuffer[i] == null || wallBuffer[i].distToCamera > b))
                        wallBuffer[i] = new WallSlice(wall, p1, p2, currentRay, b);
                }
            }

            //Move on to the next ray.
            currentRay += rayAngle;
        }
    }

    public void draw(WallSlice[] wallBuffer, BufferedImage canvas) {
        Color temp = Color.white;

        //remove
        for(int i = 0; i < canvas.getWidth(); i++)
            for(int j = 0; j < canvas.getHeight(); j++)
                canvas.setRGB(i, j, temp.getRGB());

        double fixedDistance;
        int onscreenHeight;
        int wallStart;
        int wallEnd;

        //Iterate through the wall slices and draw them.
        for(int i = 0; i < canvas.getWidth(); i++) {

            if(wallBuffer[i] == null) {

            }
            else {
                //The fixed distance between the wall slice and the camera removing the fish-eye effect.
                fixedDistance = wallBuffer[i].distToCamera * FastMath.cos(wallBuffer[i].rayAngle);
                //Calculate the height of the wall on the screen.
                onscreenHeight = (int) Math.round((wallBuffer[i].wall.getHeight() / fixedDistance));
                //Calculate the height where the wall starts.
                wallStart = canvas.getHeight() / 2 - onscreenHeight / 2;

                if(wallStart < 0)
                    wallStart = 0;

                //Calculate the height where the wall ends.
                wallEnd = wallStart + onscreenHeight;

                if(wallEnd > canvas.getHeight())
                    wallEnd = canvas.getHeight();

                for (int j = wallStart; j < wallEnd; j++) {
                        canvas.setRGB(i, j, 255);
                }
            }

            //Texture
//            if (wallBuffer[i] != null) {
//                //The ratio of
//                double textureRatio = -wallBuffer[i].point1.x / (wallBuffer[i].point2.x - wallBuffer[i].point1.x);
//
//            }
        }
    }
}