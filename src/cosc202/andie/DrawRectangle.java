package cosc202.andie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to draw a rectangle. 
 * </p>
 * 
 * <p>
 * Takes a series of inputs and uses them to draw a rectangle, potentially
 * with different outputs. Brush size and whether the rectangle is filled can 
 * all be altered. 
 * </p>
 * 
 * @author Samuel Goddard
 * @version 1.0
 */
public class DrawRectangle implements ImageOperation , java.io.Serializable {

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
     * color - color of the rectangle 
     * fill - whether the rectangle will be filled 
     * </p>
     */
    private int xPos , yPos , newWidth , newHeight , slider ;
    private Color color ;  
    private boolean fill ; 
    
    /**
     * @param xPos x coordinate value
     * @param yPos y coordinate value
     * @param newWidth width of rectangle
     * @param newHeight height of rectangle
     * @param color the color of the rectangle
     * @param fill whether the rectangle will be filled
     */
    public DrawRectangle ( int xPos , int yPos , int newWidth , int newHeight , Color color , int slider , boolean fill ) {

        this.xPos = xPos ; 
        this.yPos = yPos ; 
        this.newWidth = newWidth ; 
        this.newHeight = newHeight ; 
        this.color = color ; 
        this.slider = slider ; 
        this.fill = fill ; 

    }

    /**
     * Default constructor
     */
    public DrawRectangle () {

        this.xPos = 0 ; 
        this.yPos = 0 ; 
        this.newHeight = 0 ; 
        this.newWidth = 0 ; 
        this.color = Color.WHITE ; 
        this.slider = 1 ; 
        this.fill = false ; 

    }
   /**
     * <p>
     * Applies a rectangle to the image. 
     * </p>
     * 
     * <p>
     * Rectangle output depends on states of the parameters passed. Rectangle can
     * have its line thickness changed via the slider int, and color can also be 
     * changed by user input. The rectangle image is also put through a series 
     * of bufferedImages to make sure the raster and other aspects match the working image. 
     * </p>
     * 
     * @param input the image to be drawn
     * @return the drawn image
     */
    public BufferedImage apply ( BufferedImage input ) {
        
        BufferedImage filteredImage = new BufferedImage( input.getColorModel() , input.copyData(null) , input.isAlphaPremultiplied() , null ) ;
        Graphics2D g2d = filteredImage.createGraphics() ; 
        g2d.setColor( color ) ; 
        g2d.setStroke( new BasicStroke( slider ) ) ;
        if ( fill == false ) { 
            g2d.drawRect( xPos , yPos , newWidth , newHeight ) ; 
        } else {
            g2d.fillRect( xPos , yPos , newWidth , newHeight ) ; 
        }
        BufferedImage output = new BufferedImage( filteredImage.getColorModel() , filteredImage.copyData( filteredImage.getRaster().createCompatibleWritableRaster() ) , filteredImage.isAlphaPremultiplied() , null ) ; 
        return output ; 

    }
    
}