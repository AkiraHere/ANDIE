package cosc202.andie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to draw a line.  
 * </p>
 * 
 * <p>
 * Takes a series of values and uses it to draw a line 
 * on the worked image. 
 * </p>
 * 
 * @author Samuel Goddard
 * @version 1.0
 */
public class DrawLine implements ImageOperation , java.io.Serializable {

    /**
     * <p>
     * Data fields
     * </p>
     * 
     * <p>
     * xPos - x coordinate of the upper-left corner of line
     * yPos - y coordinate of the upper-left corner of line
     * newWidth - width of line
     * newHeight - height of line
     * color - color of the line
     */
    private int xPos , yPos , newWidth , newHeight , slider ; 
    private Color color ; 
    
    /**
     * @param xPos x coordinate value
     * @param yPos y coordinate value
     * @param newWidth width of line
     * @param newHeight height of line
     * @param color color of the line
     */
    public DrawLine ( int xPos , int yPos , int newWidth , int newHeight , Color color , int slider ) {

        this.xPos = xPos ; 
        this.yPos = yPos ; 
        this.newWidth = newWidth ; 
        this.newHeight = newHeight ; 
        this.color = color ; 
        this.slider = slider ; 

    }

    /**
     * Default constructor
     */
    public DrawLine () {

        this.xPos = 0 ; 
        this.yPos = 0 ; 
        this.newHeight = 0 ; 
        this.newWidth = 0 ; 
        this.color = Color.WHITE ; 
        this.slider = 1 ; 

    }
   /**
     * <p>
     * Applies a line to the image. 
     * </p>
     * 
     * <p>
     * Takes the input values and applies the line correspondingly. 
     * Brush size and and line placement are done here, alongwith the raster 
     * checking to make sure the bufferedImage matches the previous version. 
     * </p>
     * 
     * @param input the image to be drawn on
     * @return the drawn on image 
     */
    public BufferedImage apply ( BufferedImage input ) {
        
        BufferedImage filteredImage = new BufferedImage( input.getColorModel() , input.copyData(null) , input.isAlphaPremultiplied() , null ) ;
        Graphics2D g2d = filteredImage.createGraphics() ; 
        g2d.setColor( color ) ; 
        g2d.setStroke( new BasicStroke( slider ) ) ;
        g2d.drawLine( xPos , yPos , newWidth , newHeight ) ; 
        BufferedImage output = new BufferedImage( filteredImage.getColorModel() , filteredImage.copyData( filteredImage.getRaster().createCompatibleWritableRaster() ) , filteredImage.isAlphaPremultiplied() , null ) ; 
        return output ; 

    }
    
}
