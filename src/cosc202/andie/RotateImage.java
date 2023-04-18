package cosc202.andie;

import java.awt.image.BufferedImage;
/**
 * <p>
 * Allows for rotation of the input image.
 * </p>
 * 
 * <p>
 * Rotation of an image is achieved by first storing the degree of rotation upon construction of the 
 * RotateImage object. When the apply method is called, we refer to the value stored in the degrees data-
 * field in order to call the correct rotate method. Once called, the rotate methods will iterate over 
 * the pixel values of an input BufferedImage and assign them to the correct rotated locations in the 
 * output BufferedImage.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Tan Robertson
 * @version 1.0
 */
public  class RotateImage implements ImageOperation, java.io.Serializable {

    /**
     * the degree of rotation that will rotate teh image.
     */
    private int degrees;

    /**
     * Constructor for a RotateImage object.
     * 
     * @param degrees The degree of rotation. Must be either -90, 90 or 180.
     */
    public RotateImage(int degrees) {
        this.degrees = degrees;
    }

    /**
     * Method used to apply a rotation to an input BufferedImage. Refers to the degrees
     * data-field to apply the desired rotation.
     * 
     * @param inputImg The image to be rotated.
     */
    public BufferedImage apply(BufferedImage inputImg) {
        if(degrees == 90){
            return rotateClockwise90(inputImg);
        }
        if(degrees == -90){
            return rotateCounterclockwise90(inputImg);
        }
        else{
            return rotate180(inputImg);
        }
    }

    /**
     * Iterates through the pixels values of the input image to achieve a clockwise 90-degree
     * rotation.
     * 
     * @param inputImage the image to be rotated.
     * @return The rotated image.
     */
    public BufferedImage rotateClockwise90(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(height, width, inputImage.getType());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                outputImage.setRGB(height - y - 1, x, inputImage.getRGB(x, y));
            }
        }
        return outputImage;
    }
    /**
     * Iterates through the pixels values of the input image to achieve a counter-clockwise
     * 90-degree rotation.
     * 
     * @param inputImage the image to be rotated.
     * @return The rotated image.
     */
    public BufferedImage rotateCounterclockwise90(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(height, width, inputImage.getType());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                outputImage.setRGB(y, width - x - 1, inputImage.getRGB(x, y));
            }
        }
        return outputImage;
    }
    /**
     * Iterates through the pixels values of the input image to achieve a full
     * 180-degree rotation.
     * 
     * @param inputImage the image to be rotated.
     * @return The rotated image.
     */
    public BufferedImage rotate180(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                outputImage.setRGB(width - x - 1, height - y - 1, inputImage.getRGB(x, y));
            }
        }
        return outputImage;
    }
    
}


