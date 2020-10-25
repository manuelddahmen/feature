package one.empty3.feature ;

import one.empty3.io.ProcessFile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import one.empty3.library.core.lighting.Colors;
public class HarrisProcess extends ProcessFile {
     PixM m;
     
     public HarrisProcess () {
         
     }
     
     public boolean process(File in, File out) {
       try {
        
        
           PixM m = PixM.getPixM(ImageIO.read(in), 500.0);
        
           PixM  m2 = new PixM(m.columns, m.lines);
           
           HarrisToPointInterest h = new HarrisToPointInterest(2, 2);
           
           m2 = m.applyFilter(h);
           
          LocalExtrema le = new LocalExtrema(m2.columns, m2.lines, 3, 9);
            
          //m2 = le.filter(new M3(m2, 1, 1) ).getImagesMatrix () [0] [0] ;
        le = new LocalExtrema(m2.columns, m2.lines, 3, 6);
            
          m2 = le.filter(new
            M3(m2, 1, 1) ).getImagesMatrix () [0] [0] ;
        
           ImageIO.write(m2.getImage(), "JPEG", out);
           
           return true;
      } catch(Exception ex) {
           ex.printStackTrace();
      }
          
      return false;
    } 
}
