package one.empty3.feature;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.PrimitiveIterator;
import java.util.Random;

public class M3 {
    public static PrimitiveIterator.OfDouble r = new Random().doubles().iterator();
    public static final Double noValue = r.next();
    private double[] x;
    public final int columns;
    public final int lines;
    protected final int columnsIn;
    protected final int linesIn;
    protected int compNo;
    protected BufferedImage image;
    private int compCount;
    private int currentX;
    private int currentY;
    private int savedY;
    private int savedX;
    private static int incrGetOut = 0;

    public M3(int columns, int lines, int columnsIn, int linesIn) {
        this.lines = lines;
        this.columns = columns;
        this.linesIn = linesIn;
        this.columnsIn = columnsIn;
        compCount = 4;
        init();
    }

    private void init() {
        x = new double[columns * lines * columnsIn * linesIn * compCount];
    }


    public M3(BufferedImage image, int columnsIn, int linesIn) {
        this(image.getWidth(), image.getHeight(), columnsIn, linesIn);
        this.image = image;

        float[] colorComponents = new float[getCompCount()];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                int rgb = image.getRGB(i, j);
                colorComponents = new Color(rgb).getColorComponents(colorComponents);
                for (int ii = 0; ii < columnsIn; ii++)
                    for (int ij = 0; ij < linesIn; ij++) {
                        for (int com = 0; com < getCompCount(); com++) {
                            setCompNo(com);
                            set(i, j, ii, ij, colorComponents[com]);
                        }
                    }
            }
        }
    }


    public double get(int column, int line, int columnIn, int lineIn) {
        if (column >= 0 && column < columns && line >= 0 && line < lines && columnIn >= 0 && columnIn < columnsIn
                && lineIn >= 0 && lineIn < linesIn && compNo >= 0 && compNo < compCount) {
            return x[index(column, line, columnIn, lineIn)];
        } else {
            incrGetOut++;
            return 0.0; // OutOfBound?
        }

    }

    public int index(int column, int line, int columnIn, int lineIn) {
        return compNo + compCount * (lineIn + linesIn * (columnIn + columnsIn * (line + lines * (column))));
    }

    public void set(int column, int line, int columnIn, int lineIn, double d) {
        if (column >= 0 && column < columns && line >= 0 && line < lines && columnsIn >= 0 && columnIn < columnsIn
                && lineIn >= 0 && lineIn < linesIn && compNo >= 0 && compNo < compCount) {
            x[index(column, line, columnIn, lineIn)] = d;
        } else {
            incrGetOut++;
            System.out.println("Outs : " + incrGetOut);
        }
    }

    public void getHarris(int line, int column) {

    }


    public M3 copy() {
        M3 copy = new M3(columns, lines, columnsIn, linesIn);

        for (int i = 0; i < copy.columns; i++) {
            for (int j = 0; j < copy.lines; j++) {
                for (int ii = 0; ii < copy.columnsIn; ii++)
                    for (int ij = 0; ij < copy.linesIn; ij++) {
                        for (int c = 0; c < getCompCount(); c++) {
                            setCompNo(c);
                            copy.setCompNo(c);
                            copy.set(i, j, ii, ij, get(i, j, ii, ij));
                        }
                    }
            }
        }
        return copy;
    }

    public int getCompCount() {
        return compCount;
    }

    protected double get(int ii, int ij) {
        return get(currentX(), currentY(), ii, ij);
    }

    private int currentY() {
        return currentY;
    }

    private int currentX() {
        return currentX;
    }

    protected void setXY(int i, int j) {
        currentX = i;
        currentY = j;
    }

    protected void restoreXY() {
        currentX = savedX;
        currentY = savedY;
    }

    protected void saveXY(int x, int y) {
        savedX = currentX;
        savedY = currentY;
        currentX = x;
        currentY = y;


    }

    public void set(int i, int j, double colorComponent) {
        set(currentX, currentY, i, j, (double) colorComponent);
    }


    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }

    public int getCompNo() {
        return compNo;
    }

    public PixM[][] getImagesMatrix() {
        return normalize(0.0, 1.0);
    }

    public PixM[][] normalize(final double min, final double max) {
        PixM[][] res = new PixM[columnsIn][linesIn];

        double[][][] maxRgbai = new double[compCount][columnsIn][linesIn];
        double[][][] meanRgbai = new double[compCount][columnsIn][linesIn];
        double[][][] minRgbai = new double[compCount][columnsIn][linesIn];


        for (int comp = 0; comp < getCompCount(); comp++) {
            setCompNo(comp);
            for (int ii = 0; ii < columnsIn; ii++) {
                for (int ij = 0; ij < linesIn; ij++) {
                    maxRgbai[comp][ii][ij] = -Double.MAX_VALUE;
                    minRgbai[comp][ii][ij] = Double.MAX_VALUE;
                    meanRgbai[comp][ii][ij] = 0;
                }
            }
        }


        for (int comp = 0; comp < getCompCount(); comp++) {
            for (int ii = 0; ii < columnsIn; ii++) {
                for (int ij = 0; ij < linesIn; ij++) {
                    for (int i = 0; i < columns; i++) {
                        for (int j = 0; j < lines; j++) {
                            setCompNo(comp);
                            if (get(i, j, ii, ij) > maxRgbai[comp][ii][ij]) {
                                maxRgbai[comp][ii][ij] = get(i, j, ii, ij);
                            }
                            if (get(i, j, ii, ij) < minRgbai[comp][ii][ij]) {
                                minRgbai[comp][ii][ij] = get(i, j, ii, ij);
                            }
                            meanRgbai[comp][ii][ij] += get(i, j, ii, ij);
                        }
                    }
                    meanRgbai[comp][ii][ij] /= (lines * columns);
                    System.out.println("Mean ii, ij" + meanRgbai[comp][ii][ij]);
                }
            }
        }

        for (int ii = 0; ii < columnsIn; ii++) {
            for (int ij = 0; ij < linesIn; ij++) {
                PixM image = new PixM(columns, lines);
                res[ii][ij] = image;
                for (int i = 0; i < image.columns; i++) {
                    for (int j = 0; j < image.lines; j++) {
                        for (int comp = 0; comp < getCompCount(); comp++) {
                            image.setCompNo(comp);
                            setCompNo(comp);
                            double v;
                            v = get(i, j, ii, ij);
                            /*value = ((v - minRgbai[comp][ii][ij])
                                        / (maxRgbai[comp][ii][ij] - minRgbai[comp][ii][ij]));*/
                            //value = (float) ((value - min) * (max - min));
                            //value = (float) Math.max(value, min);
                            //value = (float) Math.min(value, max);

                            image.set(i, j, v);
                        }
                    }
                }

            }
        }
        System.out.println("Outs : " + incrGetOut);
        return res;
    }
}
