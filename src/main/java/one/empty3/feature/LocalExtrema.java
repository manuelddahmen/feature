package one.empty3.feature;

import org.apache.poi.hssf.util.HSSFColor;

import java.util.ArrayList;

public class LocalExtrema extends FilterMatPixM {
    private final int pointsCount;
    private final int neighbourSize;
    private   int subStartX = 0;
    private   int subStartY = 0;
    private   int columns;
    private   int lines;
    protected double sub[];
    private double threshold = 0.2;

    public int getCompNo() {
        return compNo;
    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }

    private int compNo;

    public LocalExtrema(int width, int height, int neighbourSize, int pointsCount) {
        this.columns = width;
        this.lines = height;
        initGNormalise();
        subStartX = columns/10;
        subStartY = lines/10;
        this.neighbourSize = neighbourSize;
        this.pointsCount = pointsCount;
        //sub = new double[4*lines*columns];
    }

    public double getSub(int x, int y, int subI, int subJ) {
        return index(x, y, subI, subJ);
    }

    private int index(int x, int y, int subI, int subJ) {
        return subJ+ subStartY *(subI+ subStartX *(y+lines*(x+columns+compNo*(4))));
    }

    public void setSub(int x, int y, int subI, int subJ, double value) {
        this.sub[index(x, y, subI, subJ)] = value;
    }
    private void initGNormalise() {

    }
    // Detect regions, vertex(lines or curves), edges
    public ArrayList<AreaDescriptor> searchForFeaturePlaces() {
        ArrayList<AreaDescriptor> areas = new ArrayList<>();
        return areas;

        // Global search AreaDescriptor
        // Edge
        // Vertex
        // Similar colors Region
    }

    @Override
    public M3 filter(M3 original) {
        M3 copy = new M3(original.columns, original.lines, 1, 1);
/*
        for(int c=0; c< original.getCompCount(); c++) {
            original.setCompNo(c);
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    for (int ii = -1; ii < 1; ii++) {
                        for (int ij = -1; ij < 1; ij++) {
                            copy.set(i+ii, j+ij, 0, 0, lambda1dot2div1sum2(original, original.getCompNo(), i, j));
                        }
                    }
                }
            }
        }*/
        for(int c = 0; c< copy.getCompCount(); c++) {
            copy.setCompNo(c);
            original.setCompNo(c);
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    boolean isMaximum = true;
                    double maxLocal = 0.0;
                    int countOut = 0;
                    int countIn = 0;

                    if (maxLocal > getThreshold()) {
                        for (int ii = -neighbourSize/2; ii <= neighbourSize/2; ii++) {
                            for (int ij = neighbourSize/2; ij <= neighbourSize/2; ij++) {
                                double v = original.get(i + ii, j + ij, 0, 0);
                                if (v< maxLocal && !(ii == 0 && ij == 0) && v>=threshold) {
                                    countIn++;
                                } else if (ii != 0 || ij != 0) {
                                    countOut++;
                                }
                                else {
                                    countIn = 0;
                                }

                            }
                        }
                    }
                    if (countIn < pointsCount) {
                        copy.set(i, j, 0, 0, 1.0);
                    }
                    else {
                        copy.set(i, j, 0, 0, 0.0);
                    }
                }
            }
        }
        return copy;

    }

    private double lambda1dot2div1sum2(M3 original, int compNo, int i, int j) {
        PixM pixM = new PixM(3, 3);
        pixM.setRegionCopy(original, 0, 0,  i-1, j-1, i+1, j+1, pixM, 0, 0);
        return pixM.determinant()/ pixM.diagonalSum();
    }

    @Override
    public void element(M3 source, M3 copy, int i, int j, int ii, int ij) {

    }

    @Override
    public M3 norm(M3 m3, M3 copy) {
        return m3.copy();
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
