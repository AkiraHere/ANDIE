package test.cosc202.andie;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

import cosc202.andie.ConvertToGrey;

public class ConvertToGreyTest {

    private ConvertToGrey testFilter = new ConvertToGrey(); 
    private static BufferedImage testingImage;


    //Initializes our testing image
    @BeforeAll
    static void getImage(){
        try{
            testingImage = ImageIO.read(new File("C:/Users/lukew/OneDrive - University of Otago/Papers/COSC202/ANDIE/andie/src/test/cosc202/andie/clocktower.jpg"));

        }catch (IOException e){
            System.out.println("Failed to find image");
            fail();
        }
    }

    //Dummy Test, always succeeds
    @Test
    void initialDummyTest(){
    }

    //Tests that the same filter is applied when looking at another instance
    @Test 
    void argbValueTest(){
        ConvertToGrey filter = new ConvertToGrey();
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


}
