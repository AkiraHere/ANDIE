package cosc202.andie;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.* ; 
import javax.swing.* ;

/**
 * <p>
 * Actions provided by the Theme menu.
 * </p>
 * 
 * <p>
 * Host to the different selectable themes in Andie.
 * Makes use of the functionality within the {@link ThemesManager}. These themes update
 * the GUI to provide a different aesthetic to the menus and the program itself.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Luke Webb
 * @version 1.0
 */
public class ThemeActions {
    protected ArrayList<Action> actions ;
    
    /**
     * <p>
     * Constructor - creates a new ThemeActions instance. 
     * </p>
     * 
     * <p>
     * Creates the menu items for each of the available themes.
     * </p>
     */
    public ThemeActions(){

        actions = new ArrayList<Action>() ; 
        actions.add( new SetThemeMetal("Metal", null , Andie.getLanguage("metal_description") , Integer.valueOf( KeyEvent.VK_P) ) ) ; 
        actions.add( new SetThemeNimbus("Nimbus", null , Andie.getLanguage("nimbus_description") , Integer.valueOf( KeyEvent.VK_P) ) ) ;
        actions.add( new SetThemeMotif("Motif", null , Andie.getLanguage("motif_description") , Integer.valueOf( KeyEvent.VK_P) ) ) ;
        actions.add( new SetThemeWindows("Windows", null , Andie.getLanguage("windows_description") , Integer.valueOf( KeyEvent.VK_P) ) ) ; 
    }

    /**
     * <p>
     * Called by the Andie method that instantiates all of the menus.
     * </p>
     * @return the Menu of the all the themes
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.getLanguage("jmenu_themes"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to apply the Nimbus theme / Look and feel to the program.
     * </p>
     * 
     */
    public class SetThemeNimbus extends ImageAction {
        
        /**
         * <p>
         * Create a new SetThemeNimbus action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        SetThemeNimbus(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the SetThemeNimbus action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SetThemeNimbus is triggered.
         * Simply calls the setTheme method in Andie and passes the key associated
         * with the class path for the theme.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Andie.setTheme("nimbus");
        }

    }

    /**
     * <p>
     * Action to apply the Metal theme / Look and feel to the program.
     * </p>
     * 
     */
    public class SetThemeMetal extends ImageAction {
        
        /**
         * <p>
         * Create a new SetThemeMetal action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        SetThemeMetal(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the SetThemeMetal action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SetThemeMetal is triggered.
         * Simply calls the setTheme method in Andie and passes the key associated
         * with the class path for the theme.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Andie.setTheme("metal");
        }

    }

    /**
     * <p>
     * Action to apply the Motif theme / Look and feel to the program.
     * </p>
     * 
     */
    public class SetThemeMotif extends ImageAction {

        /**
         * <p>
         * Create a new SetThemeMotif action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        SetThemeMotif(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the SetThemeMotif action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SetThemeMotif is triggered.
         * Simply calls the setTheme method in Andie and passes the key associated
         * with the class path for the theme.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Andie.setTheme("motif");
        }

    }

    /**
     * <p>
     * Action to apply the Windows theme / Look and feel to the program.
     * </p>
     * 
     */
    public class SetThemeWindows extends ImageAction {
        
        /**
         * <p>
         * Create a new SetThemeWindows action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        SetThemeWindows(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the SetThemeWindows action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SetThemeWindows is triggered.
         * Simply calls the setTheme method in Andie and passes the key associated
         * with the class path for the theme.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Andie.setTheme("windows");
        }

    }


}
