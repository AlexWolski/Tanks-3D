import Tanks3D.FastMath.FastMath;
import Tanks3D.Object.Wall.Wall;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import Tanks3D.FastMath.Point;


public class testWall {
    @Test
    public void testConstructor() {
        Wall wall1 = new Wall(new Point(0, 0), new Point(-2, -2), 5);

        assertEquals( wall1.getAngle(), 45);
        assertEquals(wall1.getLength(), 2.828);
    }

    @Test
    public void testMath() {
        FastMath.init();

        double test1 = FastMath.cos(20);
        test1 *= 2;

        System.out.println("test");
    }

    @Test
    public void testRotation() {
        FastMath.init();

        Point p1 = new Point(0, 2);
        Point pivot = new Point(0, 0);

        FastMath.rotatePoint(p1, pivot, 20);

        System.out.println("test");
    }
}
