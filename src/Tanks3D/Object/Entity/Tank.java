package Tanks3D.Object.Entity;

import Tanks3D.GameData;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Tank extends Entity {
    public double rotationSpeed;
    private Color color;

    public Tank(Point2D.Double position, double angle, Color color) {
        super(position, angle, 0);

        this.rotationSpeed = 0;
        this.color = color;
    }

    public void update(GameData data, double deltaTime) {
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