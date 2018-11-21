package Tanks3D.Object.Wall;

import Tanks3D.Utilities.Image;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {
    private static BufferedImage texture;

    //When the game starts, load the default texture once.
    static {
        texture = Image.load("resources/Textures/CrackedBrick.png");
    }

    //Create a new wall and a texture with the given color.
    public BreakableWall(Point2D.Double Point1, Point2D.Double Point2, Color textureColor) {
        super(Point1, Point2, texture, textureColor);
    }

    public void breakWall() {
        super.invisible();
    }
    public double getHeight() { return defaultWallHeight(); }
}
