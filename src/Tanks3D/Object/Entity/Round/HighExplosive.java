package Tanks3D.Object.Entity.Round;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.ListIterator;

public class HighExplosive extends Round {
    public HighExplosive(Point2D.Double position, double angle, int speed) {
        super(position, angle, speed);
    }

    public void collide(Object object, ListIterator iterator) {

    }
}
