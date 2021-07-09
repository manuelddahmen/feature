package one.empty3.feature.selection;

import one.empty3.feature.PixM;
import one.empty3.library.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/***
 * TODO Trouver les primitives.
 */
public abstract class Selection {
    public abstract List<Point3D> select(List<Point3D> preSelection, PixM pix,
                                         int rgb, double threshold);

    public abstract List<Point3D> selectColorPoint(List<Point3D> preSelection, PixM pix,
                                                   int rgb, int x, int y, double threshold);

    public abstract List<Point3D> selectPoint(List<Point3D> preSelection, PixM pix,
                                              int x, int y);

    public abstract List<Point3D> selectInRect(List<Point3D> preSelection, PixM pix,
                                               int x1, int y1, int x2, int y2);


}
