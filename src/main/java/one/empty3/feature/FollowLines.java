package one.empty3.feature;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class FollowLines {
    private M3 traces;
    private double threshold = 0.1;
    private ArrayList<Line> lines = new ArrayList<>();

    public FollowLines(M3 traces) {
        this.traces = traces;
    }
    // point00, neighbours 01 angle10, dist11

    /***
     * add line segment and join
     * @param ii column
     * @param ij line
     * @return m3 traiter matrice (ii,ij)
     */
    public ArrayList<Line> processPoints(int ii, int ij) {
        M3 points = new M3(traces.columns, traces.lines, 1, 1);
        for (int comp = 0; comp < 4; comp++) {
            for (int i = 0; i < traces.columns; i++) {
                for (int j = 0; j < traces.lines; j++) {
                    double[][] neighbours = neighbours(i, j, ii, ij);
                    for (int m = 0; m < neighbours.length; m++) {
                        for (int n = 0; n < neighbours[m].length; n++) {
                            if (neighbours[m][n] > threshold) {
                                Line l = new Line(i, j, i + m - 1, j + n - 1);
                                lines.add(l);
                            }
                        }

                    }
                }

            }
        }
/*
        final int[] linesRemoved = {1};
        while (linesRemoved[0] > 0) {
            System.out.println("Lines size : " + lines.size() + " ; lines removed" + linesRemoved[0]);
            linesRemoved[0] = 0;
            lines.stream().iterator().forEachRemaining(line0 -> lines.stream().iterator().forEachRemaining(line1 -> {
                if (line0.join(line1)) {
                    lines.remove(line1);
                    linesRemoved[0]++;
                }
            }));
        }*/
        return lines;
    }

    private double[][] neighbours(int i, int j, int ii, int ij) {
        double[][] a = new double[3][3];
        for (int m = 0; m < 3; m++)
            for (int n = 0; n < 3; n++)
                a[m][n] = traces.get(i - 1 + m, j - 1 + n, ii, ij);

        return a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowLines)) return false;
        FollowLines that = (FollowLines) o;
        return Double.compare(that.threshold, threshold) == 0 &&
                Objects.equals(traces, that.traces) &&
                Objects.equals(lines, that.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(traces, threshold, lines);
    }
}
