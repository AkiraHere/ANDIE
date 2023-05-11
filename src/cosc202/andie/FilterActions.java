package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultStyledDocument.ElementSpec;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood. 
 * This includes a mean filter (a simple blur) in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FilterActions {
    
    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(new MeanFilterAction(Andie.getLanguage("mean_name"), null, Andie.getLanguage("mean_description"), Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new MedianFilterAction(Andie.getLanguage("median_name"), null, Andie.getLanguage("median_description"), Integer.valueOf(KeyEvent.VK_N)));
        actions.add(new SharpenFilterAction( Andie.getLanguage("sharpen_name") , null , Andie.getLanguage("sharpen_description") , Integer.valueOf(KeyEvent.VK_B))) ; 
        actions.add(new GaussianBlurFilterAction( Andie.getLanguage("gaussian_name"), null , Andie.getLanguage("gaussian_description") , Integer.valueOf(KeyEvent.VK_G))) ;
        
        actions.add(new EmbossFilterAction( Andie.getLanguage("emboss_name"), null , Andie.getLanguage("emboss_description") , Integer.valueOf(KeyEvent.VK_E))) ; 
    }

    /**
     * <p>
     * Create a menu contianing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.getLanguage("jmenu_filter"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            
            try { 

                // Determine the radius - ask the user.
                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                int option = JOptionPane.showOptionDialog(null, radiusSpinner, Andie.getLanguage("filter_radius"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }

                // Create and apply the filter
                target.getImage().apply(new MeanFilter(radius));
                target.repaint();
                target.getParent().revalidate();

            // if no image has been opened, caught by NullPointerException and shows error message
            } catch ( NullPointerException error ) {

                JOptionPane.showMessageDialog( null , Andie.getLanguage("error_no_image_opened") , Andie.getLanguage("error_title") , JOptionPane.WARNING_MESSAGE );
                return ; 

            }

        }

    }

    /**
     * <p>
     * Action to sharpen an image with a sharpen filter. 
     * </p>
     * 
     * @see SharpenFilter
     */
    public class SharpenFilterAction extends ImageAction {

        /**
         * <p>
         * Creates a new SharpenFilterAction. 
         * </p>
         * 
         * @param name the name of the action (ignored if null)
         * @param icon an icon used to represent the action (igorned if null)
         * @param desc a brief description of the action (ignored if null)
         * @param mnemonic a mnemonic key used as a shortcut (ignored if null)
         */
        SharpenFilterAction( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon , desc , mnemonic ) ; 

        }

        /**
         * <p>
         * Callback for when the sharpen filter action is triggered. 
         * </p>
         * 
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * No user input required, uses default 3x3 kernel size {@link SharpenFilter}
         * try/catch block to show error message if filter is applied when no image is opened. 
         * </p>
         * 
         * @param e The event triggering this callback 
         */
        public void actionPerformed( ActionEvent e ) {

            try { 

                target.getImage().apply( new SharpenFilter() ) ; 
                target.repaint() ; 
                target.getParent().revalidate() ; 

            // if no image has been opened, caught by NullPointerException and shows error message
            } catch ( NullPointerException error ) {

                JOptionPane.showMessageDialog( null , Andie.getLanguage("error_no_image_opened") , Andie.getLanguage("error_title") , JOptionPane.WARNING_MESSAGE );
                return ; 

            }

        }

    }

    /**
     * <p>
     * Action to blur an image with a gaussian filter. 
     * </p>
     * 
     * @see GaussianBlurFilter
     */
    public class GaussianBlurFilterAction extends ImageAction {

        /**
         * <p> 
         * Create a new GaussianBlurFilterAction. 
         * </p>
         * 
         * @param name the name of the action (ignored if null)
         * @param icon an icon used to represent the action (igorned if null)
         * @param desc a brief description of the action (ignored if null)
         * @param mnemonic a mnemonic key used as a shortcut (ignored if null)
         */
        GaussianBlurFilterAction( String name , ImageIcon icon , String desc , Integer mnemonic ) {

            super( name , icon , desc , mnemonic ) ; 

        } 

        /**
         * <p>
         * Callback for when the gaussian filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianBlurFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link GaussianBlurFilter}.
         * try/catch block if filtered is applied when no image is opened. 
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed( ActionEvent e ) { 

            try {

                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                int option = JOptionPane.showOptionDialog(null, radiusSpinner, Andie.getLanguage("filter_radius"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }

                // Create and apply the filter
                target.getImage().apply(new GaussianBlurFilter(radius));
                target.repaint();
                target.getParent().revalidate();

            // if no image has been opened, caught by NullPointerException and shows error message
            } catch ( NullPointerException error ) {

                JOptionPane.showMessageDialog( null , Andie.getLanguage("error_no_image_opened") , Andie.getLanguage("error_title") , JOptionPane.WARNING_MESSAGE );
                return ; 

            }  
            
        }

    }

    /**
     * <p>
     * Action to blur an image with a median filter.
     * </p>
     * 
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction{
         
        /**
         * <p>
         * Create a new MedianFilterAction.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the median filter is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link MedianFilter}.
         * try/catch block if filtered is applied when no image is opened.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            
            try { 

                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                int option = JOptionPane.showOptionDialog(null, radiusSpinner, Andie.getLanguage("filter_radius"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }else{
                    radius = 1;
                }

                // Create and apply the filter
                target.getImage().apply(new MedianFilter(radius));
                target.repaint();
                target.getParent().revalidate();

            // if no image has been opened, caught by NullPointerException and shows error message
            } catch ( NullPointerException error ) {

                JOptionPane.showMessageDialog( null , Andie.getLanguage("error_no_image_opened") , Andie.getLanguage("error_title") , JOptionPane.WARNING_MESSAGE );
                return ; 

            }

        }

    }


     /**
     * <p>
     * Action to filter an image with an Embossing effect.
     * </p>
     * 
     * @see EmbossFilter
     */
    public class EmbossFilterAction extends ImageAction{
         
        /**
         * <p>
         * Create a new EmbossFilterAction.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Emboss filter is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the EmbossFilterAction is triggered.
         * It prompts the user for an option that represents the direction of the emboss effect.
         * try/catch block if filtered is applied when no image is opened.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            
            try { 

                int embossOption = 0;
                Object[] options = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
                // Pop-up dialog box to ask for the radius value.
                    // BELOW WAS THE OLD SPINNER METHOD
                    // SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, 7, 1);
                    // JSpinner radiusSpinner = new JSpinner(radiusModel);
                    // int option = JOptionPane.showOptionDialog(null, radiusSpinner, Andie.getLanguage("emboss_option"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    int option = JOptionPane.showOptionDialog(target,
                    Andie.getLanguage("emboss_option"),
                    Andie.getLanguage("emboss_name"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    -1);
                // Check the return value from the dialog box.
                if(option == -1){
                    return;
                }
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    embossOption = option;
                }

                // Create and apply the filter
                target.getImage().apply(new EmbossFilter(embossOption));
                target.repaint();
                target.getParent().revalidate();

            // if no image has been opened, caught by NullPointerException and shows error message
            } catch ( NullPointerException error ) {

                JOptionPane.showMessageDialog( null , Andie.getLanguage("error_no_image_opened") , Andie.getLanguage("error_title") , JOptionPane.WARNING_MESSAGE );
                return ; 

            }

        }

    }


}
