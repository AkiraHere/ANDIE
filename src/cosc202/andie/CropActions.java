package cosc202.andie;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.* ;
/**
 * <p>
 * Class for Mouse Actions
 * </p>
 * 
 * <p>
 * This class adds to the Mouse menu and calls to their respective classes
 * This includes a crop function
 * </p>
 *  
 * @author Samuel Goddard
 * @version 1.0
 */
public class CropActions implements KeyListener {
    
    /** A list of actions for the Mouse menu. */
    protected ArrayList<Action> actions ; 
    /**
     * <p>
     * Create a set of Mouse menu actions.
     * </p>
     */
    public CropActions() {
       
        actions = new ArrayList<Action>();
        actions.add( new CropAction( Andie.getLanguage("crop") , null , Andie.getLanguage("crop_description") , Integer.valueOf( KeyEvent.VK_X ) ) ) ;

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(e.isControlDown()) {
            for (Action action : actions) {
                Integer actionKeyCode = (Integer) action.getValue(Action.MNEMONIC_KEY);
                if (actionKeyCode != null && actionKeyCode == keyCode) {
                    action.actionPerformed(null);
                    break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * <p>
     * Creates a menu containing a list of all mouse actions
     * </p>
     * @return the mouse menu
     */
    public JMenu createMenu() {
        
        JMenu mouseMenu = new JMenu( Andie.getLanguage("crop_name") ) ;

        for ( Action action : actions ) {

            mouseMenu.add( new JMenuItem( action ) ) ;

        }

        return mouseMenu;

    }
    /**
     * <p>
     * Action to start the action of cropping an image
     * </p>
     * 
     * @see Cropper
     */
    public class CropAction extends ImageAction {
        /**
         * <p>
         * Starts cropping action
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        CropAction( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon , desc , mnemonic ) ;


        }
        /**
         * <p>
         * Callback for when crop is selected
         * </p>
         * 
         * @param e The event triggering this callback. 
         */
        public void actionPerformed( ActionEvent e ) {

            if ( target.getImage().hasImage() == true ) {  
                target.cropActive( true ) ; 
            } else {
                JOptionPane.showMessageDialog(null, Andie.getLanguage("error_no_image_operations"), Andie.getLanguage("error_title"), JOptionPane.ERROR_MESSAGE);
            }
            
        }

    }

    /**
     * <p>
     * Action to stop the action of cropping an image
     * </p>
     * 
     * @see Cropper
     */
    public class StopAction extends ImageAction {

        /**
         * <p>
         * Stops cropping action
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        StopAction( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon , desc , mnemonic ) ;

        }
        /**
         * <p>
         * Callback for when stopping crop is confirmed
         * </p>
         * 
         * @param e The event triggering this callback. 
         */
        public void actionPerformed( ActionEvent e ) {

            target.cropActive( false ) ; 

        }

    }

}