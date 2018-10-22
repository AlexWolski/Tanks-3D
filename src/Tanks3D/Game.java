package Tanks3D;

import Tanks3D.GameComponents.Minimap;
import Tanks3D.GameComponents.Player;

import javax.swing.*;

//Controls the entire game. The program enters from this 'Game' class, which is a singleton class.
public class Game {
    private JFrame gameDisplay;
    //Store the time that the last frame was drawn so that 'deltaTime' can be calculated.
    private long timeOfLastFrame;
    //The time that elapsed since the last frame was drawn. This is used to keep the game running at a certain speed independent of FPS.
    private long deltaTime;

    //The 'Level' object stores all of the data for the game world and draws it to the screen
    protected Level indoorArena;
    //The two 'Player' objects that will update each players' tank and side of the screen
    protected Player player1;
    protected Player player2;
    //The 'Minimap' object that will show a simplified overview of the map on the top of the screen
    protected Minimap minimap;

    //Create a single 'Game' object and keep updating the game.
    public static void main(String[] args) {
        Game Tanks3d = new Game();

        while(true) {
            try {
                Tanks3d.update();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //Initialize variables. The constructor is public because other classes need to extend it to access variables.
    //The singleton class rule is upheld because only one instance is created by main.
    public Game() {
        gameDisplay = new JFrame("Tanks 3D");
        indoorArena = new Level("Indoor Arena.txt");
        player1 = new Player();
        player2 = new Player();
        minimap = new Minimap();

        timeOfLastFrame = System.currentTimeMillis();
    }

    //Get the current time, calculate the time elapsed since the last frame, and store it in 'deltaTime;.
    private void updateDeltaTime() {
        //currentTimeMillis() is used instead of nanoTime() because its less expensive and more precise. And its max speed of 1000 FPS is more than enough.
        long currentTime = System.currentTimeMillis();
        deltaTime = currentTime - timeOfLastFrame;
        //Save the current time so that 'deltaTime' can be calculated next frame.
        timeOfLastFrame = currentTime;
    }

    private void update() {
        //Update the time since the last frame
        updateDeltaTime();

        //Update each tank and their effect on the world first. Then draw both players' screens.
        player1.update();
        player2.update();
        player1.draw();
        player2.draw();

        minimap.draw();
    }
}