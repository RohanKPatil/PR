import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

public class ProcessComponent {
	static BufferedImage image;
	//Color color;
	static int pixels[][]=new int[1000][1000];
	int temp[][]=new int[1000][1000];
	int sample[][]=new int[100][100];
	int startpixel[]={-1,-1};
	static int h;
	static int w;
	static int xmin,xmax,ymin,ymax;
	int visited[][]=new int[1000][2];
	ArrayList<Integer> xcoord = new ArrayList<Integer>();
	ArrayList<Integer> ycoord = new ArrayList<Integer>();
	static int maxy;
	static int f=0;
	
	/*public static void main(String[] args) throws IOException
	{
		ProcessComponent m = new ProcessComponent();
		File f = new File("Cluster_2.png");
		image = ImageIO.read(f);
		h = image.getHeight();
		w = image.getWidth();
		pixels = m.jpgtomatrix(image);
		m.componentseparation();
	}*/
	
	public ArrayList<imagecomponent> process(BufferedImage im) throws IOException
	{
		image = im;
		h = image.getHeight();
		w = image.getWidth();
		pixels = jpgtomatrix(image);
		
		ArrayList<imagecomponent> allcomp = new ArrayList<imagecomponent>();
		allcomp = componentseparation();
		return allcomp;
	}
	
	public int[][] jpgtomatrix(BufferedImage im) throws IOException
	{		
		int a[][] = new int[1000][1000];
		for(int i=0;i<im.getHeight();i++)
		{
			for(int j=0;j<im.getWidth();j++)
			{
				if(im.getRGB(j, i)==-1)
					a[i][j]=0;
				else
					a[i][j]=1;	
			}
		}
		
		for(int i=0;i<h;i++)
		{
			for(int j=0;j<w;j++)
				temp[i][j]=0;
		}
		
		return a;
	}
	
	
	public ArrayList<imagecomponent> componentseparation() throws IOException
	{
		ArrayList<imagecomponent> comp = new ArrayList<imagecomponent>(); 
		ymax=90;
		maxy=90;
		while(true)
		{
			imagecomponent ic = new imagecomponent();
			startpixel(xmax+1,ymin);
			if(f==1)
				break;
			//System.out.println(xmin+","+ymin);
			flood(xmin,ymin);
			xmin = xcoord.get(0);
			xmax = xcoord.get((xcoord.size())-1);
			ymin = ycoord.get(0);
			ymax = ycoord.get((ycoord.size())-1);
			
			if(ymax>maxy)
				maxy=ymax;
			
			ic.topleftx = xmin;
			ic.toplefty = ymin;
			
			int arr[][]=new int[ymax-ymin+1][xmax-xmin+1];
			
			for(int i=ymin,y=0; i<=ymax; i++,y++)
			{
		        for(int j=xmin,x=0; j<=xmax; j++,x++)
		        {
		        	if(pixels[i][j]==1)
		        		arr[y][x]=1;
		        }
		       
		    }
			
			ic.binaryarray = arr;
			
			xcoord.clear();
			ycoord.clear();
			
			getImageFile();
			ic.binaryarrayscaled = scale();
			comp.add(ic);
			//matchimages();
		}	
		return comp;
	}
	
	public void startpixel(int sx,int sy)
	{	
		for(int j=sx;j<w;j++)
			{
				for(int i=sy;i<maxy;i++)
				{
					if(pixels[i][j]==1)
					{
						xmin=j;
						ymin=i;
						return;
					}
				}
			}
			if((maxy+100)<h)
			{
				int i = maxy;
				maxy = maxy + 100;
				startpixel(0,(i+1));
				return;
			}
			else
				f = 1;
	}
	

