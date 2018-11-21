package Tanks3D.Object.Entity.Round;

import Tanks3D.Object.Entity.Entity;
import Tanks3D.Object.Entity.Tank;
import Tanks3D.Object.Wall.BreakableWall;
import Tanks3D.Object.Wall.UnbreakableWall;

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

    public Round(Point2D.Double position, int zPos, double angle, int speed, int damage, BufferedImage[] sprites, Color imageColor) {
        super(hitCircleRadius, position, angle, speed);
        //Set the sprites for the round.
        setSprites(sprites, imageColor, (int)(sprites[0].getWidth() * scale), (int)(sprites[0].getHeight() * scale));
        //Set the in game height of the round.
        super.zPos = zPos;

        this.damage = damage;
    }

    public void collide(Object object, ListIterator iterator) {
        //If the round hits a breakable wall, break it.
        if(object instanceof BreakableWall) {
            ((BreakableWall) object).breakWall();
            iterator.remove();
        }
        //If the round hits an unbreakable wall, destroy the round.
        if(object instanceof UnbreakableWall) {
            entityList.remove(this);
        }
        //If the round hits a tank, damage it.
        if(object instanceof Tank)
            ((Tank) object).damage(damage);
    }

    //Create a new round and add it to the entity list
    public static void newRound(Round newRound) {
        entityList.add(newRound);
        System.out.println("test");
    }

    public static int getHitCircleRadius() {
        return hitCircleRadius;
    }
}
