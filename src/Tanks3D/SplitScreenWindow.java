package Tanks3D;

import javax.swing.*;
import java.awt.*;

public class SplitScreenWindow extends JFrame {
    private JPanel container;

    public SplitScreenWindow(String s, Dimension frameSize, JPanel screen1, JPanel screen2, JPanel minimap) {
        super(s);

        setLayout(new BorderLayout());
        setSize(frameSize);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Instantiate the Panel objects.
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1,2));

        container.add(screen1);
        container.add(screen2);

        //container.add(new Minimap());
        add(container);

        pack();
        setVisible(true);
    }
}