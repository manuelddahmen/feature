package one.empty3.feature;


import one.empty3.io.ProcessFile;

import java.io.File;

import static org.junit.Assert.*;

import javax.imageio.ImageIO;

import one.empty3.feature.*;

import java.util.logging.*;

public class GradProcess extends ProcessFile {

    public boolean process(File in, File out) {

        if (!in.getName().endsWith(".jpg"))
            return false;
        File file = in;
        PixM pix;
        try {
            pix = PixM.getPixM(ImageIO.read(file), maxRes);
            GradientFilter gf = new GradientFilter(pix.getColumns(),
                    pix.getLines());
            PixM r = gf.filter(
                    new M3(
                            pix, 1, 1)
            ).getImagesMatrix()[0][0];

            ImageIO.write(r.normalize(0.0, 1.0).getImage(), "jpg", out);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
