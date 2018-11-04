package Tanks3D.FastMath;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

//A class for fast trigonometric functions
public final class FastMath {
    //The two tables for storing the pre-calculated values of sin and cos
    private static double sinTable[];
    private static double cosTable[];

    //This class is non-instantiable
    private FastMath() {
    }

    //When the object is first called, fill the tables with the cos and sin values of every integer between 0 and 360.
    static {
        sinTable = new double[361];

        for(int i = 0; i < 361; i++)
            sinTable[i] = Math.sin(Math.toRadians(i));

        cosTable = new double[361];

        for(int i = 0; i < 361; i++)
            cosTable[i] = Math.cos(Math.toRadians(i));
    }

    //Restrict the angle between 0 and 360 degrees
    private static double formatAngle(double angle) {
        //If the angle is close to 0, set it to 0.
        if(angle < 0.00000001 && angle > -0.00000001)
            angle = 0;

        angle %= 360;

        if(angle < 0)
            angle += 360;

        return angle;
    }

    //Look up the angle in the sin table.
    public static double sin(double angle) {
        angle = formatAngle(angle);
        double prevVal = sinTable[(int) Math.floor(angle)];
        double nextVal = sinTable[(int) Math.floor(angle) + 1];

        //Use linear interpolation to guess the values in-between two known values
        return ((angle-Math.floor(angle)) * (nextVal-prevVal)) + prevVal;
    }

    //Look up the angle in the cos table.
    public static double cos(double angle) {
        angle = formatAngle(angle);
        double prevVal = cosTable[(int) Math.floor(angle)];
        double nextVal = cosTable[(int) Math.floor(angle) + 1];

        //Use linear interpolation to guess the values in-between two known values
        return ((angle-Math.floor(angle)) * (nextVal-prevVal)) + prevVal;
    }

    public static void add(Point2D.Double point, double x, double y) {
        point.x += x;
        point.y += y;
    }

    public static void add(Point2D.Double point1, Point2D.Double point2) {
        point1.x += point2.x;
        point1.y += point2.y;
    }

    public static void subtract(Point2D.Double point, double x, double y) {
        point.x -= x;
        point.y -= y;
    }

    public static void subtract(Point2D.Double point1, Point2D.Double point2) {
        point1.x -= point2.x;
        point1.y -= point2.y;
    }

    //Rotate a point around a pivot by some angle.
    public static void rotate(Point2D.Double point, Point2D.Double pivot, double angle) {
        subtract(point, pivot);

        double newX = (point.x * cos(angle)) + (point.y * sin(angle));
        double newY = (point.y * cos(angle)) - (point.x * sin(angle));

        point.setLocation(newX + pivot.x, newY + pivot.y);
    }

    //Rotate a line round a pivot by some angle.
    public static void rotate(Line2D.Double line, Point2D.Double pivot, double angle) {
        Point2D.Double point1 = new Point2D.Double(line.x1, line.y1);
        Point2D.Double point2 = new Point2D.Double(line.x2, line.y2);

        rotate(point1, pivot, angle);
        rotate(point2, pivot, angle);

        line.setLine(point1, point2);
    }

    public  static void translate(Line2D.Double line, double xDist, double yDist) {
        line.x1 += xDist;
        line.y1 += yDist;
        line.x2 += xDist;
        line.y2 += yDist;
    }

    //Return the y-coordinate where the line intersects the y-axis. If the line doesn't intersect the y-axis, return -1.
    public static double getYIntercept(Line2D.Double line) {
        //If the points are on opposite sides of the y axis, the wall intersects with the ray.
        if(line.x1 > 0 && line.x2 < 0 || line.x1 < 0 && line.x2 > 0) {
            //Modified the linear equation y = mx + b. b is the y coordinate of the intersection, and x is always 0.
            double b = line.y1 - ((line.y2 - line.y1) / (line.x2 - line.x1) * line.x1);

            //If the intercept is close to 0, return 0.
            if(b < 0.00000001 && b > -0.00000001)
                return 0;

            return b;
        }

        //If the wall doesn't intersect the y axis, return -1.
        return -1;
    }
}