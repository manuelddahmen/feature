
package one.empty3.feature;

import java.io.File;
import one.empty3.io.ProcessFile;
import java.util.ArrayList;

public class ExtremaProcess extends ProcessFile {
    private boolean setMin = true;
    private final int pointsCount;
    private final int neighbourSize;
    protected double sub[];
    private double threshold = 0.5;

    public int getCompNo() {
        return compNo;
    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }

    private int compNo;

    public ExtremaProcess(int neighbourSize, int pointsCount) {
        this.neighbourSize = 8;//neighbourSize;
        this.pointsCount = 1; //pointsCount;
        //sub = new double[4*lines*columns];
    }
    public ExtremaProcess() {
        
        private int compNo;

         
         
         }
    

    @Override
    public boolean process(File in, File out){
        
        
        LocalExtrema le =  new LocalExtrema(pix.getColumns(), pix.getLines(), 3, 0) {
   
            
            M3 m = le.filter(new M3(pix, 1, 1));
            
    }
    
}
