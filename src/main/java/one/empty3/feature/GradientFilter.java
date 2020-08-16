package one.empty3.feature;

import java.awt.image.BufferedImage;

public class GradientFilter extends FilterMatPixM{

    public GradientFilter(BufferedImage image) {
        super(image, 1, 1);
    }

    public void filter(PixM image, int i, int j, int ii, int ij) {
        set (i, j, 0, 0, ( - image.get(ii-1, ij) - 
         - image.get(ii, ij-1) - 
         + 4*  image.get(ii, ij)
         + image.get(ii+1, ij)
         + image.get(ii, ij+1) 
      ) / 4.0);
        
 }

}
