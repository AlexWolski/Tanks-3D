package Tanks3D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//Draws both players' screens and the minimap to the display.
public class SplitWindow extends JFrame {
    private GameData gameData;
    private SplitPanel panel;

    public SplitWindow(GameData data, String s, Dimension panelSize) {
        super(s);

        gameData = data;

        //Set the settings for the JFrame.
        setLayout(new BorderLayout());
        setSize(panelSize);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Instantiate the Panel object and add it to the JFrame.
        panel = new SplitPanel(panelSize);
        panel.setPreferredSize(panelSize);
        add(panel);

        //Resize the JFrame and make it visible.
        pack();
        setVisible(true);
    }

    //Return the buffers for each player's screen and the minimap.
    public BufferedImage getScreen1Buffer() { return panel.getScreen1Buffer(); }
    public BufferedImage getScreen2Buffer() { return panel.getScreen2Buffer(); }
    public BufferedImage getMinimapBuffer() { return panel.getMinimapBuffer(); }

    //Draw the two screens and the minimap to buffers, then paint them on the display.
    public void draw() {
        //Use an 'ExecutorService' object to manage the threading of the draws.
        ExecutorService es = Executors.newCachedThreadPool();
        //Create threads to draw the two screens and the minimap concurrently.
        es.execute(gameData.player1);
        es.execute(gameData.player2);
        es.execute(gameData.minimap);
        es.shutdown();

        //Wait for all of the threads to complete before continuing.
        try {
            while (!es.awaitTermination(1, TimeUnit.MINUTES));
        } catch (Exception e) {
        }

        //Paint all three buffers on the display. 'paintImmediately' is used to prevent the buffers being modified before painting is complete.
        panel.paintImmediately(panel.getBounds());
    }
}