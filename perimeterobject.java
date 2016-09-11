import java.util.ArrayList;

public class perimeterobject 
{
  public perimeterobject()
  {
	  for(int i=0;i<arr[0].length;i++)
	  {
		  for(int j=0;j<arr.length;j++)
			  arr[i][j]=0;
	  }
  }
  
  int length; // length of perimeter
  String angularencoding;
  ArrayList<point> coords;
  int[][] arr = new int[100][100];
  
}