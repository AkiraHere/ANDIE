package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

import cosc202.andie.GaussianBlurFilter;

public class GaussianBlurTest {

    static final GaussianBlurFilter testFilter = new GaussianBlurFilter();
    private static BufferedImage testingImage;

    //Test that we can retrieve an image, May need to specify a directory depending on machine.
    @BeforeAll
    static void initializeImage(){
        try{
            testingImage = ImageIO.read(GaussianBlurTest.class.getResourceAsStream("clocktower.jpg"));
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

    //Dummy Test - always a pass
    @Test
    void initialDummyTest(){
    }

    //Testing that 2 separate instances of GaussianBlurFilter apply the same filter to the test image
    @Test
    void filterImageTest1(){
        GaussianBlurFilter filter = new GaussianBlurFilter();
        try{
            BufferedImage manualImage = ImageIO.read(GaussianBlurTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilter.apply(testingImage);

            int randomXCoord = randomInRange(testImage1.getWidth());
            int randomYCoord = randomInRange(testImage1.getHeight());

            Assertions.assertEquals(testImage1.getRGB(randomXCoord, randomYCoord),testImage2.getRGB(randomXCoord, randomYCoord));
            Assertions.assertEquals(testImage1.getRGB(0, 0),testImage2.getRGB(0, 0));
            Assertions.assertEquals(testImage1.getRGB(testImage1.getWidth()-1, testImage1.getHeight()-1),testImage2.getRGB(testImage2.getWidth()-1, testImage2.getHeight()-1));

        }catch (IOException e){
            System.out.println("Failed to find image");
            fail();
        }
    }

    //Tests the gaussianValue method returns the expected output.
    @Test
    void gaussianValueTest(){
        GaussianBlurFilter filter = new GaussianBlurFilter();
        Assertions.assertEquals(testFilter.returnGaussianValue(), filter.returnGaussianValue());
    }

    //Tests the gaussianValue method at different radius.
    @Test
    void gaussianValueTest2(){
        GaussianBlurFilter filter = new GaussianBlurFilter(3);
        GaussianBlurFilter filter2 = new GaussianBlurFilter(3);
        Assertions.assertEquals(filter2.returnGaussianValue(), filter.returnGaussianValue());
    }



    

}
