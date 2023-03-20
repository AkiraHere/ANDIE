package cosc202.andie;

import java.awt.image.*;

public class ContrastAdjuster implements ImageOperation, java.io.Serializable{

    private int contrastPercent;
    private int brightPercent;
    /**
     * Default Constructor
     */
    public ContrastAdjuster(){
        this.contrastPercent = 0;
        this.brightPercent = 0;
    }

    public ContrastAdjuster(int contrastPercent, int brightPercent){
        this.contrastPercent = contrastPercent;
        this.brightPercent = brightPercent;
    }

    /**
     * @param input The image to be converted to adjusted
     * @return The resulting adjusted image.
     */
    public BufferedImage apply(BufferedImage input) {
        

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                // Adjusting rgb values according to formula given
                r = (int) Math.round((1 + (contrastPercent / 100))*(r - 127.5) + 127.5 *(1 + (brightPercent / 100)));
                g = (int) Math.round((1 + (contrastPercent / 100))*(g - 127.5) + 127.5 *(1 + (brightPercent / 100)));
                b = (int) Math.round((1 + (contrastPercent / 100))*(b - 127.5) + 127.5 *(1 + (brightPercent / 100)));

                // Clip RGB values to range [0, 255]
                r = Math.min(Math.max(r, 0), 255);
                g = Math.min(Math.max(g, 0), 255);
                b = Math.min(Math.max(b, 0), 255);

                argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }

        return input;
    };
    
}
