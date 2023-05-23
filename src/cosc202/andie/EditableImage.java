package cosc202.andie;

import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.JOptionPane;

/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 * 
 * <p>
 * The EditableImage represents an image with a series of operations applied to it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can be undone.
 * THis is what is meant by "A Non-Destructive Image Editor" - you can always undo back to the original image.
 * </p>
 * 
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original image 
 * and the result of applying the current set of operations to it. 
 * The operations themselves are stored on a {@link Stack}, with a second {@link Stack} 
 * being used to allow undone operations to be redone.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
class EditableImage {

    /** The original image. This should never be altered by ANDIE. */
    private BufferedImage original;
    /** The current image, the result of applying {@link ops} to {@link original}. */
    private BufferedImage current;
    /** The sequence of operations currently applied to the image. */
    private Stack<ImageOperation> ops;
    /** A memory of 'undone' operations to support 'redo'. */
    private Stack<ImageOperation> redoOps;
    /** The file where the original image is stored/ */
    private String imageFilename;
    /** The file where the operation sequence is stored. */
    private String opsFilename;
    /** String of characters which cannot appear in a file name. */
    private String unacceptableCharacters = "#%&{}\"<>*?/ $!\'\\:@+`|=" ; 

    /** Sequence of operations that begin when macro is enabled */
    private Stack<ImageOperation> macroOps;
    /** The file where the operation sequence is stored. */
    private String macroOpsFilename;
    /** Tracks the status of the macro operation */
    private boolean macroEnabled;

    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     * 
     * <p>
     * A new EditableImage has no image (it is a null reference), and an empty stack of operations.
     * </p>
     */
    public EditableImage() {
        original = null;
        current = null;
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        imageFilename = null;
        opsFilename = null;

        //Data fields related to macros
        macroOpsFilename = null;
        macroOps = new Stack<ImageOperation>();
        macroEnabled = false; 

    }

    /**
     * <p>
     * Check if there is an image loaded.
     * </p>
     * 
     * @return True if there is an image, false otherwise.
     */
    public boolean hasImage() {
        return current != null;
    }

    /**
     * <p>
     * Make a 'deep' copy of a BufferedImage. 
     * </p>
     * 
     * <p>
     * Object instances in Java are accessed via references, which means that assignment does
     * not copy an object, it merely makes another reference to the original.
     * In order to make an independent copy, the {@code clone()} method is generally used.
     * {@link BufferedImage} does not implement {@link Cloneable} interface, and so the 
     * {@code clone()} method is not accessible.
     * </p>
     * 
     * <p>
     * This method makes a cloned copy of a BufferedImage.
     * This requires knoweldge of some details about the internals of the BufferedImage,
     * but essentially comes down to making a new BufferedImage made up of copies of
     * the internal parts of the input.
     * </p>
     * 
     * <p>
     * This code is taken from StackOverflow:
     * <a href="https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
     * in response to 
     * <a href="https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
     * Code by Klark used under the CC BY-SA 2.5 license.
     * </p>
     * 
     * <p>
     * This method (only) is released under <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
     * </p>
     * 
     * @param bi The BufferedImage to copy.
     * @return A deep copy of the input.
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    /**
     * <p>
     * Open an image from a file.
     * </p>
     * 
     * <p>
     * Opens an image from the specified file.
     * Also tries to open a set of operations from the file with <code>.ops</code> added.
     * So if you open <code>some/path/to/image.png</code>, this method will also try to
     * read the operations from <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param filePath The file to open the image from.
     * @throws Exception If something goes wrong.
     */
    public void open(String filePath) throws Exception {
        imageFilename = filePath;
        opsFilename = imageFilename + ".ops";
        File imageFile = new File(imageFilename);
        original = ImageIO.read(imageFile);
        current = deepCopy(original);
        
        try {
            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            redoOps.clear();
            objIn.close();
            fileIn.close();
        } catch (Exception ex) {
            // Could be no file or something else. Carry on for now.
        }
        this.refresh();
        Andie.getFrame().setSize( getCurrentImage().getWidth() , getCurrentImage().getHeight() ) ; 
    }

