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
 * in and out. This class also handles mouseListener cases, allowing for the cropping and 
 * drawing on of images. This is managed through actionListeners that change boolean cases
 * that allow for different actions upon refreshing the EditableImage and graphics. 
 * </p>
 * 
 * <p>
 * A lot of coding inspiration and snippets came from 
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/mousemotionlistener.html"></a>
 * which involved a lot of the actual implementation of the mouseListeners and how the rectangle 
 * dimensions functioned around the mouse location on the component. 
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

    /** 
     * <p>
     * Rectangles to handle the simultaneous drawing of the cropping space
     * and shapes with the movement of the mouse. 
     * </p>
     * 
     * <p>
     * currentRect is the rectangle current on the screen influenced by the user and 
     * their mouse position. rectToDraw is the rectangle to update the method to the new location of 
     * the users mouse. previousRectDrawn is the most recent rectangle drawn used for 
     * calculating the new positon of a rectangle. 
     * </p>
     */
    private Rectangle currentRect = null ; 
    private Rectangle rectToDraw = null ; 
    private Rectangle previousRectDrawn = new Rectangle() ;

    /**
     * Boolean statements that dictate whether certain draw / crop 
     * features are active. 
     */
    private boolean cropActive = false ;  
    private boolean drawLineActive = false ; 
    private boolean drawCircleActive = false ; 
    private boolean drawRectangleActive = false ; 

    /**
     * Color of the shape to be drawn. 
     */
    private Color color ; 

    /* *
     * Temporary buffered image used as a background for the drawing 
     * functions. Could be implemented in use later to have multiple drawings 
     * on a single buffered image without the need for an imageoperation to 
     * save every step of the process. 
     */

    private BufferedImage tempDrawn = null ; 

    /**
     * <p>
     * Size of the brush (pixels). 
     * </p>
     */
    private int sliderSize = 1 ;  

    /**
     * <p>
     * Current mouse position on the screen. 
     * </p>
     */
    private int currentMouseX = 0 ; 
    private int currentMouseY = 0 ; 

    /**
     * <p>
     * The starting position of the mouse on the screen, which does not 
     * change until the mouse is released and clicked on a new location, upon
     * which this updates to the new click coordinates. 
     * </p>
     */
    private int startMouseX = 0 ; 
    private int startMouseY = 0 ; 

    /** 
     * <p>
     * The within bounds coordinates of the rectangle. 
     * </p>
     * 
     * <p>
     * Within bounds counts as anywhere where the EditableImage exists
     * within the JFrame. This is primarly used in later features to 
     * ensure that drawing and cropping features remain within the 
     * BufferedImage, rather than straying out onto the component instead. 
     * </p>
     */
    private int withinBoundsX = 0 ; 
    private int withinBoundsY = 0 ; 
    private int withinBoundsWidth = 0 ; 
    private int withinBoundsHeight = 0 ;

    /** 
     * <p>
     * The locked in coordinates of the rectangle. 
     * </p>
     * 
     * <p>
     * These coordinates are used when the rectangle strays outside of
     * the BufferedImage. In the case this happens, these x and y coordinates remain as the 
     * last point at which the rectangle was inside the image, allowing for drawings and 
     * cropping to snap to this point and remain, regardless of what the user does with 
     * their mouse outside the image.
     * </p>
     */
    private int lockedWithinBoundsX;
    private int lockedWithinBoundsY;
    private int lockedWithinBoundsWidth;
    private int lockedWithinBoundsHeight;

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
        
        // initialisation of the image and scale data fields 
        image = new EditableImage();
        scale = 1.0;

        /**
         * <p>
         * Implements the mouseInputAdapter class. 
         * </p>
         * 
         * <p>
         * Allows for the movement and actions of the users mouse to be recorded
         * in one class. Given certain boolean cases, these mouse actions 
         * result in actions such as drawing and cropping to take place, 
         * utilizing further methods such as {@link Cropper} and {@link DrawActions}. 
         * </p>
         */
        class MyMouseListener extends MouseInputAdapter {

            /** 
             * <p>
             * Mouse event for when the mouse is pressed. 
             * </p>
             * 
             * <p>
             * Checks multiple boolean statements regarding what feature 
             * is currently active. Given a mouse press, this will usually 
             * initiate the start of a draw or crop sequence, which will draw a 
             * shape that follows the mouse. 
             * </p>
             * 
             * @param e the mouse event passed to the method. 
             */
            public void mousePressed( MouseEvent e ) {

                if ( cropActive == true || drawLineActive == true || drawCircleActive == true || drawRectangleActive == true ) {

                    // gathers information on current mouse position and creates a new rectangle 
                    currentMouseX = e.getX() ;
                    currentMouseY = e.getY() ; 
                    startMouseX = e.getX() ; 
                    startMouseY = e.getY() ; 
                    currentRect = new Rectangle( currentMouseX , currentMouseY , 0 , 0 ) ;
                    
                    // tempDrawn allows for the draw feature to retain a clean background while drawing multiple lines 
                    if ( tempDrawn == null ) {
                        tempDrawn = image.getCurrentImage() ; 
                    }

                }
                
            }

            /** 
             * <p>
             * Mouse event for when the mouse is dragged. 
             * </p>
             * 
             * <p>
             * Checks multiple boolean statements regarding what feature 
             * is currently active. Given a mouse drag, this will usually 
             * follow through with the initiation of a mouse press, allowing
             * for the user to drag their mouse around and have a shape or 
             * crop window follow. 
             * </p>
             * 
             * @param e the mouse event passed to the method. 
             */
            public void mouseDragged( MouseEvent e ) {

                if ( cropActive == true || drawLineActive == true || drawCircleActive == true || drawRectangleActive == true) {

                    // updates and refreshes information regarding the rectangle and GUI
                    updateSize( e ) ; 

                }

            }

            /** 
             * <p>
             * Mouse event for when the mouse is released. 
             * </p>
             * 
             * <p>
             * Checks multiple boolean statements regarding what feature 
             * is currently active. Given a mouse release, this will usually 
             * end the current feature being utilised. In these cases, the 
             * information regarding the drawing / cropping is passed onto a 
             * corresponding class that implements the ImageOperation class, 
             * allowing for the image changes to be implemented on the stack. 
             * </p>
             * 
             * @param e the mouse event passed to the method. 
             */
            public void mouseReleased( MouseEvent e ) {

                // deals with the crop cases
                if ( cropActive == true ) {

                    updateSize( e ) ; 

                    // confirming the selected area as the area that needs to be cropped 
                    int result = JOptionPane.showConfirmDialog( null , "Want to crop the selected area?" , "Crop Selection" , JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE ) ; 
                    
                    // either applies the new image to the ImageOperation and EditableImage stack, or closes the current crop attempt
                    if ( result == JOptionPane.YES_OPTION ) {
                        cropActive = false ;
                        image.apply( new Cropper( (int)(withinBoundsX/scale) , (int)(withinBoundsY/scale) , (int)(withinBoundsWidth/scale) , (int)(withinBoundsHeight/scale) ) ) ; 

                    // both options here clear the rectangles back to default, or having zero depth, width, or height. 
                    } else if ( result == JOptionPane.NO_OPTION ) {
                        currentRect = new Rectangle( currentMouseX , currentMouseY , 0 , 0 ) ; 
                        updateDrawableRect( getWidth() , getHeight() ) ;
                    } else {
                        currentRect = new Rectangle( currentMouseX , currentMouseY , 0 , 0 ) ; 
                        updateDrawableRect( getWidth() , getHeight() ) ;
                    }

                }

                // deals with the drawing cases 
                else if ( drawLineActive == true || drawCircleActive == true || drawRectangleActive == true ) {
                    
                    updateSize(e) ; 

                    // deals with the drawing cases, utilising outside classes to finish the image and put it on the stack
                    if ( drawLineActive == true ) {

                        image.apply( new DrawLine( (int)(startMouseX/scale) , (int)(startMouseY/scale) , (int)(currentMouseX/scale) , (int)(currentMouseY/scale) , color , sliderSize ) ) ; 

                    } else if ( drawCircleActive == true ) { 

                        image.apply( new DrawOval( withinBoundsX , withinBoundsY , withinBoundsWidth , withinBoundsHeight , color , sliderSize ) ) ; 

                    } else if ( drawRectangleActive == true ) {

                        image.apply( new DrawRectangle( withinBoundsX , withinBoundsY , withinBoundsWidth , withinBoundsHeight , color , sliderSize ) ) ; 

                    }
                    

                }

            }

            /**
             * <p>
             * Mouse event for when the mouse exits the frame (JFrame)
             * </p>
             * 
             * <p>
             * Resets boolean values back to false of the different drawing and crop actions, 
             * as to stop them from being allowed to work with other menu options being messed around with. 
             */
            public void mouseExited( MouseEvent e ) {

                if ( cropActive == true || drawLineActive == true || drawCircleActive == true || drawRectangleActive == true) {

                    cropActive = false ; 
                    drawLineActive = false ; 
                    drawCircleActive = false ; 
                    drawRectangleActive = false ; 

                }

            }

            /**
             * <p>
             * Updates the size of the drawable rectangle and repaints.
             * </p>
             * 
             * @param e the mouse evevnt passed to the method
             */
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

        // implementation of the listener to the component
        MyMouseListener myListener = new MyMouseListener() ; 
        addMouseListener( myListener ) ; 
        addMouseMotionListener( myListener ) ; 

    }

    /** 
     * <p>
     * Updates the size of the drawable rectangle accounting for edge cases. 
     * </p>
     * 
     * <p>
     * Accounts for cases where the movement of the mouse may produce a negative 
     * result (such as if the mouse were to move to the left, negative x from starting 
     * position, and up, negative y from the starting position). Also deals with cases 
     * where the rectangle extends past the component edges. Doesn't repaint or 
     * refresh the GUI. 
     * </p>
     * 
     * @param compWidth the width of the component being drawn onto
     * @param compHeight the height of the component being drawn onto
     */
    private void updateDrawableRect( int compWidth, int compHeight ) {
        
        // current rectangle dimensions in a more accessible form
        int x = currentRect.x;
        int y = currentRect.y;
        int width = currentRect.width;
        int height = currentRect.height;
 
        // make the width and height positive, if necessary.
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
 
        // the rectangle shouldn't extend past the drawing area.
        if ((x + width) > compWidth) {
            width = compWidth - x;
        }
        if ((y + height) > compHeight) {
            height = compHeight - y;
        }
       
        // update rectToDraw after saving old value.
        if (rectToDraw != null) {
            previousRectDrawn.setBounds(
                        rectToDraw.x, rectToDraw.y, 
                        rectToDraw.width, rectToDraw.height);
            rectToDraw.setBounds(x, y, width, height);
        } else {
            rectToDraw = new Rectangle(x, y, width, height);
        }
    }

    /** 
     * <p>
     * Sets crop to active and refreshes the GUI. 
     * </p>
     * 
     * @param status whether crop is active or not
     */
    public void cropActive( boolean status ) {
        currentRect = null ; 
        this.cropActive = status ; 
        repaint() ; 
    }

    /**
     * <p>
     * Sets draw line to active and refreshes the GUI. 
     * </p?
     * 
     * @param status whether draw line is active or not
     */
    public void drawLineActive( boolean status ) {
        currentRect = null ; 
        this.drawLineActive = status ; 
        repaint() ; 
    }

    /**
     * <p>
     * Sets draw circle to active and refreshes the GUI. 
     * </p>
     * 
     * @param status whether draw circle is active or not 
     */
    public void drawCircleActive( boolean status ) {
        currentRect = null ; 
        this.drawCircleActive = status ; 
        repaint() ; 
    }

    /**
     * <p>
     * Sets draw rectangle to active and refreshes the GUI. 
     * </p>
     * 
     * @param status whether the draw rectangle is active or not
     */
    public void drawRectangleActive( boolean status ) {
        currentRect = null ; 
        this.drawRectangleActive = status ; 
        repaint() ; 
    }

    /**
     * <p>
     * Takes user input from JColorChooser and sets the 
     * color of the shapes being drawn. 
     * </p>
     * 
     * @param color the color of the shapes being drawn
     */
    public void setColor( Color color ) {
        this.color = color ; 
    }

    /**
     * <p>
     * Takes user input from a radius wheel and changes the 
     * size of the brush for drawing. 
     * </p>
     * 
     * @param sliderSize
     */
    public void setSlider( int sliderSize ) {
        this.sliderSize = sliderSize ; 
    }

    /**
     * <p>
     * Clears the previous drawings from the background. 
     * </p>
     * 
     * <p>
     * This is namely to avoid excess lines being left on the component while moving 
     * the mouse around. Due to the nature of the updating rectangle and the buffered image, 
     * the rectangle can leave outlines as it moves. Thus, this short method allows for the 
     * outlined background image to be refreshed and repainted each call of the draw and crop method. 
     * </p>
     * 
     * @param g the graphics instance used to repaint the component and image
     */
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
     * <p>
     * Deals with all of the cases of different draw and crop methods of the shape following the mouse. 
     * Uses booleans to determine which function is chosen. 
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        
        // general housekeeping
        super.paintComponent(g);
        clearPreviousDrawing(g);
        Graphics2D g2  = (Graphics2D) g.create() ;
        g2.setColor( color ) ;
        g2.setStroke( new BasicStroke( sliderSize ) ) ; 
        
        // checks if an image has been loaded into ANDIE, and if so, draws it on the component
        if (image.hasImage()) {
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
        }

        // if any of the draw or crop methods are active
        if (cropActive == true || drawLineActive == true || drawCircleActive == true || drawRectangleActive == true && image.hasImage() ) {
            
            // only occurs if crop is active - draws a darkened background to distinguish crop area
            if ( cropActive == true ) { 
                BufferedImage temp = image.getCurrentImage() ; 
                RescaleOp op = new RescaleOp(.4f, 0, null); 
                BufferedImage output = op.filter( temp , null);
                g2.drawImage(output, null, 0, 0); 
            } 
            
            // only occurs if mouse has been pressed - deals with out of bound cases - variation of updateDrawableRect method
            if (currentRect != null ) {

                // more easily accessible variables
                int x = currentRect.x ; 
                int y = currentRect.y ; 
                int width = currentRect.width ; 
                int height = currentRect.height ; 

                // deals with negatives
                if ( width < 0 ) {
                    width = Math.abs( width ) ; 
                    x = x - width ;
                }
                if ( height < 0 ) {
                    height = Math.abs( height ) ; 
                    y = y - height ; 
                }

                // deals with out of bound cases, assigning them to point from which they left the image
                if( width > image.getCurrentImage().getWidth() - x  ) {
                    withinBoundsWidth = image.getCurrentImage().getWidth() - x ; 
                }
                if( height > image.getCurrentImage().getHeight() - y ) {
                    withinBoundsHeight = image.getCurrentImage().getHeight() - y ; 
                }  

                // cropping 
                if ( width != 0 && height != 0 && currentMouseX < (int)(image.getCurrentImage().getWidth()*scale) && currentMouseY < (int)(image.getCurrentImage().getHeight()*scale) && cropActive == true ) {
                    
                    // accounts for border loss & control as well as scale 
                    withinBoundsX = (int)(x/scale) ; 
                    withinBoundsY = (int)(y/scale) ; 
                    withinBoundsWidth = (int)(width/scale) ; 
                    withinBoundsHeight = (int)(height/scale) ;

                    lockedWithinBoundsX = withinBoundsX ; 
                    lockedWithinBoundsY = withinBoundsY ; 
                    lockedWithinBoundsWidth = withinBoundsWidth ; 
                    lockedWithinBoundsHeight = withinBoundsHeight ; 

                    // draws image with variables based on mouse location 
                    BufferedImage cropped = null ; 
                    cropped = image.getCurrentImage().getSubimage( withinBoundsX , withinBoundsY , withinBoundsWidth , withinBoundsHeight ) ;
                    g2.drawImage( cropped , null , withinBoundsX , withinBoundsY ) ; 
                } else if ( width != 0 && height != 0 && cropActive == true ) {
                    BufferedImage cropped = image.getCurrentImage().getSubimage( lockedWithinBoundsX , lockedWithinBoundsY , lockedWithinBoundsWidth , lockedWithinBoundsHeight ) ; 
                    g2.drawImage( cropped , null , lockedWithinBoundsX , lockedWithinBoundsY );  
                }

                // drawing a line 
                if ( width != 0 && height != 0 && currentMouseX < (int)(image.getCurrentImage().getWidth()*scale) && currentMouseY < (int)(image.getCurrentImage().getHeight()*scale) && drawLineActive == true ) {
                    
                    // more easily accessible variables
                    withinBoundsX = (int)(x/scale) ; 
                    withinBoundsY = (int)(y/scale) ; 
                    withinBoundsWidth = (int)(width/scale) ; 
                    withinBoundsHeight = (int)(height/scale) ;

                    lockedWithinBoundsX = withinBoundsX ; 
                    lockedWithinBoundsY = withinBoundsY ; 
                    lockedWithinBoundsWidth = withinBoundsWidth ; 
                    lockedWithinBoundsHeight = withinBoundsHeight ; 

                    // draws images with variables based on location
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
                    
                    // more easily accessable variable names 
                    withinBoundsX = (int)(x/scale) ; 
                    withinBoundsY = (int)(y/scale) ; 
                    withinBoundsWidth = (int)(width/scale) ; 
                    withinBoundsHeight = (int)(height/scale) ;

                    lockedWithinBoundsX = withinBoundsX ; 
                    lockedWithinBoundsY = withinBoundsY ; 
                    lockedWithinBoundsWidth = withinBoundsWidth ; 
                    lockedWithinBoundsHeight = withinBoundsHeight ; 

                    // draws images with variables based on location
                    g2.drawOval( withinBoundsX , withinBoundsY , withinBoundsWidth , withinBoundsHeight ) ; 

                } else if ( width != 0 && height != 0 && drawCircleActive == true ) {

                    g2.drawOval( lockedWithinBoundsX , lockedWithinBoundsY , lockedWithinBoundsWidth , lockedWithinBoundsHeight );  

                }

                // drawing a rectangle
                if ( width != 0 && height != 0 && currentMouseX < (int)(image.getCurrentImage().getWidth()*scale) && currentMouseY < (int)(image.getCurrentImage().getHeight()*scale) && drawRectangleActive == true ) {
                    
                    // easier access to variables
                    withinBoundsX = (int)(x/scale) ; 
                    withinBoundsY = (int)(y/scale) ; 
                    withinBoundsWidth = (int)(width/scale) ; 
                    withinBoundsHeight = (int)(height/scale) ;

                    lockedWithinBoundsX = withinBoundsX ; 
                    lockedWithinBoundsY = withinBoundsY ; 
                    lockedWithinBoundsWidth = withinBoundsWidth ; 
                    lockedWithinBoundsHeight = withinBoundsHeight ; 

                    // draws a image based on variables based on location
                    g2.drawRect( withinBoundsX , withinBoundsY , withinBoundsWidth , withinBoundsHeight ) ; 

                } else if ( width != 0 && height != 0 && drawRectangleActive == true ) {

                    g2.drawRect( lockedWithinBoundsX , lockedWithinBoundsY , lockedWithinBoundsWidth , lockedWithinBoundsHeight );  

                }
            }

        }

        // clears used resources 
        g2.dispose();
    }
}
