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
     public HarrisProcess (){}
     
     public boolean process(File in, File out) {
      try {
        
        
           PixM imageCoutours = PixM.getPixM(ImageIO.read(in), 500.0);
        
           this.m = imageCoutours;
        
           BufferedImage img = m.getImage();
        
           PixM  m2 = new PixM(m.columns, m.lines);
           
           HarrisToPointsOfInterest h = new HarrisToPointsOfInterest();
           
           m2 = m.applyFilter(h);
           
        
           ImageIO.write(m2.getImage(), "JPEG", out);
           
           
      } catch() {
           ex.printStackTrace();
      }
          

} 
