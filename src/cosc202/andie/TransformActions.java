package cosc202.andie;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * <p>
 * Actions provided by the Transform menu.
 * </p>
 * 
 * <p>
 * The transform menu provides options for manipulating the size, rotation and plane of
 * an image.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Nick Garner, Tan Robertson
 * @version 1.0
 */
public class TransformActions {
 
    /**
     * A list of actions for the Transform menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Transform menu actions.
     * </p>
     */
    public TransformActions() {
        actions = new ArrayList<Action>();
        actions.add(new ResizeImageAction(Andie.getLanguage("resize_name"), null, Andie.getLanguage("resize_description"), Integer.valueOf(KeyEvent.VK_PLUS)));
        actions.add(new RotateImageAction(Andie.getLanguage("rotate_name"), null, Andie.getLanguage("rotate_description"), Integer.valueOf(KeyEvent.VK_MINUS)));
        actions.add(new FlipImageAction(Andie.getLanguage("flip_name"), null, Andie.getLanguage("flip_description"), Integer.valueOf(KeyEvent.VK_1)));
    }
    /**
     * <p>
     * Create a menu contianing the list of Transform actions.
     * </p>
     * 
     * @return The Transform menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.getLanguage("jmenu_transform"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }
    
    /*
     * Resizes the image. 
     */
    public class ResizeImageAction extends ImageAction{
        /**
         * <p>
         * Create a new Resize image action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */       
        ResizeImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Method to display an input box for a rescale percentage and applies it to resize method
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            int resizeFactor = 0;
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(100, 1, 1000, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, Andie.getLanguage("resize_scale"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                resizeFactor = radiusModel.getNumber().intValue();
            }
            
            target.getImage().apply(new ResizeImage(resizeFactor));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * Rotates the image.
     */
    public class RotateImageAction extends ImageAction {
        /**
         * <p>
         * Create a new Rotate image action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */ 
        RotateImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        
        /**
         * <p>
         * Method to display a pop-up box that allows for choice of rotation.
         * </p>
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] options = { 
                    Andie.getLanguage("degrees_right") , 
                    Andie.getLanguage("degrees_left") , 
                    Andie.getLanguage("degrees_flip")
            };
            int selectedOption = JOptionPane.showOptionDialog(target, Andie.getLanguage("rotation_message"), Andie.getLanguage("rotation_title"),
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.PLAIN_MESSAGE, 
                    null, options, options[0]);
    
            int degrees;
            if (selectedOption == 0) {
                degrees = 90;
            } else if (selectedOption == 1) {
                degrees = -90;
            } else {
                degrees = -180;
            }
    
            target.getImage().apply(new RotateImage(degrees));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /*
     * Flips the image.
     */
    public class FlipImageAction extends ImageAction{
        /**
         * <p>
         * Create a new flip image action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */ 
        FlipImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**
         * <p>
         * Method to display a pop-up box that allows for choice of flip.
         * </p>
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] options = {Andie.getLanguage("flip_horizontal"), Andie.getLanguage("flip_vertical")};
            int direction = JOptionPane.showOptionDialog(target,
                    Andie.getLanguage("flip_message"),
                    Andie.getLanguage("flip_title"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    -1);

    if (direction == -1) {
        return;
    } 
    if (direction == 0) {
        target.getImage().apply(new FlipImage(true));
    } else {
        target.getImage().apply(new FlipImage(false));
    }

            target.repaint();
            target.getParent().revalidate();
        }
    }
}

