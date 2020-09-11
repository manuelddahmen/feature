package one.empty3.feature;
import java.awt.images.*;
import java.io.File;
public class WriteFile {
    static int no = 1;
    static String directory = "./output/";
    public static void init() {
      
    }
  
    public static boolean writeNext(String name, BufferedImage imageJpeg) {
        new File(directory).mkdirs();
        File n = new File(directory+File.separator+name+".jpg");
        try {
            ImageIO.write(imageJpeg, "jpg", n);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        no++;
    }
}
