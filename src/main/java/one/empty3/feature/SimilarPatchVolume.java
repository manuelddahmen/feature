package one.empty3.feature;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public class SimilarPatchVolume {
    private File directory;
    private BufferedImage image2;
    private BufferedImage image1;

    /*
    public static void makeGoodOutput(File original, File folderOutput) {
        try {
            Path source = FileSystems.getDefault().getPath(original.getAbsolutePath());
            Path newDir = FileSystems.getDefault().getPath(folderOutput.getAbsolutePath());
            Files.copy(source, newDir.resolve(source.getFileName()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
*/
    public static void work(File dir, BufferedImage imageToWrite, String outputFilename) throws IOException {
        File dir1 = new File(dir.getAbsolutePath() + "/" + outputFilename.substring(0,
                outputFilename.lastIndexOf("/")));
        File file = new File(dir.getAbsolutePath() + "/" + outputFilename);
        if (dir1.mkdirs())
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
        new SimilarPatchVolume().exec();
    }

    public BufferedImage getImageFromDir(String filename1) {
        String s0 = filename1.substring(filename1.lastIndexOf(".") + 1);
        //String filename = s.substring(0, s.lastIndexOf("."));
        String ext = s0.equals("jpg") || s0.equals("jpeg") ? "jpg" : s0;
        if (Arrays.asList(ImageIO.getWriterFormatNames()).contains(ext)) {
            try {

                if (directory.mkdirs())
                    System.out.println("Directory created" + directory.getAbsolutePath());
                System.out.println("format name image1 " + ext + " found");

                image1 = ImageIO.read(new File("resources/" + filename1));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image1;
    }

    public void exec() {

        Arrays.stream(ImageIO.getWriterFormatNames()).forEach(s1 ->
                System.out.println("Format name : \"" + s1 + "\""));
        directory = new File("outputFiles/_" + System.nanoTime() + "__" +

                Time.from(Instant.now()).toString().replace(' ', '_').replace('|', '_')
                        .replace('\\', '_').replace('/', '_').replace(':', '_')
                + "/");
        //int img = 0;
        for (String filename1 : Objects.requireNonNull(new File("resources").list())) {
            for (String filename2 : Objects.requireNonNull(new File("resources").list())) {
                try {

                    //img++;
                    String s = filename1;
                    image1 = getImageFromDir(filename1);
                    image2 = getImageFromDir(filename2);
                    if (image1 != null && image2 != null) {
                        GradientFilter gradientMask = new GradientFilter(image1.getWidth(), image1.getHeight());
                        PixM pixMOriginal = new PixM(image1);
                        M3 imgFprGrad = new M3(image1, 500, 500, 2, 2);
                        M3 filter = gradientMask.filter(imgFprGrad);
                        PixM[][] imagesMatrix = filter.getImagesMatrix();//.normalize(0, 1);


//                    image1 = null;

                        // Zero. +++Zero orientation variation.
                        Linear linear = new Linear(imagesMatrix[1][0], imagesMatrix[0][0],
                                new PixM(pixMOriginal.columns, pixMOriginal.lines));
                        linear.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
                        PixM smoothedGrad = linear.getImages()[2]; //.applyFilter(new GaussFilterPixM(4, sigma));
                        int itereAngleGrad = 12;
                        M3 filter3 = new AfterGradientBeforeExtemum(itereAngleGrad).filter(new M3(smoothedGrad, 1, 1));

                        try {
                            work(directory, pixMOriginal.getImage(), s + "/original.png");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        for (double angle = 0.8;
                             angle < 2 * Math.PI; angle += 2 * Math.PI / itereAngleGrad) {
                            stream(filter3, angle, s);
                            System.gc();
                        }

                        for (double sigma = 0.8; sigma < 2.0; sigma += 0.2) {
                            PixM pixM = smoothedGrad.applyFilter(new GaussFilterPixM(4, sigma));


                            for (int size = 1; size < 16; size *= 2) {
                                //
                                M3 smoothedGradM3 = new M3(pixM.subSampling(size), 1, 1);
                                // Search local maximum
                                LocalExtrema localExtrema = new LocalExtrema(smoothedGradM3.columns, smoothedGradM3.lines, 3, 2);
                                PixM[][] filter2 = localExtrema.filter(smoothedGradM3).normalize(0.0, 1.0);
                                PixM filter1 = filter2[0][0];
                                BufferedImage image1 = filter1.getImage();
                                System.out.println("Original read image1");
                                work(directory, imagesMatrix[0][0].getImage(), s + "/1/sigma" + sigma + "/size" + size + "gradient.png");
                                System.out.println("oriented grad extremum search (max==1.0) ");
                                work(directory, filter1.getImage(), s + "/2/smoothed_grad-" + sigma + "/size" + size + ".png");
                                System.out.println("oriented grad extremum search (max==1.0) ");
                                work(directory, image1, s + "/3/extremum_search" + sigma + "/size" + size + ".png");

                                System.gc();
                            }
                        }

                        System.out.println("Thread terminated without exception");

                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        }
    }

    private void stream(M3 smoothedGradM3, double angle, String s) {
        //int[] i = {0};
        Arrays.stream(smoothedGradM3.getImagesMatrix()).forEach(pixMS -> Arrays.stream(pixMS).forEach(pixM1 -> {
                    LocalExtrema localExtrema1 = new LocalExtrema(smoothedGradM3.columns, smoothedGradM3.lines, 3, 0);
                    M3 extremaOrientedGrad = localExtrema1.filter(new M3(pixM1, 1, 1));
                    try {
                        System.out.println("Gradient (gx,gy).(nx,ny)");
                        work(directory, pixM1.getImage(), s + "/4/OrientedGradExtremum_1_" + angle + ".png");
                        System.gc();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("oriented grad extremum search (max==1.0) ");
                    Arrays.stream(extremaOrientedGrad.getImagesMatrix()).forEach(pixMS1 -> Arrays.stream(pixMS1).forEach(pixM -> {
                        try {
                            work(directory, pixM.getImage(), s + "/4/OrientedGradExtremum_2_" +
                                    +angle + ".png");
                            //i[0]++;
                            System.gc();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }));

                })
        );
    }

    public double distance(PixM image1, PixM image2, int x1, int y1, int s1, int x2, int y2, int s2) {
        // Prendre 2 patches 2 à 2.
        // Les faire tourner,
        // Prendre x tailles dans 1, 2 à partir de 3x3
        // 1) Brute "force".


        return 0.0;

    }

}
