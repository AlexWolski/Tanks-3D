package Tanks3D.Object.Entity;

import Tanks3D.HitCircle;
import Tanks3D.Object.Wall.Wall;
import Tanks3D.Utilities.FastMath;
import Tanks3D.GameData;
import Tanks3D.Object.GameObject;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Entity extends GameObject {
    public double angle;
    public double speed;
    protected Point2D.Double position;
    private HitCircle hitCircle;

    public Entity(Point2D.Double position, int hitCircleRadius, double angle, double speed) {
        this.position = position;
        this.hitCircle = new HitCircle(position, hitCircleRadius);
        this.angle = angle;
        this.speed = speed;
    }

    //All entity classes have a 'collide' method that handles the event that it collides with a wall or another entity.
    public abstract void collide(Object object);

    //Check if this entity collides with any walls. If it does, pass the wall to the 'collide' method.
    private void checkCollisionWall(ArrayList<Wall> wallList) {
        Line2D.Double rotatedLine;

        for(Wall wall : wallList) {
            //Copy the line of the wall.
            rotatedLine = wall.getLine();
            //Rotate the line around the entity so that it is vertical.
            FastMath.rotate(rotatedLine, this.position, -wall.getAngle());

            //If the distance to the line is less than the hit circle radius and the line is next to the entity, call the 'collide' method.t
            if(Math.abs(rotatedLine.x1 - this.position.x) < this.getHitCircleRadius()
               && ((rotatedLine.y1 >= this.position.y - getHitCircleRadius() && rotatedLine.y2 <= this.position.y) || (rotatedLine.y1 <= this.position.y && rotatedLine.y2 >= this.position.y)))
                this.collide(wall);
        }
    }

    //Check if this entity collides with any other entities.  If it does, pass the other entity to the 'collide' method.
    private void checkCollisionEntity(ArrayList<Entity> entityList) {
        //The distance between the two entities squared.
        double actualDistSquared;
        //The distance where the two entities touch squared.
        double minDistSquared;

        for(Entity entity : entityList) {
            //Prevent collisions between an entity and itself.
            if(this != entity) {
                //Calculate the two distances.
                actualDistSquared = Math.pow(entity.position.x - this.position.x, 2) + Math.pow(entity.position.y - this.position.y, 2);
                minDistSquared = Math.pow(entity.getHitCircleRadius() + this.getHitCircleRadius(), 2);

                //If the entities collide, call the collide method of this entity.
                if (actualDistSquared < minDistSquared)
                    this.collide(entity);
            }
        }
    }

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

    public Point2D.Double getPosition() {
        return new Point2D.Double(position.x, position.y);
    }
    public int getHitCircleRadius() {
        return hitCircle.getRadius();
    }
}
