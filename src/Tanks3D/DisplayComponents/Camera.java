package Tanks3D.DisplayComponents;

import Tanks3D.GameData;
import Tanks3D.Object.Entity.Entity;
import Tanks3D.Object.Wall.Wall;
import Tanks3D.Utilities.FastMath;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Draw the level and entities.
public class Camera {
    //A struct that contains the necessary data about the game.
    private final GameData gameData;
    //An buffer that get written to, then displayed on the screen.
    private final BufferedImage canvas;
    //An array containing the wall slices that need to be drawn.
    private final ObjectSlice[] wallBuffer;
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
        wallBuffer = new ObjectSlice[canvas.getWidth()];
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

        //Iterate through each ray.
        for(int i = 0; i < wallBuffer.length; i++) {
            wallBuffer[i] = null;

            //Iterate through each wall and determine which one to draw.
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
                        wallBuffer[i] = new ObjectSlice(wall, dist, -line.x1/wall.getLength());
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
                onscreenHeight = (int) (Wall.getHeight() / wallBuffer[i].distToCamera * distProjectionPlane);
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
        //Distance from the camera to the center of the entity.
        double dist;
        //The onscreen width and height of the entity accounting for distance.
        Dimension entitySize;
        //A line representing the entity's location and the width of its image.
        Line2D.Double rotatedLine = new Line2D.Double();
        //A list of entities that the ray intersects.
        ArrayList<ObjectSlice> visibleEntities = new ArrayList<>();
        //Temporarily hold an object slice before its stored in the array list.
        ObjectSlice currentSlice;

        //Iterate through each ray.
        for(int i = 0; i < wallBuffer.length; i++) {
            //Empty the list of entities.
            visibleEntities.clear();

            //Iterate through each entity.
            for (Entity entity : gameData.entityList) {
                //The in-game dimensions of the entity.
                entitySize = entity.getSize();

                //Create a line at the entity's position with the same width. It is horizontal.
                rotatedLine.setLine(entity.position.x - entitySize.width/2.0, entity.position.y, entity.position.x + entitySize.width/2.0, entity.position.y);
                //Rotate the line around itself so that it is facing the camera.
                FastMath.rotate(rotatedLine, entity.position, Math.toDegrees(Math.atan2(entity.position.x - position.x, entity.position.y - position.y)));
                //Rotate the line around the camera so that the ray is along the y axis.
                FastMath.rotate(rotatedLine, position, -angle - currentRay);
                FastMath.translate(rotatedLine, -position.x, -position.y);

                //Get the distance to the entity using the line accounting for the fish eye effect.
                dist = FastMath.getYIntercept(rotatedLine) * FastMath.cos(currentRay);

                //If the entity intersects with the ray and it is in front of the walls, draw it.
                if (dist > 0 && dist <= wallBuffer[i].distToCamera) {
                    //Recalculate the distance to the center of the entity, not where it intersects.
                    dist =  Math.hypot(entity.position.x - position.x, entity.position.y - position.y);
                    //Create the object slice.
                    currentSlice = new ObjectSlice(entity, dist, -rotatedLine.x1/entitySize.width);

                    //If the array list is empty, add the object slice.
                    if(visibleEntities.isEmpty())
                        visibleEntities.add(currentSlice);
                    //Otherwise, insert the object slice in order by distance.
                    else
                        for(int j = 0; j < visibleEntities.size(); j++)
                            if(dist < visibleEntities.get(j).distToCamera)
                                visibleEntities.add(j, currentSlice);
                }
            }

            //Draw all of the entities in the array list in order from nearest to farthest.
            for(ObjectSlice slice : visibleEntities) {
                int sliceHeight = (int) (((Entity) slice.object).getSize().height / slice.distToCamera * distProjectionPlane);
                int sliceStart = canvas.getHeight()/2 - sliceHeight/2;
                int sliceEnd = canvas.getHeight()/2 + sliceHeight;

                if(sliceStart < 0)
                    sliceStart = 0;

                if(sliceEnd > canvas.getHeight())
                    sliceEnd = canvas.getHeight();

                for (int j = sliceStart; j < sliceEnd; j++) {
                    canvas.setRGB(i, j, Color.yellow.getRGB());
                    pixelTable[i][j] = false;
                }
            }

            //Move on to the next ray.
            currentRay += rayAngle;
        }
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