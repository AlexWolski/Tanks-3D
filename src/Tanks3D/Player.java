package Tanks3D;

import Tanks3D.Object.Entity.Tank;
import Tanks3D.Object.SpawnPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {
    private GameData gameData;
    private BufferedImage canvas;
    private Tank myTank;
    private Point position;
    private double angle;

    public Player(GameData data, SpawnPoint spawnPoint, Color tankColor, Dimension panelSize) {
        gameData = data;
        canvas = new BufferedImage(panelSize.width, panelSize.height, BufferedImage.TYPE_INT_RGB);

        position = spawnPoint.getPosition();
        angle = spawnPoint.getAngle();
        myTank = new Tank(position, angle, tankColor);
    }

    public void update() {

    }

    public void draw(BufferedImage canvas) {
        gameData.gameLevel.draw(canvas, position, angle);
    }
}