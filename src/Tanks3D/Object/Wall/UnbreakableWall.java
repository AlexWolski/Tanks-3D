package Tanks3D.Object.Wall;

import Tanks3D.Utilities.Image;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall {
    private static final String textureFile = "resources/Textures/Brick.png";
    private static BufferedImage defaultTexture;

    //When the game starts, load the default texture once.
    static {
        defaultTexture = Tanks3D.Utilities.Image.load(textureFile);
    }

    //Create a new wall and a texture with the given color.
    public UnbreakableWall(Point2D.Double Point1, Point2D.Double Point2, Color textureColor) {
        //Create the wall.
        super(Point1, Point2);

        //Create a new texture and color it.
        BufferedImage coloredTexture = Image.copy(defaultTexture);
        Tanks3D.Utilities.Image.setHue(coloredTexture, textureColor);

        //Set the texture fo the wall.
        super.setTexture(coloredTexture);
    }
}
