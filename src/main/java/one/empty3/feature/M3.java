package one.empty3.feature;

import java.awt.*;
import java.awt.image.BufferedImage;

public class M3 {
    private double[] x;
    private final int columns;
    private final int lines;
    protected final int columnsIn;
    protected final int linesIn;
    protected int compNo;
    private BufferedImage image;
    private int compCount;

    public M3(int columns, int lines, int columnsIn, int linesIn) {
        this.lines = lines;
        this.columns = columns;
        this.linesIn = linesIn;
        this.columnsIn = columnsIn;
        init();
    }

    private void init() {
        x =new double[columns*lines*columnsIn*linesIn*compCount];
    }

    public M3(BufferedImage image, int columnsIn, int linesIn) {
        this(image.getWidth(), image.getHeight(), columnsIn, linesIn);
        setCompCount(columnsIn*linesIn);
    }

    private void setCompCount(int i) {

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

    public M3 filter(FilterMatPixM f, int [] comps) {
        M3 c = this;
        double sum = 0.0;

        for (int comp = 0; comp < linesIn*columnsIn; comp++) {

            setCompNo(comp);
            c.setCompNo(comp);




            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    for(int ii=0; ii<c.columnsIn; ii++)
                        for(int ij=0; ij<c.linesIn; ij++) {
                            setCompNo(ij*columnsIn+ii);
                            f.filter(this, i, j, ii, ij);
                        }
                }

            }
        }
        return this;
    }


    public BufferedImage getImage(int [] compsNo) {

        BufferedImage image = new BufferedImage(columns,
                lines, BufferedImage.TYPE_INT_RGB);


        int savedComp = getCompNo();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                float[] rgba = new float[getCompCount()];
                for( int comp : compsNo ) {
                    setCompNo(comp);
                    float value = (float) get(i, j, comp%columnsIn, comp/linesIn);
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

    private int getCompCount() {
        return compCount;
    }
}
