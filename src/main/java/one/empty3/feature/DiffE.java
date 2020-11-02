package one.empty3.feature ;

import one.empty3.library.Point2D;
import java.awt.Color;

public class DiffE {
    class ColorTranform {
        Circle ref;
        public void rotate(){}
        public double compare(double[] rbga){}
        
    }
    class Circle {
        PixM p;
        double x, y, r, a;
        public Circle(PixM m,double x,double
                          y, double r)
            {
        p=m; this.x=x;this.y=y;this.r=r;
        }
        public void rotate(double angle) {
            a+=angle;
        }
       public Point2D get(double a, double r) {
           return new Point2D(r*Math.cos(2*Math.PI*a),
                              r*Math.sin(2*Math.PI*a));
       }
        public void variate(double x, double y,
                           double r, double rotate) {
            this.x+= x;
            this.y+= y;
            this.r+= r;
            this.a+= rotate;
        }
        public double get(double r) {
            double pi = 0.0;
            for (double a = 0; a<r; a+= 1/r/2/Math.PI)
            
            pi+= p.getIntensity((int)(r*Math.cos(a)) ,(int)(r*Math.sin(a)));
            return pi;
        }
        
        public int compareTo(Circle c2 ){
            return (int)(r-c2.r);
        }
        public double dist(Circle b) {
            for(int i = - r; i<r;i++) 
                diff += m.getRgb(b.x. b.y) .
               . moins(m.getRGB(x,y);

            return diff; // attention red
                         
        } 
    }
    public void sort(double [] [] m) {

        // loop for rows of matrix

        for (int i = 0; i < m.length; i++) {

 

            // loop for column of matrix

            for (int j = 0; j < m[i].length; j++) {

 

                // loop for comparison and swapping

                for (int k = 0; k < m[i].length - j - 1; k++) {

                    if (m[i][k] > m[i][k + 1]) {

 

                        // swapping of elements

                        double t = m[i][k];

                        m[i][k] = m[i][k + 1];

                        m[i][k + 1] = t;

                    

                }
              }
            }
        }
        
    }
        
    int tri = 100;
    public double[][] dist(PixM p1, PixM p2) {
       double dist [] = new double[20*20];
       for(double g=0; g<p1.columns*p1.lines;  g++) {
           for(int h=0; h<p1.columns*p1.lines;  h++) {
       Circle cij = new Circle(p1, g*p1,columns/20., 1.*h*p1.lines/20., 20.);
       Circle ci1 = new Circle(p2, g*p2,columnns/20., 1.*h*p2.lines/20., 20.);  
       Circle cir = new Circle(p2, g*r*p2.columns/20., 1.*h*r*p2.lines/20., 20.);
                               
                          
               // try min diff
               // sort by importance or surgace
               // mapping
           
       
    
        
           
        for(int i=0; i<cij.r; i++)
           for(int j = 0 ; j< ci1.r; j++)
               dist[i][j]= cij.r-ci1.r;
            
        
    for(int i=0; i<cij.r; i++)
           for(int j = 0 ; j< cir.r; j++)
               dist[i][j]= cij.r-cir.r;
            
              }
           }
    
       // dist[i][j].sort();
           
           
        //return dist;
      }// end method
   //end class
}// end class
