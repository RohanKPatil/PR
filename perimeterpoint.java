import java.text.DecimalFormat;
import java.util.*;


public class perimeterpoint 
{
public int sequence ; // indicates sequence number in traversal
public int px;
public int py; //indicats coordinate of point
public ArrayList<point> perimeterpointlist; // ArrayList of perimeter point
public ArrayList<Double> centeranglelist;// indicates average angle when point is at center of angle
public ArrayList<Double> leftanglelist;//// indicates average angle when point is at one side of angle between length and length/2 point on one side
public ArrayList<Double> rightanglelist;// similar as above but on opposite side

public double calcangle(int beforelength,int afterlength) // generalize method to calculate angle beforepoint,point,afterpoint
{
	ArrayList<Double> anglelist = new ArrayList<Double>();      
	int cx=0,cy=0,bx=0,by=0,ax=0,ay=0;
	int vx1=0,vy1=0,vx2=0,vy2=0;
	double angle=0.0;
	
	//System.out.println("sequence="+sequence+",beforelength="+beforelength+",afterlength="+afterlength);
		cx = perimeterpointlist.get(sequence).x;
		cy = perimeterpointlist.get(sequence).y;
		
		ax = perimeterpointlist.get(sequence+afterlength>=0?((sequence+afterlength)%perimeterpointlist.size()):(sequence+afterlength+perimeterpointlist.size())).x;
		ay = perimeterpointlist.get(sequence+afterlength>=0?((sequence+afterlength)%perimeterpointlist.size()):(sequence+afterlength+perimeterpointlist.size())).y;
		
		
		bx = perimeterpointlist.get(sequence-beforelength>=0?((sequence-beforelength)%perimeterpointlist.size()):(sequence-beforelength+perimeterpointlist.size())).x;
		by = perimeterpointlist.get(sequence-beforelength>=0?((sequence-beforelength)%perimeterpointlist.size()):(sequence-beforelength+perimeterpointlist.size())).y;

		
		
		vx1 = (cx>=bx?(cx-bx):(bx-cx));
		vy1 = (cy>=by?(cy-by):(by-cy));;
		
		vx2 = (ax>=cx?(ax-cx):(cx-ax));
		vy2 = (ay>=cy?(ay-cy):(cy-ay));
		
		double num = (vx1*vx2 + vy1*vy2);
		double denom = (Math.sqrt(vx1*vx1+vy1*vy1))*(Math.sqrt(vx2*vx2+vy2*vy2));
		//System.out.print(num+","+denom);
		
		if(num>denom)
			denom = Math.round(denom);
		
		
		angle = (180/Math.PI)*(Math.acos(num/denom));
		//System.out.println("angle="+angle);
	
	return angle;
	
}
public ArrayList<ArrayList<Double>> calcavgangle(int lowrange,int highrange) // this method will call above method and size of perimeter list
{
	centeranglelist = new ArrayList<Double>();
	leftanglelist = new ArrayList<Double>();
	rightanglelist = new ArrayList<Double>();
	
	double c_sum=0.0,b_sum=0.0,a_sum=0.0,c_angle=0.0,b_angle=0.0,a_angle=0.0;
	ArrayList<Double> c_angles = new ArrayList<Double>();
	ArrayList<Double> b_angles = new ArrayList<Double>();
	ArrayList<Double> a_angles = new ArrayList<Double>();
	ArrayList<ArrayList<Double>> all_avgangles = new ArrayList<ArrayList<Double>>();
	
	
	for(int i=0;i<perimeterpointlist.size();i++)
	{
		c_sum=0.0;
		b_sum=0.0;
		a_sum=0.0;
		for(int j=lowrange;j<=highrange;j++)
		{
			sequence=i;
			c_angle = calcangle(j,j);
			c_angles.add(c_angle);
			b_angle = calcangle(j,-j*multiplier);
			b_angles.add(b_angle);
			a_angle = calcangle(-j*multiplier,j);
			a_angles.add(a_angle);
			
			c_sum = c_sum + c_angle;
			b_sum = b_sum + b_angle;
			a_sum = a_sum + a_angle;
			
		}
		System.out.println("\nFor point #"+(sequence+1)+": x="+perimeterpointlist.get(i).x+",y="+perimeterpointlist.get(i).y);
		System.out.println("Central angle list:");
		for(int k=0;k<c_angles.size();k++)
		{
			System.out.print(String.format( "%.2f",c_angles.get(k))+",");
		}
		System.out.println("");
		System.out.println("After angle list:");
		for(int k=0;k<a_angles.size();k++)
		{
			System.out.print(String.format( "%.2f",a_angles.get(k))+",");
		}
		System.out.println("");
		System.out.println("Before angle list:");
		for(int k=0;k<b_angles.size();k++)
		{
			System.out.print(String.format( "%.2f",b_angles.get(k))+",");
		}
		System.out.println("");
		
		
		avgcenterangle = (double)c_sum/c_angles.size();
		avgbeforeangle = (double)b_sum/b_angles.size();
		avgafterangle = (double)a_sum/a_angles.size();
		
		centeranglelist.add(avgcenterangle);
		leftanglelist.add(avgbeforeangle);
		rightanglelist.add(avgafterangle);
		
		c_angles.clear();
		a_angles.clear();
		b_angles.clear();
	}
	all_avgangles.add(centeranglelist);
	all_avgangles.add(leftanglelist);
	all_avgangles.add(rightanglelist);
	
	return all_avgangles;
}
public char status; // D DEADEND B BEND  C CURVE S STLINE
public String detailstatus; 
double avgcenterangle;
double avgbeforeangle;
double avgafterangle;
int multiplier=2;
//public abstract ArrayList calcavgbeforeangle(float lowrange,float highrange,double multiplier); // this method will call above method and size of perimeter list
//public abstract ArrayList calcavgafterangle(float lowrange,float highrange,double multiplier); // this method will call above method and size of perimeter list

 
}