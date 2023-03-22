package cosc202.andie;

import java.awt.image.BufferedImage;

public  class RotateImage implements ImageOperation, java.io.Serializable {
    private int degrees;

    public RotateImage(int degrees) {
        this.degrees = degrees;
    }

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


