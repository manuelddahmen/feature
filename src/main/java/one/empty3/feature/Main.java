package one.empty3.feature;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Arrays.stream(ImageIO.getWriterFormatNames()).forEach(s1 ->
                System.out.println("Format name : \"" + s1 + "\""));
        Arrays.stream(Objects.requireNonNull(new File("resources").list())).sorted().forEach(s -> {
            String s0 = s.substring(s.lastIndexOf(".") + 1);
            String ext = s0.equals("jpg")||s0.equals("jpeg")?"jpg":s0;
            if (Arrays.asList(ImageIO.getWriterFormatNames()).contains(ext)) {
                try {

                    System.out.println("format name image " + ext + " found");

                    PixM pixM = new PixM(ImageIO.read(new File("resources/"+s)), PixM.COMP_RED);
                    BufferedImage grayScale = pixM.getGrayScale();
                    File file = new File("outputFiles/res_" + "00"+System.nanoTime()+"__"+

                            Time.from(Instant.now()).toString().replace(' ', '_').replace('|', '_')
                                    .replace('\\', '_').replace('/', '_').replace(':', '_')
                            + ".png");
                    System.out.println(file.getAbsolutePath() + "\n(width, height) = " + grayScale.getWidth() +
                            ", " + grayScale.getHeight() + ")");

                    if (!ImageIO.write((RenderedImage) grayScale, "png", file)) {
                        System.out.println("Error inappropriate writer or not found "+"png");
                        System.exit(-2);
                    } else {
                        System.out.println("Done writing : " + file.toString());

                    }
                    System.out.println("Thread terminated without exception");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
