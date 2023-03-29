package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.ColourActions;

public class ColourActionsTest{

    final ColourActions testColourActions = new ColourActions();

    @Test
    void initialDummyTest(){
    }

    //test successful instantiation of the object
    @Test
    void testColourActionsNotNull(){
        ColourActions colourActions = new ColourActions();
        Assertions.assertNotNull(colourActions);
    }

    
}
