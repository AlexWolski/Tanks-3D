package Tanks3D.Object.Entity;

import Tanks3D.Object.GameObject;
import Tanks3D.Object.Wall.UnbreakableWall;
import Tanks3D.Object.Wall.Wall;
import Tanks3D.Utilities.FastMath;
import Tanks3D.GameData;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;

public abstract class Entity extends GameObject {
    //The radius of the hit circle of the entity.
    private final int hitCircleRadius;
    //An array containing the sprites for the entity at different angles.
    private BufferedImage sprites[];
    //The in-game size of the entity. The sprites are stretched to fit this.
    private Dimension entitySize;
    //The entity's data in 3d space.
    public Point2D.Double position;
    public double angle;
    public double speed;

    public Entity(Point2D.Double position, int hitCircleRadius, double angle, double speed) {
        this.hitCircleRadius = hitCircleRadius;
        this.position = position;
        this.angle = angle;
        this.speed = speed;
    }

    //All entity classes have a 'collide' method that handles the event that it collides with a wall or another entity.
    public abstract void collide(Object object, ListIterator iterator);

    public void update(GameData gamedata, double deltaTime) {
        //Move the entity based on its angle and speed.
        double distMoved = speed * deltaTime / 1000;
        //Equivalent of cos(angle-90)
        position.x += distMoved * FastMath.sin(angle);
        //Equivalent of sin(angle-90)
        position.y += distMoved * FastMath.cos(angle);

        //Check if the entity collides with any walls or entities.
        checkCollisionWall(gamedata.gameLevel.wallObjects);
        checkCollisionEntity(gamedata.entityList);
    }

    //Draw the entity.
    protected void draw(BufferedImage canvas) {

    }

    //Check if this entity collides with any walls. If it does, pass the wall to the 'collide' method.
    private void checkCollisionWall(ArrayList<Wall> wallList) {
        //The line of the wall rotated so that the ray is along the y axis.
        Line2D.Double rotatedLine;
        //Iterator for checking all of the walls.
        ListIterator<Wall> iterator = wallList.listIterator();
        //A temporary wall object to reference the wall being checked.
        Wall wall;

        //Iterate through the array of walls and check if the entity collides with it.
        while(iterator.hasNext()) {
            //Store the wall being checked.
            wall = iterator.next();

            //Check for collisions with the wall if it is visible.
            if(wall.getVisible()) {
                //Check if the entity hits the sides of the wall. If it does, call the 'collide' method.
                if(wall instanceof UnbreakableWall)
                    if (FastMath.isPointInCircle(wall.getPoint1(), position, this.getHitCircleRadius()))
                        this.collide(wall.getPoint1(), iterator);
                    else if(FastMath.isPointInCircle(wall.getPoint2(), position, this.getHitCircleRadius()))
                        this.collide(wall.getPoint2(), iterator);

                //Copy the line of the wall.
                rotatedLine = wall.getLine();
                //Rotate the line around the entity so that it is vertical.
                FastMath.rotate(rotatedLine, this.position, -wall.getAngle());

                //If the distance to the line is less than the hit circle radius and the line is next to the entity, call the 'collide' method.
                if (Math.abs(rotatedLine.x1 - this.position.x) < this.getHitCircleRadius()
                        && ((rotatedLine.y1 >= this.position.y - this.getHitCircleRadius()
                        && rotatedLine.y2 <= this.position.y) || (rotatedLine.y1 <= this.position.y
                        && rotatedLine.y2 >= this.position.y))) {
                    //Pass the iterator in case the entity needs to delete the wall.
                    this.collide(wall, iterator);
                }
            }
        }
    }

    //Check if this entity collides with any other entities.  If it does, pass the other entity to the 'collide' method.
    private void checkCollisionEntity(ArrayList<Entity> entityList) {
        //The distance between the two entities squared.
        double actualDistSquared;
        //The distance where the two entities touch squared.
        double minDistSquared;

        //Iterator for checking all of the entities.
        ListIterator<Entity> iterator = entityList.listIterator();
        //A temporary entity object to reference the entity being checked.
        Entity entity;

        //Iterate through the array of entities and check if the entity collides with it.
        while(iterator.hasNext()) {
            //Store the wall being checked.
            entity = iterator.next();

            //Prevent collisions between an entity and itself.
            if(this != entity) {
                //Calculate the two distances.
                actualDistSquared = Math.pow(entity.position.x - this.position.x, 2) + Math.pow(entity.position.y - this.position.y, 2);
                minDistSquared = Math.pow(entity.getHitCircleRadius() + this.getHitCircleRadius(), 2);

                //If the entities collide, call the collide method of this entity.
                if (actualDistSquared < minDistSquared)
                    //Pass the iterator in case this entity needs to delete the other.
                    this.collide(entity, iterator);
            }
        }
    }

    private int getHitCircleRadius() {
        return hitCircleRadius;
    }
    public Dimension getSize() {
        return entitySize;
    }

    //Set the sprites and size of the entity. The sprites start with a front shot and rotate clockwise.
    protected void setSprites(BufferedImage images[], int width, int height) {
        this.sprites = images;
        this.entitySize = new Dimension(width, height);
    }

    //Given the angle of the camera, return the appropriate image.
    public BufferedImage getImage(double viewerAngle) {
        //Get the difference in angle between the tank and the viewer.
        viewerAngle = FastMath.formatAngle(this.angle - viewerAngle - 540.0/ sprites.length);
        //Map the angle to one of the sprites and return it.
        return sprites[(int)(viewerAngle * sprites.length/360)];
    }
}
