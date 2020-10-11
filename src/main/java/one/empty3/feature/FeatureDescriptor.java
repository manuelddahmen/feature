package one.empty3.feature;

import one.empty3.library.Point3D;
import java.util.*;
import java.io.File;

public class FeatureDescriptor extends PixM  {
  private PixM m;
  private PixM pi;
  private List<Point3D> poi;
  public FeatureDescriptor() {
      super(3,3);
  }
  public boolean setDescription(double[][] values) {
    for(int i=0; i<lines; i++)
      for(int j=0; j<columns; j++)
        set(i, j, values[i][j]);
        
      return true;
  }
  public void setPixM(PixM m) {
      this.m = m;
      HarrisToPointInterest h = new HarrisToPointInterest(
          m.columns, m.lines);
      pi = m.applyFilter(h);
      poi = h.getPoi();
  }
  // table line
  // featuredescriptor, image, imagelocation, matchscore* 
  // *e min
  public  List<FeatureImageLocationMatchScore> matchesAll(FeatureDescriptor[] fd, 
            List<Point3D> poi
                                  ){
       List<FeatureImageLocationMatchScore> l = new ArrayList<>();
       return l;
      
  }
             
 
  public double distance(FeatureDescriptor b, PixM set, int i, int j) {
      return set.getColorsRegion(i-columns/2, j-lines/2).distance(this);
  }
  
  
  public double filter(double x, double y) {
    
    
 
     return 0.0;
  }
}
