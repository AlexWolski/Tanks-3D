package Tanks3D;

import Tanks3D.Object.SpawnPoint;
import Tanks3D.Object.Wall.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

//Load the map from a file and store it.
public class Level {
    public ArrayList<Wall> wallObjects;
    public Point.Double mapCenter;
    private double mapWidth;
    private double mapHeight;
    private SpawnPoint player1Spawn;
    private SpawnPoint player2Spawn;
    private int floorColor;
    private int ceilColor;

    //Read the data file and translate it into objects. Save the player spawn-points to construct the player objects.
    public Level(String levelFile) {
        //Read the data file and create all of the map objects.
        parseDataFile(levelFile);
    }

    //Read the json objects from the file and create them. Walls are stored in the 'wallObjects' ArrayList.
    private void parseDataFile(String levelFile) {
        //remove
        wallObjects = new ArrayList<>();
        player1Spawn = new SpawnPoint(new Point2D.Double(-1, -2), 0, 1);
        player2Spawn = new SpawnPoint(new Point2D.Double(1, -2), 0, 2);

        floorColor = new Color(0x803700).getRGB();
        ceilColor = new Color(0).getRGB();

        wallObjects.add(new UnbreakableWall(new Point2D.Double(3, 3), new Point2D.Double(-3, 3)));
        wallObjects.add(new UnbreakableWall(new Point2D.Double(-3, 3), new Point2D.Double(-3, -3)));
        wallObjects.add(new UnbreakableWall(new Point2D.Double(-3, -3), new Point2D.Double(3, -3)));
        wallObjects.add(new UnbreakableWall(new Point2D.Double(3, -3), new Point2D.Double(3, 3)));

        wallObjects.add(new UnbreakableWall(new Point2D.Double(0, 3), new Point2D.Double(0, 0)));
        wallObjects.add(new UnbreakableWall(new Point2D.Double(0, 0), new Point2D.Double(3, 0)));

        mapCenter = new Point.Double(0, 0);
        mapWidth = 6;
        mapHeight = 6;
    }

    public double getMapWidth() { return mapWidth; }
    public double getMapHeight() { return mapHeight; }
    public int getFloorColor() {
        return floorColor;
    }
    public int getCeilColor() {
        return ceilColor;
    }

    public SpawnPoint getPlayer1Spawn() {
        return player1Spawn;
    }
    public SpawnPoint getPlayer2Spawn() {
        return player2Spawn;
    }
}