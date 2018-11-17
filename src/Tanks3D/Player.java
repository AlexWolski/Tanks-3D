package Tanks3D;

import Tanks3D.DisplayComponents.Camera;
import Tanks3D.DisplayComponents.HUD;
import Tanks3D.Object.Entity.Entity;
import Tanks3D.Object.Entity.Tank;
import Tanks3D.Object.SpawnPoint;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Manage the player's tank and screen. This extends 'Runnable' so that the draw function can be threaded.
public class Player implements Runnable {
    //An array that contains all of the objects.
    private ArrayList<Entity> entityList;
    private final Tank myTank;
    //A camera for displaying the game world.
    private final Camera camera;
    //The heads up display for the player.
    private final HUD hud;
    //Booleans to remember what keys are being pressed. When the tank is created, it isn't moving.
    private boolean forwardPressed, backPressed, leftPressed, rightPressed = false;
    //An in that determines if the controls are inverted or not. Either -1 or 1.
    private int invertControls = 1;

    public Player(GameData gameData, BufferedImage canvas, SpawnPoint spawnPoint, Color tankColor, String iconSide) {
        this.entityList = gameData.entityList;

        //Create a new tank given the spawn-point and add it to the entity list.
        myTank = new Tank(spawnPoint.getPosition(), spawnPoint.getAngle(), tankColor);
        //Add the new tank to the list of all entities.
        entityList.add(myTank);

        //Create a new camera given the spawn-point position and angle.
        camera = new Camera(gameData, canvas);

        //Create a new HUD object with the tank's color. 'iconSide' determines which side of the screen the HUD icons are on.
        hud = new HUD(canvas, getColor(), iconSide);
    }

    //Draw the player's screen.
    public void draw() {
        //Draw the walls and entities.
        camera.draw(myTank.getPosition(), myTank.getAngle());

        //Draw the HUD.
        hud.draw(myTank.getMaxHealth(), myTank.getHealth(), myTank.getLives());
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
            myTank.speed += myTank.getMaxSpeed();
            forwardPressed = true;
        }
        else if (!keyPressed && forwardPressed){
            myTank.speed -= myTank.getMaxSpeed();
            forwardPressed = false;
        }
    }

    //If the back key has been pressed or released, update the tank's speed.
    public void back(boolean keyPressed) {
        if(keyPressed && !backPressed) {
            myTank.speed -= myTank.getMaxSpeed();
            backPressed = true;

            //Invert the left and right controls.
            myTank.rotationSpeed *= -1;
            invertControls *= -1;
        }
        else if (!keyPressed && backPressed){
            myTank.speed += myTank.getMaxSpeed();
            backPressed = false;

            //Revert the left and right controls.
            myTank.rotationSpeed *= -1;
            invertControls *= -1;
        }
    }

    //If the left key has been pressed or released, update the tank's rotation.
    public void left(boolean keyPressed) {
        if(keyPressed && !leftPressed) {
            myTank.rotationSpeed -= myTank.getMaxRotationSpeed() * invertControls;
            leftPressed = true;
        }
        else if (!keyPressed && leftPressed){
            myTank.rotationSpeed += myTank.getMaxRotationSpeed() * invertControls;
            leftPressed = false;
        }
    }

    //If the right key has been pressed or released, update the tank's rotation.
    public void right(boolean keyPressed) {
        if(keyPressed && !rightPressed) {
            myTank.rotationSpeed += myTank.getMaxRotationSpeed() * invertControls;
            rightPressed = true;
        }
        else if (!keyPressed && rightPressed){
            myTank.rotationSpeed -= myTank.getMaxRotationSpeed() * invertControls;
            rightPressed = false;
        }
    }

    //If the fire key was pressed, tell the tank to fire.
    public void fire() {
        myTank.fire(entityList);
    }
}