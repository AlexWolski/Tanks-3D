package Tanks3D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SplitWindow extends JFrame {
    private GameData gameData;
    private SplitPanel panel;

    public SplitWindow(GameData data, String s, Dimension panelSize) {
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

    public BufferedImage getScreen1Buffer() {
        return panel.screen1Buffer;
    }

    public BufferedImage getScreen2Buffer() {
        return panel.screen2Buffer;
    }

    public BufferedImage getMinimapBuffer() {
        return panel.minimapBuffer;
    }

    public void draw() {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(gameData.player1);
        es.execute(gameData.player2);
        es.execute(gameData.minimap);
        es.shutdown();
        try {
            while (!es.awaitTermination(1, TimeUnit.MINUTES)) ;
        } catch (Exception e) {
        }

        panel.paintImmediately(panel.getBounds());
    }
}