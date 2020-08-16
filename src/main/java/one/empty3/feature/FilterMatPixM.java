package one.empty3.feature;

import java.awt.image.BufferedImage;

public abstract class FilterMatPixM extends M3 {
    public FilterMatPixM(BufferedImage image, int c, int l) {
        super(image, c, l);
    }

    public int getInLines() {
        return linesIn;
    }

    public int getInColumbs() {
        return columnsIn;
    }

    public abstract void element(PixM m3, M3 c, int i, int j, int ii, int ij);

    public abstract void norm(M3 m3);
}
