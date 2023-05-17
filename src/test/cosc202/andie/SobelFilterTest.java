package test.cosc202.andie;


import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

import cosc202.andie.SobelFilter;

public class SobelFilterTest {

    private final SobelFilter testFilter1 = new SobelFilter(0);
    private final SobelFilter testFilter2 = new SobelFilter(1);
    private static BufferedImage testingImage;

    //Initialize the image before testing
    @BeforeAll
    static void getImage(){
        try{
            testingImage = ImageIO.read(SobelFilterTest.class.getResourceAsStream("clocktower.jpg"));
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

    //Dummy Test
    @Test
    void initialDummyTest(){
    }

    //Test that the correct image operation has been completed using horizontal kernel
    @Test
    void imageTest1(){
        SobelFilter filter = new SobelFilter(0);
        try{
            BufferedImage manualImage = ImageIO.read(SobelFilterTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilter1.apply(testingImage);

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

        //Test that the correct image operation has been completed using the vertical kernel
        @Test
        void imageTest2(){
            SobelFilter filter = new SobelFilter(1);
            try{
                BufferedImage manualImage = ImageIO.read(SobelFilterTest.class.getResourceAsStream("clocktower.jpg"));
                BufferedImage testImage1 = filter.apply(manualImage);
                BufferedImage testImage2 = testFilter2.apply(testingImage);
    
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
}