package one.empty3.feature;

import java.awt.image.BufferedImage;

public abstract class FilterMatPixM extends M3{
    private final BufferedImage image;

    public FilterMatPixM(int columns, int lines, int columnsIn, int linesIn, BufferedImage image) {
        super(columns, lines, columnsIn, linesIn);
        this.image = image;
    }

    public FilterMatPixM(BufferedImage image, int c, int l) {
        super(image.getWidth(), image.getHeight(), c, l);
        this.image = image;
    }

    public int getInLines() {
        return linesIn;
    }
    public int getInColumbs() {
        return columnsIn;
    }

    public abstract void filter(M3 pixM, int i, int j, int columnsIn, int linesIn);
}
