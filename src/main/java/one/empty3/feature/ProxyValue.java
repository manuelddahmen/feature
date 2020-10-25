package one.empty3.feature ;
import java.io.File ;
import one.empty3.library.Point2D;
public class Proxy Value extends ProcessFile {

    public boolean process (File in, File out) {
        
        
         for (int i = 0; i < original.columns; i++)

                for (int j = 0; j < original.lines; j++)
                  if(original.getIntensity(i,j)>0.5){
                        
                    
                    for (int c = 0; c < 4; c++) {
                        original.setCompNo(c);
                        Point3D p = searchFromTo(original, i, j, 0.5, 1.0);
                        
                           
                       
                    }
                  }
                
          }
        return false;
    } 
    public Point3D searchFromTo(
           PixM original, int i, int j, double min, value) {
        Point3D p = null;
        for(int i2=0; j2<original.columns/2; i2++)
            for(int j2=0; j2<original.columns/2; j2++)
                { 
                    if(original.get(i+i2, j+j2)>= min) {
                        p = new Point3D(i-i2, j-j2, original.get(i-i2,j-j2));
                    }
                if(original.get(i+i2, j-j2)>= min) {
                        p = new Point3D(i+i2, j-j2, original.get(i+i2,j-j2));
                    }
                if(original.get(i-i2, j+j2)>= min) {
                        p = new Point3D(i-i2, j+j2, original.get(i-i2,j+j2));
                    }
                if(original.get(i-i2, j-j2)>= min) {
                        p = new Point3D(i+i2, j-j2, original.get(i-i2,j-j2));
                    }
                
                ifp!=null)
                    return p;
             }
          }
        
        return null;
    }
} 
