package cosc202.andie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to draw an oval. 
 * </p>
 * 
 * <p>
 * Takes input parameters and produces a oval drawn on the 
 * working image. 
 * </p>
 * 
 * @author Samuel Goddard
 * @version 1.0
 */
public class DrawOval implements ImageOperation , java.io.Serializable {

    /**
     * <p>
     * Data fields
     * </p>
     * 
     * <p>
     * xPos - x coordinate of the upper-left corner of oval
     * yPos - y coordinate of the upper-left corner of oval
     * newWidth - width of oval
     * newHeight - height of oval
     * color - color of the oval
     * fill - whether the oval is to be filled solid
     * </p>
     */
    private int xPos , yPos , newWidth , newHeight , slider ; 
    private Color color ; 
    boolean fill ; 
    
    /**
     * @param xPos x coordinate value
     * @param yPos y coordinate value
     * @param newWidth width of rectangle
     * @param newHeight height of rectangle
     * @param color the color of the oval
     * @param fill whether the oval will be filled 
     */
    public DrawOval ( int xPos , int yPos , int newWidth , int newHeight , Color color , int slider , boolean fill ) {

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
    public DrawOval () {

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
     * Drawn a oval onto the working image. 
     * </p>
     * 
     * <p>
     * Oval is drawn using the parameters passed to the method. Brush size can 
     * be determined, along with the color. 
     * </p>
     * 
     * @param input the image to be drawn on
     * @return the drawn image 
     */
    public BufferedImage apply ( BufferedImage input ) {
        
        BufferedImage filteredImage = new BufferedImage( input.getColorModel() , input.copyData(null) , input.isAlphaPremultiplied() , null ) ;
        Graphics2D g2d = filteredImage.createGraphics() ; 
        g2d.setColor( color ) ;
        g2d.setStroke( new BasicStroke( slider ) ) ; 
        if ( fill == false ) {
            g2d.drawOval( xPos , yPos , newWidth , newHeight ) ; 
        } else {
            g2d.fillOval( xPos , yPos , newWidth , newHeight ) ; 
        }
        BufferedImage output = new BufferedImage( filteredImage.getColorModel() , filteredImage.copyData( filteredImage.getRaster().createCompatibleWritableRaster() ) , filteredImage.isAlphaPremultiplied() , null ) ; 
        return output ; 

    }
    
}
