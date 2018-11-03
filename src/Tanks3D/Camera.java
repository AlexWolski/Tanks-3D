package Tanks3D;

import java.awt.geom.Point2D;

public class Camera {
    protected Point2D.Double position;
    protected double angle;
    protected double FOV;

    public Camera(Point2D.Double position, double angle, int FOV) {
        this.position = position;
        this.angle = angle;
        this.FOV = FOV;
    }
}
