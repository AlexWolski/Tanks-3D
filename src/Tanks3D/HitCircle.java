package Tanks3D;

import java.awt.geom.Point2D;

public class HitCircle {
    private Point2D.Double position;
    private int radius;

    public HitCircle(Point2D.Double position, int radius) {
        this.position = position;
        this.radius = radius;
    }

    public Point2D.Double getPosition() {
        return position;
    }
    public int getRadius() {
        return radius;
    }


}
