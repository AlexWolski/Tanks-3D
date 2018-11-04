package Tanks3D;

import Tanks3D.DisplayComponents.SplitWindow;
import Tanks3D.FastMath.FastMath;
import Tanks3D.InputManager.InputManager;
import Tanks3D.Object.Entity.Entity;

import java.awt.*;
import java.util.ArrayList;

//Controls the entire game. The program enters from this class, which is a singleton class.
public class GameManager {
    //Constant settings.
    private final String levelFile = "Underground Arena.txt";
    //The extra 30px is for the title bar.
    private final int defaultWidth = 1034;
    private final int defaultHeight = 500;

    //The window that the game runs in and controls drawing of the screen.
    private SplitWindow gameWindow;
    //'struct' that holds all of the data for the game world.
    private GameData gameData;
    //The object that manages the keyboard inputs.
    private InputManager inputManager;

    //Store the time that the last frame was drawn so that 'deltaTime' can be calculated.
    private long timeOfLastFrame;
    //Store the current time.
    long currentTime;
    //The time that elapsed since the last frame was drawn. This is used to run the game at a speed independent of FPS.
    private long deltaTime;

    //Remove
    //Variable to count the number of frames run.
    private int frames;
    //Timer for fps.
    private long time;

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
        gameData = new GameData();
        //Load the level from a text file.
        gameData.gameLevel = new Level(levelFile);
        //Create the entity list.
        gameData.entityList = new ArrayList<>();

        //Create and configure the JFrame. This JFrame will have three panels: the two players screens and a minimap.
        gameWindow = new SplitWindow(gameData, "Tanks 3D", new Dimension(defaultWidth, defaultHeight));

        //Initialize the 'Player' objects. Get the initial positions for both players and pass it to their constructors.
        gameData.player1 = new Player(gameData, gameWindow.getScreen1Buffer(), gameData.gameLevel.getPlayer1Spawn(), Color.blue);
        gameData.player2 = new Player(gameData, gameWindow.getScreen2Buffer(), gameData.gameLevel.getPlayer2Spawn(), Color.green);
        gameData.minimap = new Minimap(gameData, gameWindow.getMinimapBuffer());

        //Link the controls for each player.
        inputManager = new InputManager(gameWindow.getPanel(), gameData.player1, gameData.player2);

        //Set the initial time.
        timeOfLastFrame = System.currentTimeMillis();

        //Remove
        //Set the timer for the FPS count.
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

        //Remove
        //Print the FPS every second.
        if(currentTime - time > 1000)
        {
            System.out.println(frames);
            time = System.currentTimeMillis();
            frames = 0;
        }

        //Update the positions of all of the entities.
        for(Entity entity : gameData.entityList)
            entity.update(gameData, deltaTime);

        //Update the camera for both players.
        gameData.player1.update();
        gameData.player2.update();

        //Draw both players' screen and the minimap.
        gameWindow.draw();

        //remove
        //Increment the frame count for the FPS.
        frames++;
    }
}