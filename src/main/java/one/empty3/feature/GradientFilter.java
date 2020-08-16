package one.empty3.feature;

import java.awt.image.BufferedImage;

public class GradientFilter extends FilterMatPixM {

    private final double[][][] gNormalize;
    private double gMin = 10.0;
    private double gMax = -10.0;
    private double gMax1 = Double.MIN_VALUE;
    private double gMin1 = Double.MAX_VALUE;

    public GradientFilter(BufferedImage image) {
        super(image, 1, 2);
        gNormalize = new double[1][2][2];//x, y, min/max
    }

    public void element(M3 image, M3 res, int i, int j, int ii, int ij) {
        for (int c = 0; c < image.getCompCount(); c++) {
            setCompNo(c);
            res.setCompNo(c);
            image.setCompNo(c);
            if (ii == 0 && ij == 0) {
                image.setXY(i, j);
                res.setXY(i, j);
                res.set(i, j, 0, 0, (-image.get(ii - 1, ij) -
                        -image.get(ii, ij - 1)
                        + 4 * image.get(ii, ij)
                        + image.get(ii + 1, ij)
                        + image.get(ii, ij + 1)
                ) / 4.0);
                if (res.get(i, j, 0, 0) < gMin)
                    gMin = res.get(i, j, 0, 0);
                if (res.get(i, j, 0, 0) > gMax)
                    gMax = res.get(i, j, 0, 0);

            }
            if (ii == 0 && ij == 1) {
                image.setXY(i, j);
                res.setXY(i, j);
                res.set(i, j, 0, 1, Math.atan((-image.get(ii, ij - 1) -


                        +2 * image.get(ii, ij)
                        + image.get(ii, ij + 1)) / (
                        -image.get(ii + 1, ij)
                                + 2 * image.get(ii, ij)
                                + image.get(ii + 1, ij))) / Math.PI / 2);
                if (res.get(i, j, 0, 0) < gMin1)
                    gMin1 = res.get(i, j, 0, 0);
                if (res.get(i, j, 0, 0) > gMax1)
                    gMax1 = res.get(i, j, 0, 0);
            }
        }
    }


    /***
     * Norme linéaire
     * Autre exemple : histogramme de valeurs = échelle pondérée
     *
     * @param image
     */

    public void norm(M3 image) {
        for (int c = 0; c < getCompCount(); c++)
            for (int i = 0; i < image.columns; i++)
                for (int j = 0; j < image.lines; j++) {
                    for (int ii = 0; ii < image.columnsIn; ii++)
                        for (int ij = 0; ij < image.linesIn; ij++) {
                            double v = image.get(i, j, ii, ij);
                            v = (v - gMin) / (gMax - gMin);
                            image.set(i, j, ii, ij, v);
                        }

                }

    }
}
