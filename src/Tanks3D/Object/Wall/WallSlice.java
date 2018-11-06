package Tanks3D.Object.Wall;

import java.awt.geom.Point2D;

//A 'struct' to contain the data needed to draw a wall slice. All of the members are public.
public class WallSlice {
    //The wall that this slice came from.
    public final Wall wall;
    //The two points of the wall rotated so that it lies on the y axis.
    public final Point2D.Double point1;
    public final Point2D.Double point2;
    //The angle of the that hit this wall slice.
    public final double rayAngle;
    //The distance from the camera to the intersection between the ray and the wall.
    public final double distToCamera;

    public WallSlice(Wall wall, Point2D.Double point1, Point2D.Double point2, double rayAngle, double distToCamera) {
        this.wall = wall;
        this.point1 = point1;
        this.point2 = point2;
        this.rayAngle = rayAngle;
        this.distToCamera = distToCamera;
    }
}
