/*
DBSCAN(D, eps, MinPts)
   C = 0
   pour chaque point P non visité des données D
      marquer P comme visité
      PtsVoisins = epsilonVoisinage(D, P, eps)
      si tailleDe(PtsVoisins) &lt; MinPts
         marquer P comme BRUIT
      sinon
         C++
         etendreCluster(D, P, PtsVoisins, C, eps, MinPts)
          
etendreCluster(D, P, PtsVoisins, C, eps, MinPts)
   ajouter P au cluster C
   pour chaque point P' de PtsVoisins 
      si P' n'a pas été visité
         marquer P' comme visité
         PtsVoisins' = epsilonVoisinage(D, P', eps)
         si tailleDe(PtsVoisins') >= MinPts
            PtsVoisins = PtsVoisins U PtsVoisins'
      si P' n'est membre d'aucun cluster
         ajouter P' au cluster C
          
epsilonVoisinage(D, P, eps)
 */
package one.empty3.feature;

import one.empty3.io.ProcessFile;

public class DBScan extends ProcessFile {
    public boolean process (File in, File out)
{
        return false;
}
}
