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
        if(dir.mkdirs())
            System.out.println(dir.getAbsolutePath() + " created");

        System.out.print("\n(width, height) = " + imageToWrite.getWidth() +
                ", " + imageToWrite.getHeight() + ")");

        if (!ImageIO.write(imageToWrite, "png", new File(dir.getAbsolutePath() +
                outputFilename))) {
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
        for (String s : Objects.requireNonNull(new File("resources").list())) {
            String s0 = s.substring(s.lastIndexOf(".") + 1);
            String ext = s0.equals("jpg") || s0.equals("jpeg") ? "jpg" : s0;
            if (Arrays.asList(ImageIO.getWriterFormatNames()).contains(ext)) {
                try {

                    System.out.println("format name image " + ext + " found");

                    BufferedImage image = ImageIO.read(new File("resources/" + s));
                    PixM pixM = new PixM(image);
                    //BufferedImage origImg = pixM.getImage();

                    FilterPixM gaussFilterPixM = new GaussFilterPixM(5, 4.0);

                    GradientFilter gradientMask = new GradientFilter(image.getWidth(), image.getHeight());
                    BufferedImage pic = new M3(image, 1, 1).getImagesMatrix()[0][0].getImage();
                    BufferedImage picNorm = new M3(image, 1, 1).getImagesMatrix()[0][0].normalize(0.0, 1.0).getImage();
                    BufferedImage outputImage = MIMmops.applyMultipleFilters(
                            pixM, 4, gaussFilterPixM/*, new SobelDerivative(true),
                            new SobelDerivative(false)*/).getImage();
                    M3 filter;
                    PixM[][] imagesMatrix1 = new M3(image, 2, 2).getImagesMatrix();
                    M3 ls_22 = new M3(image, 2, 2).copy();
                    filter = gradientMask.filter(new M3(image, 2, 2));

                    gradientMask = new GradientFilter(image.getWidth(), image.getHeight());
                    M3 mean = gradientMask.mean(gradientMask.filter(new M3(image, 2, 2)), new M3(image, 2, 2));
                    System.out.println("points ok : " + gradientMask.incrOK);
                    System.out.println("ls_22 c,l" + ls_22.columnsIn + "," + ls_22.linesIn);

                    File directory = new File("outputFiles/res" + System.nanoTime() + "__" +

                            Time.from(Instant.now()).toString().replace(' ', '_').replace('|', '_')
                                    .replace('\\', '_').replace('/', '_').replace(':', '_')
                            + "/");
                    String output = "/Output" + s + ".png";
                    String outputGrad = "Gradient" + s + ".png";
                    String input = "/Input" + s + ".png";
                    PixM[][] imagesMatrix = filter.getImagesMatrix();
                    work(directory, ls_22.getImagesMatrix()[1][1].getImage(), "/" + ("__load.copy.save.M3_22_11.png"));
                    work(directory, pic, "/" + ("__load.save.M3.png"));
                    work(directory, picNorm, "/" + ("__load.save.M3_normalize.png"));
                    System.out.println("filter gradient size col : " + filter.columnsIn);
                    System.out.println("filter gradient size lin : " + filter.linesIn);

                    final int[] i = new int[]{0};

                    work(directory, image, input);
                    //M3 gradientFilter = image22.filter(new GradientFilter(origImg));

                    //PixM[][] imagesMatrix = gradientFilter.getImagesMatrix();
                    Linear linear = new Linear(imagesMatrix1[1][0], imagesMatrix[0][0],
                            new PixM(image));
                    linear.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
                    PixM normalize = linear.getImages()[2].normalize(0.0, 1.0);
                    BufferedImage image1 = normalize.getImage();


                    work(directory, image1, "/" + ("GRADIENT MATRIX OUTER DOT PRODUCT") + outputGrad);

                    M3 m3 = new M3(image1, 1, 1);
                    LocalExtreMüss localExtreMüss = new LocalExtreMüss(m3.columns, m3.lines, 3, 4);
                    M3 filter2 = localExtreMüss.filter(m3);
                    PixM filter1 = filter2.getImagesMatrix()[0][0];

                    FollowLines followLines = new FollowLines(filter2);
                    ArrayList<Line> lines = followLines.processPoints(0, 0);

                    BufferedImage imageDrawn = new BufferedImage(filter2.columns, filter2.lines, BufferedImage.TYPE_INT_ARGB);
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
                    work(directory, imageDrawn, "/"
                            + ("local_matGrad_extrema_DRAW_LINES") + outputGrad);

                    work(directory, filter1.getImage(), "/"
                            + ("local_matGrad_extrema") + outputGrad);

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
                    work(directory, outputImage, output);

                    makeGoodOutput(new File("resources/" + s), directory);
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
