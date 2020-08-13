package one.empty3.feature;

import java.util.PrimitiveIterator;
import java.util.Random;

public class M {
    public static PrimitiveIterator.OfDouble r = new Random().doubles().iterator();
    public static final Double noValue = r.next();

    protected final int columns;
    protected final int lines;
    final double [] x;
    protected int compNo;
    protected int compCount = 3;
    public M(int c, int l) {
        this.lines = l;
        this.columns = c;
        x = new double[l*c*compCount];
        //System.out.println("Columns=" + columns + "\n Lines = " + lines+ " \n Total size ="+x.length);
    }
    public M(int cl) {
        this(cl, cl);
    }

    public double get(int column, int line) {
        if(column>=0 && column<columns && line>=0 && line<lines) {
            return x[(line * columns + column)+ (getCompNo()*lines*columns)];
        }
        else
            return noValue; // OutOfBound?
    }

    public int getCompNo() {
        return compNo;
    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }

    public void set(int column, int line, double d) {
        if(column>=0 && column<columns && line>=0 && line<lines) {
            x[(line * columns + column) + (getCompNo()*lines*columns)] = d;
        }

    }
    public M tild() {
            M m = new M(lines, columns);
            for(int i=0; i<lines; i++)
                for(int j=0;j< columns; j++)
                    for(int comp = 0; comp < getCompNo(); comp++)
                        m.set(i, j, get(j, i));
            return m;
    }
    public double trace() {
        return tild().dot(this).trace();
    }



    public double diagonalSum() {
        double[] sums = new double[getCompCount()];
        if(!isSquare())
            throw new MatrixFormatException("determinant: not square matrix");
        double sum = 0.0;
        for(int comp = 0; comp < getCompNo(); comp++)
            for(int i = 0; i<lines; i++)
                sums[comp]+=get(i, i);
            return sum;
    }

    public int getCompCount() {
        return compCount;
    }

    private M dot(M m) {
        if(!isSquare() || columns==m.lines)
            throw new MatrixFormatException("determinant: not square matrix");
            M res = new M(m.columns, lines);
            for(int comp = 0; comp<getCompNo(); comp++) {
                res.setCompNo(comp);
                this.setCompNo(comp);
                for (int i = 0; i < m.columns; i++) {
                    for (int j = 0; j < lines; j++) {
                        for (int k = 0; k < lines; k++)
                            res.set(i, j, res.get(i, j) + get(i, k) * res.get(k, j));
                    }
                }
            }
            return res;
    }
/*
    Recursive definition of determinate using expansion by minors.
            */
    double determinant()
    {
        if(!isSquare())
            throw new MatrixFormatException("determinant: not square matrix");
        int i,j,j1,j2;
        double det = 0;
        M m = null;

        if (lines < 1) { /* Error */
            throw new MatrixFormatException("<1 determinant");
        } else if (lines == 1) { /* Shouldn't get used */
            det = get(0, 0);
        } else if (lines== 2) {
            det = get(0, 0) * get(1,1) - get(1,0) * get(0,1);
        } else {
            det = 0;
            for (j1=0;j1<lines;j1++) {
                m = new M(lines-1);
                for (i=1;i<lines;i++) {
                    j2 = 0;
                    for (j=0;j<lines;j++) {
                        if (j == j1)
                            continue;
                        m.set(i-1,j2, get(i, j));
                        j2++;
                    }
                }
                det += Math.pow(-1.0,j1+2.0) * get(0, j1) * m.determinant();
            }
        }
        return(det);
    }

    private boolean isSquare() {
        return lines==columns;
    }

    /*
       Find the cofactor matrix of a square matrix
    */
    public M CoFactor()
    {
        if(!isSquare())
            throw new MatrixFormatException("determinant: not square matrix");
        int n = lines;
        M a = this;
        M b = new M(lines-1);


        int i,j,ii,jj,i1,j1;
        double det;
        M c;

        c = new M(n-1);

        for (j=0;j<n;j++) {
            for (i=0;i<n;i++) {

                /* Form the adjoint a_ij */
                i1 = 0;
                for (ii=0;ii<n;ii++) {
                    if (ii == i)
                        continue;
                    j1 = 0;
                    for (jj=0;jj<n;jj++) {
                        if (jj == j)
                            continue;
                        c.set(i1,j1,get(ii,jj));
                        j1++;
                    }
                    i1++;
                }

                /* Calculate the determinate */
                det = c.determinant();

                /* Fill in the elements of the cofactor */
                b.set(i,j, Math.pow(-1.0,i+j+2.0) * det);
            }
        }
        return b;
    }
    /***
     * pa: mesure de l'erreur dans la fenêtre
     * en W(0, 1, 2, 3)
     * par rapport à W(4, 5, 2, 3)
     * @param w12 x0, y0, w.w, w.h, x1, y1
     * @return E  errors sum of differences. compNo
     */
    public double error(double... w12) {
        double E = 0.0;
        for(double i=0; i <w12[2]; i++)
            for(double j=0; j <w12[3]; j++) {
                E+=
                (get((int)(w12[0]+w12[2]), (int)(w12[1]+w12[3]))
                    - get((int)(w12[4]+w12[2]), (int)(w12[5]+w12[3])));
            }
        return E;
    }

    public void setCompCount(int compCount) {
        this.compCount = compCount;
    }
}
