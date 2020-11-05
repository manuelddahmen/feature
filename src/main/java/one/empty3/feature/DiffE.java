package one.empty3.feature ;

import one.empty3.library.Vec;
import java.awt.Color;

public class DiffE extends ProcessFile {
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
        public double get(double r, double g, double b){
           return Math.sqrt(r*r+g*g+b*b);
            }
               public Circle getLevel(Histogram2.Circle c) {
        // I mean. Parcourir le cercle
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        int count = 0;
        double intensity = 0.0;
        for (double i = c.x-c.r; i <= c.x+c.r; i++) {
            for (double j = c.y-c.r; j <= c.y+c.r; j++) {
                if (Math.sqrt((i-c.x) * (i-c.x) + (j-c.y) * ( j-c.y)) <= c.r*c.r
                        && c.x-c.r>=0 && c.y-c.r>=0 && c.x+c.r<m.columns && c.y+c.r<m.lines) {
                    intensity += m.getIntensity((int) i, (int) j);
                    count++;
                }
            }
        }

        if(count>0) {
            c.i = intensity / count;
        }
        else {
            c.i = 0.0;
            c.r = 0.0;
        } 
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
            for(int i = -b.x+ b.r; i<b.y+b.r;i++) 
                diff += m.getRgb(b.x. b.y) .
               . moins(m.getRgb(x,y);

            return diff; // attention red
                         
        } 

public Circle getLevel(Circle c) {
        // I mean. Parcourir le cercle
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        int count = 0;
        double intensity = 0.0;
         c.r = r;
        for (double i = c.x-c.r; i <= c.x+c.r; i++) {
            for (double j = c.y-c.r; j <= c.y+c.r; j++) {
                if (Math.sqrt((i-c.x) * (i-c.x) + (j-c.y) * ( j-c.y)) <= c.r*c.r
                        && c.x-c.r>=0 && c.y-c.r>=0 && c.x+c.r<m.columns && c.y+c.r<m.lines) {
                    intensity += m.getIntensity((int) i, (int) j);
                    count++;
                }
            }
        }

        if(count>0) {
            c.i = intensity / count;
        }
        else {
            c.i = 0.0;
            c.r = 0;
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
    public boolean process(File in,File out) {
List<Point> candidate = new ArrayList() ;
      [] [] [] circles = new  Circle[elementSize ] [elementSize ] [2] ;
       for(double g=0; g<p1.columns;  g++) {
           for(int h=0; h<p1.lines;  h++) {
       Circle cij[g][h] [1]= new Circle(p1, g*p1.columns/elementSize., 1.*h*p1.lines/elementSize,, elementSize);
       Circle ci1 =[g][h] [0] =new Circle(p2, g*p2.columns/elementSize, 1.*h*p2.lines/elementSize, elementSize);  
       Circle cir = new Circle(p2, g*r*p2.columns/elementSize, 1.*h*r*p2.lines, elementSize, elementSize);
                               
       double[] [] di1, di2;
               
               // sort by importance or surgace
       try {
        
        
           PixM p1 = PixM.getPixM(ImageIO.read(in), 500.0);
           PixM p2 = PixM.getPixM(ImageIO.read(out), 500.0);
        
               // mapping
               
       int sort=0;
       while(int i<cir.length) {
           while(int j<cir[i].length) {
               double rMin = sort[i][j];
                
           }
       }/*
       for(int i1=0; i1<p2.columns ; i1++) 
          for(int j1=0; j1<p2.lines ; j1++) {
for(int i2=0; i1<p2.columns ; i2++) 
   
          for(int j2=0; j2<p2.lines ; j2++) {
if(level(cir) ==level(ci1) {
pix.rcolorsRegion(i1, j1, j1-j1, j1-i1, Color. Red) ;
} 

}




        // try min diff
               di1 = cij.dist(ci1);
               di2 = cir.dist(ci1);
           
        for(int i=0; i<cij.r; i++)
           for(int j = 0 ; j< ci1.r; j++)
               dist[i][j]= cij.r-ci1.r;
            
        
    for(int i=0; i<cij.r; i++)
           for(int j = 0 ; j< cir.r; j++)
               dist[i][j]= cij.r-cir.r;
            
              }
           }
        
        */
    candidates. forEach(vec - > {
       pix2 = setnCompNo(0) ;
pix2. set(vec.get(2), vec.get(3), rij) ;
           pix2 = setnCompNo(1) ;
pix2.set(vec.get(2))
           pix2 = setnCompNo(2) ;
pix1. set(di1) ) ;
pix1= setnCompNo(0) ;
pix1. set(di1) ) ;
           pix1= setnCompNo(1) ;
pix1. set(di1) ) 
           pix1= setnCompNo(2) ;
pix1. set(di1) )  ;
        //return dist;
      }// end method
   //end class
e
ImageIO.write(m2.getImage(), "JPEG", out);
           ImageIO.write(m2.getImage(), "JPEG", new File(out.getParent()+2+"jpg"));
           return true;
      } catch(Exception ex) {
           ex.printStackTrace();
      }
          
      return false;
}// end class
