package cosc202.andie;

import java.awt.image.BufferedImage;

public class FlipImage implements ImageOperation, java.io.Serializable {

    private final boolean flipHorizontal;

    public FlipImage(boolean flipHorizontal) {
        this.flipHorizontal = flipHorizontal;
    }

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