    /**
     * <p>
     * Save an image to file.
     * </p>
     * 
     * <p>
     * Saves an image to the file it was opened from, or the most recent file saved as.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @throws Exception If something goes wrong.
     */
    public void save() throws Exception {
        if (this.opsFilename == null) {
            this.opsFilename = this.imageFilename + ".ops";
        }
        // Write image file based on file extension
        String extension = imageFilename.substring(1+imageFilename.lastIndexOf(".")).toLowerCase();
        ImageIO.write(original, extension, new File(imageFilename));
        // Write operations file
        FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(this.ops);
        objOut.close();
        fileOut.close();
    }

    /**
     * <p>
     * Save an image to a speficied file.
     * </p>
     * 
     * <p>
     * Saves an image to the file provided as a parameter.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     * @throws Exception If something goes wrong.
     */
    public void saveAs(String imageFilename) throws Exception {
        this.imageFilename = imageFilename;
        this.opsFilename = imageFilename + ".ops";
        save();
    }

    /**
     * <p>
     * Exports an image with a specified name and to a specified location. 
     * </p>
     * 
     * <p>
     * Creates a new file with a user inputted name at a user inputted location. 
     * Creates no .ops files, so user cannot undo changes made to the exported
     * file. Exported files default to .jpeg files, although .gif and .png are 
     * also supported. 
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     * @throws Exception If something goes wrong.
     */
    public void export( String imageFilename ) throws Exception {
    
        // user inputted name of file 
        String fileName = imageFilename.substring( 1 + imageFilename.lastIndexOf( "/" ) ) ; 
        // pathway to file save location
        String fileLocation = imageFilename.substring( 0 , imageFilename.lastIndexOf( "/" ) + 1 ) ; 

        // deals with case where user inputted file name starts with a '.'
        if ( fileName.charAt( 0 ) == '.' ) {

            JOptionPane.showMessageDialog( null , Andie.getLanguage("error_unacceptable_file_name") , Andie.getLanguage( "error_warning" ) , JOptionPane.WARNING_MESSAGE );
            return ;

        }

        // deals with case where user inputted file name contains an unacceptable character
        for ( int i = 0 ; i < fileName.length() ; i++ ) {
            
            if ( fileName.contains( String.valueOf( unacceptableCharacters.charAt( i ) ) ) ) {

                JOptionPane.showMessageDialog( null , Andie.getLanguage("error_unacceptable_file_name") , Andie.getLanguage( "error_warning" ) , JOptionPane.WARNING_MESSAGE );
                return ; 

            } 

        }

        // saves file if user inputted name contains no '.' and saved with default ".jpeg"
        if ( ! fileName.contains( "." ) ) {

            File outputFile = new File( imageFilename + ".jpeg" ) ; 
            ImageIO.write( current , "jpeg" , outputFile ) ;
            return ; 

        } 

        String extension = fileName.substring( 1 + fileName.lastIndexOf( "." ) ).toLowerCase() ; 

        // deals with user inputted file extensions, as well as stray cases with '.'
        if ( "jpeg".equals( extension ) || "png".equals( extension ) || "gif".equals( extension ) && fileName.contains( "." ) ) {

            fileName = fileName.replace( "." , "" ) + "." + extension ; 
            String newImageFilename = fileLocation + fileName ; 

            File outputFile = new File( newImageFilename ) ; 
            ImageIO.write( current , extension , outputFile ) ;  

        } else {

            fileName = fileName.replace( "." , "" ) + ".jpeg" ; 
            String newImageFilename = fileLocation + fileName ; 

            File outputFile = new File( newImageFilename ) ; 
            ImageIO.write( current , "jpeg" , outputFile ) ; 

        }
    
    }

    /**
     * <p>
     * Apply an {@link ImageOperation} to this image.
     * </p>
     * 
     * @param op The operation to apply.
     */
    public void apply(ImageOperation op) {
        current = op.apply(current);
        ops.add(op);
        if(macroEnabled){
            macroOps.add(op);
        }
    }

    /**
     * <p>
     * Undo the last {@link ImageOperation} applied to the image.
     * </p>
     */
    public void undo() {
        ImageOperation operation = ops.pop();
        redoOps.push(operation);
        if(macroEnabled){
            macroOps.push(operation);
        }
        refresh();
    }

