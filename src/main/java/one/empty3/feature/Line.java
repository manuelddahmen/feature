package one.empty3.feature;

import one.empty3.library.Point2D;
import org.apache.poi.POITextExtractor;

import java.lang.invoke.MutableCallSite;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;

public class Line {
    private MultiLinkList xys = new MultiLinkList();
    public Line(int... xys) {
        int n = xys.length/2;


        for (int n2 = 0; n2 < xys.length-2; n2+=2) {
            this.xys.add(new MultiLinkList.P2P2(new Point2D(xys[n2], xys[n2+1]),
                    new Point2D(xys[n2+1], xys[n2+2])
            ));
        }
    }
    public double dist(Line l2) {
        return 10.0;
    }
    public int dist(Point2D pPlus) {
        final Point2D[] pCandidat = {null};
        int [] i = {0};
        final int[] x = {-1};
        iterator().forEachRemaining(new Consumer<Point2D>() {

            @Override
            public void accept(Point2D point2D) {
                if(Point2D.dist(point2D, pPlus)<1.1) {
                    pCandidat[0] = point2D;
                    x[0]= i[0]++;
                }
            }
        });
        return x[0];
    }
    public boolean join(Line l2) {
        int [] i = {0};
        l2.iterator().forEachRemaining(point2D -> {
            int dist = dist(point2D);
            if(dist==0 && dist==xys.size()-1) {
                xys.add(dist, l2.xys, dist);
            }
        });
        return false;
    }
    private ListPoint listPoint = new ListPoint();
    class ListPoint implements Iterable<Point2D> {
        class MyIterator<Point2D> implements Iterator<one.empty3.library.Point2D> {
            private int current = -1;
            private boolean p1 = true;
            @Override
            public boolean hasNext() {
                return !p1 || (current + 1) < xys.size();
            }

            @Override
            public one.empty3.library.Point2D next() {
                if(p1) {
                    p1 = false;
                    current++;
                } else
                if(!p1) {
                    p1 = true;
                }
                return new one.empty3.library.Point2D(
                        p1?xys.get(current).getP1():xys.get(current).getP1());
            }
        }

        public Iterator<Point2D> iterator() {
            return new MyIterator<>();
        }
    }

    public Iterator<Point2D> iterator() {
        return listPoint.iterator();
    }


}
