package one.empty3.feature;

import java.awt.image.BufferedImage;

public abstract class FilterMatPixM extends M3 {
    public FilterMatPixM(int columns, int lines, int c, int l) {
        super(columns, lines, c, l);
    }

    public int getInLines() {
        return linesIn;
    }

    public int getInColumbs() {
        return columnsIn;
    }

    public abstract M3 filter(M3 original);

    public abstract void element(M3 source, M3 copy, int i, int j, int ii, int ij);

    public abstract void norm(M3 m3);
}
