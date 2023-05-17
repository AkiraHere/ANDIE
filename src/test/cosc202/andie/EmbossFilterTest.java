package test.cosc202.andie;


import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.util.Random;

import cosc202.andie.EmbossFilter;

public class EmbossFilterTest {

    private final EmbossFilter testFilterNorth = new EmbossFilter(0);
    private final EmbossFilter testFilterNorthE = new EmbossFilter(1);
    private final EmbossFilter testFilterEast = new EmbossFilter(2);
    private final EmbossFilter testFilterSouthE = new EmbossFilter(3);
    private final EmbossFilter testFilterSouth = new EmbossFilter(4);
    private final EmbossFilter testFilterSouthW = new EmbossFilter(5);
    private final EmbossFilter testFilterWest = new EmbossFilter(6);
    private final EmbossFilter testFilterNorthW = new EmbossFilter(7);
    private static BufferedImage testingImage;

    //Initialize the image before testing
    @BeforeAll
    static void getImage(){
        try{
            testingImage = ImageIO.read(EmbossFilterTest.class.getResourceAsStream("clocktower.jpg"));
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

    //Test that the correct image operation has been completed using North facing kernel
    @Test
    void imageTestNorth(){
        EmbossFilter filter = new EmbossFilter(0);
        try{
            BufferedImage manualImage = ImageIO.read(EmbossFilterTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilterNorth.apply(testingImage);

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

    //Test that the correct image operation has been completed using North-East facing kernel
    @Test
    void imageTestNorthE(){
        EmbossFilter filter = new EmbossFilter(1);
        try{
            BufferedImage manualImage = ImageIO.read(EmbossFilterTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilterNorthE.apply(testingImage);

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

    //Test that the correct image operation has been completed using East facing kernel
    @Test
    void imageTestEast(){
        EmbossFilter filter = new EmbossFilter(2);
        try{
            BufferedImage manualImage = ImageIO.read(EmbossFilterTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilterEast.apply(testingImage);

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

    //Test that the correct image operation has been completed using South-East facing kernel
    @Test
    void imageTestSouthE(){
        EmbossFilter filter = new EmbossFilter(3);
        try{
            BufferedImage manualImage = ImageIO.read(EmbossFilterTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilterSouthE.apply(testingImage);

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

    //Test that the correct image operation has been completed using South facing kernel
    @Test
    void imageTestSouth(){
        EmbossFilter filter = new EmbossFilter(4);
        try{
            BufferedImage manualImage = ImageIO.read(EmbossFilterTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilterSouth.apply(testingImage);

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

    //Test that the correct image operation has been completed using South-West facing kernel
    @Test
    void imageTestSouthW(){
        EmbossFilter filter = new EmbossFilter(5);
        try{
            BufferedImage manualImage = ImageIO.read(EmbossFilterTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilterSouthW.apply(testingImage);

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

    //Test that the correct image operation has been completed using West facing kernel
    @Test
    void imageTestWest(){
        EmbossFilter filter = new EmbossFilter(6);
        try{
            BufferedImage manualImage = ImageIO.read(EmbossFilterTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilterWest.apply(testingImage);

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

    //Test that the correct image operation has been completed using North-West facing kernel
    @Test
    void imageTestNorthW(){
        EmbossFilter filter = new EmbossFilter(7);
        try{
            BufferedImage manualImage = ImageIO.read(EmbossFilterTest.class.getResourceAsStream("clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilterNorthW.apply(testingImage);

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
