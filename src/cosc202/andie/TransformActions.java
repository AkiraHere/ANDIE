package cosc202.andie;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

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
        actions.add(new ResizeImageAction("Resize", null, "Resize Image", Integer.valueOf(KeyEvent.VK_PLUS)));
        actions.add(new RotateImageAction("Rotate", null, "Rotate Image", Integer.valueOf(KeyEvent.VK_MINUS)));
        actions.add(new FlipImageAction("Flip", null, "Flip Image", Integer.valueOf(KeyEvent.VK_1)));
    }
        /**
     * <p>
     * Create a menu contianing the list of Transform actions.
     * </p>
     * 
     * @return The Transform menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Transform");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }
    
    public class ResizeImageAction extends ImageAction{
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
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, "Enter resize scale factor between 1 - 1000 (%)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
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

    
    public class RotateImageAction extends ImageAction {

        RotateImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] options = {"90 degrees left", "90 degrees right", "180 degrees"};
            int selectedOption = JOptionPane.showOptionDialog(target, "Select rotation option", "Rotate Image",
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

    public class FlipImageAction extends ImageAction{
        FlipImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] options = {"Horizontal", "Vertical"};
            int direction = JOptionPane.showOptionDialog(target,
                    "Which direction would you like to flip the image?",
                    "Flip Image",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);


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

