package test.cosc202.andie;

import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cosc202.andie.MedianFilter;

public class MedianFilterTest {

    private final MedianFilter testFilter1 = new MedianFilter();
    private final MedianFilter testFilter2 = new MedianFilter(3);
    private static BufferedImage testingImage;

    @BeforeAll
    static void getImage(){
        try{
            testingImage = ImageIO.read(new File("andie/clocktower.jpg"));
        }catch (Exception e){
            System.out.println("Failed to find image");
        }
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
            BufferedImage manualImage = ImageIO.read(new File("andie/clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilter1.apply(testingImage);
            Assertions.assertEquals(testImage1.getRGB(1, 1),testImage2.getRGB(1, 1));
            Assertions.assertEquals(testImage1.getRGB(30, 150),testImage2.getRGB(30, 150));
            Assertions.assertEquals(testImage1.getRGB(109, 800),testImage2.getRGB(109, 800));
        }catch (Exception E){
            System.out.println("Failed to find image");
        }
    }

    
    //Test that the correct image operation has been completed using a larger radius, 3
    @Test
    void imageTest2(){
        MedianFilter filter = new MedianFilter(3);
        try{
            BufferedImage manualImage = ImageIO.read(new File("andie/clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilter2.apply(testingImage);
            Assertions.assertEquals(testImage1.getRGB(1, 1),testImage2.getRGB(1, 1));
            Assertions.assertEquals(testImage1.getRGB(30, 150),testImage2.getRGB(30, 150));
            Assertions.assertEquals(testImage1.getRGB(109, 800),testImage2.getRGB(109, 800));
        }catch (Exception E){
            System.out.println("Failed to find image");
        }
    }




}
