package cosc202.andie;
import java.util.*;
import java.awt.event.*;
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
        ResizeImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        @Override
        public void actionPerformed(ActionEvent e) {

            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
    }
    public class RotateImageAction extends ImageAction{
        RotateImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        @Override
        public void actionPerformed(ActionEvent e) {

            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
    }
    public class FlipImageAction extends ImageAction{
        FlipImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        @Override
        public void actionPerformed(ActionEvent e) {

            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
    }
}

