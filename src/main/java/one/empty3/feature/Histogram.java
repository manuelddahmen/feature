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
        this.diffLevel = 1.0/levels;
        this.levels = new int[levels][image.columns][image.lines];

    }
    public void makeHistogram(double r) {

    }
    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }
    public Circle getLevel(Circle c) {
        // I mean. 
        return c;
    } 
    public List<Circle> getPointsOfInterest() {
       List<Circle> circles 
           = new ArrayList<>() ;


       // gradient radial ???
       // X-x2 > li-li+-1
       // i(x2, y2, r2) > i(x, y, r) + leveldiffi|||
       // stop
       for(int i=0; i<m.columns; i++) 
           for(int j=0; j<m.lines; j++) {
               int r = 1;
               double diffI = 0;
               while(r<m.columns & & diffI<diffLevel) {
                   Circle c1 = new Circle(i, j, r);
                   Circle c2 = new Circle (i, j, r+1);

                   double diffI = Math.abs(getLevel(c1).i-getLevel(c2).i);
                   r++;
              } 
              circles.add(c1);
           }
       return circles;
    } 
}
