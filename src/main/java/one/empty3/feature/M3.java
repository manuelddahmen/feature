package one.empty3.feature;

public class M3 {
    private final double[] x;
    private final int columns;
    private final int lines;
    protected final int columnsIn;
    protected final int linesIn;
    protected int compNo;

    public M3(int columns, int lines, int columnsIn, int linesIn) {
        this.lines = lines;
        this.columns = columns;
        this.linesIn = linesIn;
        this.columnsIn = columnsIn;
        x = new double[columns*lines*columnsIn*linesIn];
    }

    public double get(int column, int line, int columnIn, int lineIn) {
        return x[(((columns*line*column)*lines+columnIn)*lineIn+columnIn)*(compNo+1)];
    }
    public void set(int column, int line, int columnIn, int lineIn, double d) {
        x[(((columns*line*column)*lines+columnIn)*lineIn+columnIn)*(compNo+1)] = d;
    }

    public void getHarris(int line, int column) {

    }

    public void setCompNo(int comp) {
        this.compNo = comp;
    }

    public int getCompNo() {
        return compNo;
    }
}
