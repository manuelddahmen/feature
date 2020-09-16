package one.empty3.feature;

/*
sobel. 3×3 ou plus. 1*2+1
|p1 -p2| (/ n/n)?
*/
public class SizedGradient extends FilterPixM {
    private double dist = 2.0;

    public SizedGradient(double dist) {
        super((int)dist, (int)dist);
        this.dist = dist;
    }

    private void fill() {

    }


    public Point2D xy(Point2D p,
      double dist) {
        double x = p.getX();
        return new Point2D((x*2)/(x*x+1)*4,
         p.getY()-dist));
    }
    public double formula(int i, int j) {
        double d = Math.sqrt(i*i+j*j);
        double angle =  Math.atan (1.0*j/i);
        Point2D vecX = new Point2D(
           d*Math.cos(2*Math.PI*angle), 
           d*Math.sin(2*Math.PI*angle)
        );
        Point2D vecY = new Point2D(
           d*Math.sin(2*Math.PI*angle), 
           - d*Math.cos(2*Math.PI*angle)
        );
        Point2D p2 = new Matrix33 (
             vecX.getX(), vecX.getY(), 0.0,
             vecY.getX(), vecY.getY(), 0.0,
             0.0, 0.0, 1.0
        ).mult(new Point2D(i, j, 0.0);
        return xy(p2); 
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
