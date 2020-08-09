package one.empty3.feature;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.sql.Time;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.nio.file.*;

public class Main {
    public void makeGoodOutput(File original, File folderOutput, PrintWriter out) {
Path source = FileSystems.getDefault().getPath(original) ;
     Path newdir = FileSystems.getDefault().getPath(folderOutput);
     Files.copy(source, newdir.resolve(source.getFileName()));
      //  out.println("<a href=\""+original.getName()+"\">orginal</a>\n");
       // out.println("<a href=\""+folderOutput.getPath()+"\">computed result image folder</a>\n");
    }
    
    public static void main(String[] args) {
        Arrays.stream(ImageIO.getWriterFormatNames()).forEach(s1 ->
                System.out.println("Format name : \"" + s1 + "\""));
        for(String s : Objects.requireNonNull(new File("resources").list())) {
            String s0 = s.substring(s.lastIndexOf(".") + 1);
            String ext = s0.equals("jpg")||s0.equals("jpeg")?"jpg":s0;
            if (Arrays.asList(ImageIO.getWriterFormatNames()).contains(ext)) {
                try {
                    
                    System.out.println("format name image " + ext + " found");

                    PixM pixM = new PixM(ImageIO.read(new File("resources/"+s)));
                    BufferedImage grayScale = new MIMmops().harris(pixM, 1.2, 5, 1)
                            .getImage();
                    File file = new File("outputFiles/res_" + "00"+System.nanoTime()+"__"+

                            Time.from(Instant.now()).toString().replace(' ', '_').replace('|', '_')
                                    .replace('\\', '_').replace('/', '_').replace(':', '_')
                            + ".png");
                    file.mkdirs();
                    System.out.println(file.getAbsolutePath() + "\n(width, height) = " + grayScale.getWidth() +
                            ", " + grayScale.getHeight() + ")");

                    if (!ImageIO.write((RenderedImage) grayScale, "png", file)) {
                        System.out.println("Error inappropriate writer or not found "+"png");
                        System.exit(-2);
                    } else {
                        System.out.println("Done writing : " + file.toString());

                    }
                    makeGoodOutput(new File("resources/"+s), file, null);
                    System.out.println("Thread terminated without exception");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
