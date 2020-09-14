package one.empty3.feature;

/*
sobel. 3×3 ou plus. 1*2+1
|p1 -p2| (/ n/n)?
*/
public class SizedGradient extends FilterPixM {
    private double dist = 2.0;

    public SizedGradient(double dist) {
        this.dist = dist
    }

    private void fill() {

    }

    @Override
    public double filter(double x, double y) {
       
      
        // For for
        // ±±
        return 1.0;
    }
}
