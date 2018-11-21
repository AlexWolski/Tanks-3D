package Tanks3D.Object.Entity.Pickup;

import Tanks3D.Object.Entity.Entity;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ListIterator;

public abstract class Pickup extends Entity {
    private final static int hitCircleRadius = 4;
    //How much to scale the images when drawn to the screen.
    private final static double scale = 0.0175;

    public Pickup(Point2D.Double position, BufferedImage[] sprites, BufferedImage icon, Color imageColor) {
        super(hitCircleRadius, position, 0, 0);
        super.setSprites(sprites, (int)(sprites[0].getWidth() * scale), (int)(sprites[0].getHeight() * scale));
        super.setIcon(icon);
        entityColor = imageColor;

        super.zPos = (int)getHeight()/2;
        super.visible = true;
    }

    protected void removePickup(ListIterator thisObject) {
        visible = false;
        thisObject.remove();
    }
}
