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
                         
                         searchFromTo(original, copy, i, j, 0.3, 1.0);
                         if(p!=null) {
                              
                         }
               
                    }
                
          
    
    
    try {
       ImageIO.write( copy.getImage(), "jpg", out);
     } catch (Exception ex){
        ex.printStackTrace();
        return false;
     }
       
       System.gc();
     
        return true;
    } 
        
        
        
    public void searchFromTo(
           PixM original, PixM copy, int i, int j, double min, double value) {
        Point3D p = null;
        for(int i2=1; i2<original.columns/2; i2++)
            for(int j2=1; j2<original.lines/2; j2++)
                { 
                
                p = null;
                if(original.getIntensity(i+i2, j+j2)>= min) {
                        p = new Point3D(1.*i+i2, 1.*j+j2, original.get(i+i2,j+j2));
                    }
                if(original.getIntensity(i+i2, j-j2)>= min) {
                        p = new Point3D(1.*i+i2, 1.*j-j2, original.get(i+i2,j-j2));
                    }
                if(original.getIntensity(i-i2, j+j2)>= min) {
                        p = new Point3D(1.*i-i2, 1.*j+j2, original.get(i-i2,j+j2));
                    }
                if(original.getIntensity(i-i2, j-j2)>= min) {
                        p = new Point3D(1.*i-i2, 1.*j-j2, original.get(i-i2,j-j2));
                    }
                
                if(p!=null) {
                     copyPixel(original, (int)(double)(p.get(0)), 
                                     (int)(double)(p.get(1)), copy, i, j);
                    return;
               } else {
                   
                }
             }
          
        System.out.println("error not found");
        return;
    }
    
    public void copyPixel(PixM m1, int i, int j,
                            PixM m2, int i2, int j2) {
        for(int c = 0; c<4; c++) {
        
            m1.setCompNo(c);
            m2.setCompNo(c);
            
            m2.set(i2, j2, m1.get(i,j));
        }
       
    }
} 
