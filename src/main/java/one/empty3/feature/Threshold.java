package one.empty3.feature.kmeans.*;

public class FilterPixM {
    public double filter(double x, double y) {
        double d = 0.0;
        if(getIntensity(x,y)>0.8)
            return 1.0;
        return 0.0;
    }
}
