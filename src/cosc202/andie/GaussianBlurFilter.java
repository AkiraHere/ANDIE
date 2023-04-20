package cosc202.andie ; 

import java.awt.Graphics2D ;
import java.awt.geom.AffineTransform ;
import java.awt.image.* ; 
import java.lang.Math ;

/**
 * <p>
 * ImageOperation to apply a gaussian blur filter. 
 * </p>
 * 
 * <p>
 * Blurs an image according to the gaussian distribution equation, is
 * implemented via convolution. Uses graphics to resolve pixel loss at
 * the borders of the filtered BufferedImage. 
 * </p>
 * 
 * @author Samuel Goddard
 * @version 1.0
 */
public class GaussianBlurFilter implements ImageOperation , java.io.Serializable {

    /**
     * <p>
     * Data fields
     * </p>
     * 
     * <p>
     * radius - Radius of the kernel / filter to be applied - radius of 1 = 3x3 filter, radius of 3 = 5x5 filter, etc. 
     * offsets - xOffset and yOffset are used to calculate gaussian values of pixels in the kernel. 
     * sigma - sigma is utilised in the gaussian equation. 
     * </p>
     */
    private int radius , xOffset , yOffset ; 
    private float sigma ; 

    /** 
     * Constructor, assigning values using the input radius.
     * 
     * @param radius the radius of the kernal for the blur to be applied. 
     */
    public GaussianBlurFilter( int radius ) {

        this.radius = radius ; 
        this.xOffset = -radius ; 
        this.yOffset = radius ; 
        this.sigma = radius / 3.0f ; 

    }

    /**
     * Default constructor. 
     */
    public GaussianBlurFilter() {

        this.radius = 1 ; 
        this.xOffset = -1 ; 
        this.yOffset = 1 ; 
        this.sigma = 1.0f / 3.0f ; 

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
     * @param input the image to be filtered
     * @return the filtered image 
     */
    public BufferedImage apply( BufferedImage input ) {

        int size = ( 2*radius + 1 ) * ( 2*radius + 1 ) ; 
        float[] array = new float[size]; 
        float sum = 0 ; 

        // filling array with gaussian values, while calculating sum of values
        for ( int i = 0 ; i < size ; i++ ) {
            
            array[i] = returnGaussianValue() ; 
            sum = sum + array[i] ; 

            if ( xOffset == radius ) {
                
                xOffset = -radius - 1 ; 
                yOffset-- ; 
            } 
            
            xOffset++ ; 

        } 

        // reset values 
        xOffset = -radius ; 
        yOffset = radius ; 
        
        // dividing all values in the array by the sum, as to stop the filter from brightening the image while maintaining gaussian distribution
        for ( int i = 0 ; i < size ; i++ ) {
            
            array[i] /= sum ;

        }

        // larger resizing of input to be overlayed 
        int newWidth = input.getWidth() + radius*2 ; 
        int newHeight = input.getHeight() + radius*2 ; 
        BufferedImage resizedImage = new BufferedImage( newWidth , newHeight, input.getType() ) ;
        AffineTransform transform = AffineTransform.getScaleInstance(
                                    (double)newWidth / input.getWidth() ,
                                    (double)newHeight / input.getHeight() ) ;
        AffineTransformOp operation = new AffineTransformOp( transform , AffineTransformOp.TYPE_BILINEAR ) ;
        resizedImage = operation.filter( input , resizedImage ) ;

        // original image overlays the resized image
        BufferedImage mergedImage = new BufferedImage( input.getWidth() + radius*2 , input.getHeight() + radius*2 , input.getType() ) ; 
        Graphics2D g2d = mergedImage.createGraphics() ;
        g2d.drawImage( resizedImage , 0 , 0 , null ) ; 
        g2d.drawImage( input , radius , radius , null ) ;
        g2d.dispose() ;

        // kernel used to filter image and convolution 
        Kernel kernel = new Kernel( 2*radius+1 , 2*radius+1 , array ) ; 
        ConvolveOp convOp = new ConvolveOp( kernel ) ; 
        
        // filtering of the image, and trimming of the black border 
        BufferedImage filteredImage = new BufferedImage( input.getColorModel() , input.copyData(null) , input.isAlphaPremultiplied() , null ) ; 
        filteredImage = convOp.filter( mergedImage , null ) ; 
        filteredImage = filteredImage.getSubimage( radius , radius , input.getWidth() , input.getHeight() ) ; 
        BufferedImage output = new BufferedImage( filteredImage.getColorModel() , filteredImage.copyData( filteredImage.getRaster().createCompatibleWritableRaster() ) , filteredImage.isAlphaPremultiplied() , null ) ;  

        return output ; 

    }

    /**
     * <p>
     * Simple method that calculates the gaussian values for the kernel using the 
     * gaussian distribution equation (approximation). 
     * </p>
     * 
     * @return the gaussian value given the sigma value and x/y offset 
     */
    public float returnGaussianValue () {
 
        float gaussianValue = (float)(Math.exp(-(xOffset*xOffset + yOffset*yOffset) / (2 * sigma*sigma)) / (2 * Math.PI * sigma*sigma)) ; 
        return gaussianValue ; 

    }

}