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
    public boolean drawLineActive = false ; 
    public boolean drawCircleActive = false ; 
    public boolean drawRectangleActive = false ; 

    Color color ; 

    BufferedImage cropped = null ;
    BufferedImage tempDrawn = null ; 

    public boolean cropSelection = false ; 

    int currentMouseX = 0 ; 
    int currentMouseY = 0 ; 
    int currentMouseWidth = 0 ; 
    int currentMouseHeight = 0 ; 

    int startMouseX = 0 ; 
    int startMouseY = 0 ; 

    int withinBoundsX = 0 ; 
    int withinBoundsY = 0 ; 
    int withinBoundsWidth = 0 ; 
    int withinBoundsHeight = 0 ;

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
    private int lockedWithinBoundsX;
    private int lockedWithinBoundsY;
    private int lockedWithinBoundsWidth;
    int lockedWithinBoundsHeight;

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

                if ( cropActive == true || drawLineActive == true || drawCircleActive == true || drawRectangleActive == true ) {

                    currentMouseX = e.getX() ;
                    currentMouseY = e.getY() ; 
                    startMouseX = e.getX() ; 
                    startMouseY = e.getY() ; 
                    currentRect = new Rectangle( currentMouseX , currentMouseY , 0 , 0 ) ;
                    
                    if ( tempDrawn == null ) {
                        tempDrawn = image.getCurrentImage() ; 
                    }

                }
                
            }

            public void mouseDragged( MouseEvent e ) {

                if ( cropActive == true || drawLineActive == true || drawCircleActive == true || drawRectangleActive == true) {

                    updateSize( e ) ; 

                }

            }

            public void mouseReleased( MouseEvent e ) {

                if ( cropActive == true ) {

                    updateSize( e ) ; 
                    int result = JOptionPane.showConfirmDialog( null , "Want to crop the selected area?" , "Crop Selection" , JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE ) ; 
                    if ( result == JOptionPane.YES_OPTION ) {
                        cropActive = false ;
                        image.apply( new Cropper( (int)(withinBoundsX/scale) , (int)(withinBoundsY/scale) , (int)(withinBoundsWidth/scale) , (int)(withinBoundsHeight/scale) ) ) ; 
                    } else if ( result == JOptionPane.NO_OPTION ) {
                        currentRect = new Rectangle( currentMouseX , currentMouseY , 0 , 0 ) ; 
                        updateDrawableRect( getWidth() , getHeight() ) ;
                    } else {
                        currentRect = new Rectangle( currentMouseX , currentMouseY , 0 , 0 ) ; 
                        updateDrawableRect( getWidth() , getHeight() ) ;
                    }

                }

                else if ( drawLineActive == true || drawCircleActive == true || drawRectangleActive == true ) {
                    updateSize(e) ; 
                    if ( drawLineActive == true ) {

                        image.apply( new DrawLine( (int)(startMouseX/scale) , (int)(startMouseY/scale) , (int)(currentMouseX/scale) , (int)(currentMouseY/scale) , color ) ) ; 

                    } else if ( drawCircleActive == true ) { 

                        image.apply( new DrawOval( (int)(withinBoundsX/scale) , (int)(withinBoundsY/scale) , (int)(withinBoundsWidth/scale) , (int)(withinBoundsHeight/scale) , color ) ) ; 

                    } else if ( drawRectangleActive == true ) {

                        image.apply( new DrawRectangle( (int)(withinBoundsX/scale) , (int)(withinBoundsY/scale) , (int)(withinBoundsWidth/scale) , (int)(withinBoundsHeight/scale) , color ) ) ; 

                    }
                    

                }

            }

            public void mouseExited( MouseEvent e ) {

                if ( cropActive == true || drawLineActive == true || drawCircleActive == true || drawRectangleActive == true) {

                    cropActive = false ; 
                    drawLineActive = false ; 
                    drawCircleActive = false ; 
                    drawRectangleActive = false ; 

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

    public void drawLineActive( boolean status ) {
        currentRect = null ; 
        this.drawLineActive = status ; 
        repaint() ; 
    }

    public void drawCircleActive( boolean status ) {
        currentRect = null ; 
        this.drawCircleActive = status ; 
        repaint() ; 
    }

    public void drawRectangleActive( boolean status ) {
        currentRect = null ; 
        this.drawRectangleActive = status ; 
        repaint() ; 
    }

    public void setColor( Color color ) {
        this.color = color ; 
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
        g2.setColor( color ) ; 
        
        if (image.hasImage()) {
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
        }

        if (cropActive == true || drawLineActive == true || drawCircleActive == true || drawRectangleActive == true && image.hasImage() ) {
            
            if ( cropActive == true ) { 
                BufferedImage temp = image.getCurrentImage() ; 
                RescaleOp op = new RescaleOp(.4f, 0, null); 
                BufferedImage output = op.filter( temp , null);
                g2.drawImage(output, null, 0, 0); 
            } 
            
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

                if( width > image.getCurrentImage().getWidth() - x  ) {
                    withinBoundsWidth = image.getCurrentImage().getWidth() - x ; 
                }
                if( height > image.getCurrentImage().getHeight() - y ) {
                    withinBoundsHeight = image.getCurrentImage().getHeight() - y ; 
                }  

                // cropping 
                if ( width != 0 && height != 0 && currentMouseX < (int)(image.getCurrentImage().getWidth()*scale) && currentMouseY < (int)(image.getCurrentImage().getHeight()*scale) && cropActive == true ) {
                    
                    withinBoundsX = (int)(x/scale) ; 
                    withinBoundsY = (int)(y/scale) ; 
                    withinBoundsWidth = (int)(width/scale) ; 
                    withinBoundsHeight = (int)(height/scale) ;

                    lockedWithinBoundsX = withinBoundsX ; 
                    lockedWithinBoundsY = withinBoundsY ; 
                    lockedWithinBoundsWidth = withinBoundsWidth ; 
                    lockedWithinBoundsHeight = withinBoundsHeight ; 

                    BufferedImage cropped = null ; 
                    cropped = image.getCurrentImage().getSubimage( withinBoundsX , withinBoundsY , withinBoundsWidth , withinBoundsHeight ) ;
                    g2.drawImage( cropped , null , withinBoundsX , withinBoundsY ) ; 
                } else if ( width != 0 && height != 0 && cropActive == true ) {
                    BufferedImage cropped = image.getCurrentImage().getSubimage( lockedWithinBoundsX , lockedWithinBoundsY , lockedWithinBoundsWidth , lockedWithinBoundsHeight ) ; 
                    g2.drawImage( cropped , null , lockedWithinBoundsX , lockedWithinBoundsY );  
                }

                // drawing a line 
                if ( width != 0 && height != 0 && currentMouseX < (int)(image.getCurrentImage().getWidth()*scale) && currentMouseY < (int)(image.getCurrentImage().getHeight()*scale) && drawLineActive == true ) {
                    
                    withinBoundsX = (int)(x/scale) ; 
                    withinBoundsY = (int)(y/scale) ; 
                    withinBoundsWidth = (int)(width/scale) ; 
                    withinBoundsHeight = (int)(height/scale) ;

                    lockedWithinBoundsX = withinBoundsX ; 
                    lockedWithinBoundsY = withinBoundsY ; 
                    lockedWithinBoundsWidth = withinBoundsWidth ; 
                    lockedWithinBoundsHeight = withinBoundsHeight ; 

                    if ( startMouseX < currentMouseX && startMouseY < currentMouseY || startMouseX > currentMouseX && startMouseY > currentMouseY ) {
                        g2.drawLine( withinBoundsX , withinBoundsY , withinBoundsX + withinBoundsWidth , withinBoundsY + withinBoundsHeight ) ; 
                    } else { 
                        g2.drawLine( withinBoundsX , withinBoundsY + withinBoundsHeight , withinBoundsX + withinBoundsWidth , withinBoundsY ) ;
                    }
                } else if ( width != 0 && height != 0 && drawLineActive == true ) {
                
                    g2.drawLine( lockedWithinBoundsX , lockedWithinBoundsY , lockedWithinBoundsX + lockedWithinBoundsWidth , lockedWithinBoundsY + lockedWithinBoundsHeight ) ;  
                    
                }

                // drawing a oval
                if ( width != 0 && height != 0 && currentMouseX < (int)(image.getCurrentImage().getWidth()*scale) && currentMouseY < (int)(image.getCurrentImage().getHeight()*scale) && drawCircleActive == true ) {
                    
                    withinBoundsX = (int)(x/scale) ; 
                    withinBoundsY = (int)(y/scale) ; 
                    withinBoundsWidth = (int)(width/scale) ; 
                    withinBoundsHeight = (int)(height/scale) ;

                    lockedWithinBoundsX = withinBoundsX ; 
                    lockedWithinBoundsY = withinBoundsY ; 
                    lockedWithinBoundsWidth = withinBoundsWidth ; 
                    lockedWithinBoundsHeight = withinBoundsHeight ; 

                    g2.drawOval( withinBoundsX , withinBoundsY , withinBoundsWidth , withinBoundsHeight ) ; 

                } else if ( width != 0 && height != 0 && drawCircleActive == true ) {

                    g2.drawOval( lockedWithinBoundsX , lockedWithinBoundsY , lockedWithinBoundsWidth , lockedWithinBoundsHeight );  

                }

                // drawing a rectangle
                if ( width != 0 && height != 0 && currentMouseX < (int)(image.getCurrentImage().getWidth()*scale) && currentMouseY < (int)(image.getCurrentImage().getHeight()*scale) && drawRectangleActive == true ) {
                    
                    withinBoundsX = (int)(x/scale) ; 
                    withinBoundsY = (int)(y/scale) ; 
                    withinBoundsWidth = (int)(width/scale) ; 
                    withinBoundsHeight = (int)(height/scale) ;

                    lockedWithinBoundsX = withinBoundsX ; 
                    lockedWithinBoundsY = withinBoundsY ; 
                    lockedWithinBoundsWidth = withinBoundsWidth ; 
                    lockedWithinBoundsHeight = withinBoundsHeight ; 

                    g2.drawRect( withinBoundsX , withinBoundsY , withinBoundsWidth , withinBoundsHeight ) ; 

                } else if ( width != 0 && height != 0 && drawRectangleActive == true ) {

                    g2.drawRect( lockedWithinBoundsX , lockedWithinBoundsY , lockedWithinBoundsWidth , lockedWithinBoundsHeight );  

                }
            }

        }
        g2.dispose();
    }
}
