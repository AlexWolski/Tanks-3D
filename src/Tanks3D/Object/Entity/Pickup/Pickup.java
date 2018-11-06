package Tanks3D.Object.Entity.Pickup;

import Tanks3D.GameData;
import Tanks3D.Object.Entity.Entity;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Pickup extends Entity {
    private final static int hitCircleRadius = 10;

    public Pickup(Point2D.Double position) {
        super(position, hitCircleRadius, 0, 0);
    }

    public void update(GameData data, double deltaTime) {

    }

    public void draw(BufferedImage canvas) {

    }
}
