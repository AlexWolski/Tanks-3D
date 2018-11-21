package Tanks3D.Object.Entity.Pickup;

import Tanks3D.Object.Entity.Entity;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Pickup extends Entity {
    private final static int hitCircleRadius = 10;
    //How much to scale the images when drawn to the screen.
    private final static double scale = 0.01;

    public Pickup(Point2D.Double position, BufferedImage[] sprites, Color imageColor) {
        super(hitCircleRadius, position, 0, 0);
        super.setSprites(sprites, imageColor, (int)(sprites[0].getWidth() * scale), (int)(sprites[0].getHeight() * scale));
    }
}
