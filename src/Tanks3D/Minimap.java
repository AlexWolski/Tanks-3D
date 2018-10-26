package Tanks3D;

import javax.swing.*;
import java.awt.*;

public class Minimap extends JPanel {
    private GameManager tankGame;

    public Minimap(GameManager game, Dimension panelSize) {
        tankGame = game;
        setPreferredSize(panelSize);
    }

    public void update()
    {

    }

    @Override
    public void paintComponent(Graphics g) {
    }
}
