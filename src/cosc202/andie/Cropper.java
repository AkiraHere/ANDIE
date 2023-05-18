package cosc202.andie;

import java.awt.image.BufferedImage;

public class Cropper implements ImageOperation , java.io.Serializable {

    private int xPos , yPos , newWidth , newHeight ; 
    
    public Cropper ( int xPos , int yPos , int newWidth , int newHeight ) {

        this.xPos = xPos ; 
        this.yPos = yPos ; 
        this.newWidth = newWidth ; 
        this.newHeight = newHeight ; 

    }

    public Cropper () {

        this.xPos = 0 ; 
        this.yPos = 0 ; 
        this.newHeight = 0 ; 
        this.newWidth = 0 ; 

    }

    @Override
    public BufferedImage apply ( BufferedImage input ) {
        
        BufferedImage filteredImage = new BufferedImage( input.getColorModel() , input.copyData(null) , input.isAlphaPremultiplied() , null ) ;
        BufferedImage cropped = filteredImage.getSubimage( xPos , yPos , newWidth, newHeight ) ; 
        BufferedImage output = new BufferedImage( cropped.getColorModel() , cropped.copyData( cropped.getRaster().createCompatibleWritableRaster() ) , filteredImage.isAlphaPremultiplied() , null ) ; 
        return output ; 

    }
    
}
