package one.empty3.feature ;
import java.io.File ;
import one.empty3.library.Point2D;
public class Proxy Value extends ProcessFile {

    public boolean process (File in, File out) {
        
        
         for (int i = 0; i < original.columns; i++)

                for (int j = 0; j < original.lines; j++)
                  if(original.getIntensity(i,j)>0.1){
                        
                    
                    for (int c = 0; c < 4; c++) {
                        searchFromTo(original, i, j, 0.1, 1.0).forEach(
                           p -> {
                           }
                        );
                    }
                  }
                }
          }
        return false;
    } 
    public Point2D searchFromTo(
           PixM original, int i, int j, double min, value) {
        return null;
    }
} 
