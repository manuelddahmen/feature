   package one.empty3.feature ;
   import one.empty3.io.ProcessFile;

import java.io.File;

import static org.junit.Assert.*;

import javax.imageio.ImageIO;

import one.empty3.feature.*;

import java.util.logging.*; 
   
   public class GradProcess {
   
   public boolean process(File in, File out)
    {
   
    if(!in.getName().endsWith(".jpg"))
        return false;
    File file = in;
    PixM pixMOriginal = null;
    try {
        pixMOriginal = PixM.getPixM(ImageIO.read(file), 500.0);
    } catch(Exception ex) {
        ex.printStackTrace();
      return false;
       // assertTrue(false);
      
     }
     M3 pattern = new M3(pixMOriginal, pixMOriginal,
                         2, 2);
     pattern.setColorsRegion(0,0,0,0,3,3,1.0);
     pattern.setColorsRegion(0,1,0,3,3,0.0);
     LocalPattern gf = new LocalPattern(pixMOriginal.columns, pixMOriginal.lines,
                                         3, 2);
      
      PixM r = gf.filter(new M3(pixMOriginal, 1, 1)).getImagesMatrix()[0][0] ;
           try {
       ImageIO.write( r.normalize(0.0, 1.0). getImage(), "jpg", out);
     } catch (Exception ex){
         return false;
     }
     return true;
    } 
}
