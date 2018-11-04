package Tanks3D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public final class Image {
    //This class is non-instantiable
    private Image() {
    }

    public static BufferedImage copy(BufferedImage image) {
        return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
    }

    //Rotate the image and draw it to the graphic object centered at the given point.
    public static void drawRotated(Graphics2D graphic, BufferedImage image, double angle, int x, int y) {
        //Transform the image around its center.
        AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(angle), image.getWidth()/2.0, image.getHeight()/2.0);
        //Create a bilinear filter to draw the rotated image.
        AffineTransformOp op = new AffineTransformOp(rotate, AffineTransformOp.TYPE_BILINEAR);
        //Draw the rotated image.
        graphic.drawImage(op.filter(image, null), x, y, null);
    }

    //Change the hue of the given image.
    public static void setHue(BufferedImage image, Color hue) {
        //The luminance of the pixel.
        double lum;
        //Store the color of the pixel
        Color pixelColor;

        //Loop through the image and change the hue of each color.
        for(int i =0; i < image.getWidth(); i++)
            for(int j = 0; j < image.getHeight(); j++) {
                //Get the color of the current pixel.
                pixelColor = new Color(image.getRGB(i, j), true);
                //Calculate the luminance. These values are pre-determined.
                lum = (pixelColor.getRed()*0.2126 + pixelColor.getGreen()*0.7152 + pixelColor.getBlue()*0.0722)/255;

                //Calculate the new color and store it in the image.
                image.setRGB(i, j, new Color((int)(hue.getRed()*lum), (int)(hue.getGreen()*lum), (int)(hue.getBlue()*lum), pixelColor.getAlpha()).getRGB());
            }
    }
}
