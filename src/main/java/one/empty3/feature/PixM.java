package one.empty3.feature;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.Arrays;

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

public static PixM getPixM(BufferedImage image, double maxRes) {
        System.out.println("pixm resampling init"+image.getWidth()+" "+image.getHeight()+" - " + maxRes);
    
        double f = 1.0/Math.max(image.getWidth(), image.getHeight ())*maxRes;
        
        double columns2 = 1.0 * image.getWidth()* f;
        double lines2 = 1.0 * image.getHeight() * f;
        double cli2 = 1.0 * maxRes;
    System.out.println("pixm resampling in it ("+columns2+"- " + lines2+") " );
        PixM pixM = new PixM((int) (columns2), ((int) lines2));
        
    
       
        
            
            for (int i = 0; i < (int) columns2; i++) {
                for (int j = 0; j < (int) lines2; j++) {


                   int rgb = image.getRGB(
(int) (i / columns2 / f) 
      
      

, (int) (j / lines2/f ));
                float[] colorComponents = new float[pixM.getCompCount()];
                colorComponents = new Color(rgb).getColorComponents(colorComponents);
                for (int com = 0; com < pixM.getCompCount(); com++) {
                    pixM. setCompNo(com);
                    pixM. set(i, j, colorComponents[com]);
                
                    //double m = mean((int) (i * div), (int) (j * div), (int) (cli2 * div),
                    //        (int) (cli2 * div));
                    //pixM.set(i, j, );
                }
        }
         
}    
        return pixM;


    }

    public PixM applyFilter(FilterPixM filter) {
        PixM c = new PixM(columns, lines);
        double sum;
        for (int comp = 0; comp < getCompCount(); comp++) {

            setCompNo(comp);
            c.setCompNo(comp);
            filter.setCompNo(comp);


            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    c.set(i, j, 0.0); //???
                    sum = 0.0;
                    for (int u = -filter.columns / 2; u <= filter.lines / 2; u++) {
                        for (int v = -filter.lines / 2; v <= filter.lines / 2; v++) {
     
                        
                        /*V derivative = derivative(i, j, 2, null);
                        double v1 = derivative.get(0, 0);
                        double v2 = derivative.get(1, 0);
                        c.set(i, j,(v1+v2)
                                * filter.filterUVvalue(u, v, u*v));*/
                            double filterUVvalue = filter.get(u + filter.columns / 2,
                                    v + filter.lines / 2);
                            double vAtUv = get(i + u, j + v);
                            if (!(vAtUv == noValue)) {

                                c.set(i, j, c.get(i, j) + filterUVvalue * vAtUv);
                                sum += filterUVvalue;

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

    public BufferedImage getImage() {

        float[] f = new float[getCompCount()];

        BufferedImage image = new BufferedImage(columns,
                lines, BufferedImage.TYPE_INT_RGB);


        float[] rgba = new float[getCompCount()];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                for (int comp = 0; comp < 3; comp++) {
                    setCompNo(comp);
                    float value = (float) (get(i, j));
                    //TODO problems
                    //value = Math.max(value, 0f);
                    //value = Math.min(value, 1f);

                    rgba[comp] = value;
                }
                image.setRGB(i, j, new Color(rgba[0], rgba[1], rgba[2]).getRGB());
            }
        }
        return image;

    }


    public PixM normalize(final double min, final double max) {

        double[] maxRgbai = new double[compCount];
        double[] meanRgbai = new double[compCount];
        double[] minRgbai = new double[compCount];
        double minA = 0.0;
        double maxA = 1.0;
        if (min != -1 || max != -1) {
            minA = min;
            maxA = max;
        }
        for (int i = 0; i < getCompCount(); i++) {
            maxRgbai[i] = maxA;
            minRgbai[i] = minA;
            meanRgbai[i] = 0.0;
        }
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                for (int comp = 0; comp < getCompCount(); comp++) {
                    setCompNo(comp);
                    double valueAt = get(i, j);
                    if (!Double.isNaN(valueAt) || !Double.isInfinite(valueAt)) {
                        if (valueAt > maxRgbai[comp]) {
                            maxRgbai[comp] = valueAt;
                        }
                        if (valueAt < minRgbai[comp]) {
                            minRgbai[comp] = valueAt;
                        }
                    } else {
                        valueAt = 0.0;
                        set(i, j, valueAt);
                    }
                    meanRgbai[comp] += valueAt / (lines * columns);
                }
            }
        }
        PixM image = new PixM(columns, lines);
        for (int i = 0; i < image.columns; i++) {
            for (int j = 0; j < image.lines; j++) {
                for (int comp = 0; comp < getCompCount(); comp++) {
                    setCompNo(comp);
                    image.setCompNo(compNo);
                    float value;
                    value = (float) ((get(i, j) - minRgbai[comp]) / (maxRgbai[comp] - minRgbai[comp]));
                    //value = Math.max(value, 0f);
                    //value = Math.min(value, 1f);
                    //if (comp == 3) value = 1f;

                    image.set(i, j, value);
                }
            }
        }
        return image;
    }

    public PixM subSampling(double div) {
        double columns2 = 1.0 * columns / div;
        double lines2 = 1.0 * lines / div;
        double cli2 = 1.0 * 1 / div;
        PixM pixM = new PixM((int) (columns2), ((int) lines2));
        for (int c = 0; c < getCompCount(); c++) {
            setCompNo(c);
            pixM.setCompNo(c);
            for (int i = 0; i < (int) columns2; i++)
                for (int j = 0; j < (int) lines2; j++) {
                    double m = mean((int) (i * div), (int) (j * div), (int) (cli2 * div),
                            (int) (cli2 * div));
                    pixM.set(i, j, m);
                }
        }
        return pixM;
    }

    public double mean(int i, int j, int w, int h) {
        double m = 0.0;
        int p = 0;
        for (int a = i; a < i + w; a++)
            for (int b = j; b < j + h; b++) {
                m += get(a, b);
                p++;
            }
        return m / p;
    }
    
    
    
    public PixM copy() {
        double div = 1.0;
        double columns2 = 1.0 * columns / div;
        double lines2 = 1.0 * lines / div;
        double cli2 = 1.0 * 1 / div;
        PixM pixM = new PixM((int) (columns2), ((int) lines2));
        for (int c = 0; c < getCompCount(); c++) {
            setCompNo(c);
            pixM.setCompNo(c);
            for (int i = 0; i < (int) columns2; i++)
                for (int j = 0; j < (int) lines2; j++) {
                    double m = mean((int) (i * div), (int) (j * div), (int) (cli2 * div),
                            (int) (cli2 * div));
                    pixM.set(i, j, m);
                }
        }
        return pixM;
    }
    public void colorsRegion(int x, int y, int w, int h, double[] comps) {
         for(int i=x; i<x+w; i++)
              for(int j=y; j<y+h; j++)
                   for(int c=0; c<comps.length; c++) {
                        setCompNo(c);
                        set(i, j, comps[c]);
                   }
    }
    
    public PixM getColorsRegion(int x, int y, int w, int h, int sizeX, int sizeY) {
        PixM subimage = new PixM(sizeX, sizeY);
         for(int i=x; i<x+w; i++)
              for(int j=y; j<y+h; j++)
                   for(int c=0; c<comps.length; c++) {
                        setCompNo(c);
                        subimage.setCompNo(c);
                        double v = get(i, j);
                            subimage.set(1.0*(x+w-i)/w*subimage.columns, 1.0*(y+h-j)/h*subimage.lines, v);
                        set(i, j, v);
                   }
    }
    
    public void colorsRegion(int x, int y, int w, int h, PixM subimage) {
         for(int i=x; i<x+w; i++)
              for(int j=y; j<y+h; j++)
                   for(int c=0; c<comps.length; c++) {
                        setCompNo(c);
                        subimage.setCompNo(c);
                        double v = subimage.get(1.0*(x+w-i)/w*subimage.columns, 1.0*(y+h-j)/h*subimage.lines);
                        set(i, j, v);
                   }
    }
    
    public boolean equals(Object compare) {
        if(compare instanceof PixM)
            if(
                Arrays.equals
               (
                   ((PixM) compare).x,
                   x
               )
              )
                return true;
        return false;

   }
}
