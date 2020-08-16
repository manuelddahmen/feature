package one.empty3.feature;

import java.awt.image.BufferedImage;

public abstract class FilterPixM extends PixM {
    public final static int NORM_NONE = 0;
    public final static int NORM_MEAN = 1;
    public final static int NORM_MAX = 2;
    public final static int NORM_FLOOR_0 = 4;
    public final static int NORM_FLOOR_1 = 8;
    public final static int NORM_CUSTOM = 16;

    public int getNormalize() {
        return normalize;
    }

    public FilterPixM setNormalize(int normalize) {
        this.normalize = normalize;
        return this;
    }

    private int normalize = NORM_NONE;

    public FilterPixM(int l, int c) {
        super(l, c);
    }

    public FilterPixM(BufferedImage image) {
        super(image);
    }

    public abstract double filter(double i, double i1);

}
