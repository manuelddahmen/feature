package one.empty3.feature ;


import javax.imageio.ImageIO;
import one.empty3.io.ProcessFile;
import java.io.File ;
 public class TrueHarrisProcess extends ProcessFile {
 
 public boolean process (File in, File out) {
 
 if(!in.getName().endsWith(".jpg"))

        return false;

    File file = in;

    PixM pixMOriginal = null;

    try {

        pix = PixM.getPixM(ImageIO.read(file), 500.0);

    } catch(Exception ex) {

        ex.printStackTrace();

      return false;

       // assertTrue(false);

      

     }
     
     pix. applyFilter(new TrueHarris()) ;
     
                    

                

            pix.normalize(0.0,1.0);

                

                    //

                                               

                                               

                 try {            

                    ImageIO.write (pix.getImage(),

       "JPEG", out) ;

                           } catch(Exception  ex )

                           {
                           
                           
                           
                           }

                    
     return true;
 } 
 
} 
