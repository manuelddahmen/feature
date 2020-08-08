package one.empty3.feature;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PixM extends M {
    public static final int COMP_RED = 0;
    public static final int COMP_GREEN = 1;
    public static final int COMP_BLUE = 2;
    public static final int COMP_ALPHA = 3;
    public static final int COMP_INTENSITY = 4;

    public int getCompNo() {
        return compNo;
    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }

    private int compNo;

    public PixM(int l, int c) {
        super(l, c);
    }

    public PixM(BufferedImage image, int compNo) {
        super(image.getWidth(), image.getHeight());
        this.compNo = compNo;
        float[] comp = new float[4];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);
                float[] colorComponents = new Color(rgb).getColorComponents(comp);
                set(i, j, colorComponents[compNo]);
            }
        }
    }


    public PixM filter(GaussFilterPixM gaussFilter) {
        double sigmaR = gaussFilter.sigma;
        PixM c = new PixM(columns, lines);
        double sum;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                sum = 0;
                for (int u = -gaussFilter.lines/2; u <= gaussFilter.lines/2; u++)
                    for (int v = -gaussFilter.lines/2; v <= gaussFilter.lines/2; v++) {

                        /*V derivative = derivative(i, j, 2, null);
                        double v1 = derivative.get(0, 0);
                        double v2 = derivative.get(1, 0);
                        c.set(i, j,(v1+v2)
                                * gaussFilter.gauss(u, v, u*v));*/
                        double gauss = -0.5 * (Math.sqrt(u * u + v * v));
                        c.set(j, i, c.get(j,i)+gaussFilter.gauss(u, v, 0.0)
                                * Math.exp(gauss /sigmaR)
                                * (get(j+u, i+u)*get(j,i)));
                        sum += gaussFilter.gauss(u, v, 0.0)
                                * Math.exp(gauss /sigmaR);
                                //* (get(j+u, i+u)*c.get(j,i));




                    }
                c.set(j, i, c.get(j, i)/sum);
            }
        }
        return c;
    }

    public V derivative(int x, int y, int order, V originValue) {
        if(originValue == null) {
            originValue = new V(2, 1);
            originValue.set(0, 0,  get(x,y));
            originValue.set(1, 0,  get(x,y));
        }
        originValue.set(0, 0, -get(x+1, y)+2*get(x,y)-get(x-1,y));
        originValue.set(1, 0, -get(x, y+1)+2*get(x,y)-get(x,y-1));
        if(order>0) {
            derivative(x, y, order - 1, originValue);
        }

        return originValue;
    }


    public BufferedImage getGrayScale() {
        float[] f = new float[4];
        Color.white.getColorComponents(f);
        float maxColorValue = f[compNo];
        double maxRgbai = 0.0;
        double meanRgbai = 0.0;
        BufferedImage image = new BufferedImage(columns,
                lines, BufferedImage.TYPE_INT_ARGB);
        PixM c;
        GaussFilterPixM gaussFilter = new GaussFilterPixM(1, 1.2);
        c = filter(gaussFilter);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                maxRgbai = Math.max(get(i, j), meanRgbai);
                meanRgbai += c.get(i, j);
            }
        }
        meanRgbai /= (lines*columns);


        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                double value = Math.abs(c.get(i, j)/maxRgbai);
                value =  Math.max(value, 0f);
                value =  Math.min(value, 1f);

                image.setRGB(i, j,
                        new Color((float)value, (float)value, (float)value).getRGB());
            }
        }
        return image;
    }

    public int getCompCount() {
        return 4;
    }

}
