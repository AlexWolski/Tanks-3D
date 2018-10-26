package Tanks3D;

import Tanks3D.Object.Entity.Tank;
import Tanks3D.Object.SpawnPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

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
        myTank = new Tank(position, angle, tankColor);
    }

    public void update() {

    }

    public void draw() {
        gameData.gameLevel.draw(screenCanvas, position, angle);
    }

    public void run() {
        draw();
    }
}