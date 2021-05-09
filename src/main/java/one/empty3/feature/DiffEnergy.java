package one.empty3.feature ;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class DiffEnergy extends FilterPixM {
    static PrintWriter pw;
    double [] energy = new double[3];

    public DiffEnergy(int l, int c) {
        super(l, c);
    }

    public DiffEnergy(BufferedImage fo, PrintWriter printWriter) {
        super((BufferedImage) (fo));
    }

    public void init() {

    }

    @Override
    public double filter(double x, double y) {
        for(int c=0; c<3; c++) {
            setCompNo(c);
            energy[c] += get((int)x,(int) y);
        }
        return 0.0;
    }

    public void end(String comment) {
        pw.println(""+ energy[0]+", " + energy[1]+", "+energy[2]+", "+comment+"\n");
    }
}
