package one.empty3.feature ;

public class BrushGradient extends FilterPixM {
    public BrushGradient() {
    
    } 
    
    public double filter(double x, 
          double y) {
        double i, j;
        double a=1.0, b=1.0, c=0.0, d=0.0, e=1.0, g=1.0;
        double f = new Point3D (
           c+b*Math.cos(a*Math.atan(j/i)+d),
           c+b*Math.sin(a*Math.atan (j/i)+d), 
           0.0
        ).mult(e*Math.sqrt(i*i+j*j)
           / (i*i+j*j+1.0) *
           Math.exp(-(i*i+j*j)*g)
        );
        
        return f;
    } 
} 
