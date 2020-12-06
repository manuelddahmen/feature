package one.empty3.feature.kmeans ;

import java.io.*;
import one.empty3.io.ProcessFile ;
import one.empty3.feature.*;
/***
 line : l, c, r, g, b
*/
public class MakeDataset {
    public MakeDataset (File image, File outputCsv) {
        try {
         BufferedImage img = ImageIO.read 
          (image) ;
        PixM pix = new PixM(img) ;


        PrintWriter pw = new PrintWriter(output) ;
        for(int l=0; l<pix.getLines(); l++) 
         for(int c=0; c<pix.getColumns(); c++) {
          pix. setCompNo(0) ;
          double r = pix.get(l, c) ;

          pix. setCompNo(1) ;
          double g = pix.get(l, c) ;

          pix. setCompNo(2) ;
          double b = pix.get(l, c) ;

          pw.println("" +l+" " +c+" " +
             r+" " + g + " " + b) ;
         } 
      } catch(IOException ex) {
         ex.printStackTrace () ;
      } 
    } 
} 
