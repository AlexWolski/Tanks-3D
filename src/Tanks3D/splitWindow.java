package Tanks3D;

import javax.swing.*;
import java.awt.*;

public class splitWindow extends JFrame {
    private GameData gameData;
    private SplitPanel panel;

    public splitWindow(GameData data, String s, Dimension panelSize) {
        super(s);

        gameData = data;

        setLayout(new BorderLayout());
        setSize(panelSize);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Instantiate the Panel objects.
        panel = new SplitPanel(panelSize);
        panel.setPreferredSize(panelSize);

        add(panel);

        pack();
        setVisible(true);
    }

    public void draw() {
        gameData.player1.draw(panel.screen1Buffer);
        gameData.player2.draw(panel.screen2Buffer);
        gameData.minimap.draw(panel.minimapBuffer);
        panel.repaint();
    }
}