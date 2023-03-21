package cosc202.andie;

import java.awt.image.BufferedImage;

public class Flip implements ImageOperation, java.io.Serializable {

    @Override
    public BufferedImage apply(BufferedImage input) {

        return input;
    }
    
}
