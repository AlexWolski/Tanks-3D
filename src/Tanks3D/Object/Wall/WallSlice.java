package Tanks3D.Object.Wall;

//A 'struct' to contain the data needed to draw a wall slice. All of the members are public.
public class WallSlice {
    //The wall that this slice came from.
    public final Wall wall;
    //The distance from the camera to the intersection between the ray and the wall.
    public final double distToCamera;
    //How far along the wall the ray intersected. Between 0 and 1.
    public final double intersectRatio;

    public WallSlice(Wall wall, double distToCamera, double intersectRatio) {
        this.wall = wall;
        this.distToCamera = distToCamera;
        this.intersectRatio = intersectRatio;
    }
}
