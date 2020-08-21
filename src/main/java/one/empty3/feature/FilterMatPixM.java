package one.empty3.feature;

import java.awt.image.BufferedImage;

public abstract class FilterMatPixM {
    protected int columnsIn = 2;
    protected int linesIn = 2;
    protected int columns;
    protected int lines;
/*
    public int getCompNo() {
        return compNo;
    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }
*/
    public FilterMatPixM(int columns, int lines, int columnsIn, int linesIn) {
        this.columnsIn = 2;
        this.linesIn = 2;
        this.columns = columns;
        this.lines = lines;
    }
/*
    public int getCompCount() {

        return compCount;
    }

    public void setCompCount(int compCount) {
        this.compCount = compCount;
    }
*/

    public int getInLines() {
        return linesIn;
    }

    public int getInColumbs() {
        return columnsIn;
    }

    public abstract M3 filter(M3 original);

    public abstract void element(M3 source, M3 copy, int i, int j, int ii, int ij);

    public abstract void norm(M3 m3);
}
