package Tanks3D.Object.Entity;

import Tanks3D.GameData;

import java.awt.*;
import Tanks3D.FastMath.Point;
import java.awt.image.BufferedImage;

public class Tank extends Entity {
    private Color color;
    private double rotationSpeed;

    public Tank(Point position, double angle, Color color) {
        super(position, angle, 0);

        this.color = color;
        this.rotationSpeed = 0;
    }

    public void update(GameData data, double deltaTime) {
        super.update(data, deltaTime);

        angle += rotationSpeed * deltaTime;
    }

    public void draw(BufferedImage canvas) {

    }

    public Point getPosition() { return super.position; }
    public double getAngle() { return super.angle; }

    public void setSpeed(double speed) { this.speed = speed; }
    public void setRotationSpeed(double rotationSpeed) { this.rotationSpeed = rotationSpeed; }
}