package one.empty3.feature;

import one.empty3.io.ProcessFile;
import java.io.File;

public class IsleProcess extends ProcessFile {
    public boolean process(File in, File out) {



        if(!in.getName().endsWith(".jpg"))

            return false;

        File file = in;

        PixM pix = null;
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
            pix = PixM.getPixM(img, 500.0);

        } catch(Exception ex) {

            ex.printStackTrace();

            return false;

       // assertTrue(false);

      

         }
          
     IsleFilterPixM th = new IsleFilterPixM
         (pix.getImage());
   
      try {            

          ImageIO.write (pix.getImage(), "JPEG", out) ;

      } catch(Exception  ex ) {
  
      }
     
      return false;
    }
}
