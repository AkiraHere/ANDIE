package test.cosc202.andie;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

import cosc202.andie.RotateImage;

public class RotateImageTest {
    
    private RotateImage testFilter1 = new RotateImage(90); 
    private RotateImage testFilter2 = new RotateImage(180); 
    private static BufferedImage testingImage;

    //Initializes our testing image
    @BeforeAll
    static void getImage(){
        try{
            testingImage = ImageIO.read(RotateImageTest.class.getResourceAsStream("clocktower.jpg"));
        }catch (IOException e){
            System.out.println("Failed to find image");
            fail();
        }
    }

    /**
     * Method for generating a random number within the bounds of the height and width of the testing image.
     * @param x the bound of the testing image, such as height or width.
     * @return pseudo-random number within the specified range.
     */
    public int randomInRange(int x){
        Random r = new Random();
        return r.nextInt(x);
    }

    //Dummy test always passes
    @Test
    void initialDummyTest(){
    }

    //Tests that the image operation is the same output for 90 degree rotation
    @Test
    void imageTest1(){
        RotateImage filter = new RotateImage(90);
        try{
            BufferedImage manualImage = ImageIO.read(RotateImageTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.rotateClockwise90(manualImage);
            BufferedImage testImage2 = testFilter1.apply(testingImage);

            int randomXCoord = randomInRange(testImage1.getWidth());
            int randomYCoord = randomInRange(testImage1.getHeight());

            Assertions.assertEquals(testImage1.getRGB(randomXCoord, randomYCoord),testImage2.getRGB(randomXCoord, randomYCoord));
            Assertions.assertEquals(testImage1.getRGB(0, 0),testImage2.getRGB(0, 0));
            Assertions.assertEquals(testImage1.getRGB(testImage1.getWidth()-1, testImage1.getHeight()-1),testImage2.getRGB(testImage2.getWidth()-1, testImage2.getHeight()-1));
        } catch (IOException e){
            System.out.println("Failed to find image");
            fail();
        }
    }

    //Tests that the image operation is the same output for 180 degree rotation
    @Test
    void imageTest2(){
        RotateImage filter = new RotateImage(180);
        try{
            BufferedImage manualImage = ImageIO.read(RotateImageTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.rotate180(manualImage);
            BufferedImage testImage2 = testFilter2.apply(testingImage);

            int randomXCoord = randomInRange(testImage1.getWidth());
            int randomYCoord = randomInRange(testImage1.getHeight());

            Assertions.assertEquals(testImage1.getRGB(randomXCoord, randomYCoord),testImage2.getRGB(randomXCoord, randomYCoord));
            Assertions.assertEquals(testImage1.getRGB(0, 0),testImage2.getRGB(0, 0));
            Assertions.assertEquals(testImage1.getRGB(testImage1.getWidth()-1, testImage1.getHeight()-1),testImage2.getRGB(testImage2.getWidth()-1, testImage2.getHeight()-1));
        } catch (IOException e){
            System.out.println("Failed to find image");
            fail();
        }
    }


}
