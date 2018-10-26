package Tanks3D;

import Tanks3D.Object.SpawnPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


//remove
import java.util.Random;

public class Level {
    private SpawnPoint player1Spawn;
    private SpawnPoint player2Spawn;
    private ArrayList<Object> mapObjects;

    //remove
    Random rand;

    //Read the data file and translate it into objects. Save the player spawn-points to construct the player objects.
    public Level(String levelFile) {
        //remove
        rand = new Random();

        parseDataFile(levelFile);
        player1Spawn = new SpawnPoint(new Point(0, 0), 0.0, 1);
        player2Spawn = new SpawnPoint(new Point(0, 0), 0.0, 2);
    }

    //Read the json objects from the file and store the objects in the 'mapObjects' ArrayList.
    private void parseDataFile(String levelFile) {
        mapObjects = new ArrayList<>();
    }

    public SpawnPoint getPlayer1Spawn() {
        return player1Spawn;
    }
    public SpawnPoint getPlayer2Spawn() {
        return player2Spawn;
    }

    public void draw(BufferedImage screen) {
        int rbg = rand.nextInt();

        for(int loops = 0; loops < 4; loops++)
            for (int i = 0; i < screen.getWidth(); i++)
                for (int j = 0; j < screen.getHeight(); j++)
                    screen.setRGB(i, j, rbg);
    }
}