package one.empty3.feature;

public class GradientFilter extends FilterMatPixM {

    private double[][][][] gNormalize;

    public GradientFilter(int width, int height) {
        super(width, height, 2, 2);
        initGNormalise();

    }

    @Override
    public M3 filter(M3 source) {
        //System.out.println("TRUE: "+(linesIn==2 && columnsIn==2 && source.linesIn==2 && source.columnsIn==2));
        M3 copy = source.copy();
        for (int i = 0; i < copy.columns; i++) {
            for (int j = 0; j < copy.lines; j++) {

                for (int ii = 0; ii < copy.columnsIn; ii++)
                    for (int ij = 0; ij < copy.linesIn; ij++) {
                        for (int c = 0; c < 4; c++) {
                            copy.setCompNo(c);
                            source.setCompNo(c);
                            element(source, copy, i, j, ii, ij);
                        }

                    }
            }
        }
        //norm(copy);

        return copy;
    }

    /***
     * Computes gradient element p(x,y)
     * @param source 2x2 inner image matrix
     * @param copy 2x2 inner image gradient matrix
     * @param i x cordinates of p(x, y)
     * @param j y cordinates of p(x, y)
     * @param ii (0, 1) gradX, gradY
     * @param ij (0, 1) difference, angle (delta phase)
     *
     */
    @Override
    public void element(M3 source, M3 copy, int i, int j, int ii, int ij) {
        //System.out.println("element (GradientFilter class : ii,ij"+ii+","+ij);
        double d = 1.0;
        if (ii == 0 && ij == 0) {
            d=  -source.get(i - 1, j, 0, 0) + source.get(i, j, 0, 0);
            //+ image.get(i+ii + 1, j+ij)
            //+ image.get(i+ii, j+ij + 1
        }
        if (ii == 0 && ij == 1) {
            d = Math.atan(-source.get(i, j - 1, 0, 0) + source.get(i, j, 0, 0)) /
                    (-source.get(i - 1, j, 0, 0) + source.get(i, j, 0, 0));

        }
        if (ii == 1 && ij == 0) {
             d = -source.get(i, j - 1, 0, 0) + source.get(i, j, 0, 0);

        }
        if (ii == 1 && ij == 1) {
            d =  Math.atan(
                    (-source.get(i, j + 1, 0, 0) + source.get(i, j, 0, 0)) /
                            (-source.get(i + 1, j, 0, 0) + source.get(i, j, 0, 0)));

        }
        copy.set(i, j, ii, ij, d);

        if (source.get(i, j, 0, 0) < gNormalize[source.getCompNo()][ii][ij][0])
            gNormalize[source.getCompNo()][ii][ij][0] = source.get(i, j, 0, 0);
        if (copy.get(i, j, 0, 0) > gNormalize[source.getCompNo()][ii][ij][1])
            gNormalize[source.getCompNo()][ii][ij][1] = source.get(i, j, 0, 0);


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
        gNormalize = new double[4][columnsIn][linesIn][2];
        for (int c = 0; c <4; c++) {
            for (int ii = 0; ii < columnsIn; ii++)
                for (int ij = 0; ij < linesIn; ij++) {

                    gNormalize[c][ii][ij][0] = Double.MAX_VALUE;
                    gNormalize[c][ii][ij][1] = -Double.MAX_VALUE;
                }

        }
    }



}
