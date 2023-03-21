package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ResizeImage implements ImageOperation, java.io.Serializable {

    private int resizePercent = 0;

    public ResizeImage(){

    }

    public ResizeImage(int resizePercent){
        this.resizePercent = resizePercent;
    }

    public BufferedImage apply(BufferedImage input) {
        //Calculate dimensions for resized image
        int scaledWidth = (int) (input.getWidth() * resizePercent);
        int scaledHeight = (int) (input.getHeight() * resizePercent);

        return input;
    }
    
}
