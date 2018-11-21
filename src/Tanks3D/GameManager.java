package Tanks3D;

import Tanks3D.DisplayComponents.Minimap;
import Tanks3D.DisplayComponents.SplitWindow;
import Tanks3D.InputManager.InputManager;
import Tanks3D.Object.Entity.Entity;
import Tanks3D.Object.Entity.Round.Round;

import java.awt.*;
import java.util.ArrayList;

//Controls the entire game. The program enters from this class, which is a singleton class.
public class GameManager {
    //Constant settings.
    private final static String levelFile = "Underground Arena.txt";
    //Size of the display. The extra 4px on the width is for the screen divider.
    private final static int defaultWidth = 1004;
    private final static int defaultHeight = 500;
    //How much space the title bar takes.
    private final static int titleBarHeight = 30;

    //The window that the game runs in and controls drawing of the screen.
    private final SplitWindow gameWindow;
    //'struct' that holds all of the data for the game world.
    private final GameData gameData;

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
        gameWindow = new SplitWindow(gameData, "Tanks 3D", new Dimension(defaultWidth, defaultHeight), titleBarHeight);

        //Initialize the 'Player' objects. Get the initial positions for both players and indicate which side of the screen the HUD icons are on.
        gameData.player1 = new Player(gameData, gameWindow.getScreen1Buffer(), gameData.gameLevel.getPlayer1Spawn(), Color.cyan, "left");
        gameData.player2 = new Player(gameData, gameWindow.getScreen2Buffer(), gameData.gameLevel.getPlayer2Spawn(), Color.green, "right");
        gameData.minimap = new Minimap(gameData, gameWindow.getMinimapBuffer());

        //Link the controls for each player.
        InputManager.init(gameWindow.getPanel(), gameData.player1, gameData.player2);
        //Initialize the round object.
        Round.init(gameData.entityList);

        //Set the initial time.
        timeOfLastFrame = System.currentTimeMillis();

        //Remove
        //Set the timer for the FPS count.
        time = timeOfLastFrame;
    }

    private void reset() {
        //Reset the level.
        gameData.gameLevel = new Level(levelFile);
        //Reset the entity list.
        gameData.entityList = new ArrayList<>();

        //Reset the players.
        gameData.player1 = new Player(gameData, gameWindow.getScreen1Buffer(), gameData.gameLevel.getPlayer1Spawn(), Color.cyan, "left");
        gameData.player2 = new Player(gameData, gameWindow.getScreen2Buffer(), gameData.gameLevel.getPlayer2Spawn(), Color.green, "right");
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

        //Draw both players' screens and the minimap.
        gameWindow.draw();

        //remove
        //Increment the frame count for the FPS.
        frames++;
    }
}