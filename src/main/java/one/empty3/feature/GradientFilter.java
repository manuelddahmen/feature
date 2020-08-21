package one.empty3.feature;

public class GradientFilter extends FilterMatPixM {

    private double[][][][] gNormalize;

    public GradientFilter(int width, int height) {
        super(width, height, 2, 2);
        initGNormalise();

    }

    @Override
    public M3 filter(M3 original) {
        M3 copy = original.copy();
        for (int i = 0; i < copy.columns; i++) {
            for (int j = 0; j < copy.lines; j++) {

                for (int ii = 0; ii < copy.columnsIn; ii++)
                    for (int ij = 0; ij < copy.linesIn; ij++) {
                        for (int i1 = 0; i1 < getCompCount(); i1++) {
                            setCompNo(i1);
                            copy.setCompNo(i1);
                            original.setCompNo(i1);
                            element(original, copy, i, j, ii, ij);
                        }

                    }
            }
        }
        norm(copy);

        return copy;
    }

    /***
     * Computes gradient element p(x,y)
     * @param source 1x1 innner image matrix
     * @param copy 2x2 inner image gradient matrix
     * @param i x cordinates of p(x, y)
     * @param j y cordinates of p(x, y)
     * @param ii (0, 1) gradX, gradY
     * @param ij (0, 1) difference, angle (delta phase)
     */
    public void element(M3 source, M3 copy, int i, int j, int ii, int ij) {

        if (ii == 0 && ij == 0) {
            copy.set(i, j, 0, 0, -source.get(i - 1, j, 0, 0) + source.get(i, j, 0, 0));
            //+ image.get(i+ii + 1, j+ij)
            //+ image.get(i+ii, j+ij + 1
        }
        if (ii == 0 && ij == 1) {
            copy.set(i, j, 0, 1, Math.atan(-source.get(i, j - 1, 0, 0) + source.get(i, j, 0, 0)) /
                    (-source.get(i - 1, j, 0, 0) + source.get(i, j, 0, 0)));

        }
        if (ii == 1 && ij == 0) {
            copy.set(i, j, 1, 0, -source.get(i, j - 1, 0, 0) + source.get(i, j, 0, 0)
            );

        }
        if (ii == 1 && ij == 1) {
            copy.set(i, j, 1, 1, Math.atan(
                    (// Delta Y/Delta X
                            -source.get(i, j + 1, 0, 0) + source.get(i, j, 0, 0)
                            //+ image.get(i+1, j + 1) + image.get(i, j+1 + 1)
                    ) /
                            (
                                    -source.get(i + 1, j, 0, 0) + source.get(i, j, 0, 0)
                            )
            ));

        }


        if (copy.get(i, j, ii, ij) < gNormalize[getCompNo()][ii][ij][0])
            gNormalize[getCompNo()][ii][ij][0] = copy.get(i, j, ii, ij);
        if (copy.get(i, j, ii, ij) > gNormalize[getCompNo()][ii][ij][1])
            gNormalize[getCompNo()][ii][ij][1] = copy.get(i, j, ii, ij);


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
