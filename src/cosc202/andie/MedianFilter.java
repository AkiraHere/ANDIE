package cosc202.andie;

import java.awt.image.*;
import java.util.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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

        //Determine size of area based on radius datafield, default 3x3
        int xDimension = (2*radius+1);
        int yDimesion = (2*radius+1);
        int size =  xDimension * yDimesion;
        float [] array = new float[size];
        int newWidth = input.getWidth() + radius*2 ; 
        int newHeight = input.getHeight() + radius*2 ; 
        BufferedImage output = new BufferedImage( newWidth , newHeight, input.getType() ) ;

        //Arrays for storing each color property in a pixel
        int [] alphaArray = new int[size];
        int [] redArray = new int[size];
        int [] greenArray = new int[size];
        int [] blueArray = new int[size];

        //Loop through each pixel in the image to retrieve corresponding ARGB values.
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                //Begin looping through the window of neighboring pixels
                for(int j = -radius; j < radius + 1; j++){     
                    for (int i = -radius; i < radius + 1; i++){
                        int argb = input.getRGB(x + j, y + i);       
                        //store ARGB values in their own arrays
                        int a = (argb & 0xFF000000) >> 24;
                        int r = (argb & 0x00FF0000) >> 16;
                        int g = (argb & 0x0000FF00) >> 8;
                        int b = (argb & 0x000000FF);
                        alphaArray[i] = a;
                        redArray[i] = r;
                        greenArray[i] = g;
                        blueArray[i] = b; 
                    }
                    //sort the arrays
                    Arrays.sort(alphaArray);
                    Arrays.sort(redArray);
                    Arrays.sort(greenArray);
                    Arrays.sort(blueArray);           
                    //pack colors based on median of the sorted array
                    int argbOut = (alphaArray[array.length/2] << 24) | (redArray[array.length/2] << 16) | (greenArray[array.length/2]<< 8) | blueArray[array.length/2]; 
                    //assign the filtered color to the output
                    output.setRGB(x, y, argbOut);
                }
            }
        }

        // Operations below copied from MeanFilter
        // larger resizing of input to be overlayed 

        AffineTransform transform = AffineTransform.getScaleInstance(
                                    (double)newWidth / input.getWidth() ,
                                    (double)newHeight / input.getHeight() ) ;
        AffineTransformOp operation = new AffineTransformOp( transform , AffineTransformOp.TYPE_BILINEAR ) ;
        output = operation.filter( input , output ) ;

        // original image overlays the resized image
        BufferedImage mergedImage = new BufferedImage( input.getWidth() + radius*2 , input.getHeight() + radius*2 , input.getType() ) ; 
        Graphics2D g2d = mergedImage.createGraphics() ;
        g2d.drawImage( output , 0 , 0 , null ) ; 
        g2d.drawImage( input , radius , radius , null ) ;
        g2d.dispose() ;

        // kernel used to filter image and convolution 
        Kernel kernel = new Kernel( 2*radius+1 , 2*radius+1 , array ) ; 
        ConvolveOp convOp = new ConvolveOp( kernel ) ; 
        
        // filtering of the image, and trimming of the black border 
        BufferedImage filteredImage = new BufferedImage( input.getColorModel() , input.copyData(null) , input.isAlphaPremultiplied() , null ) ; 
        filteredImage = convOp.filter( mergedImage , null ) ; 
        filteredImage = filteredImage.getSubimage( radius , radius , input.getWidth() , input.getHeight() ) ; 

        return output;//Change to return newly buffered image when done
    }



}