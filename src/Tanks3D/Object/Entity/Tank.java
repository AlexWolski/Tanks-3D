package Tanks3D.Object.Entity;

import Tanks3D.GameData;
import Tanks3D.Object.Wall.*;
import Tanks3D.Utilities.FastMath;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.ListIterator;

public class Tank extends Entity {
    public double rotationSpeed;
    private final Color color;
    private final static int hitCircleRadius = 3;

    public Tank(Point2D.Double position, double angle, Color color) {
        super(position, hitCircleRadius, angle, 0);

        this.rotationSpeed = 0;
        this.color = color;
    }

    public void update(GameData data, double deltaTime) {
        //Update the angle and position of the tank.
        angle += rotationSpeed * deltaTime / 1000;
        super.update(data, deltaTime);
    }

    public void collide(Object object, ListIterator iterator) {
        if(object instanceof BreakableWall) {
            //If the tank collides with a breakable wall, destroy the wall.
            ((BreakableWall) object).breakWall();
            iterator.remove();
        }
        else if(object instanceof UnbreakableWall) {
            double lineAngle = ((UnbreakableWall) object).getAngle();
            //Copy the first point of the wall.
            Point2D.Double linePoint1 = ((UnbreakableWall) object).getPoint1();

            //Rotate the point so that the line it would be vertical next to the entity.
            FastMath.rotate(linePoint1, position, -lineAngle);
            //The x distance between the line and the entity.
            double xDistance = linePoint1.x - position.x;

            if(xDistance > 0) {
                this.position.x -= (hitCircleRadius - xDistance) * FastMath.cos(lineAngle);
                this.position.y += (hitCircleRadius - xDistance) * FastMath.sin(lineAngle);
            }
            else {
                this.position.x += (hitCircleRadius + xDistance) * FastMath.cos(lineAngle);
                this.position.y -= (hitCircleRadius + xDistance) * FastMath.sin(lineAngle);
            }
        }
        else if(object instanceof Tank) {
            System.out.println("TANK!");
        }
    }

    public Color getColor() {
        return color;
    }
    public Point2D.Double getPosition() { return super.position; }
    public double getAngle() { return super.angle; }
}