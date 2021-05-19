package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Voronoi extends ProcessFile {
    private Point3D proche(Point3D point3D, List<Point3D> p) {
        double dist = 1000000;
        Point3D pRes = null;
        for (Point3D p2 : p) {
            if (Point3D.distance(point3D, p2) < dist) {
                dist = Point3D.distance(point3D, p2);
                pRes = p2;
            }
        }
        return pRes;
    }
    @Override
    public boolean process(File in, File out) {
        try {
            List<Point3D> points = new ArrayList();
            BufferedImage read = ImageIO.read(in);
            PixM pixM = PixM.getPixM(read, maxRes);
            PixM pixMOut = pixM.copy();
            for(int i=0; i<pixM.getColumns(); i++) {
                for (int j = 0; j < pixM.getLines(); j++) {
                    if(pixM.luminance(i, j)>0.0) {
                        Point3D p = pixM.getP(i, j);
                        points.add(p);
                    }
                }
            }
            for(int i=0; i<pixM.getColumns(); i++) {
                for (int j = 0; j < pixM.getLines(); j++) {
                    Point3D proche = proche(pixMOut.getP(i, j), points);
                    Point3D p = pixMOut.getP((int) (double) proche.get(0), (int) (double) proche.get(1));
                    pixMOut.setValues(i, j, p.getX(), p.getY(), p.getZ());
                }


            }
            ImageIO.write(pixMOut.getImage(), "jpg", out);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
