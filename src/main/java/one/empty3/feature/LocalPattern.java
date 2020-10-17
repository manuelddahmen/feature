package one.empty3.feature ;

public class LocalPattern extends FilterMatPixM {
   private M3 sr ;
    
    /***
        Ligne 1 searchPattern value = x [0,1] 
           - 1 : ignore 
        Ligne 2 replacePattern value x' [0,1] 
        comp4 => rgb' = rgb *(1-opacity) si x ! =-1
       
    */
    public LocalPattern (M3 searchReplace) {
        super(searchReplace.columnsIn, searchReplace.linesIn) ;
        this.sr = searchReplace ;
          
    } 
    /***
    */
    public PixM filter(
           
           M3 original
           ) {
         copy = new M3(original.columns, original.lines,

                        1, 1);

        for (int i = 0; i < original.columns; i++) {

            for (int j = 0; j < original.lines; j++) {

                    //copy.setCompNo(c);

                    //boolean isMaximum = true;

                double maxLocal = original.getIntensity(i, j, 0, 0);

                    int countOut = 0;

                    int countIn = 0;

                if(maxLocal>=threshold) {

                        

                    for (int ii = -neighbourSize / 2; ii <= neighbourSize / 2; ii++) {

                        for (int ij = neighbourSize / 2; ij <= neighbourSize / 2; ij++) {

                                double v = original.getIntensity(i + ii, j + ij, 0, 0);

                            if (v > maxLocal) {

                                    countIn++;

                            }

                        }

                    }

                }

                if (countIn <= pointsCount) {

                            copy.setCompNo(0);

                            copy.set(i, j, 0, 0, 1);//1 au lieu value

                            copy.setCompNo(1);

                            copy.set(i, j, 0, 0, 1);//1 au lieu value

                            copy.setCompNo(2);

                            copy.set(i, j, 0, 0, 1);//1 au lieu value

                }

            }

        }

        return copy; 
        
        
        
        return mat;
    } 
} 
