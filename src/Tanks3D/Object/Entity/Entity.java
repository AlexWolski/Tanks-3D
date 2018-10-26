package Tanks3D.Object.Entity;

import Tanks3D.GameData;
import Tanks3D.Object.GameObject;

import java.awt.image.BufferedImage;

public abstract class Entity extends GameObject {
    public abstract void update(GameData game);
    public abstract void draw(BufferedImage canvas);
}
