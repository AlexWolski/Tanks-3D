package Tanks3D;

import Tanks3D.Object.Wall.Wall;
import Tanks3D.Utilities.Image;

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
        backgroundColor = new Color(80, 80, 80).getRGB();

        try {
            //Load the tank icon image from the resources folder.
            player1Icon = ImageIO.read(new File("resources/Tank Icon.png"));
            //Copy for the second player.
            player2Icon = Tanks3D.Utilities.Image.copy(player1Icon);

            //Color both images based on the tank's color.
            Tanks3D.Utilities.Image.setHue(player1Icon, gameData.player1.getColor());
            Tanks3D.Utilities.Image.setHue(player2Icon, gameData.player2.getColor());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //Determine how the game world should be mapped to the minimap based on their sizes.
        if(gameData.gameLevel.getMapWidth() > gameData.gameLevel.getMapHeight()) {
            if (canvas.getWidth() > canvas.getHeight())
                sizeRatio = gameData.gameLevel.getMapWidth() / (canvas.getHeight() - 1);
            else
                sizeRatio = gameData.gameLevel.getMapWidth() / (canvas.getWidth() - 1);
        }
        else
        {
            if (canvas.getWidth() > canvas.getHeight())
                sizeRatio = gameData.gameLevel.getMapHeight() / (canvas.getHeight() - 1);
            else
                sizeRatio = gameData.gameLevel.getMapHeight() / (canvas.getWidth() - 1);
        }
    }

    //Transform a point from the map dimensions to the minimap dimensions.
    private Point2D.Double gameToMiniMap(Point2D.Double coordinate) {
        return new Point2D.Double((coordinate.x - gameData.gameLevel.mapCenter.x)/sizeRatio + canvas.getWidth()/2.0,
                                  (coordinate.y - gameData.gameLevel.mapCenter.y)/sizeRatio + canvas.getHeight()/2.0);
    }

    //Get the two points of a wall, convert them to ints, and draw them.
    private void drawWall(Graphics2D graphic, Wall wall) {
        //Map the points in the game world to points on the minimap.
        Point2D.Double p1 = gameToMiniMap(wall.getPoint1());
        Point2D.Double p2 = gameToMiniMap(wall.getPoint2());

        //Draw the points. The y coordinates are subtracted from the canvas height because the y axis is inverse.
        graphic.drawLine((int)p1.x, canvas.getHeight()-(int)p1.y-1, (int)p2.x, canvas.getHeight()-(int)p2.y-1);
    }

    //Calculate the position to draw the tank. Then rotate and draw it.
    private void drawTank(Graphics2D graphic, Player player, BufferedImage image) {
        //Get the position of the first tank.
        Point2D.Double tankPos = gameToMiniMap(player.getPosition());
        //Center it by subtracting half of the image size.
        tankPos.x -= image.getWidth()/2.0;
        tankPos.y += image.getHeight()/2.0;
        //Draw it rotated.
        Image.drawRotated(graphic, image, player.getAngle(), (int)tankPos.x, canvas.getHeight()-(int)tankPos.y);
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
        for(Wall wall : gameData.gameLevel.wallObjects)
            drawWall(graphic, wall);

        //Draw the tanks.
        drawTank(graphic, gameData.player1, player1Icon);
        drawTank(graphic, gameData.player2, player2Icon);

        //Delete the graphics object.
        graphic.dispose();
    }

    public void run() {
        draw();
    }
}