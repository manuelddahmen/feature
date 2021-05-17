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
            PixM pixM = new PixM(ImageIO.read(in));
            PixM o = new PixM(pixM.getColumns(), pixM.getLines());
            for(int i=0; i< pixM.getColumns(); i++) {
                for(int j=0; j<pixM.getLines(); j++) {
                    listTmpCurve = new ArrayList();
                    int dist = 0;
                    while(dist<10) {
                        double value = pixM.luminance(i, j);
                        double valueMin = pixM.mean(i - dist / 2, j - dist / 2, dist, dist);
                        List<Point3D> points = neighborHood(i, j, dist, valueMin);

                        for(int c=0; c<points.size(); c++)
                            if(points.get(i).getX()==i&&points.get(i).getY()==j)
                                points.remove(c);

                            listTmpCurve.addAll(points);
                        dist++;
                    }
                }
            }
            if(listTmpCurve.size()>0)
                lists.add(listTmpCurve);

            lists.forEach(point3DS -> {
                Color r = Colors.random();
                point3DS.forEach(point3D -> {
                    o.setValues((int)(double)(point3D.getX()), (int)(double)(point3D.getY()), r.getRed(), r.getGreen(), r.getBlue());
                });
            });

            ImageIO.write(pixM.getImage(), "jpg", out);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    ArrayList listTmpCurve = new ArrayList();
    ArrayList listTmp = new ArrayList();
    private List neighborHood(int i, int j, int dist, double valueMin) {
        listTmp.clear();
        for(int x=0; x<2; x++) {
            for (int y = 0; y < 2; y++) {
                for(int s=0; s<dist*8; s++) {
                    Point point = new Point(i + (x - 1) * dist, j + (y - 1));
                    Point3D p = new Point3D(point.getX(), point.getY(),pixM.luminance((int)point.getX(), (int)point.getY()));
                    if(p.getZ()>valueMin) {
                        listTmp.add(p);
                    }
                }
            }
        }

        for (Object o : listTmp) {
            Point3D p = (Point3D) o;

        }
        return ((List) listTmp.clone());
    }
}
