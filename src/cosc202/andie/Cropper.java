package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to crop an image. 
 * </p>
 * 
 * <p>
 * Takes an input coordinates of an image and crops to 
 * new x and y values
 * </p>
 * 
 * @author Samuel Goddard
 * @version 1.0
 */
public class Cropper implements ImageOperation , java.io.Serializable {

    /**
     * <p>
     * Data fields
     * </p>
     * 
     * <p>
     * xPos - x coordinate of the upper-left corner of specified crop rectangle
     * yPos - y coordinate of the upper-left corner of specified crop rectangle
     * newWidth - width of crop rectangle
     * newHeight - height of crop rectangle
     * </p>
     */
    private int xPos , yPos , newWidth , newHeight ; 
    
    /**
     * @param xPos x coordinate value
     * @param yPos y coordinate value
     * @param newWidth width of rectangle
     * @param newHeight height of rectangle
     */
    public Cropper ( int xPos , int yPos , int newWidth , int newHeight ) {

        this.xPos = xPos ; 
        this.yPos = yPos ; 
        this.newWidth = newWidth ; 
        this.newHeight = newHeight ; 

    }

    /**
     * Default constructor
     */
    public Cropper () {

        this.xPos = 0 ; 
        this.yPos = 0 ; 
        this.newHeight = 0 ; 
        this.newWidth = 0 ; 

    }
   /**
     * <p>
     * Applies the gaussian blur filter to the image.
     * </p>
     * 
     * <p>
     * Works via convolution, where a larger radius input will result in a
     * strong blurring. Graphics are used to resolve the issue of black pixels around 
     * the border of the filtered image. A scaled BufferedImage is created, with 
     * the input image overlayed in the middle. This way, the blurring more closely 
     * resembles the outside edge of the image, and the increased size means the 
     * black pixels can be discarded without affecting the original size and 
     * appearance of the image. 
     * </p>
     * 
     * @param input the image to be cropped
     * @return the cropped image 
     */
    public BufferedImage apply ( BufferedImage input ) {
        
        BufferedImage filteredImage = new BufferedImage( input.getColorModel() , input.copyData(null) , input.isAlphaPremultiplied() , null ) ;
        BufferedImage cropped = filteredImage.getSubimage( xPos , yPos , newWidth, newHeight ) ; 
        BufferedImage output = new BufferedImage( cropped.getColorModel() , cropped.copyData( cropped.getRaster().createCompatibleWritableRaster() ) , filteredImage.isAlphaPremultiplied() , null ) ; 
        return output ; 

    }
    
}
