package one.empty3.feature;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        for (double min = 0.3; min <= 1.0; min += 0.1) {
            PixM out = new PixM(pix.columns, pix.lines);
            Histogram histogram = new Histogram(pix, 100, min, 10.0, 0.3);
            double ecart = 0.3;
            List<Histogram.Circle> pointsOfInterest = histogram.getPointsOfInterest(ecart);
            System.out.println(pointsOfInterest.size());
            pointsOfInterest.forEach(circle -> {
                System.out.println(circle.toString());
                out.set((int) circle.x, (int) circle.y, circle.i);
            });
            try {
                File outputFile = new File(s + "test" + min + ".jpg");
                ImageIO.write(out.getImage(), "jpg", outputFile);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            System.gc();
        }
    }

    public static void main(String[] args) {
        ExtractIntensityInfo extractIntensityInfo;
        if (args.length > 0 && args[0] != null) {
            extractIntensityInfo = new ExtractIntensityInfo(new File(args[0]));
        } else
            extractIntensityInfo = new ExtractIntensityInfo(null);
        try {
            extractIntensityInfo.exec();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void exec() throws IOException {
        if (dir == null)
            dir = new File("G:/Apps/_anew/feature/outputFiles/___Mon_Aug_31_08_31_16_CEST_2020/IMG_20200822_204208.jpg/2-smoothed_grad");
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
