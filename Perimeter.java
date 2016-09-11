import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;


public class Perimeter {
	
	BufferedImage imgop; 
	static int lengtharm;
	int temp[][] = new int[100][100];
	point coord = new point();
	
	
	/*public static void main(String[] args) throws IOException
	{
		Perimeter p = new Perimeter();
		int[][] boundaries = p.peri();
		ArrayList<perimeterobject> sep = p.separateboundaries(boundaries);
		p.sortclockwise(sep);
		p.angleencoding(sep);
		
	}*/
	
	public ArrayList<perimeterobject> angularencoding(imagecomponent im, int length) throws IOException
	{
		lengtharm=length;
		ArrayList<perimeterobject> sep = separateboundaries(peri(im));
		sortclockwise(sep);
		ArrayList<perimeterobject> p = angleencoding(sep);
		return p;
	}
	
	public perimeterobject getperimeter(imagecomponent im) throws IOException
	{
		ArrayList<perimeterobject> sep = separateboundaries(peri(im));
		sortclockwise(sep);
		return sep.get(0);
	}
	
	
	
	public int[][] peri(imagecomponent im) throws IOException
	{
		
		ArrayList<point> co = new ArrayList<point>();
		point c = new point();
		mainimage m = new mainimage();
		int[][] scaled = new int[100][100];
		
		//imgop = ImageIO.read(new File("B.png"));
		//scaled = m.convImageToArray(imgop);
		
		scaled = im.binaryarrayscaled;
		
		int boundary[][] = new int[100][100];
		
		for(int i=0;i<100;i++)
		{
			for(int j=0;j<100;j++)
			{
				boundary[i][j]=0;
				temp[i][j]=0;
			}
		}
		
		
		int current;
	
		int i,j;
		for(i=0;i<100;i++)
		{
			current=0;
			for(j=0;j<99;j++)
			{
				if(scaled[i][j]!=current)
				{
					boundary[i][j]=1;
					current = scaled[i][j];
				}
			}
			if(scaled[i][j]==1 || scaled[i][j]!=current)
				boundary[i][j]=1;
		}
		
	
		for(j=0;j<100;j++)
		{
			current=0;
			for(i=0;i<99;i++)
			{
				if(scaled[i][j]!=current)
				{
					boundary[i][j]=1;
					current = scaled[i][j];
				}
			}
			if(scaled[i][j]==1 || scaled[i][j]!=current)
				boundary[i][j]=1;
		}
		
		//printmatrix(boundary);
		
		return boundary;
		
	}
	
	public ArrayList<perimeterobject> separateboundaries(int[][] bound)
	{
		//ArrayList<point> singlebound = new ArrayList<point>();
		ArrayList<perimeterobject> allbound = new ArrayList<perimeterobject>();
		int c=1;
		for(int i=0;i<bound.length;i++)
		{
			for(int j=0;j<bound[0].length;j++)
			{
				if(bound[i][j]==1)
				{
					ArrayList<point> bdry = new ArrayList<point>();
					bdry = traverseboundary(i,j,bound,new ArrayList<point>());
					perimeterobject p = new perimeterobject();
					p.coords = bdry;
					allbound.add(p);
					
					for(int k=0;k<bound.length;k++)
					{
						for(int l=0;l<bound[0].length;l++)
						{
							if(temp[k][l]==1)
								bound[k][l]=0;
							temp[k][l]=0;
						}
					}
					
				}
			}
		}
		
		return allbound;
	}
	
	public ArrayList<point> traverseboundary(int x, int y, int[][]b,ArrayList<point> bdry1)
	{		
		if(x>=0 && x<100 && y>=0 && y<100 && b[x][y]==1)
		{
			temp[x][y]=1;
			point c = new point();
			c.x=y;
			c.y=x;
			bdry1.add(c);
			b[x][y]=0;
			traverseboundary(x+1,y,b,bdry1);
			traverseboundary(x-1,y,b,bdry1);
			traverseboundary(x,y+1,b,bdry1);
			traverseboundary(x,y-1,b,bdry1);
			traverseboundary(x+1,y-1,b,bdry1);
			traverseboundary(x-1,y-1,b,bdry1);
			traverseboundary(x+1,y+1,b,bdry1);
			traverseboundary(x-1,y+1,b,bdry1);
		}
		return bdry1;
	}
	
	public void printmatrix(int[][] m)
	{
		for(int i=0; i<m.length; i++)
		{
			System.out.println("");
	        for(int j=0; j<m[0].length; j++)
	        {
	        	System.out.print(m[j][i]);	//j is rows, i is columns
	        }
		}
	}
	
