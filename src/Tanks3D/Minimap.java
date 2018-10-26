package Tanks3D;

import java.awt.*;
import java.awt.image.BufferedImage;

//Displays the minimap.
public class Minimap implements Runnable{
    private GameData gameData;
    private BufferedImage minimapCanvas;

    public Minimap(GameData data, BufferedImage canvas, Dimension panelSize) {
        data = gameData;
        minimapCanvas = canvas;
    }

    public void draw() {
        for (int i = 0; i < minimapCanvas.getWidth(); i++)
            for (int j = 0; j < minimapCanvas.getHeight(); j++)
                minimapCanvas.setRGB(i, j, 0);
    }

    public void run() {
        draw();
    }
}