	public void flood(int x,int y)
	{
		
		if(temp[y][x]!=2 && pixels[y][x]==1)
		{
					temp[y][x]=2;
					xcoord.add(x);
					ycoord.add(y);
					flood(x+1,y);
					flood(x-1,y);
					flood(x+1,y+1);
					flood(x+1,y-1);
					flood(x-1,y+1);
					flood(x-1,y-1);
					flood(x,y+1);
					flood(x,y-1);
				
			
		}
		Collections.sort(xcoord);
		Collections.sort(ycoord);
		
	
	}
	
	public void getImageFile() throws IOException
	{
	    BufferedImage img=new BufferedImage((xmax-xmin)+1,(ymax-ymin)+1, BufferedImage.TYPE_BYTE_BINARY);;
	    for(int i=ymin,k=0; i<=ymax; i++,k++){
	        for(int j=xmin,l=0; j<=xmax; j++,l++)
	        {
	        	Color c = new Color(image.getRGB(j, i));
			    int red = (int)(c.getRed() * 0.299);
		          int green = (int)(c.getGreen() * 0.587);
		          int blue = (int)(c.getBlue() *0.114);
			    
	            Color newColor = new Color(red+green+blue,
	                    red+green+blue,red+green+blue);
	            img.setRGB(l,k,newColor.getRGB());
	        }
	       
	    }
	    File output1 = new File("op.png");
	    ImageIO.write(img, "png", output1);
	}

	public int[][] scale() throws IOException
	{
		BufferedImage scaled = ImageIO.read(new File("op.png"));
		Image i = scaled.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		BufferedImage buffered = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_BINARY);
		buffered.getGraphics().drawImage(i, 0, 0 , null);
		ImageIO.write(buffered, "png", new File("scaled.png"));
		int scaledarray[][] = new int[100][100];
		scaledarray = jpgtomatrix(buffered);
		return scaledarray;
	}
	
	public void matchimages() throws IOException
	{
		BufferedImage imgop = ImageIO.read(new File("scaled.png"));
		BufferedImage imgA = ImageIO.read(new File("A.png"));
		BufferedImage imgB = ImageIO.read(new File("B.png"));
		BufferedImage imgC = ImageIO.read(new File("C.png"));
		BufferedImage imgD = ImageIO.read(new File("D.png"));
		
		int op[][]=new int[100][100];
		int A[][]=new int[100][100];
		int B[][]=new int[100][100];
		int C[][]=new int[100][100];
		int D[][]=new int[100][100];
		int diffA = 0;
		int diffB = 0;
		int diffC = 0;
		int diffD = 0;
		
		op = jpgtomatrix(imgop);
		A = jpgtomatrix(imgA);
		B = jpgtomatrix(imgB);
		C = jpgtomatrix(imgC);
		D = jpgtomatrix(imgD);
		
		
		for(int i=0;i<op.length;i++)
		{
			for(int j=0;j<op[0].length;j++)
			{
				diffA = diffA + Math.abs(op[i][j] - A[i][j]);
			}
		}
		
		for(int i=0;i<op.length;i++)
		{
			for(int j=0;j<op[0].length;j++)
			{
				diffB = diffB + Math.abs(op[i][j] - B[i][j]);
			}
		}
		
		for(int i=0;i<op.length;i++)
		{
			for(int j=0;j<op[0].length;j++)
			{
				diffC = diffC + Math.abs(op[i][j] - C[i][j]);
			}
		}
		
		for(int i=0;i<op.length;i++)
		{
			for(int j=0;j<op[0].length;j++)
			{
				diffD = diffD + Math.abs(op[i][j] - D[i][j]);
			}
		}
		
		ArrayList<Integer> diff = new ArrayList<Integer>();
		diff.add(diffA);
		diff.add(diffB);
		diff.add(diffC);
		diff.add(diffD);
		
		Collections.sort(diff);
		
		
		if(diff.get(0) == diffB)
			System.out.println("Image is B");
		else if(diff.get(0) == diffA)
			System.out.println("Image is A");
		else if(diff.get(0) == diffC)
			System.out.println("Image is C");
		else if(diff.get(0) == diffD)
			System.out.println("Image is D");
	}
}
