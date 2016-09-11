import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Classifier
{
  public Classifier()
  {
  }
  //classify method 1 : indicates template
  //method will populate ArrayList classifyoutput of component
  public void classify(imagecomponent imgcomp, int classifymethod,int noofoutput)
  {

  }
  public static void main(String[] args) throws IOException
  {
	  mainimage m = new mainimage();
	  Classifier c = new Classifier();
	  BufferedImage image = ImageIO.read(new File("Test.png"));
	  ArrayList<imagecomponent> comp = m.getComponents(image);
	  for(int i=0;i<comp.size();i++)
	  {
		  System.out.println("======================================================================================================================================================");
		  System.out.println("\n\n\nComponent #"+(i+1));
		  perimeterobject p = m.getboundary(comp.get(i));
		  
		  for(int j=0;j<p.arr[0].length;j++)
		  {
			  for(int k=0;k<p.arr.length;k++)
			  {
				  System.out.print(p.arr[k][j]);
			  }
			  System.out.println("");
		  }
		  
		  ArrayList<ArrayList<Double>> avg = c.avgencoding(p);
	  }


  }

  public ArrayList<ArrayList<Double>> avgencoding(perimeterobject p)
  {
	  int lowrange,highrange;
	  ArrayList<ArrayList<Double>> avgangleslist = new ArrayList<ArrayList<Double>>();
	  ArrayList<Double> centralanglelist = new ArrayList<Double>();
	  ArrayList<Double> leftanglelist = new ArrayList<Double>();
	  ArrayList<Double> rightanglelist = new ArrayList<Double>();
	  
	  perimeterpoint pt = new perimeterpoint();
	  pt.perimeterpointlist = p.coords;
	  lowrange = (int)((2/100.0)*(p.coords.size()));
	  highrange = (int)((15/100.0)*(p.coords.size()));

	  System.out.println("Length of perimeter: "+pt.perimeterpointlist.size()+"\n");
	  
	  avgangleslist = pt.calcavgangle(lowrange, highrange);
	  
	  centralanglelist = avgangleslist.get(0);
	  leftanglelist = avgangleslist.get(1);
	  rightanglelist = avgangleslist.get(2);

	  /*if(centralanglelist.size()==leftanglelist.size()&&leftanglelist.size()==rightanglelist.size())
		  System.out.println("True");*/
	  
	  System.out.println("*********Average angle encoding*********");
	  for(int i=0;i<centralanglelist.size();i++)
	  {
		  System.out.print("For point #"+(i+1)+": x="+pt.perimeterpointlist.get(i).x+",y="+pt.perimeterpointlist.get(i).y+"    ");
		  System.out.print("Average angle: Central="+String.format( "%.2f",centralanglelist.get(i)));
		  System.out.print(", Before="+String.format( "%.2f",leftanglelist.get(i)));
		  System.out.println(", After="+String.format( "%.2f",rightanglelist.get(i)));
	  }

	  return avgangleslist;
  }


}