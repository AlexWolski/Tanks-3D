package Tanks3D;

import Tanks3D.Object.Entity.Entity;

import java.awt.*;
import java.util.ArrayList;

//Controls the entire game. The program enters from this 'Game' class, which is a singleton class.
public class GameManager {
    //Constant settings
    private final String levelFile = "Underground Arena.txt";
    private final int defaultWidth = 1000;
    private final int defaultHeight = 500;

    //The window that the game runs in.
    private SplitScreenWindow gameWindow;

    //The two objects that controls player input and displays their side of the screen.
    private Player player1;
    private Player player2;
    //The object that controls the minimap
    private Minimap minimap;
    //Store the time that the last frame was drawn so that 'deltaTime' can be calculated.
    private long timeOfLastFrame;
    //Store the current time.
    long currentTime;
    //The time that elapsed since the last frame was drawn. This is used to run the game at a speed independent of FPS.
    private long deltaTime;

    //Remove
    //Variable to count the number of frames run
    private int frames;
    //Timer for fps
    private long time;

    //The 'Level' object stores all of the data for the game world and draws it to the screen.
    public Level undergroundArena;
    //A list of entities in the game. The first index is always player 1 and the second index is always player 2.
    public ArrayList<Entity> entityList;

    //Create a single 'Game' object and keep updating the game.
    public static void main(String[] args) {
        GameManager Tanks3d = new GameManager();

        while(true) {
            try {
                Tanks3d.update();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //Initialize variables. The constructor is private to prevent any classes extending it and creating a new instance.
    private GameManager() {
        //Instantiate local objects.
        //Load the level from a text file.
        undergroundArena = new Level(levelFile);
        //Create the entity list and populate it with the player objects.
        entityList = new ArrayList<>();

        //Initialize the 'Player' objects. Get the initial positions for both players and pass it to their constructors.
        Dimension screenSize = new Dimension(defaultWidth/2, defaultHeight);
        player1 = new Player(this, undergroundArena.getPlayer1Spawn(), Color.blue, screenSize);
        player2 = new Player(this, undergroundArena.getPlayer2Spawn(), Color.green, screenSize);

        minimap = new Minimap(this, new Dimension(defaultWidth/4, defaultHeight/4));

        //Create and configure the JFrame. This JFrame will have three panels: the two players screens and a minimap.
        gameWindow = new SplitScreenWindow("Tanks 3D", new Dimension(defaultWidth, defaultHeight), player1, player2, minimap);

        //Set the initial time.
        timeOfLastFrame = System.currentTimeMillis();

        //Remove
        time = timeOfLastFrame;
    }

    //Get the current time, calculate the time elapsed since the last frame, and store it in 'deltaTime;.
    private void updateDeltaTime() {
        //currentTimeMillis() is used instead of nanoTime() because its less expensive and more precise.
        currentTime = System.currentTimeMillis();
        deltaTime = currentTime - timeOfLastFrame;

        //If the game speed drops below 5 FPS, restrict deltaTime to prevent collision issues. This will slow down the game-play.
        if(deltaTime > 200)
            deltaTime = 200;

        //If the loop runs more than 1000 FPS, limit the game speed to 1000 updates per second. This is the fastest currentTimeMillis() can run.
        while (deltaTime == 0) {
            currentTime = System.currentTimeMillis();
            deltaTime = currentTime - timeOfLastFrame;
        }

        //Save the current time so that 'deltaTime' can be calculated next frame.
        timeOfLastFrame = currentTime;
    }

    private void update() {
        //Update the time since the last frame.
        updateDeltaTime();

        frames++;

        if(currentTime - time > 1000)
        {
            System.out.println(frames);
            time = System.currentTimeMillis();
            frames = 0;
        }

        //Update all of the entities in the game
        for(Entity entity : entityList)
            entity.update(this);

        player1.update();
        player2.update();
        minimap.update();

        //Draw both players' screen
        player1.repaint();
        player2.repaint();
        //Draw the minimap
        minimap.repaint();
    }
}