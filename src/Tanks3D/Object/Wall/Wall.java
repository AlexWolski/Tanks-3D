package Tanks3D.Object.Wall;

import Tanks3D.FastMath.Point;
import Tanks3D.Object.GameObject;

public class Wall extends GameObject {
    private Point point1;
    private Point point2;
    private double height;
    private double length;
    private int angle;

    public Wall(Point point1, Point point2, double height) {
        this.point1 = point1;
        this.point2 = point2;
        this.height = height;
        //Distance formula
        length = Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
        //Modified equation for polar coordinates
        angle = (int) Math.toDegrees(Math.acos((point2.x-point1.x)/length));
    }

    public Point getPoint1() {
        return new Point(point1.x, point1.y);
    }

    public Point getPoint2() {
        return new Point(point2.x, point2.y);
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public double getAngle() {
        return angle;
    }
}
