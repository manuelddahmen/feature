package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;
import one.empty3.library.core.lighting.Colors;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Classification extends ProcessFile {
    Random random = new Random();
    private BufferedImage imageOut;
    private int SIZE = 5;
    private double ratio = 0.2;


    @Override
    public boolean process(File in, File out) {
        PixM selectPointColorMassAglo = null;
        BufferedImage read = null;
        try {
            read = ImageIO.read(in);
            selectPointColorMassAglo = PixM.getPixM(read, maxRes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            imageOut = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert selectPointColorMassAglo != null;
        SelectPointColorMassAglo selectPointColorMassAglo1 = new SelectPointColorMassAglo(selectPointColorMassAglo.getImage());
        int color = Color.WHITE.getRGB();
        for (int i = 0; i < selectPointColorMassAglo1.getColumns(); i += 1)
            for (int j = 0; j < selectPointColorMassAglo1.getLines(); j += 1) {
                selectPointColorMassAglo1.setTmpColor(Colors.random());
                double v = selectPointColorMassAglo1.averageCountMeanOf(i, j, SIZE, SIZE, 0.4);
                if (v > ratio) {
                    imageOut.setRGB(i, j, color);/*selectPointColorMassAglo.getChosenColor().getRGB()*/
                } else {
                    double[] doubles = Lumiere.getDoubles(read.getRGB(i, j));
                    for(int c=0; c<3; c++)
                        doubles[c] = doubles[c]/3;
                    imageOut.setRGB(i, j, Lumiere.getInt(doubles));
                }
            }

        try {
            ImageIO.write(imageOut, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
