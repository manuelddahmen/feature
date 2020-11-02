package one.empty3.feature ;


public class DiffE {
    class ColorTranform {
        Circle ref;
        public void rotate(){}
        public double compare(ColorTransform cf){}
        
    }
    class Circle {
         PixM p;
        double x, y, r
            public Circle(m,x,y,r)
            {
        p=m; this.x=x;this.y=y;this.r=r;
        }
        public void rotate(double angle) {
            a+=angle;
        }
        public void variate(double x, double y,
                           double r, double rotate) {
            
        }
        public double get(double r) {
            double pi = 0.0;
            for (a = 0; a<r; a+= 1/r/2/Math.PI)
            
            pi+= p.getIntensity(x+Math.cos(a) y+Math.sin(a));
            return pi;
        }
        
        public int compareTo(Circle c2 ){
            return (int)(c2.r-c1.r);
        }
        
        
    }
    private PixM p1, PixM p2;
    public DiffE(PixM p1, PixM p2){
        this.p1=p1;
        
        this.p2=p2;
    }
    variation xyr
    double[][][] scales = new double [20][20][40];
    int try = 100;
    public double[][] dist(PixM p1) {
       dist [] = new double[20*20] {0.0};
       for(int g=0; g<p1.columns*p1.lines;  g++) {
           for(int h=0; h<p1.columns*p1.lines;  h++) {
       Circle cij = new Circle(p1, p1,colums/20, p1.lines/20, 20);
       Circle ci1 = new Circle(p2, p2,colums/20, p2.lines/20, 20);  
       
        
        for(int i=0; i<cij.r; i++)
           for(int j = 0 ; j< ci1.r; j++)
               dist[i][j]= cij.get(i)-ci1.get(j);
            
        }
    
        dist[i][j]sort
           
    }
}
