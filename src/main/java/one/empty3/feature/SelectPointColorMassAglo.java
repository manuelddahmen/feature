package one.empty3.feature;

import one.empty3.library.Lumiere;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SelectPointColorMassAglo extends FilterPixM {
    private double[] chosenColor;

    public SelectPointColorMassAglo(BufferedImage image) {
        super(image);
    }

    public double [] getColor(int x, int y) {
        double [] cs = new double[3];
        for(int c=0; c<3; c++) {
            setCompNo(c);
            cs[c] = get(x, y);
        }
        return cs;
    }


    public double averageCountMeanOf(int x, int y, int width, int height, double threshold) {
        double count = 0;
        double [] rgb = getColor(x, y);
        for(int i=x-width/2; i<x+width/2; x++) {
            for(int j=y-height/2; j<y+height/2; j++) {
                double[] color = getColor(i, j);
                int col = 0;
                for(int c=0; c<3; c++) {
                    if(color[c]>=rgb[c]-threshold && color[c]<=rgb[c]) {
                        col ++;
                    }
                    if(col==3) {
                        count++;

                    }
                }

            }

        }
        return count/width/height;
    }

    @Override
    public double filter(double x, double y) {
        return averageCountMeanOf((int)x,(int)y, (int)(getColumns()/10.0), (int)(getLines()/10.0), 0.2);
    }

    public Color getChosenColor() {
        return new Color((float) (chosenColor[0]), (float) (chosenColor[1]), (float) (chosenColor[2]));
    }

    public void setChosenColor(Color chosenColor) {
        this.chosenColor = new double[] {chosenColor.getRed(), chosenColor.getGreen(), chosenColor.getBlue()};
    }
}
