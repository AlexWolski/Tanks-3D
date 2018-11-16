package Tanks3D.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Image {
    //This class is non-instantiable
    private Image() {
    }

    public static BufferedImage load(String imageFile) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(imageFile));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return image;
    }

    public static BufferedImage copy(BufferedImage image) {
        return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
    }

    //Rotate the image and draw it at the given point. There are many ways rotate an image, but this was the fastest.
    public static void drawRotated(Graphics2D graphic, BufferedImage image, double angle, int x, int y) {
        //Create a new transform.
        AffineTransform transform = new AffineTransform();
        //Translate the transform to where the image will be drawn.
        transform.translate(x, y);
        //Rotate the transform.
        transform.rotate(Math.toRadians(angle), image.getWidth()/2.0, image.getHeight()/2.0);
        //Draw the tank.
        graphic.setTransform(transform);
        graphic.drawImage(image, 0, 0, null);
    }

    //Change the hue of the given image.
    public static void setHue(BufferedImage image, Color hue) {
        //Color the image if it was loaded correctly.
        if (image != null) {
            //The luminance of the pixel.
            double lum;
            //Store the color of the pixel
            Color pixelColor;

            //Loop through the image and change the hue of each color.
            for (int i = 0; i < image.getWidth(); i++)
                for (int j = 0; j < image.getHeight(); j++) {
                    //Get the color of the current pixel.
                    pixelColor = new Color(image.getRGB(i, j), true);
                    //Calculate the luminance. These values are pre-determined.
                    lum = (pixelColor.getRed() * 0.2126 + pixelColor.getGreen() * 0.7152 + pixelColor.getBlue() * 0.0722) / 255;

                    //Calculate the new color and store it in the image.
                    image.setRGB(i, j, new Color((int) (hue.getRed() * lum), (int) (hue.getGreen() * lum), (int) (hue.getBlue() * lum), pixelColor.getAlpha()).getRGB());
                }
        }
    }
}
