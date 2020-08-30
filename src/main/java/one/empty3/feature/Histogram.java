package one.empty3.feature;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/*** 
 * radial density of region (x, y, r)
 * by mean or mean square or somewhat else. 
 */
public class Histogram {
    private final double diffLevel;
    private double min;
    private List<Circle> circles
            = new ArrayList<>();
    private PixM m = null;

    public class Circle {
        public double x, y, r;
        public double i;

        public Circle(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }
    }

    private final int[][][] levels;

    /***
     *
     * @param image image to histogram
     * @param levels 0..n exemple = level[i][x][y] = number of points of intensity ((i/n), (i+1)/n)
     */
    public Histogram(PixM image, int levels, double min) {
        this.diffLevel = 1.0 / levels;
        this.min = min;
        this.levels = new int[levels][image.columns][image.lines];
        this.m = image;
    }

    public void makeHistogram(double r) {

    }

    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }

    public Circle getLevel(Circle c) {
        // I mean. Parcourir le cercle 
        // mesurer I / numPoints
        // for(int i=Math.sqrt()
        //  return c;
        int count = 0;
        double intensity = 0.0;
        for (double i = c.x-c.r; i <= c.x+c.r; i++) {
            for (double j = c.y-c.r; j <= c.y+c.r; j++) {
                if (Math.sqrt((i - c.x) * (i - c.x) + (j - c.y) * (j - c.y)) <= c.r
                && i>=0 && j>=0 && i<m.columns && j<m.lines) {
                    intensity += m.get((int) i, (int) j);
                    count++;
                }
            }
        }


        c.i = intensity / count;


        return c;
    }

    public List<Circle> getPointsOfInterest() {

        circles = new ArrayList<>();

        // gradient radial ???
        // X-x2 > li-li+-1
        // i(x2, y2, r2) > i(x, y, r) + leveldiffi|||
        // stop
        for (int i = 0; i < m.columns; i++)
            for (int j = 0; j < m.lines; j++) {
                int r = 2;
                double diffI = 0;
                Circle c1 = null, c2;
                while (r < m.columns && diffI < diffLevel) {
                    c1 = new Circle(i, j, r);
                    c2 = new Circle(i, j, r + 1);
                    diffI = Math.abs(getLevel(c1).i - getLevel(c2).i);
                    if(getLevel(c1).i<min) break;
                    c1=c2;
                    r++;
                }
                circles.add(c1);
            }
        return circles;
    }

    public static void testCircleSelect(File file, File directory, int levels, double min) {
        for (int i = 0; i < levels; i++) {
            try {
                BufferedImage img = ImageIO.read(file);
                Histogram histogram = new Histogram(new PixM(img), levels, min);
                int finalI = i;
                histogram.getPointsOfInterest().stream().forEach(new Consumer<Circle>() {
                    @Override
                    public void accept(Circle circle) {
                        if(circle.i >= min /*<histogram.diffLevel* finalI*/) {
                            Graphics graphics = img.getGraphics();
                            graphics.setColor(Color.WHITE);
                            graphics.drawOval((int) (circle.x - circle.r), (int) (circle.y - circle.r), (int) (circle.r * 2), (int) (circle.r * 2));

                        }
                    }
                });
                File fileToWrite = new File(directory.getAbsolutePath()
                        + "level"+ finalI + ".jpg");
                fileToWrite.mkdirs();
                ImageIO.write(img, "JPEG", fileToWrite);

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int levels = 10;
        testCircleSelect(new File("resources/vg1.jpg"), new File("resources/res/"), levels, 0.3);
    }
}
