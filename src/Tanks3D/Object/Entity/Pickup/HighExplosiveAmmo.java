package Tanks3D.Object.Entity.Pickup;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.ListIterator;

public class HighExplosiveAmmo extends Pickup {
    public HighExplosiveAmmo(Point2D.Double position) {
        super(position);
    }

    public void collide(Object object, ListIterator iterator) {

    }
}