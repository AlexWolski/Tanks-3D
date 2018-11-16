package Tanks3D.Object.Entity;

import Tanks3D.GameData;
import Tanks3D.Object.Wall.*;
import Tanks3D.Utilities.FastMath;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ListIterator;

public class Tank extends Entity {
    public double rotationSpeed;
    private final Color color;
    private final static int hitCircleRadius = 5;
    //Stats of the tank.
    private final int maxHealth = 100;
    private int health = maxHealth;
    private int lives = 3;

    public Tank(Point2D.Double position, double angle, Color color) {
        super(position, 10, 10, hitCircleRadius, angle, 0);

        this.rotationSpeed = 0;
        this.color = color;

        BufferedImage images[] = new BufferedImage[4];

        //Load the images for the tank and color them.
        try {
            //Load the images.
            images[0] = ImageIO.read(new File("resources/Tank Body.png"));
            images[1] = ImageIO.read(new File("resources/Tank Gun.png"));
            images[2] = ImageIO.read(new File("resources/Health Icon.png"));
            images[3] = ImageIO.read(new File("resources/Life Icon.png"));

            //Color the images.
            Tanks3D.Utilities.Image.setHue(images[0], color);
            Tanks3D.Utilities.Image.setHue(images[1], color);
            Tanks3D.Utilities.Image.setHue(images[2], color);
            Tanks3D.Utilities.Image.setHue(images[3], color);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //Pass the images to the parent class.
        super.setImages(images);
    }

    public void update(GameData data, double deltaTime) {
        //Update the angle and position of the tank.
        angle += rotationSpeed * deltaTime / 1000;
        super.update(data, deltaTime);
    }

    public void collide(Object object, ListIterator iterator) {
        //If the tank collides with a breakable wall, destroy the wall.
        if(iterator instanceof BreakableWall) {
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