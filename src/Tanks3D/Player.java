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
    //The tank object that the player controlls.
    private Tank myTank;
    //An array containing the wall slices that need to be drawn.
    private WallSlice[] wallBuffer;
    //A camera for displaying the game world.
    private Camera camera;

    public Player(GameData gameData, BufferedImage canvas, SpawnPoint spawnPoint, Color tankColor) {
        this.gameData = gameData;
        this.canvas = canvas;
        //Initialize the array of wall slices.
        wallBuffer = new WallSlice[canvas.getWidth()];
        //Create a new camera given the spawn-point position and angle.
        camera = new Camera(spawnPoint.getPosition(), spawnPoint.getAngle(), 90);
        //Create a new tank given the spawn-point and add it to the entity list.
        myTank = new Tank(camera.position, camera.angle, tankColor);
        //Add the new tank to the list of all entities.
        gameData.entityList.add(myTank);
    }

    public void update() {

    }

    //Draw the player's screen.
    public void draw() {
        //remove
        camera.angle += 2;

        //Pass the wall buffer to 'gameLevel' so it can calculate which parts of which walls to draw.
        gameData.gameLevel.calculateBuffer(wallBuffer, camera);

        //Draw all of the wall slices.
        gameData.gameLevel.draw(wallBuffer, canvas);
    }

    //Draw the player's screen in a thread.
    public void run() {
        draw();
    }
}