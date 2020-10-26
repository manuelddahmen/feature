package one.empty3.feature ;


import java.io.File ;
import one.empty3.library.Point3D;
import one.empty3.io.ProcessFile;
import static org.junit.Assert.*;
import javax.imageio.ImageIO;
import one.empty3.feature.*;
import java.util.logging.*; 

public class ProxyValue extends ProcessFile {

    public boolean process (File in, File out) {
        
    if(!in.getName().endsWith(".jpg"))
        return false;
    File file = in;
    PixM original = null;
    
    try {
        original = PixM.getPixM(ImageIO.read(in), 500.0);
    } catch(Exception ex) {
        ex.printStackTrace();
      return false;
       // assertTrue(false);
      
     }
        PixM copy = original.copy();
         for (int i = 0; i < original.columns; i++)

                for (int j = 0; j < original.lines; j++)
                  
                        
                    
                 
                        if(original.getIntensity(i,j)<0.3){
                         
                         Point3D p = searchFromTo(original, i, j, 0.3, 1.0);
                         if(p!=null) {
                              colors(original, i, j, copy);
                         }
               
                    }
                
          
    
    
    try {
       ImageIO.write( copy.getImage(), "jpg", out);
     } catch (Exception ex){
         return false;
     }
       
       System.gc();
     
        return true;
    } 
        
        
        
    public Point3D searchFromTo(
           PixM original, int i, int j, double min, double value) {
        
        for(int i2=0; i2<original.columns/2; i2++)
            for(int j2=0; j2<original.lines/2; j2++)
                { 
                Point3D p = null;
                if(original.getIntensity(i+i2, j+j2)>= min) {
                        p = new Point3D(1.*i-i2, 1.*j-j2, original.get(i-i2,j-j2));
                    }
                if(original.getIntensity(i+i2, j-j2)>= min) {
                        p = new Point3D(1.*i+i2, 1.*j-j2, original.get(i+i2,j-j2));
                    }
                if(original.getIntensity(i-i2, j+j2)>= min) {
                        p = new Point3D(1.*i-i2, 1.*j+j2, original.get(i-i2,j+j2));
                    }
                if(original.getIntensity(i-i2, j-j2)>= min) {
                        p = new Point3D(1.*i+i2, 1.*j-j2, original.get(i-i2,j-j2));
                    }
                
                if(p!=null)
                    return p;
             }
          
        
        return null;
    }
    
    public void colors (PixM m1, int i, int j,
                            PixM m2) {
        for(int c = 0; c<4; c++) {
        
            m1.setCompNo(c);
            m2.setCompNo(c);
            
            m2.set(i, j, m1.get(i,j));
        }
       
    }
} 
