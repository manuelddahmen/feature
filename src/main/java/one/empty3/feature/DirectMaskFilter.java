package one.empty3.feature ;

import one.empty3.library.core.raytracer.tree.*;

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
        
        
        
        for(int i=0;m1.columns; i++)
            for(int j=0; j<m1.lines; j++){
        tree.setVariable("p1x", i);
        tree.setVariable("p2x", i);
        tree.setVariable("p1y", j);
        tree.setVariable("p2y", j);
        
        m1.setCompNo(0);
        tree.setVariable("c1r", m1.get(i,j));
        m1.setCompNo(1);
        tree.setVariable("c1g", m1.get(i,j));
        m1.setCompNo(2);
        tree.setVariable("c1b", m1.get(i,j));
        m2.setCompNo(0);
        tree.setVariable("c2r", m2.get(i,j));
        m2.setCompNo(1);
        tree.setVariable("c2g", m2.get(i,j));
        m2.setCompNo(2);
        tree.setVariable("c2b", m2.get(i,j));
        m1.setCompNo(3);
        tree.setVariable("c1a", m1.get(i,j));
        m2.setCompNo(3);
        tree.setVariable("c2a", m2.get(i,j));
     
                tree.construct();
                
                double value = (double)(Double)(tree.eval());
                
                
         }
    } 

}
