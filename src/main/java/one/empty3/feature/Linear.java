package one.empty3.feature;

public class Linear {
    private int type = 0;
    public static final int TYPE_2D = 0;
    public static final int TYPE_2D_2D = 1;

    private PixM[] images;
    private final M3[] imagesM;

    public Linear(PixM... images) {
        type = TYPE_2D_2D;
        this.imagesM = null;
        this.images = images;
    }

    public Linear(M3... imagesM) {
        type = TYPE_2D;
        this.images = null;
        this.imagesM = imagesM;
    }

    public boolean op2d2d(char[] op, int[][] index, int[] indexRes) {
        PixM[] workingImages = null;
        assert images != null;
        for (int x = 0; x < op.length; x++) {
            workingImages = new PixM[images.length];//.??N
        //new PixM[index[x].length];//.??N
            for (int j = 0; j < index[x].length; j++) {
                workingImages[j] = images[index[x][j]];
            }
            assert workingImages != null;

            PixM pixM = new PixM(workingImages[0].columns,
                    workingImages[0].lines);
            for (int m = 0; m < index[x].length; m++) {
                assert workingImages[m] != null;
                for (int comp = 0; comp < workingImages[m].getCompCount(); comp++) {
                    workingImages[m].setCompNo(comp);
                    for (int i = 0; i < workingImages[m].columns; i++)
                        for (int j = 0; j < workingImages[m].lines; j++)
                            switch (op[x]) {
                                case '+':
                                    pixM.set(i, j, pixM.get(i, j) + workingImages[m].get(i, j));
                                    break;
                                case '-':
                                    pixM.set(i, j, pixM.get(i, j) - workingImages[m].get(i, j));
                                    break;
                                case '*':
                                    pixM.set(i, j, pixM.get(i, j) * workingImages[m].get(i, j));
                                    break;
                                case '/'://divide M1/M2/M3
                                    pixM.set(i, j, pixM.get(i, j) / workingImages[m].get(i, j));
                                    break;
                                case '~': //average
                                    pixM.set(i, j, pixM.get(i, j) + workingImages[m].get(i, j) / workingImages.length);
                                    break;
                                case '%':
                                    pixM.set(i, j, Math.IEEEremainder(pixM.get(i, j), workingImages[m].get(i, j)));
                                    break;
                                case '|':
                                    // Norme
                                    break;
                            }
                }
            }
            workingImages[indexRes[x]] = pixM;
        }
        this.images = workingImages == null ? images : workingImages;
        return true;
    }

    public int getType() {
        return type;
    }

    public PixM[] getImages() {
        return images;
    }
}
