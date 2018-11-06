package Tanks3D.Object.Wall;

import Tanks3D.Object.GameObject;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public abstract class Wall extends GameObject {
    private Line2D line;
    private double length;
    private int angle;
    private final static double height = 1.5;

    //Constructor that takes a line.
    public Wall(Line2D.Double line) {
        this.line = line;
        init();
    }

    //Return the height of every wall.
    public static double getHeight() {
        return height;
    }

    //Constructor that takes two points.
    public Wall(Point2D.Double point1, Point2D.Double point2) {
        line = new Line2D.Double(point1, point2);
        init();
    }

    //Calculate the length and angle of the wall.
    private void init() {
        //Distance formula.
        length = Math.sqrt(Math.pow(line.getX1() - line.getX2(), 2) + Math.pow(line.getY1() - line.getY2(), 2));
        //Modified equation for polar coordinates.
        angle = (int) Math.toDegrees(Math.acos((line.getX2()-line.getX1())/length));
    }

    public Line2D.Double getLine() { return new Line2D.Double(line.getP1(), line.getP2()); }

    public Point2D.Double getPoint1() {
        return new Point2D.Double(line.getX1(), line.getY1());
    }
    public Point2D.Double getPoint2() {
        return new Point2D.Double(line.getX2(), line.getY2());
    }

    public double getLength() {
        return length;
    }

    public double getAngle() {
        return angle;
    }
}
