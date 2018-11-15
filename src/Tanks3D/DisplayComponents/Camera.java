package Tanks3D.DisplayComponents;

import Tanks3D.GameData;
import Tanks3D.Object.Entity.Entity;
import Tanks3D.Object.Wall.Wall;
import Tanks3D.Object.Wall.WallSlice;
import Tanks3D.Utilities.FastMath;

import java.awt.*;
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
    //An array full of booleans indicating whether a pixel has been written to or not.
    private final boolean[][] pixelTable;
    //The field of view of the camera.
    private static final double FOV = 70;
    //The distance from the camera to the projection plane.
    private double distProjectionPlane;

    public Camera(GameData gameData, BufferedImage canvas) {
        this.canvas = canvas;
        this.gameData = gameData;

        //Calculate the distance from the camera to the projection plane from the FOV and image buffer width.
        distProjectionPlane = FastMath.cos(FOV/2) / FastMath.sin(FOV/2) * canvas.getWidth() / 2;
        //Initialize the array of wall slices and array of booleans.
        wallBuffer = new WallSlice[canvas.getWidth()];
        pixelTable = new boolean[canvas.getWidth()][canvas.getHeight()];
    }

    //Reset the pixel table.
    private void clearPixelTable() {
        for(int i = 0; i < canvas.getWidth(); i++)
            for(int j = 0; j < canvas.getHeight(); j++)
                pixelTable[i][j] = true;
    }

    //Fill the given buffer with the slices of wall that needs to be drawn.
    private void calculateWallBuffer(Point2D.Double position, double angle) {
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

                    //The distance between the camera and the point on the wall that the ray hit. Multiply by cos to remove the fish-eye effect.
                    double dist = FastMath.getYIntercept(line) * FastMath.cos(currentRay);

                    //If this wall is visible and is closer to the camera than walls previously checked, save it in the buffer.
                    if (dist > 0 && (wallBuffer[i] == null || wallBuffer[i].distToCamera > dist))
                        wallBuffer[i] = new WallSlice(wall, dist, -line.x1/wall.getLength());
                }
            }

            //Move on to the next ray.
            currentRay += rayAngle;
        }
    }

    //Draw the wall slices in the wall buffer.
    private void drawWalls() {
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
                //Calculate the height of the wall on the screen.
                onscreenHeight = (int) Math.round(Wall.getHeight() / wallBuffer[i].distToCamera * distProjectionPlane);
                //Calculate the height where the wall starts.
                wallStart = canvas.getHeight()/2 - onscreenHeight/2;

                if(wallStart < 0)
                    wallStart = 0;

                //Calculate the height where the wall ends.
                wallEnd = wallStart + onscreenHeight;

                if(wallEnd > canvas.getHeight())
                    wallEnd = canvas.getHeight();

                //Draw the wall slice, checking that each pixel is writable.
                for (int j = wallStart; j < wallEnd; j++)
                    if(pixelTable[i][j]) {
                        canvas.setRGB(i, j, 255);
                        pixelTable[i][j] = false;
                    }
            }

            //Draw the ceiling.
            for(int j = 0; j < wallStart; j++)
                if(pixelTable[i][j]) {
                    canvas.setRGB(i, j, gameData.gameLevel.getCeilColor());
                    pixelTable[i][j] = false;
                }

            //Draw the floor.
            for(int j = wallEnd; j < canvas.getHeight(); j++)
                if(pixelTable[i][j]) {
                    canvas.setRGB(i, j, gameData.gameLevel.getFloorColor());
                    pixelTable[i][j] = false;
                }
        }
    }

    //Draw the entities, comparing their distance to the wall buffer.
    private void drawEntities(Point2D.Double position, double angle) {
        //The angle between each ray.
        double rayAngle = FOV/wallBuffer.length;
        //The angle of the first ray.
        double currentRay = -FOV/2;

        double distance;
        double spriteAngle;
        Dimension spriteSize;

        for(Entity entity : gameData.entityList) {
            //Calculate the distance between the camera and the entity, accounting for the fish-eye effect.
            distance = Math.hypot(entity.getXPos() - position.x, entity.getYPos() - position.y)* FastMath.cos(currentRay);
            //Calculate the size of the entity based on how far away it is.
            spriteSize = new Dimension((int)(entity.getWidth()/distance), (int)(entity.getWidth()/distance));


        }

        //Move on to the next ray.
        currentRay += rayAngle;
    }

    //Calculate the wall slices, draw the entities, and finally draw the walls.
    public void draw(Point2D.Double position, double angle) {
        //Reset the pixel table so that every pixel is writable.
        clearPixelTable();
        //Calculate which walls to draw given the position and angle of the camera.
        calculateWallBuffer(position, angle);
        //Draw the entities.
        drawEntities(position, angle);
        //Draw the walls.
        drawWalls();
    }
}