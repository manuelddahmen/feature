package one.empty3.feature;

import java.util.*;

/*** 
 * radial density of region (x, y, r)
 * by mean or mean square or somewhat else. 
 */
public class Histogram {

    public class Circle {
        public double x, y, r;
        public double i;
    } 

    private final int[][][] levels;

    /***
     *
     * @param image image to histogram
     * @param levels 0..n exemple = level[i][x][y] = number of points of intensity ((i/n), (i+1)/n)
     */
    public Histogram(PixM image, int levels) {
        this.levels = new int[levels][image.columns][image.lines];

    }
    public void makeHistogram(double r) {

    }
    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }
    public List<Circle> getLevel() {
       List<Circle> circles 
           = new ArrayList<>() ;


       // gradient radial ???
       // X-x2 > li-li+-1
       // i(x2, y2, r2) > i(x, y, r) + leveldiffi|||
       // stop
       return circles;
    } 
}
