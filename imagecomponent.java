import java.io.IOException;
import java.util.*;

public class imagecomponent 
{
int colorarray[][];
int binaryarray[][];
int topleftx; //Location of component with reference to top left corner X coordinate
int toplefty;
int height;
int width;
int componentid; // id are given according to sort order from top and then left
int lineno; // approximate location of component in text file if it text
int columnno;
int imgtype; // 0 text, 1 face, 2 table , 3 car etc.
int normalx;
int normaly;
int binaryarrayscaled[][]; //  normalized component to normalx , normaly i.e. 100,100
ArrayList classifyoutput; //  arraylist to store output of classifier in list of Result
                          // one component may be classified into multiple classes with different accuracy

// encoding according to reference papers
String VGSFV;
String HGSFV;
String TGSFV;
String BGSFV;
String HAWFV;
String VAWFV;
String RAWFV;
String LAWFV;

// crossig count features
String RCCFV;
String LCCFV;
String HCCFV;
String VCCFV;

// calculate arraylist of perimeter object

static ArrayList<perimeterobject> perimeterlist;

  public imagecomponent()
  {
	  //angularencoding();
  }
  
  /*public void angularencoding()
  {
	  Perimeter p = new Perimeter();
	    perimeterlist = new ArrayList<perimeterobject>();
	    try 
	    {
			perimeterlist = p.angularencoding();
			for(int i=0;i<perimeterlist.size();i++)
			{
				System.out.println("============= Boundary #"+(i+1)+" ==============");
				System.out.println(perimeterlist.get(i).angularencoding);
			}
			
		} 
	    catch (IOException e) 
		{
			e.printStackTrace();
		}    
  }*/
  
  
  
  public String printattributes() // print , separated component attributes except arrays
  {
	  String s="";
	  return s;
  }
  public static void main(String[] args)
  {
    imagecomponent imagecomponent = new imagecomponent();
    
    
  }
}