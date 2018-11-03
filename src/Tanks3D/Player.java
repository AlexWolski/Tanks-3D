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
    //Booleans to remember what keys are being pressed.
    private boolean forwardPressed, backPressed, leftPressed, rightPressed;
    //The field of view of the camera.
    private final int cameraFOV = 90;
    //How many units the tank can move per second.
    private final double tankSpeed = 2;
    //How many degrees the tank can rotate per second.
    private final double rotationSpeed = 200;

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
        //When the player is created, no keys are pressed.
        forwardPressed = false;
        backPressed = false;
        leftPressed = false;
        rightPressed = false;
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

    //If the forward key has been pressed or released, update the tank's speed.
    public void forward(boolean keyPressed) {
        if(keyPressed && !forwardPressed) {
            myTank.speed += tankSpeed;
            forwardPressed = true;
        }
        else if (!keyPressed && forwardPressed){
            myTank.speed -= tankSpeed;
            forwardPressed = false;
        }
    }

    //If the back key has been pressed or released, update the tank's speed.
    public void back(boolean keyPressed) {
        if(keyPressed && !backPressed) {
            myTank.speed -= tankSpeed;
            backPressed = true;
        }
        else if (!keyPressed && backPressed){
            myTank.speed += tankSpeed;
            backPressed = false;
        }
    }

    //If the left key has been pressed or released, update the tank's rotation.
    public void left(boolean keyPressed) {
        if(keyPressed && !leftPressed) {
            myTank.rotationSpeed += rotationSpeed;
            leftPressed = true;
        }
        else if (!keyPressed && leftPressed){
            myTank.rotationSpeed -= rotationSpeed;
            leftPressed = false;
        }
    }

    //If the right key has been pressed or released, update the tank's rotation.
    public void right(boolean keyPressed) {
        if(keyPressed && !rightPressed) {
            myTank.rotationSpeed -= rotationSpeed;
            rightPressed = true;
        }
        else if (!keyPressed && rightPressed){
            myTank.rotationSpeed += rotationSpeed;
            rightPressed = false;
        }
    }
}