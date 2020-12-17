package one.empty3.feature;

import one.empty3.feature.kmeans.*;

public class Threshold {




    public double filter(double x, double y) {
        double d = 0.0;
        int size = 2;
        double sigma = 1.2;
 
       
GaussFilterPixM gauss = new GaussFilterPixM(size, sigma);

       
        if(gauss.getIntensity((int)x,(int)y)>0.8)
            return 1.0;
        return 0.0;
    }
}
