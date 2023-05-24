package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

 /**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class EditActions implements KeyListener{
    
    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public EditActions() {
        actions = new ArrayList<Action>();
        actions.add(new UndoAction( Andie.getLanguage("undo") , null, Andie.getLanguage("undo") , Integer.valueOf(KeyEvent.VK_Z)));
        actions.add(new RedoAction( Andie.getLanguage("redo") , null, Andie.getLanguage("redo") , Integer.valueOf(KeyEvent.VK_Y)));
        actions.add(new MacroAction( Andie.getLanguage("record") , null, Andie.getLanguage("record_desc") , Integer.valueOf(KeyEvent.VK_PERIOD)));
        actions.add(new ApplyMacro( Andie.getLanguage("apply_macro") , null, Andie.getLanguage("apply_macro_desc") , Integer.valueOf(KeyEvent.VK_COMMA)));
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
     * Create a menu contianing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu(Andie.getLanguage("jmenu_edit"));

        for (Action action: actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {             
                target.getImage().undo();
                Andie.getFrame().setSize( target.getWidth() , target.getHeight() ) ;
                target.repaint();
                target.getParent().revalidate();
            } catch ( EmptyStackException a ) {
                JOptionPane.showMessageDialog(null, Andie.getLanguage("error_nothing_on_stack"), Andie.getLanguage("error_title"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

     /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#redo()
     */   
    public class RedoAction extends ImageAction {

        /**
         * <p>
         * Create a new redo action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        
        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
            target.getImage().redo();
            Andie.getFrame().setSize( target.getWidth() , target.getHeight() ) ;
            target.repaint();
            target.getParent().revalidate();
            } catch ( EmptyStackException a ) {
                JOptionPane.showMessageDialog(null, Andie.getLanguage("error_nothing_on_stack"), Andie.getLanguage("error_title"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

     /**
     * <p>
     * Action to start and stop recording macros in an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage
     */
    public class MacroAction extends ImageAction {

        /**
         * <p>
         * Create a new macro action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the macro action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MacroAction is triggered.
         * It will either start or stop recording image operations.
         * When it stops recording image operations as a macro, it will prompt 
         * the user to save the new <code>_marcro.ops</code> file.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().toggleMacro();
            if(target.getImage().getMacroStatus() == true){
                JOptionPane.showMessageDialog(null, Andie.getLanguage("macro_started"), Andie.getLanguage("macro_started_title"), JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, Andie.getLanguage("macro_stopped"), Andie.getLanguage("macro_stopped_title"), JOptionPane.INFORMATION_MESSAGE);
            }
            if (target.getImage().getMacroStatus() == false){
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle( Andie.getLanguage("save_as_name"));
                fileChooser.setLocale(Andie.getInternationalization().getLocale()) ;
                int result = fileChooser.showSaveDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        if(target.getImage().hasImage()){
                            String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                            target.getImage().saveAsMacro(imageFilepath);
                        }else{
                            JOptionPane.showMessageDialog(null, Andie.getLanguage("error_no_image"), Andie.getLanguage("error_title"), JOptionPane.ERROR_MESSAGE);
                        }   
                    } catch (Exception ex) {
                        System.exit(1);
                    }
                }
                target.getImage().clearMacroOps();
            }
            target.repaint();
            target.getParent().revalidate();
        }
    }

         /**
     * <p>
     * Action to start and stop recording macros in an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage
     */
    public class ApplyMacro extends ImageAction {

        /**
         * <p>
         * Create a new apply macro action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ApplyMacro(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the apply macro action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ApplyMacro action is triggered.
         * It will open a menu to select an ops file.
         * Once selected, it will apply those operations to an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle( Andie.getLanguage("open_name") ) ; 
            fileChooser.setLocale(Andie.getInternationalization().getLocale()) ; 
            fileChooser.setApproveButtonText( Andie.getLanguage( "approve_button" ) ) ;
            FileNameExtensionFilter filter = new FileNameExtensionFilter(Andie.getLanguage("image_files"), "ops");
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = fileChooser.getSelectedFile();
                    if(!selectedFile.exists()){
                        JOptionPane.showMessageDialog(null, Andie.getLanguage("error_file_not_found"), Andie.getLanguage("error_title"), JOptionPane.ERROR_MESSAGE);
                    } else if (!filter.accept(selectedFile)) {
                        JOptionPane.showMessageDialog(null, Andie.getLanguage("error_invalid_file"), Andie.getLanguage("error_title"), JOptionPane.ERROR_MESSAGE);
                    } else {
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().openMacro(imageFilepath);
                    }
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
            target.repaint();
            target.getParent().revalidate();
        }
    }

}
