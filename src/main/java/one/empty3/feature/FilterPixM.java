package one.empty3.feature;

import java.awt.image.BufferedImage;

public abstract class FilterPixM extends PixM {
    public FilterPixM(int l, int c) {
        super(l, c);
    }

    public FilterPixM(BufferedImage image) {
        super(image);
    }

    public abstract double filter(double i, double i1);
}
