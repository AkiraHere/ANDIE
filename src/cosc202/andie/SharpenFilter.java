package cosc202.andie ; 

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.* ; 

/**
 * <p>
 * Image operation to apply a sharpen filter. 
 * </p>
 * 
 * <p>
 * Sharpens an image using a predefined kernel, creating sharper lines
 * and more contrast in the image. Utilises convolution. Graphics are 
 * utilised to deal with pixel loss at the border. 
 * </p>
 * 
 * @author Samuel Goddard 
 * @version 1.0
 */
public class SharpenFilter implements ImageOperation , java.io.Serializable {

    /** 
     * default constructor
     */
    public SharpenFilter() {}

    /**
     * <p>
     * Applies the sharpen filter to the image. 
     * </p>
     * 
     * <p>
     * Works via convolution. Graphics are used to resolve the issue of black pixels 
     * around the border of the filtered image. A scaled BufferedImage is created, with 
     * the input image overlayed in the middle. This way, the blurring more closely 
     * resembles the outside edge of the image, and the increased size means the 
     * black pixels can be discarded without affecting the original size and 
     * appearance of the image. 
     * </p>
     * 
     * @param input the BufferedImage to be filtered
     * @return the filtered image 
     */
    public BufferedImage apply (BufferedImage input) {

        // default kernel size and values used to create the sharpen effect 
        float[] array = {     0 ,     -(1/2.0f) ,     0 ,
                          -(1/2.0f) ,     3 ,     -(1/2.0f) , 
                              0 ,     -(1/2.0f) ,     0       } ; 

        // larger resizing of input to be overlayed 
        int newWidth = input.getWidth() + 2 ; 
        int newHeight = input.getHeight() + 2 ; 
        BufferedImage resizedImage = new BufferedImage( newWidth , newHeight, input.getType() ) ;
        AffineTransform transform = AffineTransform.getScaleInstance(
                                    (double)newWidth / input.getWidth() ,
                                    (double)newHeight / input.getHeight() ) ;
        AffineTransformOp operation = new AffineTransformOp( transform , AffineTransformOp.TYPE_BILINEAR ) ;
        resizedImage = operation.filter( input , resizedImage ) ;

        // original image overlays the resized image
        BufferedImage mergedImage = new BufferedImage( input.getWidth() + 2, input.getHeight() + 2 , input.getType() ) ; 
        Graphics2D g2d = mergedImage.createGraphics() ;
        g2d.drawImage( resizedImage , 0 , 0 , null ) ; 
        g2d.drawImage( input , 1 , 1 , null ) ;
        g2d.dispose() ;

        // kernel used to filter image and convolution 
        Kernel kernel = new Kernel( 3 , 3 , array ) ; 
        ConvolveOp convOp = new ConvolveOp( kernel ) ; 
        
        // filtering of the image, and trimming of the black border 
        BufferedImage filteredImage = new BufferedImage( input.getColorModel() , input.copyData(null) , input.isAlphaPremultiplied() , null ) ; 
        filteredImage = convOp.filter( mergedImage , null ) ; 
        filteredImage = filteredImage.getSubimage( 1 , 1 , input.getWidth() , input.getHeight() ) ; 
        BufferedImage output = new BufferedImage( filteredImage.getColorModel() , filteredImage.copyData( filteredImage.getRaster().createCompatibleWritableRaster() ) , filteredImage.isAlphaPremultiplied() , null ) ;  

        return output ; 

    }

}
