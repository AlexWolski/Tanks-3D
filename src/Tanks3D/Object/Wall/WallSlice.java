package Tanks3D.Object.Wall;

import Tanks3D.FastMath.Point;

//A 'struct' to contain the data needed to draw a wall slice. All of the members are public.
public class WallSlice {
    //The wall that this slice came from.
    public Wall wall;
    //The two points of the wall rotated so that it lies on the y axis.
    public Point point1;
    public Point point2;
    //The angle of the that hit this wall slice.
    public double rayAngle;
    //The distance from the camera to the intersection between the ray and the wall.
    public double distToCamera;

    public WallSlice(Wall wall, Point point1, Point point2, double rayAngle, double distToCamera) {
        this.wall = wall;
        this.point1 = point1;
        this.point2 = point2;
        this.rayAngle = rayAngle;
        this.distToCamera = distToCamera;
    }
}
