package one.empty3.feature ;

import one.empty3.library.Point2D;
import java.awt.Color;

public class DiffE {
private int sizeElement = 20;
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
      [] [] [] circles = new  Circle[] [] [] 
       for(double g=0; g<p1.columns;  g++) {
           for(int h=0; h<p1.lines;  h++) {
       Circle cij = new Circle(p1, g*p1.columns/elementSize., 1.*h*p1.lines/elementSize,, elementSize);
       Circle ci1 = new Circle(p2, g*p2.columns/elementSize, 1.*h*p2.lines/elementSize, elementSize);  
       Circle cir = new Circle(p2, g*r*p2.columns/elementSize, 1.*h*r*p2.lines, elementSize, elementSize);
                               
       double[] [] di1, di2;
               
               // sort by importance or surgace
               
              
               // mapping
               
       int sort=0;
       while(int i<cil.length) {
           while(int j<cil[i].length) {
               double rMin = sort[i][j];
               if(cil.getIntensity(i,j)==sort[i][j];
                  
           }
       }
       for(int i1=0; i1<p2.columns ; i1++) 
          for(int j1=0; j1<p2.lines ; j1++) {
for(int i2=0; i1<p2.columns ; i2++) 

          for(int j2=0; j2<p2.lines ; j2++) {
}




        // try min diff
               di1 = cij.(ci1)
               di2 = ci1.dist(cir);
           
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
