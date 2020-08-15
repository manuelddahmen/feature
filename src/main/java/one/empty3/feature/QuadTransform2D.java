package one.empty3.feature;

import java.awt.*;
import java.awt.image.BufferedImage;

public class QuadTransform2D {
    private BufferedImage i1, i2;
    private Point [] p1;
    private Point [] p2;

    public class Inter {
        Point p;
        Color c;

        public Inter(Point p, Color c) {
            this.p = p;
            this.c = c;
        }

        public Point getP() {
            return p;
        }

        public void setP(Point p) {
            this.p = p;
        }

        public Color getC() {
            return c;
        }

        public void setC(Color c) {
            this.c = c;
        }
    }

    public QuadTransform2D(BufferedImage i1, BufferedImage i2, Point[] p1, Point[] p2) {
        this.i1 = i1;
        this.i2 = i2;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Inter inter(double p1243, double p1423, double t) {

        return null;
    }


}
