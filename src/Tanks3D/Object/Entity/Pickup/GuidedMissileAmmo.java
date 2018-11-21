package Tanks3D.Object.Entity.Pickup;

import java.awt.geom.Point2D;
import java.util.ListIterator;

public class GuidedMissileAmmo extends Pickup {
    public GuidedMissileAmmo(Point2D.Double position) {
        super(position, null, null);
    }

    public void collide(Object object, ListIterator iterator) {

    }
}
