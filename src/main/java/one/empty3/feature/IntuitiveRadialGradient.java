public class IntuitiveRadialGradient extends PixM {
      private PixM pix;
      private int angles = 12;
      public double filter (double x, double y, 
         double rMaxPixel, rMaxDiff) {

          double pixel = 0.0;
          double [] pixelExtAngular
             = new double[12] ;
          double vp =
              arc(x, y, 0, rMaxPixel,
               0., 2*Math.PI) ;
          double vfarApprox = 400000;
          double angle =-1;
          for(int i=0; i<angles; i++) {
            double v =  arc (x, y, rMaxPixel, rMaxDiff, 
              2*Math.PI*i/angles) ;
            if(Math.abs(v-vp) <vFarApprox)
               vfarApprox = v;
               angle = 2*Math.PI*(i+0.5)/angles;
          }
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
         double eval = 0.0; int count=0; double dist=0;
        for(int i=x-r2; i<x+r2; i++)
              for(int j=x-r2; j<x+r2; j++)
                  if((eval=Math.sqrt((x-i)*(x-i)
   +(y-j)* (y-j) ))<=r2 & & eval>= r1) {
                  sum+= get(i, j) ;//*gauss? Derivate? 
                  count ++;
                  dist += eval;
         return sum/count;
     } 
      public double avgPixXY(int x, int y) {
          
      } 

} 
