package one.empty3.feature;


import com.jhlabs.image.RescaleFilter;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class SimilarPatchVolume {
    private final File directory = new File("outputFiles/_" + "__" +

            Time.from(Instant.now()).toString().replace(' ', '_').replace('|', '_')
                    .replace('\\', '_').replace('/', '_').replace(':', '_')
            + "/");
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
    public static File work(File dir, BufferedImage imageToWrite, String outputFilename) throws IOException {
        File dir1 = new File(dir.getAbsolutePath() + "/" + outputFilename.substring(0,
                outputFilename.lastIndexOf("/")));
        File file = new File(dir.getAbsolutePath() + "/" + outputFilename);

        if(file.exists()) {
            System.out.println("File exists, quit"+file.getAbsolutePath());
            System.exit(-1);
        }

        if (dir1.mkdirs())
            System.out.println(dir.getAbsolutePath() + " created");
        System.out.print("\n(width, height) = " + imageToWrite.getWidth() +
                ", " + imageToWrite.getHeight() + ")");
        Iterator imageWriters = ImageIO.getImageWritersByFormatName("JPG");
        ImageWriter imageWriter = (ImageWriter) imageWriters.next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(file);
        imageWriter.setOutput(ios);
        imageWriter.write(imageToWrite);
        /*if (!ImageIO.write(imageToWrite, "image/jpg", file)) {
            System.out.println("Error inappropriate writer or not found " + "jpg");
            System.exit(-2);
        }*/
        return file;
    }

    public static void main(String[] args) {
        new SimilarPatchVolume().exec();
    }

    public BufferedImage getImageFromDir(String filename1) {
        String s0 = filename1.substring(filename1.lastIndexOf(".") + 1);
        //String filename = s.substring(0, s.lastIndexOf("."));
        Arrays.stream(ImageIO.getReaderMIMETypes()).anyMatch(s -> s == s0);
        if (Arrays.asList(ImageIO.getWriterFormatNames()).contains(s0)) {
            try {
                if (directory.mkdirs())
                    System.out.println("Directory created" + directory.getAbsolutePath());
                System.out.println("format name image1 " + s0 + " found");

                image1 = hideAlpha(new File("resources/" + filename1));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Format non found"+s0);
        }
        return image1;
    }

    public void exec() {


        //int img = 0;
        for (String filename1 : Objects.requireNonNull(new File("resources").list())) {
            //for (String filename2 : Objects.requireNonNull(new File("resources").list())) {
                try {

                    //img++;
                    String s = filename1;
                    image1 = getImageFromDir(filename1);
                    if(image1==null)
                        continue;

//                    image2 = getImageFromDir(filename2);
                    if (image1 != null /*&& image2 != null*/) {
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
                            work(directory, pixMOriginal.getImage(), s + "/original.jpg");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        for (double sigma = 0.8; sigma < 2.0; sigma += 0.2) {
                            PixM pixM = smoothedGrad.applyFilter(new GaussFilterPixM(4, sigma));


                            for (int size = 1; size <= 16; size *= 2) {
                                //
                                M3 smoothedGradM3 = new M3(pixM.subSampling(size), 1, 1);
                                // Search local maximum
                                LocalExtrema localExtrema = new LocalExtrema(smoothedGradM3.columns, smoothedGradM3.lines, 3, 0);
                                M3 filter2 = localExtrema.filter(smoothedGradM3);
                                PixM filter1 = filter2.getImagesMatrix()[0][0];
                                BufferedImage image1 = filter1.normalize(0.0, 1.0).getImage();
                                System.out.println("Original read image1");
                                work(directory, imagesMatrix[0][0].getImage(), s + "/1/sigma" + sigma + "/size" + size + "gradient.jpg");
                                System.out.println("oriented grad extremum search (max==1.0) ");
                                work(directory, filter1.normalize(0.0, 1.0).getImage(), s + "/2/smoothed_grad-" + sigma + "/size" + size + ".jpg");
                                System.out.println("oriented grad extremum search (max==1.0) ");
                                //work(directory, image1, s + "/3/extremum_search" + sigma + "/size" + size + ".jpg");

                                for (double angle = 0.8;
                                     angle < 2 * Math.PI; angle += 2 * Math.PI / itereAngleGrad) {
                                    stream(smoothedGradM3,sigma, angle, size, s);
                                    System.gc();
                            }
                        }

                        }

                        System.out.println("Thread terminated without exception");

                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            //}
        }
    }

    private void stream(M3 smoothedGradM3, double angle, double sigma, int size, String s) {
        //int[] i = {0};
        Arrays.stream(smoothedGradM3.getImagesMatrix()).forEach(pixMS -> Arrays.stream(pixMS).forEach(pixM1 -> {
                    LocalExtrema localExtrema1 = new LocalExtrema(smoothedGradM3.columns, smoothedGradM3.lines, 3, 0);
                    M3 extremaOrientedGrad = localExtrema1.filter(new M3(pixM1, 1, 1));
                    try {
                        System.out.println("Gradient (gx,gy).(nx,ny)");
                        work(directory, pixM1.normalize(0,1).getImage(), s + "/4/OrientedGradExtremum_1_sigma"+sigma+"angle" + angle + "size"+size+".jpg");
                        System.gc();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("oriented grad extremum search (max==1.0) ");
                    Arrays.stream(extremaOrientedGrad.getImagesMatrix()).forEach(pixMS1 -> Arrays.stream(pixMS1).forEach(pixM -> {
                        try {
                            String sub = s + "/4/OrientedGradExtremum_2_sigma"+sigma+"angle" + angle + "size"+size+".jpg";
                            File image = work(directory, pixM.getImage(), sub);

                            for(double min=0.0; min<1.0; min+=0.1)
                            Histogram.testCircleSelect(image,
                                    new File(directory.getAbsolutePath()+"/"+s+"/5/histogram_sigma"+sigma+"angle" + angle + "size"+size+"_"+min+".jpg"),
                                    10, min, pixM.columns/10.0);
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

    public static BufferedImage hideAlpha(File input) throws IOException {
        // Read input
        BufferedImage inputImage = ImageIO.read(input);

        // Make any transparent parts white
        if (inputImage.getTransparency() == Transparency.TRANSLUCENT) {
            // NOTE: For BITMASK images, the color model is likely IndexColorModel,
            // and this model will contain the "real" color of the transparent parts
            // which is likely a better fit than unconditionally setting it to white.

            // Fill background  with white
            Graphics2D graphics = inputImage.createGraphics();
            try {
                graphics.setComposite(AlphaComposite.DstOver); // Set composite rules to paint "behind"
                graphics.setPaint(Color.WHITE);
                graphics.fillRect(0, 0, inputImage.getWidth(), inputImage.getHeight());
            }
            finally {
                graphics.dispose();
            }
        }

        // Resample to fixed size
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        //BufferedImageOp resampler = new RescaleFilter(1f);

        // Using explicit destination, resizedImg will be of TYPE_INT_RGB
        BufferedImage resizedImg = inputImage;//resampler.filter(inputImage, new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));

        return resizedImg;

    }

    public File writeImage(BufferedImage image, File imageFile) {
        try {
            ImageIO.write(image, imageFile.getAbsolutePath().substring(imageFile.getName().lastIndexOf(".")+1),
                    imageFile);
            return imageFile;
        } catch (IOException exception) {

            exception.printStackTrace();

            return null;
        }
    }
}
