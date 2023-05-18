package cosc202.andie;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.* ;

public class MouseActions implements KeyListener {
    
    protected ArrayList<Action> actions ; 

    public MouseActions() {
       
        actions = new ArrayList<Action>();
        actions.add( new CropAction( "Crop" , null , "Crops Image" , Integer.valueOf( KeyEvent.VK_X ) ) ) ;
        actions.add( new StopAction( "Stop" , null , "Stops Crop Image" , Integer.valueOf( 0 ) ) ) ;

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

            // if ( target.getImage().hasImage() == true ) {}

                target.cropActive( true ) ; 

        // }   else {


        //     }
            
        }

    }

    public class StopAction extends ImageAction {

        StopAction( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon , desc , mnemonic ) ;

        }

        public void actionPerformed( ActionEvent e ) {

            target.cropActive( false ) ; 

        }

    }

}