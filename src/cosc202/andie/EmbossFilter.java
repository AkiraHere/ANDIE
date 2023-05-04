package cosc202.andie;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class EmbossFilter implements ImageOperation, java.io.Serializable{
    private int option;
    private static final int MID_COLOR = 128;


    /**
     * Constructor for an instance of emboss filter. Requires an option.
     * 
     * @param option the integer value between 0-7 that determines the kernel.
     */
    public EmbossFilter(int option){
        if(option < 0 || option > 7){
            this.option = 0;
        }else{
            this.option = option;
        }
    }


    /**
     * Applies the emboss filter to the input image.
     * 
     * @param input the input image to have the emboss filter applied to.
     * @return the image with the emboss filter applied.
     */
    public BufferedImage apply(BufferedImage input){
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(),input.getType());

        for(int x = 0; x < input.getWidth(); x++){
            for(int y = 0; y < input.getHeight(); y++){
                int[][] kernel = getKernel(option);

                int sumA = 0, sumR = 0, sumG = 0, sumB = 0;

                for (int i = 0; i < kernel.length; i++) {
                    for (int j = 0; j < kernel[i].length; j++) {
                        int xPos = x + j;
                        int yPos = y + i;
                        int argb;
                        if((xPos < 0 || xPos >= input.getWidth()) && (yPos < 0 || yPos >= input.getHeight())){
                            argb = input.getRGB(x, y);
                        }else if(xPos < 0 || xPos >= input.getWidth()){
                            argb = input.getRGB(x, yPos);
                        }else if(yPos < 0 || yPos >= input.getHeight()){
                            argb = input.getRGB(xPos, y);
                        }else{
                            argb = input.getRGB(xPos, yPos); 
                        }
                        int a = (argb & 0xFF000000) >> 24;
                        int r = (argb & 0x00FF0000) >> 16;
                        int g = (argb & 0x0000FF00) >> 8;
                        int b = (argb & 0x000000FF);
                        int weight = kernel[i][j];

                        sumA += a * weight;
                        sumR += r * weight;
                        sumG += g * weight;
                        sumB += b * weight;
                    }
                }

                int avg = (sumA + sumR + sumG + sumB) / 4;
                int embossVal = MID_COLOR - avg;

                Color embossColor;
                if (embossVal < 0) {
                    int darkerVal = MID_COLOR - Math.abs(embossVal);
                    embossColor = new Color(darkerVal, darkerVal, darkerVal);
                } else {
                    int brighterVal = MID_COLOR + embossVal;
                    embossColor = new Color(brighterVal, brighterVal, brighterVal);
                }

                output.setRGB(x, y, embossColor.getRGB());
            }
        }

        return output;
    }



    /**
     * Used to retrieve the kernel for desired direction of the emboss filter effect.
     * 
     * @param option the integer specified upon construction, determines
     * the direction of the emboss filter.
     * @return the kernal of the specified emboss filter.
     */
    private int[][] getKernel(int option){
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
