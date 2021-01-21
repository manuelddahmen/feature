package one.empty3.feature.kmeans ;

import java.io.*;
import one.empty3.io.ProcessFile ;
import one.empty3.feature.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
/***
 line : l, c, r, g, b
*/
public class MakeDataset {
    public MakeDataset (File image, 
            File outputCsv, int res) {
        try {
         BufferedImage img = ImageIO.read 
          (image) ;
        PixM pix = PixM.getPixM(img, res) ;


        PrintWriter pw = new PrintWriter(outputCsv) ;
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
 public static void writeFeature
  (Set<Integer, double[]> data,
   File csvout) {
       try {
      
  PrintWriter pw = new PrintWriter(csvout) ;
    
  for(int i=0; i<data.size(); i++) {
     for(int j=0; j<data.get(i).length; j++) {
     pw.print(data.get(i)[j]);
   }
     pw.println();
   }
        } catch(IOException ex) {
         ex.printStackTrace () ;
      } 
 }
 
} 
