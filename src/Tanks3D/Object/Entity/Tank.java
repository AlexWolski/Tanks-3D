package Tanks3D.Object.Entity;

import Tanks3D.GameData;
import Tanks3D.HitCircle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Tank extends Entity {
    public double rotationSpeed;
    private final Color color;
    private final HitCircle hitCircle;
    private final static int hitCircleRadius = 10;

    public Tank(Point2D.Double position, double angle, Color color) {
        super(position, angle, 0);

        this.rotationSpeed = 0;
        this.color = color;

        hitCircle = new HitCircle(super.position, hitCircleRadius);
    }

    public void update(GameData data, double deltaTime) {
        //Update the angle and position of the tank.
        angle += rotationSpeed * deltaTime / 1000;
        super.update(data, deltaTime);
    }

    public Color getColor() {
        return color;
    }

    public void draw(BufferedImage canvas) {

    }

    public Point2D.Double getPosition() { return super.position; }
    public double getAngle() { return super.angle; }
}