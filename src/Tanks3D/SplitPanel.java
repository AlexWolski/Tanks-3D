package Tanks3D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SplitPanel extends JPanel {
    Dimension panelSize;
    protected BufferedImage screen1Buffer;
    protected BufferedImage screen2Buffer;
    protected BufferedImage minimapBuffer;

    public SplitPanel(Dimension size) {
        panelSize = size;
        screen1Buffer = new BufferedImage(panelSize.width/2, panelSize.height, BufferedImage.TYPE_INT_RGB);
        screen2Buffer = new BufferedImage(panelSize.width/2, panelSize.height, BufferedImage.TYPE_INT_RGB);
        minimapBuffer = new BufferedImage(panelSize.height/4, panelSize.height/4, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphic = (Graphics2D) g;
        graphic.drawImage(screen1Buffer, 0, 0, null);
        graphic.drawImage(screen2Buffer, panelSize.width/2, 0, null);
    }
}
