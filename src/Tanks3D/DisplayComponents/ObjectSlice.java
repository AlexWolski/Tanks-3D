package Tanks3D.DisplayComponents;

import Tanks3D.Object.GameObject;

//A 'struct' to contain the data needed to draw a slice of a wall or entity.
public class ObjectSlice {
    //The wall that this slice came from.
    public final GameObject object;
    //The distance from the camera to the intersection between the ray and the wall.
    public final double distToCamera;
    //How far along the wall the ray intersected. Between 0 and 1.
    public final double intersectRatio;

    public ObjectSlice(GameObject wall, double distToCamera, double intersectRatio) {
        this.object = wall;
        this.distToCamera = distToCamera;
        this.intersectRatio = intersectRatio;
    }
}