package one.empty3.feature;

public class M3 {
    private double[] x;
    private int columns;
    private int lines;

    public M3(int columns, int lines, int columnsIn, int linesIn) {
        this.lines = lines;
        this.columns = columns;
        x = new double[columns*lines*columnsIn*linesIn];
    }

    public double get(int column, int line, int columnIn, int lineIn) {
        return x[((columns*line*column)*lines+columnIn)*lineIn+columnIn];
    }
    public void set(int column, int line, int columnIn, int lineIn, double d) {
        x[((columns*line*column)*lines+columnIn)*lineIn+columnIn] = d;
    }

    public void getHarris(int line, int column) {

    }

}
