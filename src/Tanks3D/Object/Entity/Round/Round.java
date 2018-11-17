package Tanks3D.Object.Entity.Round;

import Tanks3D.Object.Entity.Entity;
import Tanks3D.Object.Entity.Tank;
import Tanks3D.Object.Wall.BreakableWall;
import Tanks3D.Utilities.Image;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;

public abstract class Round extends Entity {
    //A struct that contains the necessary data about the game.
    static private ArrayList<Entity> entityList;

    private final static int hitCircleRadius = 2;
    //How much to scale the images when drawn to the screen.
    private final static double scale = 0.01;
    //The damage the round does when it hits a tank directly.
    private final int damage;

    public static void init(ArrayList<Entity> EntityList) {
        entityList = EntityList;
    }

    public Round(Point2D.Double position, double angle, int speed, int damage, BufferedImage[] sprites, Color imageColor) {
        super(position, hitCircleRadius, angle, speed);
        super.setSprites(sprites, imageColor, (int)(sprites[0].getWidth() * scale), (int)(sprites[0].getHeight() * scale));

        this.damage = damage;
    }

    public void collide(Object object, ListIterator iterator) {
        //If the round hits a breakable wall, break it.
        if(object instanceof BreakableWall) {
            ((BreakableWall) object).breakWall();
            iterator.remove();
        }
        //If the round hits a tank, damage it.
        if(object instanceof Tank)
            ((Tank) object).damage(damage);

        //If the object hit was not another round, delete this round.
        if(!(object instanceof Round)) {
            entityList.remove(this);
        }
    }

    public static int getHitCircleRadius() {
        return hitCircleRadius;
    }
}
