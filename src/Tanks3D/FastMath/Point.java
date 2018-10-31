package Tanks3D.FastMath;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void add(Point point2) {
        this.x += point2.x;
        this.y += point2.y;
    }

    public void subtract(Point point2) {
        this.x -= point2.x;
        this.y -= point2.y;
    }
}
