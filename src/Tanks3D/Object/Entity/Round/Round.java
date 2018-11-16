package Tanks3D.Object.Entity.Round;

import Tanks3D.GameData;
import Tanks3D.Object.Entity.Entity;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Round extends Entity {
    private final static int hitCircleRadius = 5;

    public Round(Point2D.Double position, double angle, int speed) {
        super(position, 1, 1, hitCircleRadius, angle, speed);
    }

    public void update(GameData game, double deltaTime) {

    }

    public void draw(BufferedImage canvas) {

    }
}
