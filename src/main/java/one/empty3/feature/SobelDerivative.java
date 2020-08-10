package one.empty3.feature;

import java.awt.image.BufferedImage;

/*
sobel. 3×3 ou plus. 1*2+1
|p1 -p2| (/ n/n)?
*/
public class SobelDerivative extends FilterPixM {
     double[] sobelX = new double[] {-1,-2,-1,0,0,0
                                ,1,2,1};
     double[] sobelY = new double[] {-1,0,-1,
                                 -2,0,2
                                ,1,0,1};
     public SobelDerivative(int l, int c) {
        super(l, c);
    }

    public SobelDerivative(BufferedImage image) {
        super(image);
    }

    public double x(int i, int j) {
           return sobelX[i*lines + j];
     }
   public double y(int i, int j){
        return sobelY[i*lines + j];
   }
   public void theta (int i, int j) {
   
   }

    @Override
    public double filter(double x, double y) {
        int i = (int)(x+lines/2);
        int j = (int)(x+columns/2);
        return get(i, j);
    }
}