    /**
     * <p>
     * Reapply the most recently {@link undo}ne {@link ImageOperation} to the image.
     * </p>
     */
    public void redo()  {
        ImageOperation operation = redoOps.pop();
        if(macroEnabled){
            macroOps.pop();
        }
        apply(operation);
    }

    /**
     * <p>
     * Get the current image after the operations have been applied.
     * </p>
     * 
     * @return The result of applying all of the current operations to the {@link original} image.
     */
    public BufferedImage getCurrentImage() {
        return current;
    }

    /**
     * <p>
     * Reapply the current list of operations to the original.
     * </p>
     * 
     * <p>
     * While the latest version of the image is stored in {@link current}, this
     * method makes a fresh copy of the original and applies the operations to it in sequence.
     * This is useful when undoing changes to the image, or in any other case where {@link current}
     * cannot be easily incrementally updated. 
     * </p>
     */
    private void refresh()  {
        current = deepCopy(original);
        for (ImageOperation op: ops) {
            current = op.apply(current);
        }
    }

    /**
     * <p>
     * Returns the current image filename for use in the main method to reopen the working image. 
     * </p>
     * 
     * @return image filename 
     */
    public String getFilename() {

        return imageFilename ; 

    }

    /**
     * <p>
     * Returns the status of the macro operation.
     * </p>
     * 
     * @return true if the macro operation is being recorded.
     */
    public boolean getMacroStatus(){
        return macroEnabled;
    }

    /**
     * <p>
     * Switches the macro recording on or off, depdending on its state.
     * </p> 
     */
    public void toggleMacro(){
        if(macroEnabled){
            macroEnabled = false;
        }else{
            macroEnabled = true;
        }
    }

    /**
     * <p>
     * Clears the operations stack stored in macroOps.
     * </p>
     */
    public void clearMacroOps(){
        macroOps.clear();
    }

    /**
     * <p>
     * Saves the set of recorded operations into a separate <code>_macro.ops</code> file.
     * </p>
     * 
     * <p>
     * Saves a set of operations from the file with <code>.ops</code> added.
     * This method will also save the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @throws Exception If something goes wrong.
     */
    public void saveMacro() throws Exception {
        if (this.macroOpsFilename == null) {
            this.macroOpsFilename = this.imageFilename + "_macro.ops";
        }
        // Write operations file
        FileOutputStream fileOut = new FileOutputStream(this.macroOpsFilename);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(this.macroOps);
        objOut.close();
        fileOut.close();
    }

    /**
     * <p>
     * Save a macro as a specified name.
     * </p>
     * 
     * <p>
     * Saves an <code>_macro.ops</code> file provided as a parameter.
     * </p>
     * 
     * @param imageFilename The file location to save the macro to.
     * @throws Exception If something goes wrong.
     */
    public void saveAsMacro(String imageFilename) throws Exception {
        this.macroOpsFilename = imageFilename + "_macro.ops";
        saveMacro();
    }

    /**
    * <p>
    * Selects a saved macro operation and applies it to an image.
    * </p>
    * 
    * @param filePath The file to open the image from.
    * @throws Exception If something goes wrong.
    */
   public void openMacro(String filePath) throws Exception {
       macroOpsFilename = filePath;
       current = deepCopy(original);
       
       try {
           FileInputStream fileIn = new FileInputStream(this.macroOpsFilename);
           ObjectInputStream objIn = new ObjectInputStream(fileIn);

           // Silence the Java compiler warning about type casting.
           // Understanding the cause of the warning is way beyond
           // the scope of COSC202, but if you're interested, it has
           // to do with "type erasure" in Java: the compiler cannot
           // produce code that fails at this point in all cases in
           // which there is actually a type mismatch for one of the
           // elements within the Stack, i.e., a non-ImageOperation.
           @SuppressWarnings("unchecked")
           Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
           ops = opsFromFile;
           redoOps.clear();
           objIn.close();
           fileIn.close();
       } catch (Exception ex) {
           // Could be no file or something else. Carry on for now.
       }
       this.refresh();
   }
    /**
     * Calling this clears the ops, redoOps and macroOps stacks.
     * Should only call this when absolutely needed, such as opening a new image.
     */
    public void clearOpsStack(){
        ops.clear();
        redoOps.clear();
        if(getMacroStatus() == true){
            toggleMacro();
        }
        clearMacroOps();
   }

}
