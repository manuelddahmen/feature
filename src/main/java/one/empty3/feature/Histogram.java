package one.empty3.feature;



public class Histogram {
    private final int[][][] levels;

    /***
     *
     * @param image image to histogram
     * @param levels 0..n exemple = level[i][x][y] = number of points of intensity ((i/n), (i+1)/n)
     */
    public Histogram(PixM image, int levels) {
        this.levels = new int[levels][image.columns][image.lines];

    }
    public void makeHistogram() {

    }
    public double nPoints(int x, int y, int w, int h) {
        return 0.0;
    }

}
