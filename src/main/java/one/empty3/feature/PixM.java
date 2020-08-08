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
        PixM c = new PixM(columns, lines);
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                for (int u = -2; u <= 2; u++)
                    for (int v = -2; v <= 2; v++) {
                        c.set(i, j,
                                get(i + u, j + v) - get(i, j) * gaussFilter.gauss(u, v));
                    }
            }
        }
        return c;
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
        GaussFilterPixM gaussFilter = new GaussFilterPixM(5, 2.0);
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
                float value;
                value = (float) Math.max(Math.abs(c.get(i, j)), 0f);
                value = (float) Math.min(value, 1f);

                image.setRGB(i, j,
                        new Color(value, value, value).getRGB());
            }
        }
        return image;
    }

    public int getCompCount() {
        return 4;
    }

}
