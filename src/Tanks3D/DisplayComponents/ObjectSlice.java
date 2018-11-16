package Tanks3D.DisplayComponents;

import Tanks3D.Object.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

//A 'struct' to contain the data needed to draw a slice of a wall or entity.
class ObjectSlice {
    //The wall that this slice came from.
    final GameObject object;
    //The distance from the camera to the intersection between the ray and the wall.
    final double distToCamera;
    //The image that a slice will be taken out of.
    final BufferedImage image;
    //The color to tint the image.
    final Color imageColor;
    //How far along the wall the ray intersected. Between 0 and 1.
    final double intersectRatio;

    ObjectSlice(GameObject wall, double distToCamera, BufferedImage image, Color imageColor, double intersectRatio) {
        this.object = wall;
        this.distToCamera = distToCamera;
        this.image = image;
        this.imageColor = imageColor;
        this.intersectRatio = intersectRatio;
    }
}