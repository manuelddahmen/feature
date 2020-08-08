package one.empty3.feature;

public class GaussFilterPixM extends M {
    public final double sigma;

    public double gauss(double x, double y, double epsilon) {
        return 1.0 / Math.PI / 2 / sigma / sigma * Math.exp(
                -((x-epsilon) * (x-epsilon) + (y-epsilon) * (y-epsilon))
                        / 2 / sigma / sigma);
    }

    /***
     * Gaussian filter Matrix
     * @param halfSquareSizeMinus1 n*n square distribution
     * @param sigma gauss parameter
     */
    public GaussFilterPixM(int halfSquareSizeMinus1, double sigma) {
        super(halfSquareSizeMinus1*2+1);
        this.sigma = sigma;
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
