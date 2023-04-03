package test.cosc202.andie;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

import cosc202.andie.FlipImage;

public class FlipImageTest {

    private FlipImage testHorizontalFilter = new FlipImage(true); 
    private FlipImage testVerticalFilter = new FlipImage(false); 
    private static BufferedImage testingImage;

    //Initializes our testing image
    @BeforeAll
    static void getImage(){
        try{
            testingImage = ImageIO.read(FlipImageTest.class.getResourceAsStream("clocktower.jpg"));
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

    //Dummy Test, always succeeds
    @Test
    void initialDummyTest(){
    }

    //Tests that the same filter is applied when looking at another instance of a horizontal flip
    @Test 
    void argbValueTestHorizontal(){
        FlipImage filter = new FlipImage(true);
        try{
            BufferedImage manualImage = ImageIO.read(FlipImageTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testHorizontalFilter.apply(testingImage);

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

    //Tests that the same filter is applied when looking at another instance of a vertical flip
    @Test 
    void argbValueTestVertical(){
        FlipImage filter = new FlipImage(false);
        try{
            BufferedImage manualImage = ImageIO.read(FlipImageTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testVerticalFilter.apply(testingImage);

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
