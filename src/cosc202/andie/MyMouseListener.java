package cosc202.andie ;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

/**
 * <p>
 * Implementation of a mouse listener for mouse-related actions. 
 * </p>
 * 
 * <p>
 * The actual implementation of the mouse listener to be added to the {@link JPanel} which 
 * contains the {@link BufferedImage} ANDIE is currently working on. Responds to a variety of
 * mouse actions, to be used with processes such as cropping and drawing. 
 * </p>
 * 
 * @author Samuel Goddard
 * @version 1.0
 */
public class MyMouseListener extends MouseInputAdapter {

        public void mousePressed( MouseEvent e ) {

            System.out.println( "Working! :)" ) ; 

    }

}  

    
    
