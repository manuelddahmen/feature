package one.empty3.feature;

import java.awt.image.BufferedImage;

public class GradientFilter extends FilterMatPixM {

    private double[][][][] gNormalize;

    public GradientFilter(BufferedImage image) {
        super(image, 2, 2);
        initGNormalise();
    }

    public void element(M3 source, M3 res, int i, int j, int ii, int ij) {

        for (int c = 0; c < source.getCompCount(); c++) {
            source.setCompNo(c);
            if (ii == 0 && ij == 0) {
                res.set(i, j, 0, 0, -source.get(i - 1, j, 0, 0) + source.get(i, j, 0, 0));
                //+ image.get(i+ii + 1, j+ij)
                //+ image.get(i+ii, j+ij + 1
                if (res.get(i, j, 0, 0) < gNormalize[c][0][0][0])
                    gNormalize[c][0][0][0] = res.get(i, j, 0, 0);
                if (res.get(i, j, 0, 0) > gNormalize[c][0][0][1])
                    gNormalize[c][0][0][1] = res.get(i, j, 0, 0);

            }
            if (ii == 0 && ij == 1) {
                res.set(i, j, 0, 1, Math.atan( -source.get(i, j - 1, 0, 0) + source.get(i, j, 0, 0)) /
                                (-source.get(i - 1, j, 0, 0) + source.get(i, j, 0, 0)));
                if (res.get(i, j, 0, 1) < gNormalize[c][0][1][0])
                    gNormalize[c][0][1][0] = res.get(0, 1);
                if (res.get(i, j, 0, 1) > gNormalize[c][0][1][1])
                    gNormalize[c][0][1][1] = res.get(i, j, 0, 1);

            }
            if (ii == 1 && ij == 0) {
                res.set(i, j, 1, 0, -source.get(i, j - 1, 0, 0) + source.get(i, j, 0, 0)
                );
                if (res.get(i, j, 1, 0) < gNormalize[c][1][0][0])
                    gNormalize[c][1][0][0] = res.get(i, j, 1, 0);
                if (res.get(i, j, 1, 0) > gNormalize[c][1][0][1])
                    gNormalize[c][1][0][1] = res.get(i, j, 1, 0);

            }
            if (ii == 1 && ij == 1) {
                res.set(i, j, 0, 1, Math.atan(
                        (// Delta Y/Delta X
                                -source.get(i, j+1, 0, 0) + source.get(i, j, 0, 0)
                                //+ image.get(i+1, j + 1) + image.get(i, j+1 + 1)
                        ) /
                                (
                                        -source.get(i+1, j, 0, 0) + source.get(i, j, 0, 0)
                                )
                ));
                if (res.get(i, j, 1, 1) < gNormalize[c][1][1][0])
                    gNormalize[c][1][1][0] = res.get(i, j, 0, 1);
                if (res.get(i, j, 1, 1) > gNormalize[c][1][1][1])
                    gNormalize[c][1][1][1] = res.get(i, j, 1, 1);

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
                            v = (v - gNormalize[c][ii][ij][0]) /
                                    (gNormalize[c][ii][ij][1] - gNormalize[c][ii][ij][0]);
                            image.set(i, j, ii, ij, v);
                        }

                }
        }
    }

    public void initGNormalise() {
        gNormalize = new double[getCompCount()][columnsIn][linesIn][2];
        for (int c = 0; c < getCompCount(); c++) {
            for (int ii = 0; ii < columnsIn; ii++)
                for (int ij = 0; ij < linesIn; ij++) {

                    gNormalize[c][ii][ij][0] = Double.MAX_VALUE;
                    gNormalize[c][ii][ij][1] = -Double.MAX_VALUE;
                }

        }
    }


}
