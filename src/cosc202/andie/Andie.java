package cosc202.andie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;


import javax.swing.*;

import cosc202.andie.ColourActions.BrightnessAdjusterAction;
import cosc202.andie.ColourActions.ContrastAdjusterAction;
import cosc202.andie.EditActions.MacroAction;
import cosc202.andie.EditActions.RedoAction;
import cosc202.andie.EditActions.UndoAction;
import cosc202.andie.FileActions.FileSaveAsAction;
import cosc202.andie.CropActions.CropAction;
import cosc202.andie.ViewActions.ZoomInAction;
import cosc202.andie.ViewActions.ZoomOutAction;

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
    private static ThemesManager themesManager ;

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
        //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

        
        // Add in a toolbar
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setPreferredSize(new Dimension(0, 35));

        // Using Icons for the button image
        BufferedImage saveImage = ImageIO.read(Andie.class.getResource("/cosc202/andie/icons/Save_Icon.png"));
        BufferedImage undoImage = ImageIO.read(Andie.class.getResource("/cosc202/andie/icons/Undo_Icon.png"));
        BufferedImage redoImage = ImageIO.read(Andie.class.getResource("/cosc202/andie/icons/Redo_Icon.png"));
        BufferedImage zoomInImage = ImageIO.read(Andie.class.getResource("/cosc202/andie/icons/ZoomIn_Icon.png"));
        BufferedImage zoomOutImage = ImageIO.read(Andie.class.getResource("/cosc202/andie/icons/ZoomOut_Icon.png"));
        BufferedImage cropImage = ImageIO.read(Andie.class.getResource("/cosc202/andie/icons/Crop_Icon.png"));
        BufferedImage contrastImage = ImageIO.read(Andie.class.getResource("/cosc202/andie/icons/Contrast_Icon.png"));
        BufferedImage brightnessImage = ImageIO.read(Andie.class.getResource("/cosc202/andie/icons/Brightness_Icon.png"));
        BufferedImage recordOffImage = ImageIO.read(Andie.class.getResource("/cosc202/andie/icons/RecordOff_Icon.png"));
        //BufferedImage recordOnImage = ImageIO.read(Andie.class.getResource("/cosc202/andie/icons/RecordOn_Icon.png"));

        // Scaling images to appropriate size
        Image saveIcon = saveImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        Image undoIcon = undoImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        Image redoIcon = redoImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        Image zoomInIcon = zoomInImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        Image zoomOutIcon = zoomOutImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        Image cropIcon = cropImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        Image contrastIcon = contrastImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        Image brightnessIcon = brightnessImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        Image recordOffIcon = recordOffImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        //Image recordOnIcon = recordOnImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        
        // Creating JButtons
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton saveButton = new JButton(new ImageIcon(saveIcon));
        JButton undoButton = new JButton(new ImageIcon(undoIcon));
        JButton redoButton = new JButton(new ImageIcon(redoIcon));
        JButton zoomInButton = new JButton(new ImageIcon(zoomInIcon));
        JButton zoomOutButton = new JButton(new ImageIcon(zoomOutIcon));
        JButton cropButton = new JButton(new ImageIcon(cropIcon));
        JButton contrastButton = new JButton(new ImageIcon(contrastIcon));
        JButton brightnessButton = new JButton(new ImageIcon(brightnessIcon));
        JButton recordButton = new JButton(new ImageIcon(recordOffIcon));

        // Adding tooltips to buttons
        saveButton.setToolTipText(Andie.getLanguage("save_description"));
        undoButton.setToolTipText(Andie.getLanguage("undo"));
        redoButton.setToolTipText(Andie.getLanguage("redo"));
        zoomInButton.setToolTipText(Andie.getLanguage("zoom_in_description"));
        zoomOutButton.setToolTipText(Andie.getLanguage("zoom_out_description"));
        cropButton.setToolTipText(Andie.getLanguage("crop"));
        contrastButton.setToolTipText(Andie.getLanguage("contrast_description"));
        brightnessButton.setToolTipText(Andie.getLanguage("brightness_description"));
        recordButton.setToolTipText(Andie.getLanguage("record_desc"));

        // Hiding borders 
        saveButton.setBorderPainted(false);
        undoButton.setBorderPainted(false);
        redoButton.setBorderPainted(false);
        zoomInButton.setBorderPainted(false);
        zoomOutButton.setBorderPainted(false);
        cropButton.setBorderPainted(false);
        contrastButton.setBorderPainted(false);
        brightnessButton.setBorderPainted(false);
        recordButton.setBorderPainted(false);
        
        // Using ActionListener to make buttons call their repsected classes/methods
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                FileActions save = new FileActions();
                FileSaveAsAction saveAs = save.new FileSaveAsAction(null, null, null, null);
                saveAs.actionPerformed(e);
            }
        });
        undoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                EditActions edit = new EditActions();
                UndoAction undo = edit.new UndoAction(null, null, null, null);
                undo.actionPerformed(e);
            }
        });
        redoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                EditActions edit = new EditActions();
                RedoAction redo = edit.new RedoAction(null, null, null, null);
                redo.actionPerformed(e);
            }
        });
        zoomInButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                ViewActions view = new ViewActions();
                ZoomInAction zoomIn = view.new ZoomInAction(null, null, null, null);
                zoomIn.actionPerformed(e);
            }
        });
        zoomOutButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                ViewActions view = new ViewActions();
                ZoomOutAction zoomOut = view.new ZoomOutAction(null, null, null, null);
                zoomOut.actionPerformed(e);
            }
        });
        cropButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                CropActions crop = new CropActions();
                CropAction cropper = crop.new CropAction(null, null, null, null);
                cropper.actionPerformed(e);
            }
        });
        contrastButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                ColourActions colour = new ColourActions();
                ContrastAdjusterAction contrast = colour.new ContrastAdjusterAction(null, null, null, null);
                contrast.actionPerformed(e);
            }
        });
        brightnessButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                ColourActions colour = new ColourActions();
                BrightnessAdjusterAction brightness = colour.new BrightnessAdjusterAction(null, null, null, null);
                brightness.actionPerformed(e);
            }
        });
        recordButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                EditActions edit = new EditActions();
                MacroAction macro = edit.new MacroAction(null, null, null, null);
                macro.actionPerformed(e);
            }
        });
        
        // Adding buttons to toolbar
        toolbar.add(saveButton);
        toolbar.add(undoButton);
        toolbar.add(redoButton);
        toolbar.add(zoomInButton);
        toolbar.add(zoomOutButton);
        toolbar.add(cropButton);
        toolbar.add(contrastButton);
        toolbar.add(brightnessButton);
        toolbar.add(recordButton);

        // Set up the main GUI frame
        frame = new JFrame( getLanguage("title") ) ;
        frame.add(toolbar, BorderLayout.NORTH);

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go here.
        FileActions fileActions = new FileActions();
        frame.addKeyListener(fileActions);
        menuBar.add(fileActions.createMenu());
        frame.getRootPane().addKeyListener(fileActions);


        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        frame.addKeyListener(editActions);
        menuBar.add(editActions.createMenu());
        frame.getRootPane().addKeyListener(editActions);

        // View actions control how the image is displayed, but do not alter its actual content
        ViewActions viewActions = new ViewActions();
        frame.addKeyListener(viewActions);
        menuBar.add(viewActions.createMenu());
        frame.getRootPane().addKeyListener(viewActions);

        // Filters apply a per-pixel operation to the image, generally based on a local window
        FilterActions filterActions = new FilterActions();
        frame.addKeyListener(filterActions);
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        frame.addKeyListener(colourActions);
        menuBar.add(colourActions.createMenu());
        frame.getRootPane().addKeyListener(colourActions);

        // Actions that affect orientation of image
        TransformActions transformActions = new TransformActions();
        frame.addKeyListener(transformActions);
        menuBar.add(transformActions.createMenu());
        frame.getRootPane().addKeyListener(transformActions);

        // Actions that revolve around the mouse and cropping
        CropActions mouseActions = new CropActions(); 
        frame.addKeyListener(mouseActions);
        menuBar.add(mouseActions.createMenu());
        frame.getRootPane().addKeyListener(mouseActions);

        // Actions that revolve around the mouse and drawing
        DrawActions drawActions = new DrawActions(); 
        frame.addKeyListener(drawActions);
        menuBar.add(drawActions.createMenu());
        frame.getRootPane().addKeyListener(drawActions);

        ThemeActions themeActions = new ThemeActions();
        menuBar.add(themeActions.createMenu());

        // Actions that affect language preference in ANDIE
        InternationalizationActions internationalizationActions = new InternationalizationActions() ; 
        menuBar.add( internationalizationActions.createMenu() ) ; 

        frame.addWindowListener(new WindowAdapter() {
            @Override
            /**
             * <p>
             * Triggered when the Andie program is exiting.
             * If the user has an image open, it will prompt them to save their progress.
             * </p>
             * 
             * @param e the callback to the event
             */
            public void windowClosing(WindowEvent e) {
                if(imagePanel.getImage().hasImage()){
                    boolean changesSaved = imagePanel.getImage().getSaveStatus();
                    if (!changesSaved) {
                        int option = JOptionPane.showConfirmDialog(frame, Andie.getLanguage("unsaved_changes"));
                        if (option == JOptionPane.YES_OPTION) {
                            try {
                                imagePanel.getImage().save();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            changesSaved = true;
                            frame.dispose(); // Close the program
                        } else if (option == JOptionPane.NO_OPTION) {
                            changesSaved = true;
                            frame.dispose(); // Close the program without saving
                        }else if (option == JOptionPane.CANCEL_OPTION){
                            return;
                        }
                        // If the user chooses cancel, do nothing and the program will continue running
                    } else {
                        frame.dispose(); // Close the program
                    }
                }
            }
        });

        frame.setJMenuBar(menuBar);
        frame.pack();

        //Sets the theme / Look and feel
        themesManager = new ThemesManager();
        try{
            UIManager.setLookAndFeel(themesManager.getCurrentThemeClass());
            SwingUtilities.updateComponentTreeUI(frame);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }


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
     * Sets the theme of the program.
     * </p>
     * 
     * @param themeName the key to access a theme in the ThemesManager.
     */
    public static void setTheme(String themeName){
        themesManager.switchTheme(themeName);
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
