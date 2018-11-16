package Tanks3D.Object.Entity;

import Tanks3D.GameData;
import Tanks3D.Object.Wall.*;
import Tanks3D.Utilities.FastMath;
import Tanks3D.Utilities.Image;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ListIterator;

public class Tank extends Entity {
    public double rotationSpeed;
    private final Color color;
    //The size of the hit circle around the tank.
    private final static int hitCircleRadius = 5;
    //How much to scale the images when drawn to the screen.
    private final double scale = 0.04;
    //Stats of the tank.
    private final int maxHealth = 100;
    private int health = maxHealth;
    private int lives = 3;

    public Tank(Point2D.Double position, double angle, Color spriteColor) {
        super(position, hitCircleRadius, angle, 0);

        this.rotationSpeed = 0;
        this.color = spriteColor;

        BufferedImage sprites[] = new BufferedImage[4];

        //Load the sprites.
        sprites[0] = Image.load("resources/Tank/Front.png");
        sprites[1] = Image.load("resources/Tank/Left.png");
        sprites[2] = Image.load("resources/Tank/Back.png");
        sprites[3] = Image.load("resources/Tank/Right.png");

        //Pass the sprites to the parent class.
        super.setSprites(sprites, spriteColor, (int)(sprites[0].getWidth() * scale), (int)(sprites[0].getHeight() * scale));
    }

    public void update(GameData data, double deltaTime) {
        //Update the angle and position of the tank.
        angle += rotationSpeed * deltaTime / 1000;
        angle = FastMath.formatAngle(angle);
        super.update(data, deltaTime);
    }

    public void collide(Object object, ListIterator iterator) {
        //If the tank collides with a breakable wall, destroy the wall.
        if(object instanceof BreakableWall) {
            ((BreakableWall) object).breakWall();
            iterator.remove();
        }
        //If the tank hits the corner of the wall, fix its position.
        else if(object instanceof Point2D.Double) {
            Point2D.Double point = (Point2D.Double)object;

            //Calculate the distance from the point to the edge of the hit circle.
            double distance = hitCircleRadius - Math.hypot(point.x - position.x, point.y - position.y);

            //Calculate the angle between the tank and the hit circle.
            double angle = Math.toDegrees(Math.atan2(point.x - position.x, point.y - position.y));

            //Move the tank.
            this.position.x -= distance * FastMath.sin(angle);
            this.position.y -= distance * FastMath.cos(angle);
        }
        //If the tank hits a wall, fix its position.
        else if(object instanceof UnbreakableWall) {
            //Get the angle of the line.
            double lineAngle = ((UnbreakableWall) object).getAngle();
            //Copy the first point of the wall.
            Point2D.Double linePoint1 = ((UnbreakableWall) object).getPoint1();

            //Rotate the point so that the line it would be vertical next to the entity.
            FastMath.rotate(linePoint1, position, -lineAngle);
            //The x distance between the line and the entity.
            double xDistance = linePoint1.x - position.x;

            if (xDistance > 0) {
                this.position.x -= (hitCircleRadius - xDistance) * FastMath.cos(lineAngle);
                this.position.y += (hitCircleRadius - xDistance) * FastMath.sin(lineAngle);
            } else {
                this.position.x += (hitCircleRadius + xDistance) * FastMath.cos(lineAngle);
                this.position.y -= (hitCircleRadius + xDistance) * FastMath.sin(lineAngle);
            }
        }
        else if(object instanceof Tank) {
            health--;
        }
    }

    public Color getColor() {
        return color;
    }
    public Point2D.Double getPosition() {
        return super.position;
    }
    public double getAngle() { return super.angle; }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getHealth() {
        return health;
    }
    public int getLives() {
        return lives;
    }
}