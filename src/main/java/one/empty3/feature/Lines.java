package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;
import one.empty3.library.core.lighting.Colors;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Lines extends ProcessFile {
    private PixM pixM;
    private ArrayList<List<Point3D>> lists;

    @Override
    public boolean process(File in, File out) {
        lists = new ArrayList<List<Point3D>>();
        try {
            pixM = new PixM(ImageIO.read(in));
            PixM o = new PixM(pixM.getColumns(), pixM.getLines());
            for (int i = 0; i < pixM.getColumns(); i++) {
                for (int j = 0; j < pixM.getLines(); j++) {
                    int x = i;
                    int y = j;

                    listTmpCurve = new ArrayList<Point3D>();
                    listTmpCurve.add(new Point3D((double)x, (double)y, pixM.luminance(x, y)));
                    int cont = 0;
                    double valueDiff = 0.2;
                    for (int s = 0; s < pixM.getLines(); s++) {
                        int dist = 0;
                        int listSize = 0;
                        dist = 2;
                        double value = pixM.luminance(x, y);

                        double valueAvg = pixM.mean(x - dist/2, y -dist/2, dist+1, dist+1);
                        if (valueAvg < 0.1) {
                            break;
                        }
                        List<Point3D> points = neighborHood(i, j, 2, valueAvg, valueDiff);

                        if (points.size()==0) {
                                break;
                        }

                        for (List<Point3D> ps : lists)
                            for (Point3D p : ps)
                                for (int c = 0; c < points.size(); c++)
                                    if (points.get(c).equals(p))
                                        points.remove(c);
                        for (Point3D p : listTmpCurve)
                            for (int c = 0; c < points.size(); c++)
                                if (points.get(c).equals(p))
                                    points.remove(c);

                        listTmpCurve.addAll(points);

                    }
                    if (listTmpCurve.size() > 0)
                        lists.add(listTmpCurve);
                }
            }

            lists.forEach(point3DS -> {
                Color r = Colors.random();
                point3DS.forEach(point3D -> {
                    o.setValues((int) (double) (point3D.getX()), (int) (double) (point3D.getY()), r.getRed()/255., r.getGreen()/255., r.getBlue()/255.);
                });
            });

            ImageIO.write(/*o.normalize(0.0, 1.0)*/o.getImage(), "jpg", out);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    ArrayList<Point3D> listTmpCurve = new ArrayList();
    ArrayList<Point3D> listTmp = new ArrayList();

    private List<Point3D> neighborHood(int i, int j, int dist, double valueMin, double valueDiff) {
        listTmp.clear();
        for (int x = 0; x < dist; x++) {
            for (int y = 0; y < dist; y++) {
                if (i+x != i && j+y != j) {
                    int x2 = i + (x - dist / 2);
                    int y2 = j + (y - dist / 2);
                    Point point = new Point(x2, y2);
                    Point3D p = new Point3D(point.getX(), point.getY(), pixM.mean((int) point.getX(), (int) point.getY(), dist, dist));
                    if (p.getZ() >= valueMin - valueDiff && p.getZ() <= valueMin + valueDiff) {
                        listTmp.add(p);
                        break;
                    }
                }
            }
        }

        for (Object o : listTmp) {
            Point3D p = (Point3D) o;

        }
        return ((List<Point3D>) listTmp.clone());
    }
}
