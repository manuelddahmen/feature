package one.empty3.feature;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PixM extends M {
    public static final int COMP_RED = 0;
    public static final int COMP_GREEN = 1;
    public static final int COMP_BLUE = 2;
    public static final int COMP_ALPHA = 3;
    public static final int COMP_INTENSITY = 4;

    public PixM(int l, int c) {
        super(l, c);
    }

    public PixM(BufferedImage image) {
        super(image.getWidth(), image.getHeight());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);
                float[] colorComponents = new float[getCompCount()];
                colorComponents = new Color(rgb).getColorComponents(colorComponents);
                for (int com = 0; com < getCompCount(); com++) {

                    setCompNo(com);
                    set(i, j, colorComponents[com]);
                }
            }
        }
    }


    public PixM applyFilter(FilterPixM gaussFilter) {
        PixM c = new PixM(columns, lines);
        double sum;
        for (int comp = 0; comp < getCompCount(); comp++) {

            setCompNo(comp);
            c.setCompNo(comp);
            gaussFilter.setCompNo(comp);


            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    c.set(i, j, 0.0); //???
                    sum = 0.0;
                    for (int u = -gaussFilter.columns / 2; u <= gaussFilter.lines / 2; u++) {
                        for (int v = -gaussFilter.lines / 2; v <= gaussFilter.lines / 2; v++) {
     
                        
                        /*V derivative = derivative(i, j, 2, null);
                        double v1 = derivative.get(0, 0);
                        double v2 = derivative.get(1, 0);
                        c.set(i, j,(v1+v2)
                                * gaussFilter.gauss(u, v, u*v));*/
                            double gauss = gaussFilter.get(u + gaussFilter.columns/2,
                                    v + gaussFilter.lines/2);
                            double value1 = get(i, j);
                            if (!Double.isNaN(value1)) {

                                c.set(i, j, c.get(i, j) + gauss * get(i, j));
                                sum += gauss;
                            }


                        }
                    }
                    c.set(i, j, c.get(i, j) / sum);
                }
            }
        }
        return c;
    }

    public V derivative(int x, int y, int order, V originValue) {
        if (originValue == null) {
            originValue = new V(2, 1);
            originValue.set(0, 0, get(x, y));
            originValue.set(1, 0, get(x, y));
        }
        originValue.set(0, 0, -get(x + 1, y) + 2 * get(x, y) - get(x - 1, y));
        originValue.set(1, 0, -get(x, y + 1) + 2 * get(x, y) - get(x, y - 1));
        if (order > 0) {
            derivative(x, y, order - 1, originValue);
        }

        return originValue;
    }

    public PixM exampleFilter() {
        PixM c;
        GaussFilterPixM gaussFilter = new GaussFilterPixM(10, 3.0);
        gaussFilter.fill();
        c = applyFilter(gaussFilter);
        return c;
    }

    public BufferedImage getImage() {
        /*
        float[] f = new float[getCompCount()];
        Color.white.getColorComponents(f);
        float [] maxColorValue = f;
        double[] maxRgbai = new double[getCompCount()];
        double[] meanRgbai = new double[getCompCount()];

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                for (int comp = 0; comp < getCompCount(); comp++) {
                    setCompNo(comp);

                    if (get(i, j) > maxRgbai[comp]) {
                        maxRgbai[comp] = get(i, j);
                    }
                    meanRgbai[comp] += get(i, j);
                }
            }
        }
        for (int comp = 0; comp < getCompCount(); comp++) {
            setCompNo(comp);
            meanRgbai[comp] /= (lines * columns);
        }
        */

        BufferedImage image = new BufferedImage(columns,
                lines, BufferedImage.TYPE_INT_RGB);


        int savedComp = getCompNo();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                float[] rgba = new float[getCompCount()];
                for (int comp = 0; comp < getCompCount(); comp++) {
                    setCompNo(comp);
                    float value = (float) get(i, j);
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


    private void saveCompNo() {

    }

}
