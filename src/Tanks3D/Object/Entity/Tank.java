package Tanks3D.Object.Entity;

import Tanks3D.GameData;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tank extends Entity {
    private Point position;
    private double angle;
    private Color color;

    public Tank(Point spawnPoint, double defaultAngle, Color tankColor) {
        position = spawnPoint;
        angle = defaultAngle;
        color = tankColor;
    }

    public void update(GameData data) {

    }

    public void draw(BufferedImage canvas) {

    }
}