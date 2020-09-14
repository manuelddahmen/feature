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
    public double formula(int i, int j) {
        double d = Math.sqrt(i*i+j*j);
        double angle =  Math.atan (j/i*1.0)
        Point2D vec = new Point2D(
           d*Math.cos(2*Math.PI*angle), 
           d*Math.sin(2*Math.PI*angle)
        );
        
    }
    @Override
    public double filter(double x, double y) {
       
      
        // For for
        // ±± (x*2)/(x*x+1)*k
        for(int i=-avg; i<avg; i++)
           for(int j=-avg; j<avg; j++) {
              dxy += formula(i, j);
              count++;
           }
        return 1.0;
    }
}
