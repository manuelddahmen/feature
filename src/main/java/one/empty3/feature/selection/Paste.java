package one.empty3.feature.selection;

import one.empty3.feature.PixM;
import one.empty3.library.ITexture;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;

import java.util.List;

public class Paste {

    /***
     *
     * @param points Sélection de points
     * @param img Image sur laquelle dessiner
     * @param col Couleur ou texture de dessin
     */
    public void pasteList(List<Point3D> points, PixM img, ITexture col) {

        for (int i = 0; i < points.size(); i++) {

            int x = (int) (double) points.get(i).getX();
            int y = (int) (double) points.get(i).getY();

            int rgb = points.get(i).texture().getColorAt(
                    points.get(i).getX()/img.getColumns(),
                    points.get(i).getY()/img.getLines());

            double [] rgbD = Lumiere.getDoubles(col.getColorAt((double)x/img.getColumns(),
                    (double)y/img.getLines()));
            for (int i1 = 0; i1 < 3; i1++) {
                img.setCompNo(i1);

                img.set(x, y, rgbD[i1]);
            }

        }

    }
}
