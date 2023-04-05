package cosc202.andie;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

public class Internationalization {

    private ResourceBundle bundle ; 
    private Preferences prefs ; 
    private Locale.Builder locale ; 

    public Internationalization() {

        prefs = Preferences.userNodeForPackage( Internationalization.class ) ; 

        locale = new Locale.Builder() ; 
        locale.setLanguage( prefs.get( "language" , "en" ) ) ; 
        locale.setRegion( prefs.get( "country" , "NZ" ) ) ; 
        Locale.setDefault( locale.build() ) ; 
        
        this.bundle = ResourceBundle.getBundle( "cosc202.andie.MessageBundle" ) ; 

    }

    public String getI18NString ( String input ) {

        return bundle.getString( input ) ; 

    }

    public void setLang ( String language , String country ) {

        String currLanguage = Locale.getDefault().getLanguage() ; 
        String currCountry = Locale.getDefault().getCountry() ; 
        
        if ( language.equals(currLanguage) && country.equals(currCountry) ) {

            JOptionPane.showMessageDialog( null , Andie.getLanguage("error_same_language") , Andie.getLanguage("title") , 0 ) ;
            return ; 

        }

        String[] options = { Andie.getLanguage("continue") , Andie.getLanguage("exit") } ; 
        int selectedOption = JOptionPane.showOptionDialog( null , 
            Andie.getLanguage("warning_message") , 
            Andie.getLanguage("warning_title") ,
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.PLAIN_MESSAGE, 
            null, options, options[0]);

        if ( selectedOption == 0 ) {

            prefs.put( "language" , language ) ; 
            prefs.put( "country" , country ) ; 
            
            try {
                if ( FileActions.FileSaveAction.target.getImage().hasImage() ) {
                    FileActions.FileSaveAction.target.getImage().save() ; 
                    String Filename = FileActions.FileOpenAction.target.getImage().getFilename() ; 
                    Andie.closeFrame() ;
                    String[] args = { Filename } ; 
                    Andie.main( args ) ; 
                } else {
                    Andie.closeFrame() ; 
                    Andie.main( new String[]{} ) ; 
                } 
            }
            catch (Exception e) {
                e.printStackTrace();
            } 

        } else {

            return ; 

        }

    }
    
}
