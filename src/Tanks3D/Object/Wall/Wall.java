package Tanks3D.Object.Wall;

import Tanks3D.Object.GameObject;
import Tanks3D.Utilities.Image;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject {
    private BufferedImage texture;
    private Line2D.Double line;
    private double length;
    private double angle;
    private boolean visible;
    private final static double height = 20;

    //Constructor that takes two points.
    Wall(Point2D.Double point1, Point2D.Double point2) {
        line = new Line2D.Double(point1, point2);
        //The wall can be seen and has collision.
        visible = true;
        //Distance formula.
        length = Math.sqrt(Math.pow(line.x1 - line.x2, 2) + Math.pow(line.y1 - line.y2, 2));
        //Modified equation for spherical coordinates.
        angle = Math.toDegrees(Math.atan2(line.x2-line.x1, line.y2-line.y1));
    }

    public BufferedImage getTexture() {
        return texture;
    }
    public Line2D.Double getLine() {
        return new Line2D.Double(line.getP1(), line.getP2());
    }
    public Point2D.Double getPoint1() {
        return new Point2D.Double(line.getX1(), line.getY1());
    }
    public Point2D.Double getPoint2() {
        return new Point2D.Double(line.getX2(), line.getY2());
    }
    public double getWidth() {
        return length;
    }
    public double getHeight() {
        return height;
    }
    public double getAngle() {
        return angle;
    }
    public boolean getVisible() { return visible; }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }
    void invisible() { visible = false; }
}
