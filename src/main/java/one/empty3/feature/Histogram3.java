package one.empty3.feature;

import one.empty3.io.ProcessFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*** 
 * radial density of region (x, y, r)
 *
 */
public class Histogram3 extends ProcessFile {
    private int numLevels = 3;
    private double[] max;
    private double[] min;
    private double minimumI = 0.1;
    public double rFact = 2.0;

    public class Circle {
        public double x, y, r;
        public double i;

        public Circle(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
            i = 0.0;
        }

        @Override
        public String toString() {
            return "Circle{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    ", i=" + i +
                    '}';
        }
    }

    /***
     *
     *
     */

    public Histogram3(int numLevels) {
        rFact = 2.0;
        numLevels = 3;
        minimumI = 0.1;
        min = new double[numLevels];
        max = new double[numLevels];

        for (int i = 0; i < numLevels; i++) {
            min[i] = 1.0 * i / numLevels;
            max[i] = 1.0 * (i + 1) / numLevels;
        }
        minimumI = 0.2;
    }

    public Histogram3() {
        this(5);

    }

    public void makeHistogram(double r) {

    }

    public Circle getLevel(PixM m, Circle c) {
        // I mean. Parcourir le cercle
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        int count = 0;
        double intensity = 0.0;
        for (double i = -c.r; i <= c.r; i++) {
            for (double j = -c.r; j <= c.r; j++) {
                if (Math.sqrt((i) * (i) + (j) * (j)) <= c.r * c.r
                        && c.x - i >= 0 && c.y - j >= 0 && c.x + i < m.columns && c.y + j < m.lines) {
                    intensity += m.getIntensity((int) (c.x + i), (int) (c.y + j));
                    count++;
                }
            }
        }

        if (count > 0) {
            c.i = intensity / count;
        } else {
            c.i = 0.0;
            c.r = 0;
        }


        return c;
    }

    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }


    public List<Circle> getPointsOfInterest(PixM m, double rMin0, double iMin) {
        ArrayList<Circle> circles;
        circles = new ArrayList<>();

        // Classer les points par intensité et rayon

        // for(double intensity=1.0; intensity>=0.0; intensity-=0.1) {
        for (int i = 0; i < m.columns; i++) {
            for (int j = 0; j < m.lines; j++) {
                double rMin = rMin0;
                Circle level = getLevel(m, new Circle(i, j, rMin));
                // level.i = intensity;
                //int index = Math.max(((int) (level.i * numLevels)), 0);
                //index = Math.min(numLevels-1, index);
                double iOrigin = level.i;
                int
                        index0 = (int) (level.i * (numLevels - 1));
                //if(index0<0) index0 = 0;
                //if(index0<=min.length) index0 = min.length-1;
                while (level.i >= iMin && level.i >= iOrigin - 1.0 / numLevels && level.i <= iOrigin + 1.0 / numLevels && level.r < Math.max(m.columns, m.lines)) {

                    level.r *= rFact;
                    getLevel(m, level);
                }
                level.r /= rFact;
                if (level.r >= rMin0) {
                    circles.add(level);
                }
            }

            //   }
        }


        return circles;
    }

    public List<List<Circle>> group(List<Circle> circles) {
        List<List<Circle>> out = new ArrayList<>();


        return out;

    }

    public boolean process(File in, File out) {
        try {
            PixM m = new PixM(ImageIO.read(in));
            BufferedImage image = m.getImage();


            double radiusIncr = 4;


            BufferedImage img2 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            List<Circle> pointsOfInterest;
            pointsOfInterest = getPointsOfInterest(m, 4.0, 0.25);
            // grands;cercles = grandes iles les separer
            // verifier les distances et constantes i
            // petits cercles successifs entoures 
            // de grands ou plus grands cercles = 
            // coins, corners et possibles features.

            System.out.println("getPointsOfInterest ");


            double [] i_ir = new double[] {0, 0};
            pointsOfInterest.forEach(c1 -> {
                if(i_ir[0]<c1.i)
                    i_ir[0] = c1.i;
                if(i_ir[1]<c1.i/c1.r)
                    i_ir[1] = c1.i/c1.r;

            });
            pointsOfInterest.sort((o1, o2) -> {
                double v = (o2.i/o2.r - o1.i/o1.r)/i_ir[1];
                if (v < 0)
                    return -1;
                if (v > 0)
                    return 1;
                return (int) Math.signum((o2.i - o1.i) / Math.abs(o2.r - o1.r));
            });

            System.out.println("draw ");

            pointsOfInterest.forEach(circle -> {
                if (circle.i > 0.1 && circle.r > 1.0 * image.getWidth() / maxRes) {
                    try {
                        Graphics graphics = img2.getGraphics();
                        graphics.setColor(new Color((float) (circle.i / circle.r / i_ir[1]),
                                (float) (circle.i / i_ir[1]),
                                (float) (circle.i / i_ir[1])));
                        //graphics.fillOval((int) (circle.x - circle.r), (int) (circle.y - circle.r), (int) (2 * circle.r), (int) (2 * circle.r));
                        graphics.fillRect((int) (circle.x), (int) (circle.y), 1, 1);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });
            // grouper les points par similarités et distances
              /*  group(pointsOfInterest);
                File fileToWrite = new File(directory.getAbsolutePath()
                        + "level" + ".jpg");
                File fileToWrite2 = new File(directory.getAbsolutePath()
                        + "level"+ "_NEW.jpg");
                File fileToWrite3 = new File(directory.getAbsolutePath()
                        + "level"+ "_NEW_RGB.jpg");
                //fileToWrite.mkdirs();*/
            ImageIO.write(new PixM(img2).normalize(0.,1.).getImage(), "JPEG", out);
                /*
                ImageIO.write(img, "JPEG", fileToWrite);
                ImageIO.write(img, "JPEG", fileToWrite2);
                ImageIO.write(img, "JPEG", fileToWrite3);
*/


        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
