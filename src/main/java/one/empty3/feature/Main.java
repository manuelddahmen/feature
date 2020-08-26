package one.empty3.feature;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.nio.file.*;
import java.util.function.Consumer;

public class Main {
    private String directory;
    private String outputImageExtension;
    private String outputMovieExtension;

    public static void makeGoodOutput(File original, File folderOutput) {
        try {
            Path source = FileSystems.getDefault().getPath(original.getAbsolutePath());
            Path newDir = FileSystems.getDefault().getPath(folderOutput.getAbsolutePath());
            Files.copy(source, newDir.resolve(source.getFileName()));
            //  out.println("<a href=\""+original.getName()+"\">Original</a>\n");
            // out.println("<a href=\""+folderOutput.getPath()+"\">computed result image folder</a>\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void work(File dir, BufferedImage imageToWrite, String outputFilename) throws IOException {
        File file = new File(dir.getAbsolutePath() + "/" + outputFilename);
        if (file.mkdirs())
            System.out.println(dir.getAbsolutePath() + " created");
        System.out.print("\n(width, height) = " + imageToWrite.getWidth() +
                ", " + imageToWrite.getHeight() + ")");

        if (!ImageIO.write(imageToWrite, "png", file)) {
            System.out.println("Error inappropriate writer or not found " + "png");
            System.exit(-2);
        } else {
            System.out.println("Done writing : " + outputFilename);

        }
    }

    public static void main(String[] args) {
        new Main().exec();
    }

    public void exec() {

        Arrays.stream(ImageIO.getWriterFormatNames()).forEach(s1 ->
                System.out.println("Format name : \"" + s1 + "\""));
        File directory = new File("outputFiles/_" + System.nanoTime() + "__" +

                Time.from(Instant.now()).toString().replace(' ', '_').replace('|', '_')
                        .replace('\\', '_').replace('/', '_').replace(':', '_')
                + "/");
        int img = 0;
        for (String s : Objects.requireNonNull(new File("resources").list())) {
            img ++;
            String s0 = s.substring(s.lastIndexOf(".") + 1);
            String filename = s.substring(0, s.lastIndexOf("."));
            String ext = s0.equals("jpg") || s0.equals("jpeg") ? "jpg" : s0;
            if (Arrays.asList(ImageIO.getWriterFormatNames()).contains(ext)) {
                try {

                    if(directory.mkdirs())
                        System.out.println("Directory created" + directory.getAbsolutePath());
                    System.out.println("format name image " + ext + " found");

                    BufferedImage image = ImageIO.read(new File("resources/" + s));

                    GradientFilter gradientMask = new GradientFilter(image.getWidth(), image.getHeight());
                    M3 filter = gradientMask.filter(new M3(new PixM(image), 2, 2));
                    PixM[][] imagesMatrix = filter.normalize(0, 1);
                    Linear linear = new Linear(imagesMatrix[1][0], imagesMatrix[0][0],
                            new PixM(image.getWidth(), image.getHeight()));
                    linear.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
                    PixM smoothedGrad = linear.getImages()[2]; //.applyFilter(new GaussFilterPixM(4, sigma));

                    // Smooth gradient x, y

                    for (double sigma = 0.8; sigma < 4.0; sigma += 0.2) {

                        PixM pixM = smoothedGrad.applyFilter(new GaussFilterPixM(4, sigma));


                        for (int size = 1; size < 16; size*=2) {
                            //
                            M3 smoothedGradM3 = new M3(pixM.subSampling(size), 1, 1);


                            // Search local maximum
                            LocalExtrema localExtrema = new LocalExtrema(smoothedGradM3.columns, smoothedGradM3.lines, 3, 2);
                            PixM[][] filter2 = localExtrema.filter(smoothedGradM3).normalize(0.0, 1.0);
                            PixM filter1 = filter2[0][0];
                            BufferedImage image1 = filter1.getImage();
                            work(directory, image, s+"/original.png");
                            work(directory, imagesMatrix[0][0].getImage(), s+"/1/gradient.png");
                            work(directory, smoothedGradM3.getImagesMatrix()[0][0].getImage(),  s+"/2/smoothed_grad-" + sigma + "/size"+size+".png");
                            work(directory, image1,  s+"/3/extremasearch" + sigma +"/size"+size+"__feature_detector_ready_image.png");
                        }
                    }
                    String outputGrad = "Gradient" + s + ".png";
                    System.out.println("Thread terminated without exception");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getOutputImageExtension() {
        return outputImageExtension;
    }

    public void setOutputImageExtension(String outputImageExtension) {
        this.outputImageExtension = outputImageExtension;
    }

    public String getOutputMovieExtension() {
        return outputMovieExtension;
    }

    public void setOutputMovieExtension(String outputMovieExtension) {
        this.outputMovieExtension = outputMovieExtension;
    }
}
