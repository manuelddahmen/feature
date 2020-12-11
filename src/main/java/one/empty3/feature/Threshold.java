package one.empty3.feature.kmeans.*;
import one.empty3.feature.*;
public class Threshold {
    public double filter(double x, double y) {
        double d = 0.0;
        int size = 0;
        double sigma = 1.2;
        
        if(getIntensity(x,y)>0.8)
            return 1.0;
        return 0.0;
    }
}
