package one.empty3.feature ;


import javax.imageio.ImageIO;
import one.empty3.io.ProcessFile;
import java.io.File ;


public class TrueHarrisProcess extends ProcessFile {
 
    public boolean process (File in, File out) {
 
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
          
     TrueHarris th = new TrueHarris(img);
     for (int c=0; c<3; c++) {
         th.setCompNo(c);
         pix.setCompNo(c);
         
         for(int i=0;i<pix.getColumns();i++)
             for(int j=0;j<pix.getLines();j++)
                 th.filter(i, j) ;
      }
                    

                

       pix.normalize(0.0,1.0);

                

                    //

                                               

                                               

      try {            

          ImageIO.write (pix.getImage(), "JPEG", out) ;

      } catch(Exception  ex ) {
  
      }
     return true;
 } 
 
} 
