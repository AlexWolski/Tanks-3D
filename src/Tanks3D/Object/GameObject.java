package Tanks3D.Object;

public abstract class GameObject {
    protected boolean visible;
    public abstract double getWidth();
    public abstract double getHeight();

    public boolean getVisible() {
        return visible;
    }
}
