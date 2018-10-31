package Tanks3D.Object.Entity;

import Tanks3D.GameData;

import java.awt.*;
import Tanks3D.FastMath.Point;
import java.awt.image.BufferedImage;

public class Tank extends Entity {
    private Point position;
    private double angle;
    private Color color;

    public Tank(Point position, double angle, Color color) {
        this.position = position;
        this.angle = angle;
        this.color = color;
    }

    public void update(GameData data) {

    }

    public void draw(BufferedImage canvas) {

    }
}