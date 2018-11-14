package Tanks3D.DisplayComponents;

import java.awt.*;
import java.awt.image.BufferedImage;

//A Heads Up Display that shows the player's tank, health, and lives.
public class HUD {
    //An buffer that get written to, then displayed on the screen.
    private final BufferedImage canvas;
    //The immobile portion of the player's tank and the gun.
    private final BufferedImage body, gun;
    //The ratio between how large the image is and its size onscreen.
    private final double scale;

    public HUD(BufferedImage canvas, BufferedImage body, BufferedImage gun) {
        this.canvas = canvas;
        this.body = body;
        this.gun = gun;

        scale = (double)canvas.getWidth()/body.getWidth();
    }

    //Draw the player's tank and status.
    public void draw() {
        Graphics2D graphic = canvas.createGraphics();
        graphic.drawImage(body, canvas.getWidth()/2-(int)(body.getWidth()/2*scale), canvas.getHeight()-(int)(body.getHeight()*scale), (int)(body.getWidth()*scale), (int)(body.getHeight()*scale), null);
        graphic.drawImage(gun, canvas.getWidth()/2-(int)(gun.getWidth()/2*scale), canvas.getHeight()-(int)(gun.getHeight()*scale), (int)(gun.getWidth()*scale), (int)(gun.getHeight()*scale),null);
    }
}
