package one.empty3.feature;

public class WindowPixM extends M {
    private final double sigma;

    public double gauss(double x, double y) {
        return 1.0 / Math.PI / 2 / sigma / sigma * Math.exp(-(x * x + y * y) / 2 / sigma / sigma);
    }

    /***
     * Gaussian filter Matrix
     * @param size n*n square distribution
     * @param sigma gauss parameter
     */
    public WindowPixM(int size, double sigma) {
        super(size);
        this.sigma = sigma;
  //      fill();
    }
/*
    private void fill() {
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < lines; j++) {
                set(i, j, gauss(1.0 * (lines - i) / 2.,
                        1.0 * (columns - i) / 2.,
                        sigma));
            }
    }
*/
}
