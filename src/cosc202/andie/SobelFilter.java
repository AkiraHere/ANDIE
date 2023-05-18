package cosc202.andie;

import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 * <p>
 * Image operation used to apply an edge-detection aka sobel filter, achieves the appearance of 
 * finding horizontal or vertical edges.
 * </p>
 * 
 * <p>
 * Implemented similarly to the EmbossFilter.
 * The operation involves first selecting a direction for the effect to take place (e.g. Horizontal or Vertical).
 * The kernel associated with the chosen direction is then applied to the entire image. Due to the 
 * implementation of an the edge detection effect, some results are returned as negative. As such, we apply an
 * offset of the middle colour value in RGB, see the MID_COLOR data field below. Once the final color value
 * of a pixel is calculated, it is then assigned to the coordinate position in the returned image.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Luke Webb
 * @version 0.1;
 * 
 */
public class SobelFilter implements ImageOperation, java.io.Serializable{
    private int option;
    private static final int MID_COLOR = 127;


    /**
     * Constructor for an instance of Sobel filter. Requires an option, otherwise chooses.
     * 
     * @param option the integer value, either 1 or 0, that determines the plane of the kernel.
     */
    public SobelFilter(int option){
        if(option < 0 || option > 1){ // if somehow an option outside of the range is selected.
            this.option = 0;
        }else{
            this.option = option;
        }
    }


    /**
     * Applies the Sobel filter to the input image.
     * Iterates through the pixels within the range of the input image. Finds the corresponding kernal
     * relevant to the option data field. Then, by iterating though the kernal, calculations are performed
     * to find the average of the each pixel in the kernal. Sets the pixel a new colour of the mid value,
     * being the final data field 127, minus the calculated average.
     * 
     * @param input the input image to have the sobel filter applied to.
     * @return the image with the Sobel filter applied.
     */
    public BufferedImage apply(BufferedImage input){
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(),input.getType());

        for(int x = 0; x < input.getWidth(); x++){
            for(int y = 0; y < input.getHeight(); y++){
                double[][] kernel = getKernel(option);

                int sumA = 0, sumR = 0, sumG = 0, sumB = 0;

                for (int i = 0; i < kernel.length; i++) {
                    for (int j = 0; j < kernel[i].length; j++) {
                        int xPos = x + j;
                        int yPos = y + i;
                        int argb;
                        if((xPos < 0 || xPos >= input.getWidth()) && (yPos < 0 || yPos >= input.getHeight())){
                            argb = input.getRGB(x, y);
                        }else if(xPos < 0 || xPos >= input.getWidth()){
                            argb = input.getRGB(x, yPos);
                        }else if(yPos < 0 || yPos >= input.getHeight()){
                            argb = input.getRGB(xPos, y);
                        }else{
                            argb = input.getRGB(xPos, yPos); 
                        }
                        int a = (argb & 0xFF000000) >> 24;
                        int r = (argb & 0x00FF0000) >> 16;
                        int g = (argb & 0x0000FF00) >> 8;
                        int b = (argb & 0x000000FF);
                        double weight = kernel[i][j];

                        sumA += (int)(a * weight);
                        sumR += (int)(r * weight);
                        sumG += (int)(g * weight);
                        sumB += (int)(b * weight);
                    }
                }

                int avg = (int)((sumR + sumG + sumB) / 3.0); 
                int embossVal = MID_COLOR - avg;

                Color embossColor;
                if (embossVal < 0) {
                    int darkerVal = Math.abs(embossVal);
                    if(darkerVal < 0){
                        darkerVal = 0;
                    }
                    embossColor = new Color(darkerVal, darkerVal, darkerVal);
                } else {
                    int brighterVal = Math.abs(embossVal);
                    if(brighterVal > 255){
                        brighterVal = 255;
                    }
                    embossColor = new Color(brighterVal, brighterVal, brighterVal);
                }

                output.setRGB(x, y, embossColor.getRGB());
            }
        }

        return output;
    }



    /**
     * Used to retrieve the kernel for the edge detection of the Sobel Filter.
     * 
     * @param option the integer specified upon construction, determines
     * the direction of the sobel filter.
     * @return the kernal of the specified sobel filter.
     */
    private double[][] getKernel(int option){
        double[][][] options = {
            {
                {-1/2, 0, 1/2},
                {-1.0  , 0, 1.0},   //Option 0, Horizontal
                {-1/2, 0, 1/2}
            },
            {
                {-1/2, -1.0, -1/2},
                {0,  0,  0},    //Option 1, Vertical
                {1/2, 1.0, 1/2}
            },
        };
        return options[option];
    }
}
