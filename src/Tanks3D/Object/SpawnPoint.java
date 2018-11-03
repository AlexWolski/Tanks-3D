package Tanks3D.Object;

import java.awt.geom.Point2D;

public class SpawnPoint {
    private Point2D.Double spawnPoint;
    private int spawnAngle;
    private int player;

    public SpawnPoint(Point2D.Double spawnPoint, int spawnAngle, int player) {
        this.spawnPoint = spawnPoint;
        this.spawnAngle = spawnAngle;
        this.player = player;
    }

    public Point2D.Double getPosition() {
        return spawnPoint;
    }

    public int getAngle() {
        return spawnAngle;
    }
}