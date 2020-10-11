package one.empty3.feature;

public class FeatureDescriptor extends FilterPixM {
  private PixM m;
  public FeatureDescriptor() {
      super(5,5);
  }
  public boolean setDescription(double[][] values) {
      return true;
  }
  public void setPixM(PixM m) {
      this.m = m;
  }
  public double distance(FeatureDescriptor B,
                         
                         File [] set) {
      return 1.0;
  }
  
  public double distance(File set) {
      return 1.0;
  }
  public double filter(double x, double y) {
    
  }
}
