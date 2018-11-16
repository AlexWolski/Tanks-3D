package Tanks3D.DisplayComponents;

import Tanks3D.Utilities.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

//A Heads Up Display that shows the player's tank, health, and lives.
public class HUD {
    //An buffer that get written to, then displayed on the screen.
    private final BufferedImage canvas;
    //The gradient used for the health bar.
    private final GradientPaint gradient;
    //The side of the screen that the stats will be on.
    private final String iconSide;
    //Images that will be displayed.
    private BufferedImage body, gun, healthIcon, lifeIcon;
    //The position and size of the tank images. The gun's position is not final so it can be animated.
    private Point gunPos;
    private final Point bodyPos;
    private final Dimension bodyDim, gunDim;
    //The position of the status images.
    private final Point healthIconPos, lifeIconPos;
    //The position and dimension of the health bar.
    private final Point healthBarPos;
    private final Dimension healthBarDim;
    //The size of the health and life icons.
    private final int iconSize = 50;
    //The distance between the icons and the side of the screen.
    private final int iconGap = 20;
    //The distance between each life icon. Can be positive or negative depending on if its on the left or right.
    private final int lifeIconGap;

    public HUD(BufferedImage canvas, Color hudColor, String iconSide) {
        this.canvas = canvas;
        this.iconSide = iconSide;
        this.healthBarDim = new Dimension(canvas.getWidth()/2, canvas.getWidth()/15);

        //Load the images.
        body = Image.load("resources/HUD/Tank Body.png");
        gun = Image.load("resources/HUD/Tank Gun.png");
        healthIcon = Image.load("resources/HUD/Health Icon.png");
        lifeIcon = Image.load("resources/HUD/Life Icon.png");

        //Color the images.
        Tanks3D.Utilities.Image.tintImage(body, hudColor);
        Tanks3D.Utilities.Image.tintImage(gun, hudColor);
        Tanks3D.Utilities.Image.tintImage(healthIcon, hudColor);
        Tanks3D.Utilities.Image.tintImage(lifeIcon, hudColor);

        //The ratio between how large the image is and its size onscreen.
        double scale = (double)canvas.getWidth()/body.getWidth();

        //Calculate the location and dimension of the body and gun.
        bodyPos = new Point(canvas.getWidth()/2 - (int) (body.getWidth()/2 * scale), canvas.getHeight() - (int) (body.getHeight() * scale));
        gunPos = new Point(canvas.getWidth()/2 - (int) (gun.getWidth()/2 * scale), canvas.getHeight() - (int) (gun.getHeight() * scale));
        bodyDim = new Dimension((int) (body.getWidth() * scale), (int) (body.getHeight() * scale));
        gunDim = new Dimension((int) (gun.getWidth() * scale), (int) (gun.getHeight() * scale));

        //Calculate the position of the health and life depending on if they are on the right or the left.
        if(iconSide.equals("left")) {
            gradient = new GradientPaint(0, 0, Color.darkGray, 500, 0, hudColor);
            healthIconPos = new Point(iconGap, iconGap);
            healthBarPos = new Point(healthIconPos.x + iconGap + iconSize, healthIconPos.y + (iconSize - healthBarDim.height)/2);
            lifeIconPos = new Point(iconGap, 2*iconGap + iconSize);
            lifeIconGap = iconSize + iconGap;
        }
        else {
            gradient = new GradientPaint(500, 0, Color.darkGray, 0, 0, hudColor);
            healthIconPos = new Point(canvas.getWidth() - iconGap - iconSize, iconGap);
            healthBarPos = new Point(healthIconPos.x  - iconGap - healthBarDim.width, healthIconPos.y + (iconSize - healthBarDim.height)/2);
            lifeIconPos = new Point(canvas.getWidth() - iconGap - iconSize, 2*iconGap + iconSize);
            lifeIconGap = -(iconSize + iconGap);
        }
    }

    //Draw the player's tank and status.
    public void draw(int maxHealth, int health, int lives) {
        Graphics2D graphic = canvas.createGraphics();

        //Draw the tank.
        graphic.drawImage(body, bodyPos.x, bodyPos.y, bodyDim.width, bodyDim.height, null);
        graphic.drawImage(gun, gunPos.x, gunPos.y, gunDim.width, gunDim.height, null);

        //Draw the health icon.
        graphic.drawImage(healthIcon, healthIconPos.x, healthIconPos.y, iconSize, iconSize, null);

        //Draw the hearts.
        for (int i = 0; i < lives; i++)
            graphic.drawImage(lifeIcon, lifeIconPos.x + i * lifeIconGap, lifeIconPos.y, iconSize, iconSize, null);

        //Calculate how wide the health bar is based on the current and maximum health.
        int healthWidth = (int)(healthBarDim.width * (health/(double)maxHealth));

        //Draw the health bar background.
        graphic.setPaint(Color.darkGray);
        graphic.fillRect(healthBarPos.x, healthBarPos.y, healthBarDim.width, healthBarDim.height);

        //Set the gradient for the health bar.
        graphic.setPaint(gradient);

        //Draw the current heath.
        if(iconSide.equals("left"))
            graphic.fillRect(healthBarPos.x, healthBarPos.y, healthWidth, healthBarDim.height);
        else
            graphic.fillRect(healthBarPos.x + (healthBarDim.width - healthWidth), healthBarPos.y, healthWidth, healthBarDim.height);

        //Draw the max health.
        graphic.setStroke(new BasicStroke(2));
        graphic.setColor(Color.white);
        graphic.drawRect(healthBarPos.x, healthBarPos.y, healthBarDim.width, healthBarDim.height);
    }
}