package one.empty3.feature;

import java.awt.image.BufferedImage;

public class GradientFilter extends FilterMatPixM{

    public GradientFilter(BufferedImage image, int l, int c) {
        super(image, 1, 2);
    }

    public void filter(PixM image, int i, int j, int ii, int ij) {
        if((ii==1.0 && ij==0.0) ||(ii==0.0 && ij==1.0))
            set(i, j, ii, ij, image.get(ii, ij)-image.get(i, j));
        if((ii==0.0 && ij==0.0))
            set(i, j, ii, ij,  image.get(ii, ij)-image.get(i, j));
    }

}
