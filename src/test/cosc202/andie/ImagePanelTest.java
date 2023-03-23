package test.cosc202.andie;

import java.awt.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.ImagePanel;

public class ImagePanelTest {

    @Test
    void initialDummyTest(){
    }

    @Test
    void getZoomInitialValue(){
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(100.0, testPanel.getZoom());
       // Assertions.assertEquals(-1, testPanel.getZoom());//this test should fail on purpose
    }

    //Tests zoom function
    @Test
    void getZoomAfterSetZoom(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(0.0);
        
        Assertions.assertFalse(testPanel.getZoom() == 100.0);
        Assertions.assertTrue(testPanel.getZoom() >= 50.0);
    }

    //Tests that the default preffered size of an image is 450 x 450
    @Test
    void getDefaultPrefferedSize(){
        ImagePanel testPanel = new ImagePanel();
        Dimension dimension = new Dimension(450, 450);
        Assertions.assertEquals(dimension.getWidth(), testPanel.getPreferredSize().getWidth());
        Assertions.assertEquals(dimension.getHeight(), testPanel.getPreferredSize().getHeight());
    } 

}
