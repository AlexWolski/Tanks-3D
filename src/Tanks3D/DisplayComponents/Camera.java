package Tanks3D.DisplayComponents;

import Tanks3D.GameData;
import Tanks3D.Object.Wall.Wall;
import Tanks3D.Object.Wall.WallSlice;
import Tanks3D.Utilities.FastMath;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

//Draw the level and entities.
public class Camera {
    //A struct that contains the necessary data about the game.
    private final GameData gameData;
    //An buffer that get written to, then displayed on the screen.
    private final BufferedImage canvas;
    //An array containing the wall slices that need to be drawn.
    private final WallSlice[] wallBuffer;
    //The field of view of the camera.
    private static final double FOV = 80;
    //The distance from the camera to the projection plane.
    private double distProjectionPlane;
    //The position of the point.
    private final Point2D.Double position;
    //The angle of the camera.
    public double angle;

    public Camera(GameData gameData, BufferedImage canvas, Point2D.Double position, double angle) {
        this.canvas = canvas;
        this.gameData = gameData;
        this.position = position;
        this.angle = angle;

        //Calculate the distance from the camera to the projection plane from the FOV and image buffer width.
        distProjectionPlane = FastMath.cos(FOV/2) / FastMath.sin(FOV/2) * canvas.getWidth() / 2;
        //Initialize the array of wall slices.
        wallBuffer = new WallSlice[canvas.getWidth()];
    }

    //Fill the given buffer with the slices of wall that needs to be drawn.
    public void calculateWallBuffer() {
        //The angle between each ray.
        double rayAngle = FOV/wallBuffer.length;
        //The angle of the first ray.
        double currentRay = -FOV/2;

        //Iterate through each ray and determine which wall to draw.
        for(int i = 0; i < wallBuffer.length; i++) {
            wallBuffer[i] = null;

            for(Wall wall : gameData.gameLevel.wallObjects) {
                //Scan the wall if it is visible.
                if(wall.getVisible()) {
                    //Copy the points of the wall.
                    Line2D.Double line = wall.getLine();

                    //Rotate the wall so that the ray is facing along the y axis.
                    FastMath.rotate(line, position, -angle - currentRay);
                    FastMath.translate(line, -position.x, -position.y);

                    //The distance between the camera and the point on the wall that the ray hit.
                    double dist = FastMath.getYIntercept(line);

                    //If this wall is visible and is closer to the camera than walls previously checked, save it in the buffer.
                    if (dist > 0 && (wallBuffer[i] == null || wallBuffer[i].distToCamera > dist))
                        wallBuffer[i] = new WallSlice(wall, null, null, currentRay, dist);
                }
            }

            //Move on to the next ray.
            currentRay += rayAngle;
        }
    }

    //Draw the wall slices in the wall buffer.
    public void drawWalls() {
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
                onscreenHeight = (int) Math.round(Wall.getHeight() / fixedDistance * distProjectionPlane);
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
            for(int j = 0; j < wallStart; j++) {
                canvas.setRGB(i, j, gameData.gameLevel.getCeilColor());
            }

            //Draw the floor.
            for(int j = wallEnd; j < canvas.getHeight(); j++)
                canvas.setRGB(i, j, gameData.gameLevel.getFloorColor());
        }
    }

    //Calculate the wall slices, draw the entities, and finally draw the walls.
    public void draw() {
        //Pass the wall buffer to 'gameLevel' so it can calculate which parts of which walls to draw.
        calculateWallBuffer();

        //Draw all of the wall slices.
        drawWalls();
    }
}