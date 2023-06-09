package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel directly 
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions implements KeyListener {
    
    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction(Andie.getLanguage("greyscale_name"), null, Andie.getLanguage("greyscale_description") , Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new ContrastAdjusterAction(Andie.getLanguage("contrast_name"), null, Andie.getLanguage("contrast_description"), Integer.valueOf(KeyEvent.VK_C)));
        actions.add(new BrightnessAdjusterAction(Andie.getLanguage("brightness_name"), null, Andie.getLanguage("brightness_description"), Integer.valueOf(KeyEvent.VK_B)));
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
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu( Andie.getLanguage("jmenu_colour") );

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
        }

    }
    /**
     * Action to adjust image's contrast levels
     */
    public class ContrastAdjusterAction extends ImageAction {

         /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */       
        ContrastAdjusterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Method to display input boxes for contrast and brightness percentage levels
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            int contrastPercent = 0;

            
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, -100, 100, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option1 = JOptionPane.showOptionDialog(null, radiusSpinner, Andie.getLanguage("contrast_percentage") , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
           

            if (option1 == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option1 == JOptionPane.OK_OPTION) {
                contrastPercent = radiusModel.getNumber().intValue();
            }

            target.getImage().apply(new ContrastAdjuster(contrastPercent));
            target.repaint();
            target.getParent().revalidate();
        }
    }
        /**
     * Action to adjust image's brightness levels
     */
    public class BrightnessAdjusterAction extends ImageAction {

        /**
        * <p>
        * Create a new convert-to-grey action.
        * </p>
        * 
        * @param name The name of the action (ignored if null).
        * @param icon An icon to use to represent the action (ignored if null).
        * @param desc A brief description of the action  (ignored if null).
        * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
        */       
       BrightnessAdjusterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
           super(name, icon, desc, mnemonic);
       }
       /**
        * <p>
        * Method to display input boxes for contrast and brightness percentage levels
        * </p>
        */
       public void actionPerformed(ActionEvent e) {
           int brightPercent = 0;
           
           SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, -100, 100, 1);
           JSpinner radiusSpinner = new JSpinner(radiusModel);
           int option = JOptionPane.showOptionDialog(null, radiusSpinner, Andie.getLanguage("brightness_percentage") , JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
          

           if (option == JOptionPane.CANCEL_OPTION) {
               return;
           } else if (option == JOptionPane.OK_OPTION) {
               brightPercent = radiusModel.getNumber().intValue();
           }

           target.getImage().apply(new BrightnessAdjuster(brightPercent));
           target.repaint();
           target.getParent().revalidate();
       }
       



    }
}
