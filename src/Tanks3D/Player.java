package Tanks3D;

import Tanks3D.Object.Entity.Tank;
import Tanks3D.Object.SpawnPoint;
import Tanks3D.Object.Wall.WallSlice;

import java.awt.*;
import java.awt.image.BufferedImage;

//Manage the player's tank and screen. This extends 'Runnable' so that the draw function can be threaded.
public class Player implements Runnable {
    //A struct that contains the necessary data about the game.
    private GameData gameData;
    //An buffer that get written to, then displayed on the screen.
    private BufferedImage canvas;
    //The tank object that the player controls.
    private Tank myTank;
    //An array containing the wall slices that need to be drawn.
    private WallSlice[] wallBuffer;
    //A camera for displaying the game world.
    private Camera camera;
    //The field of view of the camera.
    private final int cameraFOV = 90;
    //How fast the tank can move.
    private final double tankSpeed = 0.001;
    //How fast the tank can turn.
    private final double rotationSpeed = 0.1;

    public Player(GameData gameData, BufferedImage canvas, SpawnPoint spawnPoint, Color tankColor) {
        this.gameData = gameData;
        this.canvas = canvas;
        //Initialize the array of wall slices.
        wallBuffer = new WallSlice[canvas.getWidth()];
        //Create a new tank given the spawn-point and add it to the entity list.
        myTank = new Tank(spawnPoint.getPosition(), spawnPoint.getAngle(), tankColor);
        //Create a new camera given the spawn-point position and angle.
        camera = new Camera(myTank.getPosition(), myTank.getAngle(), cameraFOV);
        //Add the new tank to the list of all entities.
        gameData.entityList.add(myTank);
    }

    //Set the angle of the camera to the angle of the tank. The camera has a reference to the tank's position.
    public void update() {
        camera.angle = myTank.getAngle();
    }

    //Draw the player's screen.
    public void draw() {
        //Pass the wall buffer to 'gameLevel' so it can calculate which parts of which walls to draw.
        gameData.gameLevel.calculateBuffer(wallBuffer, camera);

        //Draw all of the wall slices.
        gameData.gameLevel.draw(wallBuffer, canvas);
    }

    //Draw the player's screen in a thread.
    public void run() {
        draw();
    }

    public void forward(boolean keyPressed) {
        if(keyPressed)
            myTank.setSpeed(tankSpeed);
        else
            myTank.setSpeed(0);
    }

    public void back(boolean keyPressed) {
        if(keyPressed)
            myTank.setSpeed(-tankSpeed);
        else
            myTank.setSpeed(0);
    }

    public void left(boolean keyPressed) {
        if(keyPressed)
            myTank.setRotationSpeed(rotationSpeed);
        else
            myTank.setRotationSpeed(0);
    }

    public void right(boolean keyPressed) {
        if(keyPressed)
            myTank.setRotationSpeed(-rotationSpeed);
        else
            myTank.setRotationSpeed(0);
    }
}