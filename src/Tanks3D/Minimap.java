package Tanks3D;

import Tanks3D.Object.Wall.Wall;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//Displays the minimap.
public class Minimap implements Runnable{
    private GameData gameData;
    private BufferedImage canvas;
    private double sizeRatio;
    private int backgroundColor;
    private BufferedImage player1Icon, player2Icon;

    public Minimap(GameData gameData, BufferedImage canvas) {
        this.gameData = gameData;
        this.canvas = canvas;
        backgroundColor = new Color(30,30,30).getRGB();


        try {
            //Load the tank icon image from the resources folder.
            player1Icon = ImageIO.read(new File("Tank Icon.png"));
            //Copy for the second player.
            player2Icon = Image.copy(player1Icon);

            //Color both images based on the tank's color.
            Image.setHue(player1Icon, gameData.player1.getColor());
            Image.setHue(player2Icon, gameData.player2.getColor());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //Determine how the game world should be mapped to the minimap based on their sizes.
        if(gameData.gameLevel.getMapWidth() > gameData.gameLevel.getMapHeight()) {
            if (canvas.getWidth() > canvas.getHeight())
                sizeRatio = gameData.gameLevel.getMapWidth() / (canvas.getWidth() - 1);
            else
                sizeRatio = gameData.gameLevel.getMapWidth() / (canvas.getHeight() - 1);
        }
        else
        {
            if (canvas.getWidth() > canvas.getHeight())
                sizeRatio = gameData.gameLevel.getMapHeight() / (canvas.getHeight() - 1);
            else
                sizeRatio = gameData.gameLevel.getMapHeight() / (canvas.getHeight() - 1);
        }
    }

    //Transform a point from the map dimensions to the minimap dimensions.
    public Point2D.Double gameToMiniMap(Point2D.Double coordinate) {
        return new Point2D.Double((coordinate.x - gameData.gameLevel.mapCenter.x)/sizeRatio + canvas.getWidth()/2.0,
                                  (coordinate.y - gameData.gameLevel.mapCenter.y)/sizeRatio + canvas.getHeight()/2.0);
    }

    public void draw() {
        //Draw a black background
        for(int i = 0; i < canvas.getWidth(); i++) {
            for (int j = 0; j < canvas.getHeight(); j++)
                canvas.setRGB(i, j, backgroundColor);
        }

        //Create a graphic so that shapes can be drawn to the buffered image.
        Graphics2D graphic = canvas.createGraphics();

        //Draw the walls
        for(Wall wall : gameData.gameLevel.wallObjects) {
            Point2D.Double p1 = gameToMiniMap(wall.getPoint1());
            Point2D.Double p2 = gameToMiniMap(wall.getPoint2());

            graphic.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
        }

        //Draw the tanks.
        Point2D.Double tankPos = gameToMiniMap(gameData.player1.getPosition());
        graphic.drawImage(player1Icon, (int)tankPos.x - player1Icon.getWidth()/2, (int)tankPos.y - player1Icon.getHeight()/2, null);

        tankPos = gameToMiniMap(gameData.player2.getPosition());
        graphic.drawImage(player2Icon, (int)tankPos.x - player2Icon.getWidth()/2, (int)tankPos.y - player2Icon.getHeight()/2, null);

        //Delete the graphics object.
        graphic.dispose();
    }

    public void run() {
        draw();
    }
}