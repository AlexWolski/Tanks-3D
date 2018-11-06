package Tanks3D;

import Tanks3D.Object.Entity.Tank;
import Tanks3D.Object.SpawnPoint;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

//Manage the player's tank and screen. This extends 'Runnable' so that the draw function can be threaded.
public class Player implements Runnable {
    //The tank object that the player controls.
    private final Tank myTank;
    //A camera for displaying the game world.
    private final Camera camera;
    //Booleans to remember what keys are being pressed.
    private boolean forwardPressed, backPressed, leftPressed, rightPressed;
    //How many units the tank can move per second.
    private final static double tankSpeed = 2;
    //How many degrees the tank can rotate per second.
    private double rotationSpeed = 200;

    public Player(GameData gameData, BufferedImage canvas, SpawnPoint spawnPoint, Color tankColor) {
        //Create a new tank given the spawn-point and add it to the entity list.
        myTank = new Tank(spawnPoint.getPosition(), spawnPoint.getAngle(), tankColor);
        //Create a new camera given the spawn-point position and angle.
        camera = new Camera(gameData, canvas, myTank.getPosition(), myTank.getAngle());
        //Add the new tank to the list of all entities.
        gameData.entityList.add(myTank);
        //When the player is created, no keys are pressed.
        forwardPressed = false;
        backPressed = false;
        leftPressed = false;
        rightPressed = false;
    }

    //Draw the player's screen.
    public void draw() {
        //Move the camera to the tank's position.
        camera.angle = myTank.getAngle();
        //Draw the walls and entities.
        camera.draw();
        //Draw the HUD.
    }

    //Draw the player's screen in a thread.
    public void run() {
        draw();
    }

    public Color getColor() {
        return myTank.getColor();
    }
    public Point2D.Double getPosition() {
        return myTank.getPosition();
    }
    public double getAngle() {
        return myTank.getAngle();
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

            //Invert the left and right controls.
            myTank.rotationSpeed *= -1;
            rotationSpeed *= -1;
        }
        else if (!keyPressed && backPressed){
            myTank.speed += tankSpeed;
            backPressed = false;

            //Revert the left and right controls.
            myTank.rotationSpeed *= -1;
            rotationSpeed *= -1;
        }
    }

    //If the left key has been pressed or released, update the tank's rotation.
    public void left(boolean keyPressed) {
        if(keyPressed && !leftPressed) {
            myTank.rotationSpeed -= rotationSpeed;
            leftPressed = true;
        }
        else if (!keyPressed && leftPressed){
            myTank.rotationSpeed += rotationSpeed;
            leftPressed = false;
        }
    }

    //If the right key has been pressed or released, update the tank's rotation.
    public void right(boolean keyPressed) {
        if(keyPressed && !rightPressed) {
            myTank.rotationSpeed += rotationSpeed;
            rightPressed = true;
        }
        else if (!keyPressed && rightPressed){
            myTank.rotationSpeed -= rotationSpeed;
            rightPressed = false;
        }
    }
}