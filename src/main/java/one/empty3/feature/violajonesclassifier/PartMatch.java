package one.empty3.feature.violajonesclassifier;

import one.empty3.feature.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartMatch extends ProcessFile {
    List<PixM> features = new ArrayList<>();

    public PartMatch() {
        int N = 25;
        for (int n = 0; n < N; n++) {
            PixM pixM = new PixM(n, n);
            for (int a = 0; a < 8; a++) {
                double lineAx = Math.cos(2 * Math.PI * N / a);
                double lineAy = Math.sin(2 * Math.PI * N / a);
                double lineBx = -Math.cos(2 * Math.PI * N / a);
                double lineBy = -Math.sin(2 * Math.PI * N / a);

                Point2D pa = new Point2D(lineAx, lineAy);
                Point2D pb = new Point2D(lineBx, lineBy);

                for (int i = 0; i < pixM.getColumns(); i++)
                    for (int j = 0; i < pixM.getLines(); j++) {
                        Point2D p = new Point2D(i - n / 2., j - n / 2.);
                        double signum = Math.signum(((pa.getX() - pb.getX()) * (p.getX() - pb.getX())) +
                                ((pa.getY() - pb.getY()) * (pa.getY() - pb.getY())));
                        pixM.setValues(i, j, signum, signum, signum);
                    }
            }
            pixM.normalize(-1., 1.);


            features.add(pixM);
        }
    }

    @Override
    public boolean process(File in, File out) {
        try {
            PixM pix = PixM.getPixM(ImageIO.read(in), maxRes);
            int[][] largeurs = new int[pix.getColumns()][pix.getLines()];
            double lastMatchScore = 0;
            for (int n = 1; n < 25; n *= 2) {
                double matchScoreMax;
                int index = 0;
                for (int i = 0; i < pix.getColumns(); i++) {
                    for (int j = 0; j < pix.getLines(); j++) {
                        double m;
                        double matchScoreMin = 0.5;
                        if ((m = matchScore(pix, i, j, n, features.get(index)))>lastMatchScore
                              && m>=matchScoreMin) {
                            classify(m, features.get(index));
                            lastMatchScore = m;
                        }
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
    }

    private void classify(double m, PixM pixM) {

    }

    private double matchScore(PixM pix, int i, int j, int n, PixM pixM) {
        return 0;
    }
}