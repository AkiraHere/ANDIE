package cosc202.andie;

import java.awt.image.*;
import java.util.*;
/**
 * <p>
 * Image operation used to apply a median filter, a type of blurring effect, to an image.
 * </p>
 * 
 * <p>
 * Using this operations results in similar effects of other types of blurs, only instead of taking an average
 * of all neighboring pixels to create the blur effect, we take those neighboring pixels and sort them.
 * The result is a blurred image with sharper differences between contrasting colours? <<<FACT CHECK THIS
 * </p>
 * 
 * @author Luke Webb
 * @version 0.1;
 * 
 */

public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /** 
    * <p>
    * The size of the filter is the 'radius' of the convolution kernel used.
    * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
    * Larger filters give a stronger blurring effect.
    * </p>
    * 
    * @param radius The radius of the newly constructed MeanFilter
    */
    MedianFilter(int radius){
        this.radius = radius;
    }


    /**
     * Default constructor for a median filter object, applies a default 
     */
    MedianFilter(){
        this.radius = 1;
    }

    /***
     * <p>
     * Used to apply a median filter to an image.
     * </p>
     * 
     * <p>
     * The median filter will differ from the mean filter, by taking the median instead of a mean to apply
     * the filter.
     * </p>
     * 
     * @param input The image input subject to the median filter.
     * @retun The outcome of applying the median filter.
    */

    public BufferedImage apply(BufferedImage input){

        //Determine size of area based on radius datafield
        int size = (2*radius+1) * (2*radius+1);
        float [] array = new float[size];
        Arrays.fill(array, 1.0f/size);

        //Loop through each pixel to retrieve corresponding ARGB values, but then what do we do?
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                //Code from Grey filter, used to assign then pack a grey color based on the ARGB pixel values.
                //Instead of changing the color like below, need to find a way to sort them...
                int grey = (int) Math.round(0.3*r + 0.6*g + 0.1*b); 
                argb = (a << 24) | (grey << 16) | (grey << 8) | grey; //Will pack like this with sorted values?

                input.setRGB(x, y, argb);
            }
        }

        //Something needed here, possibly use Kernal and ConvolveOp like in MeanFilter().


        return input;//Change to return newly buffered image when done
    }



}