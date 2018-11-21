package Tanks3D.Object.Entity.Pickup;

import java.awt.geom.Point2D;
import java.util.ListIterator;

public class HighExplosiveAmmo extends Pickup {
    public HighExplosiveAmmo(Point2D.Double position) {
        super(position, null, null);
    }

    public void collide(Object object, ListIterator thisObject, ListIterator iterator) {

    }
}