package one.empty3.feature;

import java.awt.image.BufferedImage;

public class Harris extends M3 {

    /***
     * Product of the Gradients at (x,y) (1,1)
     * @param columns
     * @param lines
     */
    public Harris(int columns, int lines) {
        super(columns, lines, 3, 3);
    }
    public void init(BufferedImage bufferedImage) {

    }
}
