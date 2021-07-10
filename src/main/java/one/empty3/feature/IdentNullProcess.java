package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.ColorTexture;
import one.empty3.library.LineSegment;
import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IdentNullProcess extends ProcessFile {

    @Override
    public boolean process(File in, File out) {
        try {
            PixM pixM = null;
            pixM = new PixM(ImageIO.read(in));
            ImageIO.write(pixM.getImage(), "jpg", out);

            return true;
        } catch (
                IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
