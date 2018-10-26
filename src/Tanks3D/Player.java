package Tanks3D;

import Tanks3D.Object.Entity.Tank;
import Tanks3D.Object.SpawnPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

//Manage the player's tank and screen. This extends 'Runnable' so that the draw function can be threaded.
public class Player implements Runnable {
    private GameData gameData;
    private BufferedImage screenCanvas;
    private Tank myTank;
    private Point position;
    private double angle;

    public Player(GameData data, BufferedImage canvas, SpawnPoint spawnPoint, Color tankColor, Dimension panelSize) {
        gameData = data;
        screenCanvas = canvas;
        position = spawnPoint.getPosition();
        angle = spawnPoint.getAngle();
        //Create a new tank given the spawn-point and add it to the entity list.
        myTank = new Tank(position, angle, tankColor);
        gameData.entityList.add(myTank);
    }

    public void update() {

    }

    //Draw the player's screen.
    public void draw() {
        //Pass the buffer to 'gameLevel' so that it can draw the walls, floor, and ceiling.
        gameData.gameLevel.draw(screenCanvas, position, angle);
    }

    //Draw the player's screen in a thread.
    public void run() {
        draw();
    }
}