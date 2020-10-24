package one.empty3.feature ;
public class DirectMaskFilter {
    PixM m1; PixM m2;
    public DirectMaskFilter(PixM m1, PixM m2)
    {
         
        this.m1 = m1;
        this.m2 = m2;
    }
    /* (M3.p =) = p1x, p1y, 
    
, p2x, p2y, p1, p2,,c1r,c2g ,a1, */
    public void applyOperator(String formula) {
        AlgebricTree tree = new AlgebricTree (formula) ;
        
        tree.setVariable("p1x", i);
        tree.setVariable("p2x", i);
        tree.setVariable("p1y", j);
        tree.setVariable("p2y", j);
        m1.setCompNo(0);
        tree.setVariable("c1r", m1.get(i,j));
        /*...*/
    } 

}
