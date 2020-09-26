package one.empty3.feature;

import one.empty3.library.Point3D;

public class IntuitiveRadialGradient extends FilterPixM {
      private PixM pix;
      private int angles = 12;
double rMaxPixel=2.0, rMaxDiff = 5.0;
      public IntuitiveRadialGradient(PixM image) {
this.pix = image;
      } 

     public void setMax(double rMax, double rMax2) {
         rMaxPixel = rMax;
         rMaxDiff = rMax2;
     } 

      public double filter (double x, double y) {
         
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
               vfarApprox =
                  Math.abs(v-vp);
               angle = 2*Math.PI*(i+0.5)/angles;
          }
          return angle;
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
        int count=0; 
        double dist=0;
        for(int i=x-r2; i<x+r2; i++)
              for(int j=x-r2; j<x+r2; j++) {
                    eval=Math.sqrt(1.0*(x-i)*(x-i)+(y-j)*(y-j));
                  if(eval<=r2 && eval>= r1 && 
          Math.abs(Math.tan(-a1+Math.abs((y-j) /(x-i)) ))>=Math.tan(a1) && 
          Math.abs(Math.tan(a2-Math.abs((y-j) /(x-i)) )) <=Math.tan(a2)
                         ) {
                  sum+= pix.get(i, j) ;//*gauss? Derivate? 
                  count ++;
                  dist += eval;
                       } 
                }
         return sum/count;
    } 

} 
