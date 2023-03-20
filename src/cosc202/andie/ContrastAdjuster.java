package cosc202.andie;

import java.awt.image.*;

public class ContrastAdjuster implements ImageOperation, java.io.Serializable{

    private int contrastPercent;
    private int brightPercent;
    /**
     * Default Constructor
     */
    ContrastAdjuster(){
        this.contrastPercent = 0;
        this.brightPercent = 0;
    }

    ContrastAdjuster(int contrastPercent, int brightPercent){
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

                // int adjusted = (int) Math.round(0.3*r + 0.6*g + 0.1*b);
                //int adjustedA = (int) Math.round((1 + (contrastPercent / 100))*(a - 127.5) + 127.5 *(1 + (brightPercent / 100)));
                r = (int) Math.round((1 + (contrastPercent / 100))*(r - 127.5) + 127.5 *(1 + (brightPercent / 100)));
                g = (int) Math.round((1 + (contrastPercent / 100))*(g - 127.5) + 127.5 *(1 + (brightPercent / 100)));
                b = (int) Math.round((1 + (contrastPercent / 100))*(b - 127.5) + 127.5 *(1 + (brightPercent / 100)));

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
