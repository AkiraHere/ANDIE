package cosc202.andie;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well as zooming
 * in and out. 
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {
    
    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;  
    public Rectangle currentRect = null ; 
    public Rectangle rectToDraw = null ; 
    public Rectangle previousRectDrawn = new Rectangle() ;
    public boolean cropActive = false ;  
    public boolean mouseDragged = false ; 

    int currentMouseX = 0 ; 
    int currentMouseY = 0 ; 
    int currentMouseWidth = 0 ; 
    int currentMouseHeight = 0 ; 

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally as a percentage.
     * </p>
     */
    private double scale ;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%. Also implements a mouse listener
     * using {@link MouseInputAdapter} for cropping and drawing actions. 
     * </p>
     */
    public ImagePanel() {
        
        image = new EditableImage();
        scale = 1.0;

        class MyMouseListener extends MouseInputAdapter {

            public void mousePressed( MouseEvent e ) {

                if ( cropActive == true ) {

                    currentMouseX = e.getX() ;
                    currentMouseY = e.getY() ; 
                    currentRect = new Rectangle( currentMouseX , currentMouseY , 0 , 0 ) ; 
                    updateDrawableRect( getWidth() , getHeight() ) ;

                }
                
            }

            public void mouseDragged( MouseEvent e ) {

                if ( cropActive == true ) {

                    updateSize( e ) ; 
                    mouseDragged = true ; 

                }

            }

            public void mouseReleased( MouseEvent e ) {

                if ( cropActive == true ) {

                    updateSize( e ) ; 
                    mouseDragged = false  ;

                }

            }

            public void mouseExited( MouseEvent e ) {

                if ( cropActive == true ) {

                    cropActive = false ; 

                }

            }

            void updateSize( MouseEvent e ) {

                currentMouseX = e.getX() ;
                currentMouseY = e.getY() ;
                currentRect.setSize( currentMouseX - currentRect.x ,
                                     currentMouseY - currentRect.y ) ;

                updateDrawableRect( getWidth() , getHeight() ) ;
                Rectangle totalRepaint = rectToDraw.union( previousRectDrawn ) ;
                repaint( totalRepaint.x , totalRepaint.y ,
                         totalRepaint.width , totalRepaint.height ) ;

            }
  
        }

        MyMouseListener myListener = new MyMouseListener() ; 
        addMouseListener( myListener ) ; 
        addMouseMotionListener( myListener ) ; 

    }

    private void updateDrawableRect( int compWidth, int compHeight ) {
        int x = currentRect.x;
        int y = currentRect.y;
        int width = currentRect.width;
        int height = currentRect.height;
 
        //Make the width and height positive, if necessary.
        if (width < 0) {
            width = 0 - width;
            x = x - width + 1; 
            if (x < 0) {
                width += x; 
                x = 0;
            }
        }
        if (height < 0) {
            height = 0 - height;
            y = y - height + 1; 
            if (y < 0) {
                height += y; 
                y = 0;
            }
        }
 
        //The rectangle shouldn't extend past the drawing area.
        if ((x + width) > compWidth) {
            width = compWidth - x;
        }
        if ((y + height) > compHeight) {
            height = compHeight - y;
        }
       
        //Update rectToDraw after saving old value.
        if (rectToDraw != null) {
            previousRectDrawn.setBounds(
                        rectToDraw.x, rectToDraw.y, 
                        rectToDraw.width, rectToDraw.height);
            rectToDraw.setBounds(x, y, width, height);
        } else {
            rectToDraw = new Rectangle(x, y, width, height);
        }
    }

    public void cropActive( boolean status ) {
        currentRect = null ; 
        this.cropActive = status ; 
        repaint() ; 
    }

    private void clearPreviousDrawing(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        repaint(); 
    }



    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * </p>
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100*scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }

    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth()*scale), 
                                 (int) Math.round(image.getCurrentImage().getHeight()*scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        clearPreviousDrawing(g);
        Graphics2D g2  = (Graphics2D) g.create() ;
        if (image.hasImage()) {
            // Graphics2D g2  = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
        }
        if (cropActive == true) {
            
            BufferedImage temp = image.getCurrentImage() ; 
            RescaleOp op = new RescaleOp(.4f, 0, null); 
            BufferedImage output = op.filter( temp , null);
            g2.drawImage(output, null, 0, 0); 
            
            if (currentRect != null ) {

                int x = currentRect.x ; 
                int y = currentRect.y ; 
                
                int width = currentRect.width ; 
                int height = currentRect.height ; 

                if ( width < 0 ) {
                    width = Math.abs( width ) ; 
                    x = x - width ;
                }
                if ( height < 0 ) {
                    height = Math.abs( height ) ; 
                    y = y - height ; 
                }
                
                if ( width != 0 && height != 0 && currentMouseX < image.getCurrentImage().getWidth() - 1 && currentMouseY < image.getCurrentImage().getHeight() - 1 ) {
                    BufferedImage cropped = null ; 
                    cropped = image.getCurrentImage().getSubimage( x , y , width , height ) ;
                    g2.drawImage( cropped , null , x , y ) ; 
                }
                //Draw a rectangle on top of the image.
                float alpha = (float) 0.0 ;
                Color color = new Color(1, 0, 0, alpha); //Red 
                g2.setPaint(color); 
                g2.setXORMode(color); //Color of line varies
                                       //depending on image colors
                g2.drawRect(rectToDraw.x + 1 , rectToDraw.y + 1 , 
                       rectToDraw.width - 2 , rectToDraw.height - 2 ) ;
            }
        } 
        g2.dispose();
    }
}
