package one.empty3.feature.kmeans;
/*
 * Programmed by Shephalika Shekhar
 * class containing methods to calculate distance between two points with features 
 * based on distance metric
 */
public class Distance {
    public static float [] r;
	public Distance(float x, float y) {
		float rx = 1f/(float)x;
		float ry = 1f/(float)y;
		r = new float[] {1f, 1f, 1f, 1f, 1f};
	}
	
	public static double eucledianDistance(double[] point1, double[] point2) {
        double sum = 0.0;
	
	if(point1.length<5) {
		System.out.println("kmeans distance error point1"+point1.length);
        	System.exit(-1);
}
	if(point2.length<5) {
		System.out.println("kmeans distance error point2"+point2.length);
        	
        	System.exit(-1);
		
}
/*
	for(int i = 0; i < 2; i++) {
		double comp = (  (point1[i] - point2[i]) * (point1[i] - point2[i]) )*(r[i]*r[i]/100f);
		if(Double.isNaN(comp) 
		  || Double.isInfinite(comp))
			comp = 100000;
		sum += comp;
        }
*/
        for(int i = 2; i < 5; i++) {
            sum += ((point1[i] - point2[i]) * (point1[i] - point2[i]))*r[i]*r[i];
        }
        return Math.sqrt(sum);
    }
    
    public static double manhattanDistance(double point1[], double point2[]){
    	double sum = 0.0;
        for(int i = 0; i < 5; i++) {
            sum += (Math.abs(point1[i] - point2[i]))*r[i]*r[i];
        }
        return sum;
    }
    

}
