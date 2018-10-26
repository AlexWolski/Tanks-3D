package Tanks3D;

import Tanks3D.Object.Entity.Tank;
import Tanks3D.Object.SpawnPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends JPanel {
    private GameManager tankGame;
    private BufferedImage canvas;
    private Tank myTank;
    private Point position;
    private double angle;

    public Player(GameManager game, SpawnPoint spawnPoint, Color tankColor, Dimension panelSize) {
        tankGame = game;
        setPreferredSize(panelSize);
        canvas = new BufferedImage(panelSize.width, panelSize.height, BufferedImage.TYPE_INT_RGB);

        position = spawnPoint.getPosition();
        angle = spawnPoint.getAngle();
        myTank = new Tank(position, angle, tankColor);
    }

    public void update() {
        tankGame.undergroundArena.draw(canvas);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage temp = new BufferedImage(canvas.getColorModel(), canvas.copyData(null), false, null);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(temp, null, null);
    }
}