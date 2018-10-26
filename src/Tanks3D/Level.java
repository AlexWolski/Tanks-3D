package Tanks3D;

import Tanks3D.Object.SpawnPoint;
import Tanks3D.Object.Wall.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


//remove
import java.util.Random;

//Store the list of immobile objects and print them to a screen.
public class Level {
    private SpawnPoint player1Spawn;
    private SpawnPoint player2Spawn;
    private ArrayList<Wall> wallObjects;

    //remove
    Random rand;

    //Read the data file and translate it into objects. Save the player spawn-points to construct the player objects.
    public Level(String levelFile) {
        //remove
        rand = new Random();
        //Read the data file and create all of the map objects.
        parseDataFile(levelFile);
    }

    //Read the json objects from the file and create them. Walls are stored in the 'wallObjects' ArrayList.
    private void parseDataFile(String levelFile) {
        wallObjects = new ArrayList<>();
        player1Spawn = new SpawnPoint(new Point(0, 0), 0.0, 1);
        player2Spawn = new SpawnPoint(new Point(0, 0), 0.0, 2);
    }

    public SpawnPoint getPlayer1Spawn() {
        return player1Spawn;
    }
    public SpawnPoint getPlayer2Spawn() {
        return player2Spawn;
    }

    //Draw the walls, ceiling, and floors to a buffer.
    public void draw(BufferedImage canvas, Point position, double angle) {
        int rbg = rand.nextInt();

        for (int i = 0; i < canvas.getWidth(); i++)
            for (int j = 0; j < canvas.getHeight(); j++)
                canvas.setRGB(i, j, rbg);
    }
}