package test.cosc202.andie;
import java.awt.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.MedianFilter;

public class MedianFilterTest {
    @Test
    void initialDummyTest(){
    }

    @Test
    void getDefaultRadius(){
        MedianFilter filter = new MedianFilter();
        MedianFilter filter2 = new MedianFilter(1);
        Assertions.assertEquals(filter2.getRadius(), filter.getRadius());
        Assertions.assertTrue(1 == filter.getRadius());
    }

    @Test
    void applyRadius(){
        MedianFilter filter = new MedianFilter(9);
        Assertions.assertTrue(9 == filter.getRadius());
    }
}
