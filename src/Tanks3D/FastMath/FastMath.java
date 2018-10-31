package Tanks3D.FastMath;

//A class for fast trigonometric functions
public final class FastMath {
    //The two tables for storing the pre-calculated values of sin and cos
    private static double sinTable[];
    private static double cosTable[];

    //Trig is non-instantiable
    private FastMath() {
    }

    //Fill the tables with the cos and sin of every integer between 0 and 360. The values are rounded to 4 decimal places
    public static void init() {
        sinTable = new double[361];

        for(int i = 0; i < 361; i++)
            sinTable[i] = Math.sin(Math.toRadians(i));

        cosTable = new double[361];

        for(int i = 0; i < 361; i++)
            cosTable[i] = Math.cos(Math.toRadians(i));
    }

    //Restrict the angle between 0 and 360 degrees
    private static double formatAngle(double angle) {
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

    //Rotate a point around a pivot by some angle
    public static void rotatePoint(Point point, Point pivot, double angle) {
        point.subtract(pivot);

        double newX = (point.x * cos(angle)) + (point.y * sin(angle));
        double newY = (point.y * cos(angle)) - (point.x * sin(angle));

        point.x = newX + pivot.x;
        point.y = newY + pivot.y;
    }
}