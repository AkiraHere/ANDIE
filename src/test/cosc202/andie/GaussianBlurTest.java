package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

import cosc202.andie.GaussianBlurFilter;

public class GaussianBlurTest {

    static final GaussianBlurFilter testFilter = new GaussianBlurFilter();
    private static BufferedImage testingImage;

    //Test that we can retrieve an image, May need to specify a directory depending on machine.
    @BeforeAll
    static void initializeImage(){
        try{
            testingImage = ImageIO.read(new File("C:/Users/lukew/OneDrive - University of Otago/Papers/COSC202/ANDIE/andie/src/test/cosc202/andie/clocktower.jpg"));
        }catch (IOException e){
            System.out.println("Failed to find image");
            fail();
        }
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
            BufferedImage manualImage = ImageIO.read(new File("C:/Users/lukew/OneDrive - University of Otago/Papers/COSC202/ANDIE/andie/src/test/cosc202/andie/clocktower.jpg"));
            BufferedImage testImage1 = filter.apply(manualImage);
            BufferedImage testImage2 = testFilter.apply(testingImage);

            Assertions.assertEquals(testImage1.getRGB(1, 1),testImage2.getRGB(1, 1));
            Assertions.assertEquals(testImage1.getRGB(30, 150),testImage2.getRGB(30, 150));
            Assertions.assertEquals(testImage1.getRGB(109, 800),testImage2.getRGB(109, 800));

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
