package Tanks3D;

import java.awt.image.BufferedImage;

//Displays the minimap.
public class Minimap implements Runnable{
    private GameData gameData;
    private BufferedImage canvas;

    public Minimap(GameData gameData, BufferedImage canvas) {
        this.gameData = gameData;
        this.canvas = canvas;
    }

    public void draw() {
        for (int i = 0; i < canvas.getWidth(); i++)
            for (int j = 0; j < canvas.getHeight(); j++)
                canvas.setRGB(i, j, 16777215);
    }

    public void run() {
        draw();
    }
}
