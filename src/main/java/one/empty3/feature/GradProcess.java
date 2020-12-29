package one.empty3.feature ;


import one.empty3.io.ProcessFile;

import java.io.File;

import static org.junit.Assert.*;

import javax.imageio.ImageIO;

import one.empty3.feature.*;

import java.util.logging.*; 
   
public class GradProcess extends ProcessFile {
   
   public boolean process(File in, File out)
    {
   
    if(!in.getName().endsWith(".jpg"))
        return false;
    File file = in;
    PixM pix = null;
    try {
        pix = PixM.getPixM(ImageIO.read(file), 500.0);
    } catch(Exception ex) {
        ex.printStackTrace();
      return false;
       // assertTrue(false);
      
     }
 /*    M3 p = new M3(pixMOriginal.columns, pixMOriginal.lines,
                         2, 2);
     PixM p1 = new PixM(3,3);
     p1.colorsRegion(0,0,3,3,
                   new double[]{
                      1.0,1.0,1.0,
                      1.0,1.0,1.0,
                      1.0,1.0,1.0
                               }
                   );
     p.setMatrix(0,0,p1);
    // pattern.setColorsRegion(0,1,0,3,3,0.0);
     LocalPattern gf = new LocalPattern(p);
      */
      M3 gf = new GradientFilter(pix.getColumns(),
                                pix.getLines());
      PixM r = gf.filter(pix);getImagesMatrix()[0][0];
           try {
       ImageIO.write( r.normalize(0.0, 1.0). getImage(), "jpg", out);
     } catch (Exception ex){
         return false;
     }
     return true;
    } 
}
