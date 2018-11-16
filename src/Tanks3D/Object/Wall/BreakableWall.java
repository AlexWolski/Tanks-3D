package Tanks3D.Object.Wall;

import java.awt.*;
import java.awt.geom.Point2D;

public class BreakableWall extends Wall {
    public BreakableWall(Point2D.Double Point1, Point2D.Double Point2, String textureFile, Color textureColor) {
        super(Point1, Point2, textureFile, textureColor);
    }

    public void breakWall() {
        super.invisible();
    }
}
