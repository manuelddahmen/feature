package one.empty3.feature;

import com.jogamp.common.util.ArrayHashSet;
import one.empty3.library.Point2D;
import one.empty3.library.core.lighting.Colors;

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
        File directory = new File("outputFiles/res" + System.nanoTime() + "__" +

                Time.from(Instant.now()).toString().replace(' ', '_').replace('|', '_')
                        .replace('\\', '_').replace('/', '_').replace(':', '_')
                + "/");
        int img = 0;
        for (String s : Objects.requireNonNull(new File("resources").list())) {
            img ++;
            String s0 = s.substring(s.lastIndexOf(".") + 1);
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


                        for (int size = 1; size < 4; size++) {
                            //
                            M3 smoothedGradM3 = new M3(pixM.subSampling(size), 1, 1);


                            // Search local maximum
                            LocalExtrema localExtrema = new LocalExtrema(smoothedGradM3.columns, smoothedGradM3.lines, 0, 1);
                            PixM[][] filter2 = localExtrema.filter(smoothedGradM3).normalize(0.0, 1.0);
                            PixM filter1 = filter2[0][0];
                            BufferedImage image1 = filter1.getImage();
                            //work(directory, smoothedGrad.getImage(), "/" + ("1 before extrema search"));
                            work(directory, imagesMatrix[0][0].getImage(), img+"/1/gradient.png");
                            work(directory, smoothedGradM3.getImagesMatrix()[0][0].getImage(),  img+"2/smoothed_grad-" + sigma + "/size"+size+".png");
                            work(directory, image1,  img+"/3/extremasearch" + sigma +"/size"+size+"__feature_detector_ready_image.png");
                        }
                    }

                    //BufferedImage pic = new M3(image, 1, 1).getImagesMatrix()[0][0].getImage();
                    //BufferedImage picNorm = new M3(image, 1, 1).getImagesMatrix()[0][0].normalize(0.0, 1.0).getImage();
                    //BufferedImage outputImage = MIMmops.applyMultipleFilters(
                    //        pixM, 4, gaussFilterPixM/*, new SobelDerivative(true),
                    //        new SobelDerivative(false)*/).getImage();
                    //M3 filter;
                    //PixM[][] imagesMatrix1 = new M3(image, 2, 2).getImagesMatrix();
                    //M3 ls_22 = new M3(image, 2, 2).copy();
                    //filter = gradientMask.filter(new M3(image, 2, 2));

                    //M3 mean = gradientMask.mean(gradientMask.filter(new M3(image, 2, 2)), new M3(image, 2, 2));
                    //System.out.println("points ok : " + gradientMask.incrOK);
                    //System.out.println("ls_22 c,l" + ls_22.columnsIn + "," + ls_22.linesIn);

                    //String output = "/Output" + s + ".png";
                    String outputGrad = "Gradient" + s + ".png";
                    String input = "/Input" + s + ".png";


                    //work(directory, ls_22.getImagesMatrix()[1][1].getImage(), "/" + ("__load.copy.save.M3_22_11.png"));
                    //work(directory, pic, "/" + ("__load.save.M3.png"));
                    //work(directory, picNorm, "/" + ("__load.save.M3_normalize.png"));


                    System.out.println("filter gradient size col : " + filter.columnsIn);
                    System.out.println("filter gradient size lin : " + filter.linesIn);

                    //nal int[] i = new int[]{0};

                    work(directory, image, input);
                    //M3 gradientFilter = image22.filter(new GradientFilter(origImg));

                    //PixM[][] imagesMatrix = gradientFilter.getImagesMatrix();

/*
                    work(directory, filter1.getImage(), "/"
                            + ("local_matGrad_extrema_SMOOTHED") + outputGrad);
*/
                    //M3 filteredSmoothed = m3.filter(new GaussFilterPixM(2, 1.5), 0, 0);
                    //PixM filteredLines = new LocalExtreMüss(m3.columns, m3.lines, 3, 4).filter(filteredSmoothed)
                    //        .getImagesMatrix()[1][1];
                    //M3 localExtreMüss = new LocalExtreMüss(m3.columns, m3.lines, 3, 2)
                    //        .filter(m3);
                    //FollowLines followLines = new FollowLines(m3);
                    //ArrayList<Line> lines = followLines.processPoints(0, 0);
/*
                    BufferedImage imageDrawn = new BufferedImage(m3.columns, m3.lines,
                            BufferedImage.TYPE_INT_ARGB);
                    Graphics graphics = imageDrawn.getGraphics();
                    for (Line line : lines) {
                        Color c = Colors.random();
                        for (int a = 0; a < line.xys.size(); a++)
                        {
                            graphics.setColor(c);
                            P2P2 p2P2 = line.xys.get(a);
                            graphics.drawLine((int) p2P2.getP0().getX(), (int) p2P2.getP0().getY(),
                                    (int) p2P2.getP1().getX(), (int) p2P2.getP1().getY());

                        }
                    }
*/
                    //work(directory, imageDrawn, "/"
                    //        + ("local_matGrad_extrema_DRAW_LINES") + outputGrad);

                    //work(directory, filter1.getImage(), "/"
                    //        + ("local_matGrad_extrema") + outputGrad);
/*
                    Arrays.stream(imagesMatrix1).forEach(pixMS -> Arrays.stream(pixMS).forEach(pixM1 -> {
                        try {
                            work(directory, pixM1.getImage(), "/__load.22.matrix22.imagesMatrix" + (i[0] % 4) + outputGrad);//
                            i[0]++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        i[0]++;
                    }));
                    Arrays.stream(imagesMatrix).sequential().forEach(bufferedImages -> Arrays.stream(bufferedImages).forEach(bufferedImage -> {
                        try {
                            work(directory, bufferedImage.normalize(0.0, 1.0).getImage(), "/" + (i[0] % 4) + outputGrad);//
                            i[0]++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }));
                    Arrays.stream(mean.getImagesMatrix()).forEach(pixMS -> Arrays.stream(pixMS).forEach(pixM1 -> {
                        try {
                            work(directory, pixM1.normalize(0.0, 1.0).getImage(), "/___mean_for_matGrad" + (i[0] % 4) + outputGrad);//
                            i[0]++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }));
*/
                    //work(directory, outputImage, output);

                    //makeGoodOutput(new File("resources/" + s), directory);
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
