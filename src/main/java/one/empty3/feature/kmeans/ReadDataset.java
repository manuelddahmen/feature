package one.empty3.feature.kmeans;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/*
 * Programmed by Shephalika Shekhar
 * Class to extract features from file
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.channels.ReadPendingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ReadDataset {
	
	protected static List<double[]> features=new ArrayList<>();
	protected static int numberOfFeatures;
	
	public List<double[]> getFeatures()
	{
		return features;
	}
	
	void read(String s) throws NumberFormatException, IOException {
		
		File file=new File(s);
		
	        try {
		BufferedReader readFile=new BufferedReader(new FileReader(file));
		String line;
		while((line=readFile.readLine()) != null)
			{
			
			 String[] split = line.split(" ");
            		 double[] feature = new double[5];
             		int i=0 ;
			
            		for (i = 0; i < split.length; i++)
               			 feature[i] = Double.parseDouble(split[i]);
			numberOfFeatures = split.length;
			
			features.add(feature);
			
			}
			
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}


    void display() {
	Iterator<double[]> itr=features.iterator();
	while(itr.hasNext())
	{ 
		double db[]=itr.next();
		for(int i=0; i<db.length;i++)
		{
			System.out.print(db[i]+" ");
		}	
	
	}
	
     }
}
