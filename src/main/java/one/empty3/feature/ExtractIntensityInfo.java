package one.empty3.feature;

import one.empty3.library.core.lighting.Colors;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ExtractIntensityInfo {
    private File dir;
    private File file;

    public ExtractIntensityInfo(File file) {
        if (file != null)
            if (file.exists() && file.isDirectory())
                this.dir = file;
            else if (file.isFile())
                this.file = file;
    }

    public static void stream(File f) throws IOException {
        BufferedImage read = ImageIO.read(f);

        PixM pix = new PixM(read);
        String s = "outputFiles/Extracts/";
        new File(s).mkdirs();
     double in = 0.0;
        for(double rMin = 3.0; rMin<9.0; rMin+= 0.001) {
           // for (double min = 0.3; min <= 1.0; min += 0.1) {
                BufferedImage img3 = new BufferedImage(read.getWidth(), read.getHeight(), BufferedImage.TYPE_INT_RGB);
                PixM out = new PixM(pix.columns, pix.lines);
                Histogram2 histogram = new Histogram2(pix);


                List<Histogram2.Circle> pointsOfInterest = histogram.getPointsOfInterest(rMin);

                Color[] colors = new Color[histogram.numLevels];

                for (int i = 0; i < colors.length; i++) {
                    colors[i] = Colors.random();

                }


                double finalMin = min;
                pointsOfInterest.stream().filter(new Predicate<Histogram2.Circle>() {
                    @Override
                    public boolean test(Histogram2.Circle circle) {
                        return circle.i > finalMin;
                    }
                }).forEach(circle -> {
                    System.out.println(circle.toString());
                    out.set((int) circle.x, (int) circle.y, circle.i);


                    Color color = colors[(int) circle.i];
                    Graphics graphics = img3.getGraphics();
                    graphics.setColor(color);
                    graphics.drawRect((int) (circle.x), (int) (circle.y), (int) (1), (int) (1));
                    //img3.setRGB((int) (circle.x), (int) (circle.y), color.getRGB());

                });
                try {
                    File outputFile = new File(s + "0test" + f.getName() + "min" + min + "+rMin"+rMin+".jpg");
                    File outputFile2 = new File(s + "1test" + f.getName() + "min" + min + "rMin2_"+rMin+".jpg");
                    ImageIO.write(out.getImage(), "jpg", outputFile);
                    ImageIO.write(img3, "jpg", outputFile2);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }


                System.gc();
            }
        }
    }

    public static void main(String[] args) {
        ExtractIntensityInfo extractIntensityInfo;
        if (args.length > 1 && args[0] != null) {
            dir = args[0];
        
            file = args[1];
             }
        try {
            extractIntensityInfo.exec();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void exec() throws IOException {
        if (dir == null)
            dir "./";
        if (file == null) {
            Arrays.stream(Objects.requireNonNull(dir.listFiles())).sequential().forEach(f -> {
                try {
                    stream(f);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }
        else
            stream(file);
    }

}
