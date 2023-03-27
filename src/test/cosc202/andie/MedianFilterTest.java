package test.cosc202.andie;
import java.awt.image.BufferedImage;
import java.awt.image.*;
import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.MedianFilter;

public class MedianFilterTest {

    private final MedianFilter testFilter1 = new MedianFilter();

    //Dummy Test
    @Test
    void initialDummyTest(){
    }

    //Tests Default radius is 1.
    @Test
    void getDefaultRadius(){
        
        MedianFilter filter = new MedianFilter(1);
        Assertions.assertEquals(filter.getRadius(), testFilter1.getRadius());
        Assertions.assertTrue(1 == testFilter1.getRadius());
    }

    //Tests that if we call constructor passing a value it will change the radius.
    @Test
    void applyRadius(){
        MedianFilter filter = new MedianFilter(9);
        Assertions.assertTrue(9 == filter.getRadius());
    }

    //Test that the correct image operation has been completed
    @Test
    void testImages(){
        MedianFilter filter2 = new MedianFilter(2);
        //filter2.apply(null);
    }


}