	public void sortclockwise(ArrayList<perimeterobject> list)
	{
		ArrayList<perimeterobject> listofboundaries = new ArrayList<perimeterobject>();
		for(int i=0;i<list.size();i++)
			listofboundaries.add(list.get(i));
		int[][] boundarypixels=new int[100][100];
		int x=0,y=0;
		
		for(int n=0;n<listofboundaries.size();n++)
		{
			ArrayList<point> bound = new ArrayList<point>();
			for(int i=0;i<listofboundaries.get(n).coords.size();i++)
				bound.add(listofboundaries.get(n).coords.get(i));
			
			for(int i=0;i<boundarypixels.length;i++)
			{
				for(int j=0;j<boundarypixels[0].length;j++)
				{
					boundarypixels[i][j]=0;
				}
			}
			
			for(int p=0;p<bound.size();p++)
			{
				x = bound.get(p).x;
				y = bound.get(p).y;
				boundarypixels[x][y]=1;	//actual image matrix, not array
			}
			
			int originalsize = bound.size();
			bound.clear();
			
			/*============Code for detecting the next pixel===============*/
			
			
			//Code for detecting starting pixel
			for(int i=0;i<boundarypixels[0].length;i++)
			{
				for(int j=0;j<boundarypixels.length;j++)
				{
					if(boundarypixels[i][j]==1)
					{
						x = i;
						y = j;
						break;
					}	
				}
				if(x!=0 || y!=0)
					break;
			}
			
			
			for(int i=0;i<boundarypixels[0].length;i++)
			{
				for(int j=0;j<boundarypixels.length;j++)
				{
					if(boundarypixels[x][y]==1)
					{
						bound.add(new point(x,y));	//actual image
						boundarypixels[x][y]=2;
						
						if((x+1)<100 && boundarypixels[x+1][y]==1)
						{
							x = x+1;
						}
						else if((x-1)>=0 && boundarypixels[x-1][y]==1)
						{
							x = x-1;
						}
						else if((y-1)>=0 && boundarypixels[x][y-1]==1)
						{
							y = y-1;
						}
						else if((x+1)<100 && (y-1)>=0 && boundarypixels[x+1][y-1]==1)
						{
							y = y-1;
							x = x+1;
						}
						else if((y-1)>=0 && (x-1)>=0 && boundarypixels[x-1][y-1]==1)
						{
							y = y-1;
							x = x-1;
						}
						else if((y+1)<100 && boundarypixels[x][y+1]==1)
						{
							y = y+1;
						}
						else if((x+1)<100 && (y+1)<100 && boundarypixels[x+1][y+1]==1)
						{
							y = y+1;
							x = x+1;
						}
						else if((y+1)<100 && (x-1)>=0 && boundarypixels[x-1][y+1]==1)
						{
							y = y+1;
							x = x-1;
						}
					}
				}
				
				
			}
			perimeterobject p = new perimeterobject();
			//Collections.reverse(bound);
			p.coords = bound;
			p.length = bound.size();
			for(int i=0;i<bound.size();i++)
			{
				p.arr[bound.get(i).x][bound.get(i).y] = 1;
				
			}
			list.set(n, p);
			System.out.println("\n\n");
		}		
	}
	
	public ArrayList<perimeterobject> angleencoding(ArrayList<perimeterobject> list)
	{
		//ArrayList<ArrayList<Double>> totalencoding = new ArrayList<ArrayList<Double>>();
		String totalencodingg="";
		int x,y,x1,y1,x2,y2;
		double angle=0;
		double slope1=0,slope2=0;
		
		for(int i=0;i<list.size();i++)
		{
			totalencodingg="";
			ArrayList<point> boundary = new ArrayList<point>();
			for(int k=0;k<list.get(i).coords.size();k++)
				boundary.add(list.get(i).coords.get(k));
			
			for(int j=0;j<boundary.size();j++)
			{
				x = boundary.get(j).x;
				y = boundary.get(j).y;
				
				if((j+1)==boundary.size()) //if current pixel is the last one
				{
					x1 = boundary.get(j-lengtharm).x;
					y1 = boundary.get(j-lengtharm).y;
					x2 = boundary.get(1).x;
					y2 = boundary.get(1).y;
				}
				else if((j+lengtharm)==boundary.size()) //if current pixel is second last
				{
					x1 = boundary.get(j-lengtharm).x;
					y1 = boundary.get(j-lengtharm).y;
					x2 = boundary.get(0).x;
					y2 = boundary.get(0).y;
				}
				else if(j==0) //if current pixel is the first one
				{
					x1 = boundary.get(boundary.size()-lengtharm).x;
					y1 = boundary.get(boundary.size()-lengtharm).y;
					x2 = boundary.get(j+lengtharm).x;
					y2 = boundary.get(j+lengtharm).y;
				}
				else if(j==1) //if current pixel is second one
				{
					x1 = boundary.get(boundary.size()-1).x;
					y1 = boundary.get(boundary.size()-1).y;
					x2 = boundary.get(j+lengtharm).x;
					y2 = boundary.get(j+lengtharm).y;
				}
				else
				{
					x1 = boundary.get(j-lengtharm).x;
					y1 = boundary.get(j-lengtharm).y;
					x2 = boundary.get(j+lengtharm).x;
					y2 = boundary.get(j+lengtharm).y;
				}
					
				
				double angle1 = Math.atan2(((-y2)-(-y)), (x2-x));
				//if(angle1<0 && (-y)<(-y1))
					//angle1 = angle1+Math.PI;
				if((-y2)>(-y))
					angle1 = -1*angle1;
				double angle2 = Math.atan2(((-y)-(-y1)), (x-x1));
				//angle = (180/Math.PI)*(angle1-angle2));
				if((-y1)>(-y))
					angle2 = -1*angle2;
				
				angle = (180/Math.PI)*(Math.abs((angle1)-(angle2)));
				
				//System.out.println("\n\nCurrent: "+x+","+y);
				//System.out.println("Previous: "+x1+","+y1);
				//System.out.println("Next: "+x2+","+y2);
				//System.out.println("Angle with next: "+(180/Math.PI)*angle1);
				//System.out.println("Angle with previous: "+(180/Math.PI)*angle2);
				//System.out.println("Angle: "+angle);
				
				totalencodingg = totalencodingg + angle + ",";
				//angles.add(angle);
			}
			//System.out.println(totalencodingg);
			list.get(i).angularencoding = totalencodingg;
			//totalencoding.add(angles); 
			//angles.clear();
			
		}
		return list;
		//return totalencoding;
	}
}
