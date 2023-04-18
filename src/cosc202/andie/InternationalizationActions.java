package cosc202.andie ;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.* ; 

import javax.swing.* ;

/**
 * <p>
 * Actions provided by the language menu. 
 * </p>
 * 
 * <p>
 * Allows the user to change the language used by the GUI. Implemented
 * via internationalization, which requires the GUI to restart each time
 * a different language is chosen. 
 * </p>
 * 
 * @author Samuel Goddard
 * @version 1.0
 */
public class InternationalizationActions {

    // data fields
    protected ArrayList<Action> actions ; 

    /**
     * <p>
     * Creates a set of language menu actions. 
     * </p>
     */
    public InternationalizationActions() {

        actions = new ArrayList<Action>() ; 
        actions.add( new EnglishNewZealand( Andie.getLanguage("english_name") , null , Andie.getLanguage("english_description") , Integer.valueOf( KeyEvent.VK_0 ) ) ) ; 
        actions.add( new FrenchFrance( Andie.getLanguage("french_name") , null , Andie.getLanguage("french_description") , Integer.valueOf( KeyEvent.VK_1 ) ) ) ;
        actions.add( new MāoriNewZealand( Andie.getLanguage("māori_name") , null , Andie.getLanguage( "māori_description") , Integer.valueOf( KeyEvent.VK_2 ) ) ) ; 

    }

    /**
     * <p>
     * Creates a menu containing the list of language options.
     * </p>
     * 
     * @return the language menu
     */
    public JMenu createMenu() {

        JMenu languageMenu = new JMenu( Andie.getLanguage("jmenu_language")) ; 

        for ( Action action : actions ) {

            languageMenu.add( new JMenuItem( action ) ) ; 

        }

        return languageMenu ; 
    }

    /**
     * <p>
     * Action to change language to new zealand english. 
     * </p>
     * 
     * @see Internationalization
     */
    public class EnglishNewZealand extends AbstractAction {

        /**
         * <p>
         * Creates an action to change language to new zealand english. 
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        EnglishNewZealand( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon ) ; 
            putValue( SHORT_DESCRIPTION , desc ) ; 
            putValue( MNEMONIC_KEY , mnemonic );

        }

        /**
         * <p>
         * Callback for when new zealand english language is chosen.
         * </p>
         * 
         * @param e The event triggering this callback. 
         */
        public void actionPerformed( ActionEvent e ) {
            
             Andie.setLanguage( "en" , "NZ" ) ; 

        } 

    }

    /**
     * <p>
     * Action to change language to france french.  
     * </p>
     * 
     * @see Internationalization
     */
    public class FrenchFrance extends AbstractAction {

        /**
         * <p>
         * Creates an action to change language to france french.  
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FrenchFrance( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon ) ; 
            putValue( SHORT_DESCRIPTION , desc ) ; 
            putValue( MNEMONIC_KEY , mnemonic );

        }

        /**
         * <p>
         * Callback for when france french language is chosen.
         * </p>
         * 
         * @param e The event triggering this callback. 
         */
        public void actionPerformed( ActionEvent e ) {
            
             Andie.setLanguage( "fr" , "FR" ) ; 

        } 

    }

    /**
     * <p>
     * Action to change language to new zealand māori. 
     * </p>
     * 
     * @see Internationalization
     */
    public class MāoriNewZealand extends AbstractAction {

        /**
         * <p>
         * Creates an action to change language to new zealand māori.  
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MāoriNewZealand( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon ) ; 
            putValue( SHORT_DESCRIPTION , desc ) ; 
            putValue( MNEMONIC_KEY , mnemonic );

        }

        /**
         * <p>
         * Callback for when new zealand māori language is chosen.
         * </p>
         * 
         * @param e The event triggering this callback. 
         */
        public void actionPerformed( ActionEvent e ) {
            
             Andie.setLanguage( "mi" , "NZ" ) ; 

        } 

    }

}