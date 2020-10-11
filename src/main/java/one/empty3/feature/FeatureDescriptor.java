package one.empty3.feature;

import one.empty3.library.Point3D;
import java.util.*;
import java.io.File;

public class FeatureDescriptor extends PixM  {
  private PixM m;
  private PixM pi;
  private List<Point3D> poi;
  public FeatureDescriptor() {
      super(5,5);
  }
  public boolean setDescription(double[][] values) {
      return true;
  }
  public void setPixM(PixM m) {
      this.m = m;
      HarrisToPointInterest h = new HarrisToPointInterest(
      m.columns, m.lines);
      pi = m.applyFilter(h);
      poi = h.getPoi();
  }
  
  
  public double distance(FeatureDescriptor b, File [] set) {
      return 1.0;
  }
  public double filter(double x, double y) {
     
  }
}
