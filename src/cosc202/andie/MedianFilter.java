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
 * The result is a blurred image with sharper differences between contrasting colours.
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

public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /** 
    * <p>
    * Contstructor for a median filter object.
    * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
    * Larger filters give a stronger blurring effect.
    * </p>
    * 
    * @param radius The radius of the newly constructed MeanFilter
    */
    public MedianFilter(int radius){
        this.radius = radius;
    }


    /**
     * Default constructor for a median filter object, applies a default radius of 1
     */
    public MedianFilter(){
        this.radius = 1;
    }

    /***
     * <p>
     * Used to apply a median filter to an image.
     * </p>
     * 
     * <p>
     * The median filter will differ from the mean filter, by taking the median instead of a mean to apply
     * the blurring effect.
     * </p>
     * 
     * @param input The image input subject to the median filter.
     * @return The outcome of applying the median filter.
    */

    public BufferedImage apply(BufferedImage input){

        //Determine size of area based on radius datafield, default 3x3
        int xDimension = (2*radius+1);
        int yDimesion = (2*radius+1);
        int size =  xDimension * yDimesion;
        int newWidth = input.getWidth(); 
        int newHeight = input.getHeight(); 
        BufferedImage output = new BufferedImage( newWidth , newHeight, input.getType() ) ;

        //Loop through each pixel in the image to retrieve corresponding ARGB values.
        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                //Arrays for storing each color property in a pixel
                int [] alphaArray = new int[size];
                int [] redArray = new int[size];
                int [] greenArray = new int[size];
                int [] blueArray = new int[size];
                int arrCount = 0;
                //Begin looping through the window of neighboring pixels
                for(int j = -radius; j <= radius ; j++){     
                    for (int i = -radius; i <= radius ; i++){
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
                        //store ARGB values in their own arrays
                        int a = (argb & 0xFF000000) >> 24;
                        int r = (argb & 0x00FF0000) >> 16;
                        int g = (argb & 0x0000FF00) >> 8;
                        int b = (argb & 0x000000FF);
                        alphaArray[arrCount] = a;
                        redArray[arrCount] = r;
                        greenArray[arrCount] = g;
                        blueArray[arrCount] = b; 
                        arrCount++;
                    }
                }
                //sort the arrays
                Arrays.sort(alphaArray);
                Arrays.sort(redArray);
                Arrays.sort(greenArray);
                Arrays.sort(blueArray);           
                //pack colors based on median of the sorted array
                int argbOut = (alphaArray[alphaArray.length/2] << 24) | (redArray[redArray.length/2] << 16) | (greenArray[greenArray.length/2]<< 8) | blueArray[blueArray.length/2]; 
                //assign the filtered color to the output
                output.setRGB(x, y, argbOut);
            }
        }
        return output;//Change to return newly buffered image when done
    }

    /**
     * Method used to retrieve the value stored in the radius datafield.
     * 
     * @return the radius of this object
     */
    public int getRadius(){
        return this.radius;
    }

}
