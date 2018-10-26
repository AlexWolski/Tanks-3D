package Tanks3D.Object;

import java.awt.*;

public class SpawnPoint {
    private Point position;
    private double angle;
    private int player;

    public SpawnPoint(Point spawnPosition, double playerAngle, int playerNumber) {
        position = spawnPosition;
        angle = playerAngle;
        player = playerNumber;
    }

    public Point getPosition() {
        return position;
    }

    public double getAngle() {
        return angle;
    }
}