package one.empty3.feature;

import java.awt.image.BufferedImage;

public class GradientFilter extends FilterMatPixM {

    private final double[][][] gNormalize;
    private double gMin = Double.MAX_VALUE;
    private double gMax = Double.MIN_VALUE;
    private double gMax1 = Double.MIN_VALUE;
    private double gMin1 = Double.MAX_VALUE;

    public GradientFilter(BufferedImage image) {
        super(image, 1, 2);
        gNormalize = new double[columns][lines][2];//x, y, min/max
    }

    public void element(PixM image, M3 res, int i, int j, int ii, int ij) {
        for (int c = 0; c < image.getCompCount(); c++) {
            setCompNo(c);
            res.setCompNo(c);
            image.setCompNo(c);
            if (ii == 0 && ij == 0) {
                res.setXY(i, j);
                res.set(0, 0, (-image.get(ii - 1, ij) -
                        -image.get(ii, ij - 1)
                        + 4 * image.get(ii, ij)
                        + image.get(ii + 1, ij)
                        + image.get(ii, ij + 1)
                ) / 4.0);
                if (res.get(i, j, 0, 0) < gNormalize[0][0][0])
                    gNormalize[0][0][0] = res.get(i, j, 0, 0);
                if (res.get(i, j, 0, 0) >  gNormalize[0][0][1])
                    gNormalize[0][0][1] = res.get(i, j, 0, 0);

            }
            if (ii == 0 && ij == 1) {
                res.setXY(i, j);
                res.set(0, 1, Math.atan((-image.get(ii, 0) -


                        +2 * image.get(ii, ij)
                        + image.get(ii, ij + 1)) / (
                        -image.get(ii + 1, ij)
                                + 2 * image.get(ii, ij)
                                + image.get(ii + 1, ij))) / Math.PI / 2);
                if (res.get(i, j, 0, 0) < gNormalize[0][1][0])
                    gNormalize[0][1][0] = res.get(i, j, 0, 1);
                if (res.get(i, j, 0, 1) >  gNormalize[0][1][1])
                    gNormalize[0][1][1] = res.get(i, j, 0, 1);

            }
        }
    }


    /***
     * Norme linéaire
     * Autre exemple : histogramme de valeurs = échelle pondérée
     *
     * @param image M3 image array
     */

    public void norm(M3 image) {
        for (int c = 0; c < image.getCompCount(); c++) {
            image.setCompNo(c);
            for (int i = 0; i < image.columns; i++)
                for (int j = 0; j < image.lines; j++) {
                    for (int ii = 0; ii < image.columnsIn; ii++)
                        for (int ij = 0; ij < image.linesIn; ij++) {
                            double v = image.get(i, j, ii, ij);
                            v = (v - gNormalize[ii][ij][0]) /
                                    (gNormalize[ii][ij][1] - gNormalize[ii][ij][0]);
                            image.set(i, j, ii, ij, v);
                        }

                }
        }
        gMin = Double.MAX_VALUE;
        gMax = Double.MIN_VALUE;
        gMax1 = Double.MIN_VALUE;
        gMin1 = Double.MAX_VALUE;

    }
}
