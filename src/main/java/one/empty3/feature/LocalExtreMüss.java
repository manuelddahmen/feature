package one.empty3.feature;

import java.util.ArrayList;

public class LocalExtreMüss {
    private   int subStartX = 0;
    private   int subStartY = 0;
    private   int columns;
    private   int lines;
    protected double sub[];

    public int getCompNo() {
        return compNo;
    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }

    private int compNo;

    public LocalExtreMüss(int width, int height) {
        this.columns = width;
        this.lines = height;
        initGNormalise();
        subStartX = columns/10;
        subStartY = lines/10;
        sub = new double[4*lines*columns*lines*columns];
    }

    public double getSub(int x, int y, int subI, int subJ) {
        return index(x, y, subI, subJ);
    }

    private int index(int x, int y, int subI, int subJ) {
        return subJ+ subStartY *(subI+ subStartX *(y+lines*(x+columns+compNo*(4))));
    }

    public void set(int x, int y, int subI, int subJ, double value) {
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

}
