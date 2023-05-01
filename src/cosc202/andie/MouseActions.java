package cosc202.andie;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.* ;

public class MouseActions {
    
    protected ArrayList<Action> actions ; 

    public MouseActions() {
       
        actions = new ArrayList<Action>();
        actions.add( new CropAction( "Crop" , null , "Crops Image" , Integer.valueOf( KeyEvent.VK_O ) ) ) ;

    }

    public JMenu createMenu() {
        
        JMenu mouseMenu = new JMenu( "Mouse" ) ;

        for ( Action action : actions ) {

            mouseMenu.add( new JMenuItem( action ) ) ;

        }

        return mouseMenu;

    }

    public class CropAction extends ImageAction {

        CropAction( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon , desc , mnemonic ) ;

        }

        public void actionPerformed( ActionEvent e ) {

            target.cropActive( true ) ; 

        }

    }

}