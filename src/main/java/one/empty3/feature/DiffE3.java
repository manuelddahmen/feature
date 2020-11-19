/*
comparaipar cerlcle d intensite et
de lumierrs filtrers.
*/

package one.empty3.feature ;
import one.empty3.io.ProcessFile;
import one.empty3.library.*;
import one.empty3.library.shader.Vec;
import java.awt.Color;
import java.io.File;
import java.awt.Point;
import java.util.*;
import javax.imageio.ImageIO;

class Circle {
        
 double x, y, r, a;
       
        public Circle(PixM m,double x,double
                          y, double r)
            {

}
        public void rotate(double angle) {
            a+=angle;
        }
        public double get(double r, double g, double b){
           return Math.sqrt(r*r+g*g+b*b);
        }
    
        
       public Point3D get(double a, double r) {
          Point3D n =new Point3D(r*Math.cos(2*Math.PI*a),
                              r*Math.sin(2*Math.PI*a), 0.0);
          return n;
       }
        public void variate(double x, double y,
                           double r, double rotate) {
            this.x+= x;
            this.y+= y;
            this.r+= r;
            this.a+= rotate;
        }
        public double getIntensity(int i, int j) {
            double count=0.0;
         double
            i0, j0;
            
              
              
              double iin =0.0;
              
 double tin= 0.0;
         for(i0=i-r; i0<i+r; i0++)
                for(j0=i-r;j0<i+r; j0++) {                  count +=1 ; ii+= r;}
                    if(Math.sqrt((i0-i)*(i0-i)
                                 +(j0-j)*(j0-j))
                       <=r) {
                                count ++;
                                iin=m.getIntensity(i,j);
                                tin  +=iin;
                            }
               
         return tin;
       }
               
               
                     public double dist(Circle b) {
           
            double diff = 0.0;
              
            for(int i = (int)(-b.x+ b.r); i<b.y+b.r;i++)
                for(int j=(int)( -b.x+ b.r); j<b.y+b.r; j++)
               
                diff  = diff + m1.getRgb(i. j).norme()
                             - m1.getRgb(i, j).norme();
             
           
                  
              
                   diff =(P.n(b.x,b.oy, b.i)).moins(
                    p1.mean(b.x-b.r, b.y-r, b.x+b.i, b.y+b.i).norme();
                       
                       
                    
               
                
     
                 return diff; // attention red
                
        } 
                           }
               
               
               
               
               


*/
public class DiffE3 extends ProcessFile {
/*
private int sizeElement = 20, elementSize=20;
    class ColorTranform {
        
        public void rotate(){}
        public double compare(double[] rbga){}
        
    }
    
    
           
        this.x=x;this.y=y;this.r=r;
        
        public double get(Circle c, double r, double a1, double a2) {
            double pi = 0.0;
            for (double a = a1; a<a2; a+= 1/r/2/Math.PI) {
            
                pi+= c.getIntensity((int)(r*Math.cos(a)) ,(int)(r*Math.sin(a)));
            }
            return pi;
                
        
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
                    intensity += m1.getIntensity((int) i, (int) j);
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
    return c;
  }
       
    
   
   
 */
    public boolean process(File in, File out) {
        
        try {
        
    if(!in.getName().endsWith(".jpg"))
        return false;
    
     
        // work on gpeatutes
       
ImageIO.write(m2.getImage(), "JPEG", out);
           ImageIO.write(mout.getImage(), "JPEG", new File(out.getParent()+2+"jpg"));
           
           //ImageIO.write(m2g, "JPEG", new File(out.getParent()+5+"jpg"));
           return true;
      } catch(Exception ex) {
           ex.printStackTrace();
      } finally {
      }
      return true;
  }
}
     
            */

