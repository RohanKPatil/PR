import java.awt.Color;
import java.awt.Image;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class mainimage 
{
String imagename; // may be filename
int height;
int width;
int colorarray[][]; // two dimensional array representing colors of pixels
int binaryarray[][]; // two dimensional array representing binary based on threshold
BufferedImage bimage;
ArrayList imagecomponents;
ArrayList<Integer> xcoord,ycoord;
int visited[][]=new int[1000][1000];
int f=0;

  public mainimage()
  {
  }
  public int[][] convImageToArray(BufferedImage bi)
  {
	  int a[][] = new int[1000][1000];
		for(int i=0;i<bi.getHeight();i++)
		{
			for(int j=0;j<bi.getWidth();j++)
			{
				if(bi.getRGB(j, i)==-1)
					a[i][j]=0;
				else
					a[i][j]=1;	
			}
		}
		return a;
  }
  public BufferedImage convFileToImage(String filename) throws IOException
  {
	  return(ImageIO.read(new File(filename)));
  }
  public int[][] convFileToArray(String filename) throws IOException
  {
	  return(convImageToArray(convFileToImage(filename)));
  }
  //public int[][] convColorToBinary(int [][] arr,double threshold);
  //public void initialize (String fname,double threshold); // take filename as input and populate remaining attributes
  //public BufferedImage convArraytoImage (int [][] arr);
  
  public void imagetofile(BufferedImage img,String filename) throws IOException
  {
	  File output1 = new File(filename);
	  ImageIO.write(img, "png", output1);
  }
  
  public ArrayList<imagecomponent> getComponents(BufferedImage image) // get arraylist of image components
  {
	  ArrayList<imagecomponent> allcomp = new ArrayList<imagecomponent>();
	  ProcessComponent pc = new ProcessComponent();
	  try 
	  {
		  allcomp = pc.process(image);		
		
	  }
	  catch (IOException e) 
	  {
		e.printStackTrace();
	  }
	  System.out.println("Total components: "+allcomp.size());
	  return allcomp;
	  
  }

	   
  public BufferedImage scaleimage (BufferedImage img,double scalehoriztal,double vertical)
  {
		Image i = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		BufferedImage buffered = new BufferedImage(50, 50, BufferedImage.TYPE_BYTE_BINARY);
		buffered.getGraphics().drawImage(i, 0, 0 , null);
		return buffered;
  }
  
  // Add each calc method for calculating varios attributes of image component
  

  //method for calculating list of  perimeter object
  public ArrayList<perimeterobject> calcperimeterlist(imagecomponent imgcomp,int lengtharm)
  {
	  	Perimeter p = new Perimeter();
	  	ArrayList<perimeterobject> allperimeter = new ArrayList<perimeterobject>();
	    try 
	    {
			allperimeter = p.angularencoding(imgcomp,lengtharm);
			imgcomp.perimeterlist = allperimeter;
			for(int i=0;i<allperimeter.size();i++)
			{
				System.out.println("============= Boundary #"+(i+1)+" ==============");
				System.out.println("Length of perimeter: "+allperimeter.get(i).length);
				for(int x=0;x<allperimeter.get(i).arr[0].length;x++)
				{
					System.out.println("");
					for(int y=0;y<allperimeter.get(i).arr[0].length;y++)
					{
						System.out.print(allperimeter.get(i).arr[y][x]);
					}
				}
					
				System.out.println();
				System.out.println("Angle encoding:\n"+allperimeter.get(i).angularencoding);
			}
			
		} 
	    catch (IOException e) 
		{
			e.printStackTrace();
		}    
	    return allperimeter;
  }
  
  public perimeterobject getboundary(imagecomponent imgcomp)
  {
	  	Perimeter p = new Perimeter();
	  	perimeterobject perimeter = new perimeterobject();
	    try 
	    {
			perimeter = p.getperimeter(imgcomp);
		} 
	    catch (IOException e) 
		{
			e.printStackTrace();
		}    
	    return perimeter;
  }
  
  
  //following methods will return arraylist of arraylist of imagecomponents
  //public ArrayList calccluster_height(ArrayList componentlist, int noofclusters);
  //public ArrayList calccluster_line(ArrayList componentlist, int noofclusters);
  //public ArrayList calccluster_position(ArrayList componentlist, int noofclusters);
  
  
}