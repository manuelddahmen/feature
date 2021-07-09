package one.empty3.feature.selection;

import one.empty3.feature.PixM;
import one.empty3.library.ITexture;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;

import java.util.List;

public class Paste {
    public void pasteList(List<Point3D> points, PixM img, ITexture col) {
        for (int i = 0; i < points.size(); i++) {

            int x = (int) (double) points.get(i).getX();
            int y = (int) (double) points.get(i).getY();

            int rgb = points.get(i).texture().getColorAt(
                    points.get(i).getX()/img.getColumns(),
                    points.get(i).getY()/img.getLines());

            double [] rgbD = Lumiere.getDoubles(rgb);
            for (int i1 = 0; i1 < rgbD.length; i1++) {
                img.set(x, y, rgbD[i1]);
            }

        }

    }
}
