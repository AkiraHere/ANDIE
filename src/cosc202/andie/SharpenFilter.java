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

        // creating the a copy of the input image
        BufferedImage underImage = new BufferedImage( input.getWidth() , input.getHeight() , input.getType() ) ; 
        Graphics2D g2D = underImage.createGraphics() ; 
        g2D.drawImage( input , 0 , 0 , null ) ;
        g2D.dispose() ; 
        
        // copy is then resized to accomodate border pixel loss due to filtering 
        int newWidth = input.getWidth() + 4 ; 
        int newHeight = input.getHeight() + 4 ; 
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, underImage.getType()) ;
        AffineTransform transform = AffineTransform.getScaleInstance(
                                    (double)newWidth / input.getWidth() ,
                                    (double)newHeight / input.getHeight() ) ;
        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR ) ;
        resizedImage = operation.filter( underImage , resizedImage ) ;
        
        // original image overlays the resized base image
        BufferedImage mergedImage = new BufferedImage( resizedImage.getWidth() , resizedImage.getHeight() , resizedImage.getType() ) ; 
        Graphics2D g2d = mergedImage.createGraphics() ;
        g2d.drawImage( resizedImage , 0 , 0 , null ) ; 
        g2d.drawImage( input , 2 , 2, null ) ;
        g2d.dispose() ;

        Kernel kernel = new Kernel( 3 , 3 , array ) ; 
        ConvolveOp convOp = new ConvolveOp(kernel) ; 
        BufferedImage output = new BufferedImage( input.getColorModel() , input.copyData(null) , input.isAlphaPremultiplied() , null ) ; 
        convOp.filter(mergedImage, output) ; 

        return output; 

    }

}