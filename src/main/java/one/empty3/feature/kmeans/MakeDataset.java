package one.empty3.feature.kmeans ;

import java.io.*;

/***
 line : x, y, r, g, b
*/
public class MakeDataset {
    public MakeDataset (File image, File outputCsv) {
        BufferedImage img = ImageIO.read 
          (image) ;
        PixM pix = new PixM(img) ;

    } 

} 
