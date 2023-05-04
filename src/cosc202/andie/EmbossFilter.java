package cosc202.andie;

import java.awt.image.BufferedImage;

public class EmbossFilter implements ImageOperation, java.io.Serializable{
    private int option;

    public EmbossFilter(int option){
        if(option < 0 || option > 7){
            this.option = 0;
        }else{
            this.option = option;
        }
    }

    public BufferedImage apply(BufferedImage input){
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(),input.getType());

        for(int x = 0; x < input.getWidth(); x++){
            for(int y = 0; y < input.getHeight(); y++){
                int[][] kernal = getKernal(option);


            }
        }

        return output;
    }



    /**
     * Used to retrieve the kernal for desired direction of the emboss filter effect.
     * 
     * @param option the integer specified upon construction, determines
     * the direction of the emboss filter.
     * @return the kernal of the specified emboss filter.
     */
    private int[][] getKernal(int option){
        int[][][] options = {
            {
                {0, 0, 0},
                {1, 0, -1}, //Option 0
                {0, 0, 0}
            },
            {
                {1, 0, 0}, 
                {0, 0, 0},  //Option 1
                {0, 0, -1}
            },
            {
                {0, 1, 0},
                {0, 0, 0},  //Option 2
                {0, -1, 0}
            },
            {
                {0, 0, 1},
                {0, 0, 0},  //Option 3
                {-1, 0, 0}
            },
            {
                {0, 0, 0},
                {-1, 0, 1}, //Option 4
                {0, 0, 0}
            },
            {
                {-1, 0, 0},
                {0, 0, 0},  //Option 5
                {0, 0, 1}
            },
            {
                {0, -1, 0},
                {0, 0, 0},  //Option 6
                {0, 1, 0}
            },
            {
                {0, 0, -1},
                {0, 0, 0},  //Option 7
                {-1, 0, 0}
            }
        };
        return options[option];
    }
}
