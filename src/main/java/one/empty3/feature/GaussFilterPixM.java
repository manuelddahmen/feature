package one.empty3.feature;

public class GaussFilterPixM extends FilterPixM {
    public double sigma;

    public GaussFilterPixM(int squareSize) {
        super(squareSize, squareSize);
    }

    @Override
    public double filter(double x, double y) {
        return Math.exp(
                -(x * x + y * x)
                        / 2 / sigma / sigma);
    }

    /***
     * Gaussian filter Matrix
     * @param halfSquareSizeMinus1 n*n square distribution
     * @param sigma gauss parameter
     */
    public GaussFilterPixM(int halfSquareSizeMinus1, double sigma) {
        this(halfSquareSizeMinus1 * 2 + 1);
        this.sigma = sigma;
        for(int comp = 0 ; comp<getCompCount(); comp++) {
            setCompNo(comp);
            fill();
        }
    }

    private void fill() {
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < lines; j++) {
                set(i, j, filter(i-columns/2 ,
                        j-lines/2)
                );
            }
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }
}
