package one.empty3.feature;

import one.empty3.library.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class MultiLinkList extends ArrayList<MultiLinkList.P2P2> {
    public static class P2P2 {
        private Point2D p0;
        private Point2D p1;

        public P2P2(Point2D p0, Point2D p1) {
            this.p0 = p0;
            this.p1 = p1;
        }

        public Point2D getP0() {
            return p0;
        }

        public void setP0(Point2D p0) {
            this.p0 = p0;
        }

        public Point2D getP1() {
            return p1;
        }

        public void setP1(Point2D p1) {
            this.p1 = p1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof P2P2)) return false;
            P2P2 p2P2 = (P2P2) o;
            return Objects.equals(getP0(), p2P2.getP0()) &&
                    Objects.equals(getP1(), p2P2.getP1());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getP0(), getP1());
        }
    }
    public void add(int i, MultiLinkList list, int listItemAt) {
        add(new P2P2(get(i).getP0(), list.get(listItemAt).getP0()));
        for(int j=listItemAt; j<list.size()-1; j++)
            add(new P2P2(list.get(j).getP1(), list.get(j+1).getP0()));
        add(new P2P2(list.get(list.size()-2).getP0(), list.get(list.size()-1).getP0()));

        add(new P2P2(get(i).getP0(), list.get(listItemAt-1).getP1()));
        for(int j=listItemAt; j>0; j--)
            add(new P2P2(list.get(j).getP1(), list.get(j-1).getP0()));
        add(new P2P2(list.get(0).getP1(), list.get(0).getP0()));

    }
}
