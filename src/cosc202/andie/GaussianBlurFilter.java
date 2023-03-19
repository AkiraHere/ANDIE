package cosc202.andie ; 

import java.awt.image.* ; 
import java.lang.Math ;

public class GaussianBlurFilter implements ImageOperation , java.io.Serializable {

    private int radius , xDist , yDist ; 
    private float sigma ; 

    GaussianBlurFilter( int radius ) {

        this.radius = radius ; 
        this.xDist = -radius ; 
        this.yDist = -radius ; 
        this.sigma = radius / 3.0f ; 

    }

    GaussianBlurFilter() {

        this.radius = 1 ; 
        this.xDist = -1 ; 
        this.yDist = -1 ; 
        this.sigma = 1.0f / 3.0f ; 

    }

    public BufferedImage apply( BufferedImage input ) {

        int size = ( 2*radius + 1 ) * ( 2*radius + 1 ) ; 
        float[] array = new float[size]; 

        for ( int i = 0 ; i < size ; i++ ) {

            array[i] = returnGaussianValue() ; 

            if ( xDist == radius ) {

                xDist = -radius ; 
                yDist++ ; 

            }

            xDist++ ; 

        }

        float sum = 0 ; 
        for ( float i : array ) {

            sum = sum + i ; 

        }

        for ( int i = 0 ; i < size ; i++ ) {

            array[i] /= sum ; 
            System.out.println(array[i]) ; 

        }

        Kernel kernel = new Kernel( 2*radius+1 , 2*radius+1 , array ) ; 
        ConvolveOp convOp = new ConvolveOp( kernel ) ; 
        BufferedImage output = new BufferedImage( input.getColorModel() , input.copyData(null) , input.isAlphaPremultiplied() , null ) ; 
        convOp.filter( input , output ) ; 

        return output ; 

    }

    public float returnGaussianValue () {
 
        float gaussianValue = (float) (Math.exp(-(xDist*xDist + yDist*yDist) / (2 * sigma*sigma)) / (2 * Math.PI * sigma*sigma)) ; 

        return gaussianValue ; 

    }

}