package one.empty3.feature.kmeans;
/*
 * Programmed by Shephalika Shekhar
 * class containing methods to calculate distance between two points with features 
 * based on distance metric
 */
public class Distance {
    public static float [] r;
	public Distance(float x, float y) {
		this.rx = 1f/(float)x;
		this.ry = 1f/(float)y;
		r = new float[] {rx, ry, 1f, 1f, 1f};
	}
	
	public static double eucledianDistance(double[] point1, double[] point2) {
        double sum = 0.0;
        for(int i = 0; i < 5; i++) {
        	//System.out.println(point1[i]+" "+point2[i]);
            sum += ((point1[i] - point2[i]) * (point1[i] - point2[i]))*r[i]*r[i];
        }
        return Math.sqrt(sum);
    }
    
    public static double manhattanDistance(double point1[], double point2[]){
    	double sum = 0.0;
        for(int i = 2; i < 5; i++) {
            sum += (Math.abs(point1[i] - point2[i]))*r[i]*r[i];
        }
        return sum;
    }
    

}
