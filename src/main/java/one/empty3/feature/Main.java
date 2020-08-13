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
    public static void makeGoodOutput(File original, File folderOutput, PrintWriter out) {
        try{
            Path source = FileSystems.getDefault().getPath(original.getAbsolutePath()) ;
            Path newdir = FileSystems.getDefault().getPath(folderOutput.getAbsolutePath());
            Files.copy(source, newdir.resolve(source.getFileName()));
      //  out.println("<a href=\""+original.getName()+"\">orginal</a>\n");
       // out.println("<a href=\""+folderOutput.getPath()+"\">computed result image folder</a>\n");
     }catch(Exception ex){
            ex.printStackTrace();
        }
    } 
    
    public static void work(File dir, BufferedImage imageToWrite, String outputFilename) throws IOException
        {
                    dir.mkdirs();
                    System.out.println(dir.getAbsolutePath() + "\n(width, height) = " + imageToWrite.getWidth() +
                            ", " + imageToWrite.getHeight() + ")");

                    if (!ImageIO.write((RenderedImage) imageToWrite, "png",new File(dir.getAbsolutePath()+
                            outputFilename))) {
                        System.out.println("Error inappropriate writer or not found "+"png");
                        System.exit(-2);
                    } else {
                        System.out.println("Done writing : " + outputFilename.toString());

                    }
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
                    BufferedImage origImg = pixM.getImage();

                    FilterPixM gaussFilterPixM = new GaussFilterPixM(1, 1.1);

                    BufferedImage outputImage = MIMmops.applyMultipleFilters(
                            pixM, 4, /*gaussFilterPixM, */new SobelDerivative(true),
                            new SobelDerivative(false)).getImage();

                    File directory = new File("outputFiles/res_" + "00"+System.nanoTime()+"__"+

                            Time.from(Instant.now()).toString().replace(' ', '_').replace('|', '_')
                                    .replace('\\', '_').replace('/', '_').replace(':', '_')
                            + "/");
                    String output ="/Output"+s+".png";
                    String input ="/Input"+s+".png";
                    
                     work(directory, origImg, input);
                    work(directory, outputImage, output);
                    
                    makeGoodOutput(new File("resources/"+s), directory, null);
                    System.out.println("Thread terminated without exception");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
