package one.empty3.feature;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.PrimitiveIterator;
import java.util.Random;

public class M3 {
    public static PrimitiveIterator.OfDouble r = new Random().doubles().iterator();
    public static final Double noValue = r.next();
    private PixM pixM;
    private double[] x;
    public final int columns;
    public final int lines;
    protected final int columnsIn;
    protected final int linesIn;
    protected int compNo;
    private BufferedImage image;
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
        setCompCount();
        init();
        pixM = new PixM(columns, lines);
    }

    private void init() {
        x = new double[columns * lines * columnsIn * linesIn * compCount];
    }

    private void setCompCount() {
        compCount = 4;
    }

    public M3(BufferedImage image, int columnsIn, int linesIn) {
        this(image.getWidth(), image.getHeight(), columnsIn, linesIn);
        pixM = new PixM(image);
        float[] colorComponents = new float[compCount];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                saveXY(i, j);
                int rgb = image.getRGB(i, j);
                colorComponents = new Color(rgb).getColorComponents(colorComponents);
                for (int ii = 0; ii < columnsIn; ii++)
                    for (int ij = 0; ij < linesIn; ij++) {
                        for (int com = 0; com < getCompCount(); com++) {
                            setCompNo(com);
                            set(i, j, ii, ij, colorComponents[com]);
                        }
                    }
                restoreXY();

            }
        }
        compCount = 0;
    }


    public double get(int column, int line, int columnIn, int lineIn) {
        if (column >= 0 && column < columns && line >= 0 && line < lines && columnIn >= 0 && columnIn < columnsIn
                && lineIn >= 0 && lineIn < columns && compNo >= 0 && compNo < compCount) {
            return x[compNo + compCount * (lineIn + linesIn * (columnIn + columnsIn * (line + lines * (column + columns * 0))))];
            //compNo+compCount*(lineIn+linesIn*(columnIn+columnsIn*(line+lines*(column+columns*0))))
        } else {
            incrGetOut++;
            return 0.0; // OutOfBound?
        }

    }

    public void set(int column, int line, int columnIn, int lineIn, double d) {
        if (column >= 0 && column < columns && line >= 0 && line < lines && columnsIn >= 0 && columnIn < columnsIn
                && lineIn >= 0 && columnIn < columns && compNo >= 0 && compNo < compCount) {
            x[compNo + compCount * (lineIn + linesIn * (columnIn + columnsIn * (line + lines * (column + columns * 0))))] = d;
        }
    }

    public void getHarris(int line, int column) {

    }


    public M3 filter(FilterMatPixM f) {
        M3 c = new M3(columns, lines, f.columnsIn, f.linesIn);
        double sum = 0.0;
        int[] comps = new int[]{0, 1, 2, 3};
        for (int comp : comps) {

            setCompNo(comp);
            c.setCompNo(comp);


            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    for (int ii = 0; ii < c.columnsIn; ii++)
                        for (int ij = 0; ij < c.linesIn; ij++) {
                            c.setXY(i, j);
                            f.element(pixM, c, i, j, ii, ij);
                        }
                }

            }
        }

        f.norm(c);


        return c;
    }


    public BufferedImage getImage(int[] compsNo) {

        BufferedImage image = new BufferedImage(columns,
                lines, BufferedImage.TYPE_INT_RGB);


        int savedComp = getCompNo();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                float[] rgba = new float[getCompCount()];
                for (int comp : compsNo) {
                    setCompNo(comp);
                    float value = (float) get(i, j, 0, 0);
                    //TODO problems
                    value = Math.max(value, 0f);
                    value = Math.min(value, 1f);

                    rgba[comp] = value;

                    //values[j * columns + i] += ((rgbComp & 0xFF) << ((3-  comp) * 8));
                }
                image.setRGB(i, j, new Color(rgba[0], rgba[1], rgba[2]).getRGB());
            }
        }
        setCompNo(savedComp);
        return image;

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

    public BufferedImage[][] getImagesMatrix(int ii, int ij) {
        BufferedImage[][] arr = new BufferedImage[ii][ij];

        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[x].length; y++) {
                arr[x][y] = new BufferedImage(columns,
                        lines, BufferedImage.TYPE_INT_RGB);

                int savedComp = getCompNo();

                for (int i = 0; i < columns; i++) {
                    for (int j = 0; j < lines; j++) {
                        float[] rgba = new float[4];
                        for (int comp = 0; comp < rgba.length; comp++) {
                            setCompNo(comp);
                            float value = (float) get(i, j, x, y);
                            //TODO problems
                            value = Math.max(value, 0f);
                            value = Math.min(value, 1f);

                            rgba[comp] = value;

                            //values[j * columns + i] += ((rgbComp & 0xFF) << ((3-  comp) * 8));
                        }
                        arr[x][y].setRGB(i, j, new Color(rgba[0], rgba[1], rgba[2]).getRGB());
                    }
                }
                setCompNo(savedComp);
            }
        }
        System.out.println("Outs  = " + incrGetOut);
        return arr;
    }
}