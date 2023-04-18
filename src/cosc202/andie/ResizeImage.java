package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * <p>
 * Operation that allows for resizing of an image.
 * </p>
 * 
 * <p>
 * Allows for resizing opertions of an input image. Takes as input a double, which is interpreted
 * as a percentage. This percentage is used to scale the size of the image evenly using the
 * Image class and getScaledInstace() method.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Nick Garner
 * @version 1.0
 */
public class ResizeImage implements ImageOperation, java.io.Serializable {

    /**
     * @fields percentage by which we will resize an image.
     */
    private double resizePercent = 0;
    
    /**
     * Default Constructor
     */
    public ResizeImage(){

    }
    /**
     * Constructor
     * 
     * @param resizePercent the percentage value to resize the image by.
     */
    public ResizeImage(double resizePercent){
        this.resizePercent = resizePercent;
    }

    /**
     * @param input The image to be resized
     * @return The resulting resized image.
     */
    public BufferedImage apply(BufferedImage input) {
        //Calculate dimensions for resized image
        int scaledWidth = (int) ((input.getWidth() * (resizePercent/100)));
        int scaledHeight = (int) ((input.getHeight() * (resizePercent/100)));

        //Create new image with the new dimensions and draw new image
        Image image = input.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_AREA_AVERAGING);
        BufferedImage output = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = output.createGraphics();
        g2d.drawImage(image, 0,0, null);
        g2d.dispose();

        return output;
    }
    
}
