package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * Operation that allows for an image to be flipped.
 * </p>
 * 
 * <p>
 * Flip image is achieved by iterating through the pixel values of the input image, then 
 * simply reversing the order for the output image. Depending on the boolean flag "flipHorizontal"
 * we can monitor wheter this flip should occur on the horizontal or vertical plane.
 * </p>
 * 
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Tan Robertson
 * @version 1.0
 */

public class FlipImage implements ImageOperation, java.io.Serializable {

    /**
     * the flag that determines whether we flip an image horizontally or not.
     */
    private final boolean flipHorizontal;

    /**
     * Constructor for the FlipImage object.
     * 
     * @param flipHorizontal Flag for deciding when the image is to be flipped on the horizontal plane.
     */
    public FlipImage(boolean flipHorizontal) {
        this.flipHorizontal = flipHorizontal;
    }

    /**
     * Method used to create the flipped image effect.
     * 
     * @param inputImg The image to be flipped
     * @return The output image, flipped on either the horizontal or vertical plane.
     */
    @Override
    public BufferedImage apply(BufferedImage inputImg) {
    int width = inputImg.getWidth();
    int height = inputImg.getHeight();

    BufferedImage outputImg = new BufferedImage(width, height, inputImg.getType());
    int[] pixels = new int[width * height];
    inputImg.getRGB(0, 0, width, height, pixels, 0, width);

    if (flipHorizontal) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width / 2; x++) {
                int leftIdx = x + y * width;
                int rightIdx = (width - 1 - x) + y * width;
                int tmp = pixels[leftIdx];
                pixels[leftIdx] = pixels[rightIdx];
                pixels[rightIdx] = tmp;
            }
        }
    }else {
        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width; x++) {
                int topIdx = x + y * width;
                int bottomIdx = x + (height - 1 - y) * width;
                int tmp = pixels[topIdx];
                pixels[topIdx] = pixels[bottomIdx];
                pixels[bottomIdx] = tmp;
            }
        }
    }

    outputImg.setRGB(0, 0, width, height, pixels, 0, width);
    return outputImg;
}

}
