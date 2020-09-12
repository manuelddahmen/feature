package one.empty3.feature;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.logging.*;
public class WriteFile {
    static int no = 1;
    static String directory = "./output/";
    public static void init() {
      
    }
  public static boolean writeNext( BufferedImage imageJpeg, String name) {
      writeNext(name, imageJpeg);
        return true;
  }
    public static boolean writeNext(String name, BufferedImage imageJpeg) {
        new File(directory).mkdirs();
        File n = new File(directory+File.separator+no+"-"+name+".jpg");
        try {
            
            no++;
            ImageIO.write(imageJpeg, "jpg", n);
            Logger.getLogger(WriteFile.class).log(Level.INFO, "file written: " + n.getAbsolutePath());
            return true;
            
        } catch(Exception ex) {
            
            ex.printStackTrace();
            return false;
        }

    }
}
