package Tanks3D;

import Tanks3D.FastMath.Point;

public class Camera {
    protected Point position;
    protected double angle;
    protected double FOV;

    public Camera(Point position, double angle, int FOV) {
        this.position = position;
        this.angle = angle;
        this.FOV = FOV;
    }
}
