package Tanks3D.Object.Entity;

import Tanks3D.FastMath.FastMath;
import Tanks3D.FastMath.Point;
import Tanks3D.GameData;
import Tanks3D.Object.GameObject;

import java.awt.image.BufferedImage;

public abstract class Entity extends GameObject {
    protected Point position;
    protected double angle;
    protected double speed;

    public Entity(Point position, double angle, double speed) {
        this.position = position;
        this.angle = angle;
        this.speed = speed;
    }

    public void update(GameData game, double deltaTime) {
        double distMoved = speed * deltaTime;
        position.x += distMoved * FastMath.sin(angle);
        position.y += distMoved * FastMath.cos(angle);
    }

    protected void draw(BufferedImage canvas) {

    }
}
