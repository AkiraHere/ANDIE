package cosc202.andie ; 

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.* ; 

public class SharpenFilter implements ImageOperation , java.io.Serializable {

    SharpenFilter() {}

    public BufferedImage apply (BufferedImage input) {

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