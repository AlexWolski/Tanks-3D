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
    //How many rounds each pool will hold.
    private static final int poolSize = 10;
    //Three pools to store pre-made rounds before they are used.
    private static ArrayList<Round> ArmorPiercingPool;
    private static ArrayList<Round> GuidedMissilePool;
    private static ArrayList<Round> HighExplosivePool;
    //The damage the round does when it hits a tank directly.
    private final int damage;

    //Initialize the pools of rounds.
    public static void init(ArrayList<Entity> EntityList) {
        entityList = EntityList;

        //Initialize the three pools of rounds
        ArmorPiercingPool = new ArrayList<>();
        GuidedMissilePool = new ArrayList<>();
        HighExplosivePool = new ArrayList<>();

        //Populate each pool with rounds that are not visible.
        for(int i = 0; i < poolSize; i++) {
            ArmorPiercingPool.add(new ArmorPiercing(new Point2D.Double(0, 0), 0, 0));
            ArmorPiercingPool.get(i).visible = false;
            GuidedMissilePool.add(new GuidedMissile(new Point2D.Double(0, 0), 0, 0));
            GuidedMissilePool.get(i).visible = false;
            HighExplosivePool.add(new HighExplosive(new Point2D.Double(0, 0), 0, 0));
            HighExplosivePool.get(i).visible = false;
        }
    }

    public Round(Point2D.Double position, int zPos, double angle, int speed, int damage, BufferedImage[] sprites, Color imageColor) {
        super(hitCircleRadius, position, angle, speed);
        //Set the sprites for the round.
        setSprites(sprites, imageColor, (int)(sprites[0].getWidth() * scale), (int)(sprites[0].getHeight() * scale));
        //Set the in game height of the round.
        super.zPos = zPos;

        this.damage = damage;
    }

    public void collide(Object object, ListIterator thisObject, ListIterator collidedObject) {
        //If the round hits a breakable wall, break it.
        if(object instanceof BreakableWall) {
            ((BreakableWall) object).breakWall();
            collidedObject.remove();
            removeRound(this);
        }
        //If the round hits an unbreakable wall, destroy the round.
        else if(object instanceof UnbreakableWall) {
            removeRound(this);
        }
        //If the round hits a tank, damage it and remove the round.
        else if(object instanceof Tank) {
            ((Tank) object).damage(damage);
            removeRound(this);
        }
    }

    //Add a round from the given pool to the entity list.
    public static void addFromPool(ArrayList<Round> roundPool, double x, double y, int zPos, double angle) {
        if(!roundPool.isEmpty()) {
            //Temporarily hold the first round in the pool and remove it.
            Round tempRound = roundPool.get(0);
            roundPool.remove(0);

            //Modify the variables of the round.
            tempRound.position.setLocation(x, y);
            tempRound.zPos = zPos;
            tempRound.angle = angle;
            tempRound.visible = true;

            //Add the modified round to the entity pool.
            entityList.add(tempRound);
        }
    }

    //Create a new round and add it to the entity list
    public static void newArmorPiercing(double x, double y, int zPos, double angle) {
        addFromPool(ArmorPiercingPool, x, y, zPos, angle);
    }
    public static void newGuidedMissile(double x, double y, int zPos, double angle) {
        addFromPool(GuidedMissilePool, x, y, zPos, angle);
    }
    public static void newHighExplosive(double x, double y, int zPos, double angle) {
        addFromPool(HighExplosivePool, x, y, zPos, angle);
    }

    public static void removeRound(Round roundToDelete) {
        //Determine what type the round is and get the corresponding round pool.
        ArrayList<Round> roundPool;

        if(roundToDelete instanceof ArmorPiercing)
            roundPool = ArmorPiercingPool;
        else if(roundToDelete instanceof  GuidedMissile)
            roundPool = GuidedMissilePool;
        else
            roundPool = HighExplosivePool;

        //Make teh round invisible.
        roundToDelete.visible = false;

        //Add the round back to the round pool.
        roundPool.add(roundToDelete);
    }

    public static int getHitCircleRadius() {
        return hitCircleRadius;
    }
}
