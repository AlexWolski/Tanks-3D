package Tanks3D.Object.Entity.Pickup;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.ListIterator;

public class GuidedMissileAmmo extends Pickup {
    public GuidedMissileAmmo(Point2D.Double position) {
        super(position);
    }

    public void collide(Object object, ListIterator iterator) {

    }
}
