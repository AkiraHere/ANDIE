package cosc202.andie;

import java.awt.*;

import javax.swing.*;
import javax.imageio.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various image editing and processing operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class Andie {

    private static Internationalization internationalization ; 
    private static JFrame frame ;

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save, edit, etc. 
     * These operations are implemented {@link ImageOperation}s and triggerd via 
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {
        
        // changes default label names in jfilechooser after internationalization
        internationalization = new Internationalization() ; 
        UIManager.put( "FileChooser.cancelButtonText" , Andie.getLanguage( "cancel_button") ) ;
        UIManager.put( "FileChooser.saveButtonText" , Andie.getLanguage( "approve_button" ) ) ; 
        UIManager.put( "FileChooser.filesOfTypeLabelText" , Andie.getLanguage( "file_type" ) ) ;
        UIManager.put( "FileChooser.acceptAllFileFilterText" , Andie.getLanguage( "all_files" ) ) ;
        UIManager.put( "FileChooser.newFolderButtonText" , Andie.getLanguage( "new_folder" ) ) ;
        UIManager.put( "FileChooser.saveDialogFileNameLabelText" , Andie.getLanguage( "save_text" ) ) ; 
        UIManager.put( "FileChooser.byDateText" , Andie.getLanguage( "date_modified" ) ) ;
        UIManager.put( "FileChooser.byNameText" , Andie.getLanguage( "name_text" ) ) ;
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

        // Set up the main GUI frame
        frame = new JFrame( getLanguage("title") ) ;

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local window
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        // Actions that affect orientation of image
        TransformActions transformActions = new TransformActions();
        menuBar.add(transformActions.createMenu());

        // Actions that affect language preference in ANDIE
        InternationalizationActions internationalizationActions = new InternationalizationActions() ; 
        menuBar.add( internationalizationActions.createMenu() ) ; 
        
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true); 

    }

    /**
     * <p>
     * Sets the language given a specified language and country through the 
     * internationalization instance. 
     * </p>
     * 
     * @param language updated language of Andie's GUI
     * @param country region of the language
     */
    public static void setLanguage( String language , String country ) {
        internationalization.setLang( language , country ) ;
    }

    /**
     * <p>
     * Returns the translated phrase given a key through the 
     * internationalization instance. 
     * </p>
     * 
     * @param key the key related to the phrase to be translated 
     * @return the translated phrase 
     */
    public static String getLanguage( String key ) {
        return internationalization.getI18NString( key ) ; 
    }

    /**
     * <p>
     * Disposes of the current instance of Andie's GUI. 
     * </p>
     */
    public static void closeFrame() {
        frame.dispose() ; 
    }

    /**
     * <p>
     * Returns the current frame of Andie's GUI. 
     * </p>
     * 
     * @return the current frame of Andie's GUI
     */
    public static JFrame getFrame(){
        return frame;  
    }

    /**
     * <p>
     * Retuns the internationalization instance. 
     * </p>
     * 
     * @return instance of internationalization
     */
    public static Internationalization getInternationalization() {
        return internationalization ;
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try { 
                    createAndShowGUI();
                    // given a language change, checks if image needs to be reopened
                    if ( args.length == 3 ) {
                        FileActions.FileOpenAction.target.getImage().open( args[0] ) ; 
                        FileActions.FileOpenAction.target.repaint() ; 
                        FileActions.FileOpenAction.target.getParent().revalidate() ; 
                        int width = Integer.parseInt( args[1] ) ; 
                        int height = Integer.parseInt( args[2] ) ; 
                        frame.setSize( width , height ) ; 
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}
// @_@
