package Tanks3D.Object.Entity;

import Tanks3D.GameManager;

import java.awt.*;

public class Tank extends Entity {
    private Point position;
    private double angle;
    private Color color;

    public Tank(Point spawnPoint, double defaultAngle, Color tankColor) {
        position = spawnPoint;
        angle = defaultAngle;
        color = tankColor;
    }

    public void update(GameManager game) {

    }

    public void draw(GameManager game) {

    }
}