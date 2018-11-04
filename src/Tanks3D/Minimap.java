package Tanks3D;

import Tanks3D.Object.Wall.Wall;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

//Displays the minimap.
public class Minimap implements Runnable{
    private GameData gameData;
    private BufferedImage canvas;
    private double sizeRatio;

    public Minimap(GameData gameData, BufferedImage canvas) {
        this.gameData = gameData;
        this.canvas = canvas;

        if(gameData.gameLevel.getMapWidth() > gameData.gameLevel.getMapHeight()) {
            if (canvas.getWidth() > canvas.getHeight())
                sizeRatio = gameData.gameLevel.getMapWidth() / canvas.getWidth();
            else
                sizeRatio = gameData.gameLevel.getMapWidth() / canvas.getHeight();
        }
        else
        {
            if (canvas.getWidth() > canvas.getHeight())
                sizeRatio = gameData.gameLevel.getMapWidth() / canvas.getHeight();
            else
                sizeRatio = gameData.gameLevel.getMapWidth() / canvas.getHeight();
        }
    }

    //Transform a point from the map dimensions to the minimap dimensions.
    public Point2D.Double mapToMiniMap(Point2D.Double coordinate) {
        return new Point2D.Double((coordinate.x - gameData.gameLevel.mapCenter.x)/sizeRatio + (double) canvas.getWidth()/2,
                                  (coordinate.y - gameData.gameLevel.mapCenter.y)/sizeRatio + (double) canvas.getHeight()/2);
    }

    public void draw() {
        //Draw a black background
        for(int i = 0; i < canvas.getWidth(); i++) {
            for (int j = 0; j < canvas.getHeight(); j++)
                canvas.setRGB(i, j, 0);
        }

        //Create a graphic so that shapes can be drawn to the buffered image.
        Graphics2D graphic = canvas.createGraphics();

        //Draw the walls
        for(Wall wall : gameData.gameLevel.wallObjects) {
            Point2D.Double p1 = mapToMiniMap(wall.getPoint1());
            Point2D.Double p2 = mapToMiniMap(wall.getPoint2());

            graphic.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
        }

        //Delete the graphic
        graphic.dispose();
    }

    public void run() {
        draw();
    }
}