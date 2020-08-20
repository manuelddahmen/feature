package one.empty3.feature;

import java.awt.image.BufferedImage;

public class GradientFilter extends FilterMatPixM {

    private double[][][] gNormalize;

    public GradientFilter(BufferedImage image) {
        super(image, 1, 2);
        gNormalize = new double[][][]{{
                        {Double.MAX_VALUE, -Double.MAX_VALUE},
                {Double.MAX_VALUE, -Double.MAX_VALUE}
        }
        };//x, y, min/max
    }

    public void element(PixM image, M3 res, int i, int j, int ii, int ij) {
        for (int c = 0; c < image.getCompCount(); c++) {
            setCompNo(c);
            res.setCompNo(c);
            image.setCompNo(c);
            if (ii == 0 && ij == 0) {
                res.set(0, 0, (
                        -image.get(i+ii - 1, j+ij)
                        -image.get(i+ii, j+ij - 1)
                                + 2 * image.get(i+ii, j+ij)
                        //+ image.get(i+ii + 1, j+ij)
                        //+ image.get(i+ii, j+ij + 1)
                ));
                if (res.get(0, 0) < gNormalize[0][0][0])
                    gNormalize[0][0][0] = res.get(0, 0);
                if (res.get(0, 0) >  gNormalize[0][0][1])
                    gNormalize[0][0][1] = res.get(0, 0);

            }
            if (ii == 0 && ij == 1) {
                res.set(0, 1, Math.atan(
                        (// Delta Y
                                -image.get(i, j-1)
                                + image.get(i, j)
                                //+ image.get(i+1, j + 1) + image.get(i, j+1 + 1)
                        ) /
                        (
                                -image.get(i-1, j)
                                + image.get(i, j)
                        )
                ));
                if (res.get(0, 1) < gNormalize[0][1][0])
                    gNormalize[0][1][0] = res.get(0, 1);
                if (res.get(0, 1) >  gNormalize[0][1][1])
                    gNormalize[0][1][1] = res.get(0, 1);

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
        gNormalize = new double[columns][lines][2];//x, y, min/max

    }
}
