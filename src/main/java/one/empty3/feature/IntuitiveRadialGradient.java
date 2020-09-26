public class IntuitiveRadialGradient extends PixM {
      private PixM pix;
      public double filter (double x, double y, 
         double rMaxPixel, rMaxDiff) {
          double pixel = 0.0;
          double [] pixelExtAngular
             = new double[12] ;
          for(int i=x-rMaxPixel; i<x+rMaxPixel; i++)
              for(int j=x-rMaxPixel; j<x+rMaxPixel; j++)
                  sum+= get(i, j) ;
          arc (int x, int y, double r1, double r2, 
             double a1, double a2) ;
      } 
    public Point2D pcircle(int x, int y, 
              double angle, double r) {
        return new Point2D(Math.cos(2*Math.PI*
              angle),Math.sin(2*Math.PI*
              angle)) 
          .mult(r);
    } 
    public double arc (int x, int y, double r1, double r2, 
              double a1, double a2) {
         double eval = 0.0;
        for(int i=x-r2; i<x+r2; i++)
              for(int j=x-r2; j<x+r2; j++)
                  if((eval=Math.sqrt((x-i)*(x-i)
   +(y-j)* (y-j) ))<=r2 & & eval> r1) 
                  sum+= get(i, j) ;
     } 
      public double avgPixXY(int x, int y) {
          
      } 

} 
