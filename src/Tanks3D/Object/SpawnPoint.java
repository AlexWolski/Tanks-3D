package Tanks3D.Object;

import Tanks3D.FastMath.Point;

public class SpawnPoint {
    private Point spawnPoint;
    private int spawnAngle;
    private int player;

    public SpawnPoint(Point spawnPoint, int spawnAngle, int player) {
        this.spawnPoint = spawnPoint;
        this.spawnAngle = spawnAngle;
        this.player = player;
    }

    public Point getPosition() {
        return spawnPoint;
    }

    public int getAngle() {
        return spawnAngle;
    }
}