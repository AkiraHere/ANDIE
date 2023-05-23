package cosc202.andie;

import java.util.HashMap;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;

/**
 * <p>
 * The root where all theme related actions stem.
 * </p>
 * 
 * <p>
 * Creates a hashmap that stores all available themes, stores the selected theme
 * in the created preferences.
 *  
 * </p>
 * 
 * @author Luke Webb
 * @version 1.0
 */

public class ThemesManager {

    private Preferences prefs ; 
    private String currentThemeKey;
    private HashMap<String, String> themesMap;

    /**
     * <p>
     * Constructor for the ThemeManager.
     * </p>
     */
    public ThemesManager(){
        themesMap = new HashMap<>();
        themesMap.put("metal", "javax.swing.plaf.metal.MetalLookAndFeel");
        themesMap.put("nimbus", "javax.swing.plaf.nimbus.NimbusLookAndFeel");
        themesMap.put("motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        themesMap.put("windows", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        
        prefs = Preferences.userNodeForPackage( ThemesManager.class ) ; 
        currentThemeKey = prefs.get("theme", "metal");
        
    }

    /**
     * <p>
     * Retrieves the class plath that contains the information needed to set
     * the theme of the program.
     * </p>
     * @return the class path for the look and feel of the theme.
     */
    public String getCurrentThemeClass(){
        return themesMap.get(currentThemeKey);
    }

    /**
     * Used to swap out the theme of the program.
     * First, checks if the current theme is already selected. If so, creates an error message.
     * Stores the desired theme in the preferences, so that the user can expect the
     * same them to appear the next time they launch the program.
     * Next, we check to see if an image is already opened, if so we pass the information to 
     * a new instance of Andie, so the user doesn't lose any progress.
     * 
     * @param key the key to access the stored theme class.
     */
    public void switchTheme(String key) {
        // checking if the user already has this theme selected.
        if(currentThemeKey.equals(key)){
            JOptionPane.showMessageDialog( null , Andie.getLanguage("error_same_theme") , Andie.getLanguage("title") , 0 ) ;
            return ;
        }

        prefs.put("theme", key);

        try{
             // if an image is currently open in Andie 
             if ( FileActions.FileSaveAction.target.getImage().hasImage() ) {

                // saves image and sources image file name and string along with dimensions of frame
                FileActions.FileSaveAction.target.getImage().save() ; 
                String width = Integer.toString( Andie.getFrame().getWidth() ) ; 
                String height = Integer.toString( Andie.getFrame().getHeight() ) ; 
                String Filename = FileActions.FileOpenAction.target.getImage().getFilename() ; 

                // closes the current instance of Andie, creating a new instance and passing it the filename
                Andie.closeFrame() ;
                String[] args = { Filename , width, height} ; 
                Andie.main( args ) ; 
            // if no image is open in Andie
            } else {
                Andie.closeFrame() ; 
                Andie.main( new String[]{} ) ; 

            } 
        } catch (Exception e) {

            System.exit( 1 ) ; 

        } 
    }

}
