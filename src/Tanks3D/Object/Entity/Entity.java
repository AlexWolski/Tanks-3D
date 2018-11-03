package Tanks3D.Object.Entity;

import Tanks3D.FastMath.FastMath;
import Tanks3D.GameData;
import Tanks3D.Object.GameObject;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Entity extends GameObject {
    public double angle;
    public double speed;
    protected Point2D.Double position;

    public Entity(Point2D.Double position, double angle, double speed) {
        this.position = position;
        this.angle = angle;
        this.speed = speed;
    }

    public void update(GameData game, double deltaTime) {
        double distMoved = speed * deltaTime / 1000;
        position.x += distMoved * FastMath.sin(angle);
        position.y += distMoved * FastMath.cos(angle);
    }

    protected void draw(BufferedImage canvas) {

    }
}
