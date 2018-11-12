package Tanks3D.Object.Entity.Round;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.ListIterator;

public class GuidedMissile extends Round {
    public GuidedMissile(Point2D.Double position, double angle, int speed) {
        super(position, angle, speed);
    }

    public void collide(Object object, ListIterator iterator) {

    }
}
