package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications, 
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FileActions {
    
    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {
        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction(Andie.getLanguage("open_name"), null, Andie.getLanguage("open_description"), Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction(Andie.getLanguage("save_name"), null, Andie.getLanguage("save_description"), Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction(Andie.getLanguage("save_as_name"), null, Andie.getLanguage("save_as_description"), Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExportAction(Andie.getLanguage("export_name"), null, Andie.getLanguage("export_description"), Integer.valueOf(KeyEvent.VK_E))) ; 
        actions.add(new FileExitAction(Andie.getLanguage("exit_name"), null, Andie.getLanguage("exit_description"), Integer.valueOf(0)));
    }

    /**
     * <p>
     * Create a menu contianing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.getLanguage("jmenu_file"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(Andie.getLanguage("image_files"), "jpg", "jpeg", "png", "gif");
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
                        target.getImage().open(imageFilepath);
                    }
                } catch (Exception ex) {
                    System.exit(1);
                }
            }

            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                if(target.getImage().hasImage()){
                    target.getImage().save();
                }else{
                    JOptionPane.showMessageDialog(null, Andie.getLanguage("error_no_image"), Andie.getLanguage("error_title"), JOptionPane.ERROR_MESSAGE);
                }           
            } catch (Exception ex) {
                System.exit(1);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

         /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    if(target.getImage().hasImage()){
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().saveAs(imageFilepath);
                    }else{
                        JOptionPane.showMessageDialog(null, Andie.getLanguage("error_no_image"), Andie.getLanguage("error_title"), JOptionPane.ERROR_MESSAGE);
                    }   
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
        }

    }

    /**
     * <p>
     * Action to export a file to a new file location. 
     * </p>
     * 
     * @see EditableImage#export(String) 
     */
    public class FileExportAction extends ImageAction {

        /**
         * <p>
         * Creates a new file export action. 
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileExportAction( String name , ImageIcon icon , String desc , Integer mnemonic ) {
            super( name , icon , desc, mnemonic ) ; 
        }

        /**
         * <p>
         * Callback for when the export action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExportAction is triggered.
         * It prompts the user to select a save location and file name. 
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed( ActionEvent e ) {

            JFileChooser fileChooser = new JFileChooser() ; 
            fileChooser.setDialogTitle( "Export" ) ;
            // JOptionPane.showMessageDialog( null , ".jpeg is default, although .png or .gif can also be inputted" , "Notice" , JOptionPane.WARNING_MESSAGE );
            int result = fileChooser.showSaveDialog( target ) ; 

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    if(target.getImage().hasImage()){
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().export(imageFilepath);
                    }else{
                        JOptionPane.showMessageDialog(null, Andie.getLanguage("error_no_image"), Andie.getLanguage("error_title"), JOptionPane.ERROR_MESSAGE);
                    }    
                } catch (Exception ex) {
                    System.exit(1);
                }
                
            }

        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

         /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }

}
