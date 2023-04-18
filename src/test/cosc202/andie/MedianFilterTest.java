package test.cosc202.andie;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

import cosc202.andie.MedianFilter;

public class MedianFilterTest {

    private final MedianFilter testFilter1 = new MedianFilter();
    private final MedianFilter testFilter2 = new MedianFilter(3);
    private static BufferedImage testingImage;

    //Initialize the image before testing
    @BeforeAll
    static void getImage(){
        try{
            testingImage = ImageIO.read(MedianFilterTest.class.getResourceAsStream("clocktower.jpg"));
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

    //Tests Default radius is 1.
    @Test
    void getDefaultRadius(){
        MedianFilter filter = new MedianFilter(1);
        Assertions.assertEquals(filter.getRadius(), testFilter1.getRadius());
        Assertions.assertTrue(1 == filter.getRadius());
    }

    //Tests that if we call constructor passing a value it will change the radius.
    @Test
    void applyRadius(){
        MedianFilter filter = new MedianFilter(9);
        Assertions.assertTrue(9 == filter.getRadius());
    }

    //Test that the correct image operation has been completed using default radius
    @Test
    void imageTest1(){
        MedianFilter filter = new MedianFilter();
        try{
            BufferedImage manualImage = ImageIO.read(MedianFilterTest.class.getResourceAsStream("clocktower.jpg"));
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

    
    //Test that the correct image operation has been completed using a larger radius, 3
    @Test
    void imageTest2(){
        MedianFilter filter = new MedianFilter(3);
        try{
            BufferedImage manualImage = ImageIO.read(MedianFilterTest.class.getResourceAsStream("clocktower.jpg"));
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
