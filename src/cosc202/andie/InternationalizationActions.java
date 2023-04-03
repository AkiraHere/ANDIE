package cosc202.andie ;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.* ; 

import javax.swing.* ;

public class InternationalizationActions {

    protected ArrayList<Action> actions ; 

    public InternationalizationActions() {

        actions = new ArrayList<Action>() ; 
        actions.add( new EnglishNewZealand( Andie.getI18N().getI18NString("english_name") , null , Andie.getI18N().getI18NString("english_description") , Integer.valueOf( KeyEvent.VK_0 ) ) ) ; 
        actions.add( new FrenchFrance( Andie.getI18N().getI18NString("french_name") , null , Andie.getI18N().getI18NString("french_description") , Integer.valueOf( KeyEvent.VK_1 ) ) ) ;

    }

    public JMenu createMenu() {

        JMenu languageMenu = new JMenu( Andie.getI18N().getI18NString("jmenu_language")) ; 

        for ( Action action : actions ) {

            languageMenu.add( new JMenuItem( action ) ) ; 

        }

        return languageMenu ; 
    }

    public class EnglishNewZealand extends AbstractAction {

        EnglishNewZealand( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon ) ; 
            putValue( SHORT_DESCRIPTION , desc ) ; 
            putValue( MNEMONIC_KEY , mnemonic );

        }

        public void actionPerformed( ActionEvent e ) {
            
             Andie.getI18N().setLang( "en" , "NZ" ) ; 

        } 

    }

    public class FrenchFrance extends AbstractAction {

        FrenchFrance( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon ) ; 
            putValue( SHORT_DESCRIPTION , desc ) ; 
            putValue( MNEMONIC_KEY , mnemonic );

        }

        public void actionPerformed( ActionEvent e ) {
            
             Andie.getI18N().setLang( "fr" , "FR" ) ; 

        } 

    }

}