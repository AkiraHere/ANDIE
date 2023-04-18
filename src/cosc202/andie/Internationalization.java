package cosc202.andie;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

/**
 * <p>
 * Various methods that allow for the internationalization of the program.
 * </p>
 * 
 * <p>
 * Creates the Preferences, Locale, and ResourceBundle required for the 
 * internationaliziton to work. Preferences save last language chosen between 
 * instances, locale deals with the language and country selection, and the 
 * ResourceBundle locates the property files and chooses the translation upon
 * runtime. 
 * </p>
 * 
 * @author Samuel Goddard
 * @version 1.0
 */
public class Internationalization {

    // data fields 
    private ResourceBundle bundle ; 
    private Preferences prefs ; 
    private Locale.Builder localeBuilder ; 
    private Locale locale ; 

    /**
     * <p>
     * Constructor - creates a new Internationalization instance. 
     * </p>
     * 
     * <p>
     * Sets up the basics of internationalization. Preferences are created, locale is built
     * and and ResourceBundle is created. ResourceBundle links the MessageBundles to the current 
     * preferences, facilitated by the locale and locale builder. 
     * <p>
     */
    public Internationalization() {

        prefs = Preferences.userNodeForPackage( Internationalization.class ) ; 

        localeBuilder = new Locale.Builder() ; 
        localeBuilder.setLanguage( prefs.get( "language" , "en" ) ) ; 
        localeBuilder.setRegion( prefs.get( "country" , "NZ" ) ) ; 
        locale = localeBuilder.build() ; 
        Locale.setDefault( locale ) ; 
        
        this.bundle = ResourceBundle.getBundle( "cosc202.andie.MessageBundle" ) ; 

    }

    /**
     * <p>
     * Accesses the MessageBundle and utilizes the key to return the 
     * appropriately translated phrase. 
     * </p>
     * 
     * @param key the key used for mutliple translations of the same phrase
     * @return the equivalent translated key
     */
    public String getI18NString ( String key ) {

        return bundle.getString( key ) ; 

    }

    /**
     * <p>
     * Returns the locale from the locale.builder. 
     * </p>
     * 
     * @return the locale
     */
    public Locale getLocale() {
        
        return locale ; 

    }

    /**
     * <p>
     * Changes all language in the GUI to the users choice. 
     * </p>
     * 
     * <p>
     * Taking input from {@link InternationalizationActions} and changes the langauge of the
     * GUI accordingly. If the same language is chosen, JOptionPane informs user as such.
     * Preferences are set, which will then apply the chosen language upon Andie
     * being ran again. Creates a new instance of ANDIE main, passing it the filename 
     * of the image currently being worked on. This instance then opens the file in 
     * main {@link Andie}. If no image is open, the program simply disposes of the previous 
     * instance of Andie and creates a new instance. 
     * </p>
     * 
     * @param language the language to be changed to in Andie's GUI
     * @param country the origin of the language
     */
    public void setLang ( String language , String country ) {

        String currLanguage = Locale.getDefault().getLanguage() ; 
        String currCountry = Locale.getDefault().getCountry() ; 
        
        // checks if the chosen language is currently in use
        if ( language.equals(currLanguage) && country.equals(currCountry) ) {

            JOptionPane.showMessageDialog( null , Andie.getLanguage("error_same_language") , Andie.getLanguage("title") , 0 ) ;
            return ; 

        }
        
        // can be reimplemented if issues occur with opening the working image in the new instance of Andie
        // String[] options = { Andie.getLanguage("continue") , Andie.getLanguage("exit") } ; 
        // int selectedOption = JOptionPane.showOptionDialog( null , 
        //     Andie.getLanguage("warning_message") , 
        //     Andie.getLanguage("warning_title") ,
        //     JOptionPane.DEFAULT_OPTION, 
        //     JOptionPane.PLAIN_MESSAGE,
        //     null, options, options[0]);

        // if ( selectedOption == 0 ) {

        // changes preferences to chosen language
        prefs.put( "language" , language ) ; 
        prefs.put( "country" , country ) ; 
            
        try {

            // if an image is currently open in Andie 
            if ( FileActions.FileSaveAction.target.getImage().hasImage() ) {

                // saves image and sources image file name and string along with dimensions of frame
                FileActions.FileSaveAction.target.getImage().save() ; 
                String width = Integer.toString( Andie.getFrame().getWidth() ) ; 
                String height = Integer.toString( Andie.getFrame().getHeight() ) ; 
                String Filename = FileActions.FileOpenAction.target.getImage().getFilename() ; 

                // closes the current instance of Andie, creating a new instance and passing it the filename
                Andie.closeFrame() ;
                String[] args = { Filename , width , height } ; 
                Andie.main( args ) ; 

            // if no image is open in Andie
            } else {

                Andie.closeFrame() ; 
                Andie.main( new String[]{} ) ; 

            } 

        }

        catch (Exception e) {

            System.exit( 1 ) ; 

        } 

        // } else {

        //     return ; 

        // }

    }
    
}
