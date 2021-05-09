package one.empty3.feature;

import com.badlogic.gdx.graphics.Color;
import jogamp.opengl.GLBufferObjectTracker;
import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.core.lighting.Colors;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Classification extends ProcessFile {
    Random random = new Random();
    private BufferedImage imageOut;
    private int SIZE = 20;
    private double ratio = 8;
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
        SelectPointColorMassAglo selectPointColorMassAglo1 = new SelectPointColorMassAglo(read);
        int i = 0;
        for(i = 0; i< selectPointColorMassAglo1.getColumns()/SIZE; i++)
            for(int j = 0; j< selectPointColorMassAglo1.getLines()/SIZE; j++) {
                selectPointColorMassAglo1.setChosenColor(Colors.random());
                int x = i * SIZE;
                int y = j * SIZE;
                double v = selectPointColorMassAglo1.averageCountMeanOf(x, y, SIZE, SIZE, 0.2);
                if (v > SIZE*SIZE / ratio) {
                    java.awt.Color color = new java.awt.Color(imageOut.getRGB(x, y));
                    imageOut.setRGB(x, y, Color.WHITE.toIntBits());/*selectPointColorMassAglo.getChosenColor().getRGB()*/
                } else {
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
