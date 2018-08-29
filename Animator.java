
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animator {
	
	//rotations are counter-clock wise, to the left, and toward the viewpoint
	
	/*
	 * <[x, y, z, r, g, b], ... >
	 * 
	 *
	 * z is positive distance from viewpoint
	 */
	Graphics g;
	
	//x: -screenSize/2 to screenSize/2
	//y: -screenSize/2 to screenSize/2
	//z: -screenSize/2 to screenSize/2
	ArrayList<double[]> points;
	int screenSize;
	double[][][] screen;
	double perspective;
	
	
	public Animator(int size, Graphics a){
		g=a;
		points=new ArrayList<double[]>(10000);
		screenSize=size;
		perspective=size*5;
	}
	
	//can only write to images
	public Animator(int size){
		points=new ArrayList<double[]>(10000);
		screenSize=size;
		perspective=size*5;
	}
	
	//sets size to 500 500
	public Animator(Graphics a){
		points=new ArrayList<double[]>(10000);
		g=a;
		screenSize=500;
		perspective=500*5;
	}
	
	
	//fill fills in shape
	//draw only outlines shape
	
	//adds point to list of points
	public void drawPoint(double x, double y, double z, Color c){
		double[] temp={x,y,z,c.getRed(),c.getGreen(),c.getBlue()};
		points.add(temp);
	}
	//draws at half resolution (pretty good)
	public void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, Color c){
		//some number of times
		double res = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1));
		for(int i=0;i<res+1;i++){
			drawPoint(x1+(x2-x1)/res*i, y1+(y2-y1)/res*i, z1+(z2-z1)/res*i, c);
		}
		
	}
	public void drawRectPrism(double x1, double y1, double z1, double xRange, double yRange, double zRange, Color c){
		
		//top (z constant)
		drawLine(x1, y1, z1, x1+xRange, y1, z1, c);
		drawLine(x1+xRange, y1, z1, x1+xRange, y1+yRange, z1, c);
		drawLine(x1, y1, z1, x1, y1+yRange, z1, c);
		drawLine(x1, y1+yRange, z1, x1+xRange, y1+yRange, z1, c);
		
		//connectors (z varies)
		drawLine(x1, y1, z1, x1, y1, z1+zRange, c);
		drawLine(x1+xRange, y1, z1, x1+xRange, y1, z1+zRange, c);
		drawLine(x1, y1+yRange, z1, x1, y1+yRange, z1+zRange, c);
		drawLine(x1+xRange, y1+yRange, z1, x1+xRange, y1+yRange, z1+zRange, c);
		
		//bottom (z constant)
		drawLine(x1, y1, z1+zRange, x1+xRange, y1, z1+zRange, c);
		drawLine(x1+xRange, y1, z1+zRange, x1+xRange, y1+yRange, z1+zRange, c);
		drawLine(x1, y1, z1+zRange, x1, y1+yRange, z1+zRange, c);
		drawLine(x1, y1+yRange, z1+zRange, x1+xRange, y1+yRange, z1+zRange, c);
	}
	//uniform color w black outline
	public void fillRectPrism(double x1, double y1, double z1, double xRange, double yRange, double zRange, Color c){
		double res = Math.sqrt(xRange*xRange+zRange*zRange+yRange*yRange);
		
		//top (z varies)
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res, y1+yRange/res, z1+zRange/res*i, x1+xRange-xRange/res, y1+yRange/res, z1+zRange/res*i, c);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, x1+xRange-xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, c);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res, y1+yRange/res, z1+zRange/res*i, x1+xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, c);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange-xRange/res, y1+yRange/res, z1+zRange/res*i, x1+xRange-xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, c);
		}
		
		//(z constant)
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res*i, y1+yRange/res, z1+zRange/res, x1+xRange/res*i, y1+yRange-yRange/res, z1+zRange/res, c);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res*i, y1+yRange/res, z1+zRange-zRange/res, x1+xRange/res*i, y1+yRange-yRange/res, z1+zRange-zRange/res, c);
		}
		
		c=Color.black;
		for(int i=0;i<2;i++){
			for(int j=0;j<2;j++){
				//top (z constant)
				drawLine(x1, y1+i, z1+j, x1+xRange, y1+i, z1+j, c);
				drawLine(x1+xRange-i, y1, z1+j, x1+xRange-i, y1+yRange, z1+j, c);
				drawLine(x1+j, y1, z1+i, x1+j, y1+yRange, z1+i, c);
				drawLine(x1, y1+yRange-i, z1+j, x1+xRange, y1+yRange-i, z1+j, c);
				
				//connectors (z varies)
				drawLine(x1+j, y1+i, z1, x1+j, y1+i, z1+zRange, c);
				drawLine(x1+xRange-i, y1+j, z1, x1+xRange-i, y1+j, z1+zRange, c);
				drawLine(x1+j, y1+yRange-i, z1, x1+j, y1+yRange-i, z1+zRange, c);
				drawLine(x1+xRange-i, y1+yRange-j, z1, x1+xRange-i, y1+yRange-j, z1+zRange, c);
				
				//bottom (z constant)
				drawLine(x1, y1+i, z1+zRange-j, x1+xRange, y1+i, z1+zRange-j, c);
				drawLine(x1+xRange-j, y1, z1+zRange-i, x1+xRange-j, y1+yRange, z1+zRange-i, c);
				drawLine(x1+i, y1, z1+zRange-j, x1+i, y1+yRange, z1+zRange-j, c);
				drawLine(x1, y1+yRange-j, z1+zRange-i, x1+xRange, y1+yRange-j, z1+zRange-i, c);
			}
		}
	}
	//diff color for each face w black outline
	public void fillRectPrism(double x1, double y1, double z1, double xRange, double yRange, double zRange, Color c1,Color c2,Color c3,Color c4,Color c5,Color c6){
				double res = Math.sqrt(xRange*xRange+zRange*zRange+yRange*yRange);
		
		//top (z varies)
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res, y1+yRange/res, z1+zRange/res*i, x1+xRange-xRange/res, y1+yRange/res, z1+zRange/res*i, c1);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, x1+xRange-xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, c2);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res, y1+yRange/res, z1+zRange/res*i, x1+xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, c3);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange-xRange/res, y1+yRange/res, z1+zRange/res*i, x1+xRange-xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, c4);
		}
		
		//(z constant)
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res*i, y1+yRange/res, z1+zRange/res, x1+xRange/res*i, y1+yRange-yRange/res, z1+zRange/res, c5);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res*i, y1+yRange/res, z1+zRange-zRange/res, x1+xRange/res*i, y1+yRange-yRange/res, z1+zRange-zRange/res, c6);
		}
		
		Color c=Color.black;
		for(int i=0;i<2;i++){
			for(int j=0;j<2;j++){
				//top (z constant)
				drawLine(x1, y1+i, z1+j, x1+xRange, y1+i, z1+j, c);
				drawLine(x1+xRange-i, y1, z1+j, x1+xRange-i, y1+yRange, z1+j, c);
				drawLine(x1+j, y1, z1+i, x1+j, y1+yRange, z1+i, c);
				drawLine(x1, y1+yRange-i, z1+j, x1+xRange, y1+yRange-i, z1+j, c);
				
				//connectors (z varies)
				drawLine(x1+j, y1+i, z1, x1+j, y1+i, z1+zRange, c);
				drawLine(x1+xRange-i, y1+j, z1, x1+xRange-i, y1+j, z1+zRange, c);
				drawLine(x1+j, y1+yRange-i, z1, x1+j, y1+yRange-i, z1+zRange, c);
				drawLine(x1+xRange-i, y1+yRange-j, z1, x1+xRange-i, y1+yRange-j, z1+zRange, c);
				
				//bottom (z constant)
				drawLine(x1, y1+i, z1+zRange-j, x1+xRange, y1+i, z1+zRange-j, c);
				drawLine(x1+xRange-j, y1, z1+zRange-i, x1+xRange-j, y1+yRange, z1+zRange-i, c);
				drawLine(x1+i, y1, z1+zRange-j, x1+i, y1+yRange, z1+zRange-j, c);
				drawLine(x1, y1+yRange-j, z1+zRange-i, x1+xRange, y1+yRange-j, z1+zRange-i, c);
			}
		}
	
	}
	//diff color for each set of opposite faces w black outline
	public void fillRectPrism(double x1, double y1, double z1, double xRange, double yRange, double zRange, Color c1,Color c2,Color c3){
		double res = Math.sqrt(xRange*xRange+zRange*zRange+yRange*yRange);
		
		//top (z varies)
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res, y1+yRange/res, z1+zRange/res*i, x1+xRange-xRange/res, y1+yRange/res, z1+zRange/res*i, c1);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, x1+xRange-xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, c1);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res, y1+yRange/res, z1+zRange/res*i, x1+xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, c2);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange-xRange/res, y1+yRange/res, z1+zRange/res*i, x1+xRange-xRange/res, y1+yRange-yRange/res, z1+zRange/res*i, c2);
		}
		
		//(z constant)
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res*i, y1+yRange/res, z1+zRange/res, x1+xRange/res*i, y1+yRange-yRange/res, z1+zRange/res, c3);
		}
		for(int i=2;i<res-1;i++){
			drawLine(x1+xRange/res*i, y1+yRange/res, z1+zRange-zRange/res, x1+xRange/res*i, y1+yRange-yRange/res, z1+zRange-zRange/res, c3);
		}
		
		Color c=Color.black;
		for(int i=0;i<2;i++){
			for(int j=0;j<2;j++){
				//top (z constant)
				drawLine(x1, y1+i, z1+j, x1+xRange, y1+i, z1+j, c);
				drawLine(x1+xRange-i, y1, z1+j, x1+xRange-i, y1+yRange, z1+j, c);
				drawLine(x1+j, y1, z1+i, x1+j, y1+yRange, z1+i, c);
				drawLine(x1, y1+yRange-i, z1+j, x1+xRange, y1+yRange-i, z1+j, c);
				
				//connectors (z varies)
				drawLine(x1+j, y1+i, z1, x1+j, y1+i, z1+zRange, c);
				drawLine(x1+xRange-i, y1+j, z1, x1+xRange-i, y1+j, z1+zRange, c);
				drawLine(x1+j, y1+yRange-i, z1, x1+j, y1+yRange-i, z1+zRange, c);
				drawLine(x1+xRange-i, y1+yRange-j, z1, x1+xRange-i, y1+yRange-j, z1+zRange, c);
				
				//bottom (z constant)
				drawLine(x1, y1+i, z1+zRange-j, x1+xRange, y1+i, z1+zRange-j, c);
				drawLine(x1+xRange-j, y1, z1+zRange-i, x1+xRange-j, y1+yRange, z1+zRange-i, c);
				drawLine(x1+i, y1, z1+zRange-j, x1+i, y1+yRange, z1+zRange-j, c);
				drawLine(x1, y1+yRange-j, z1+zRange-i, x1+xRange, y1+yRange-j, z1+zRange-i, c);
			}
		}
	
	}
	
	
	public void drawCircle(double centerX, double centerY, double radius){
		
	}
	
	public void drawEllipse(double centerX, double centerY, double majorAxis){
		
	}
	


	
	
	
	
	
	//angle in radians
	public void rotateY(double angle){
		if(points.size()==0)return;
		double c = Math.cos(angle);
		double s = Math.sin(angle);
		double[][] transform=
			{
					{c,0.0,s,0.0,0.0,0.0},
					{0.0,1.0,0.0,0.0,0.0,0.0},
					{-s,0.0,c,0.0,0.0,0.0},
					{0.0,0.0,0.0,1.0,0.0,0.0},
					{0.0,0.0,0.0,0.0,1.0,0.0},
					{0.0,0.0,0.0,0.0,0.0,1.0}
			};
		points=Matrix.multiply(transform, points);
		
		/*
		 for(int i=0;i<points.size();i++){
			for(int j=0;j<points.get(i).length;j++){
				System.out.print(points.get(i)[j]+", ");
			}
			System.out.println();
		}
		System.out.println();
		*/
	}
	//angle in radians
	public void rotateX(double angle){
		if(points.size()==0)return;
		double c = Math.cos(angle);
		double s = Math.sin(angle);
		double[][] transform=
			{
					{1.0,0.0,0.0,0.0,0.0,0.0},
					{0.0,c,-s,0.0,0.0,0.0},
					{0.0,s,c,0.0,0.0,0.0},
					{0.0,0.0,0.0,1.0,0.0,0.0},
					{0.0,0.0,0.0,0.0,1.0,0.0},
					{0.0,0.0,0.0,0.0,0.0,1.0}
			};
		points=Matrix.multiply(transform, points);
		
		/*
		 for(int i=0;i<points.size();i++){
			for(int j=0;j<points.get(i).length;j++){
				System.out.print(points.get(i)[j]+", ");
			}
			System.out.println();
		}
		System.out.println();
		*/
	}
	//angle in radians
	public void rotateZ(double angle){
		if(points.size()==0)return;
		double c = Math.cos(angle);
		double s = Math.sin(angle);
		double[][] transform=
			{
					{c,-s,0.0,0.0,0.0,0.0},
					{s,c,0.0,0.0,0.0,0.0},
					{0.0,0.0,1.0,0.0,0.0,0.0},
					{0.0,0.0,0.0,1.0,0.0,0.0},
					{0.0,0.0,0.0,0.0,1.0,0.0},
					{0.0,0.0,0.0,0.0,0.0,1.0}
			};
		points=Matrix.multiply(transform, points);
		
		/*
		 for(int i=0;i<points.size();i++){
			for(int j=0;j<points.get(i).length;j++){
				System.out.print(points.get(i)[j]+", ");
			}
			System.out.println();
		}
		System.out.println();
		*/
	}

	//displays points on screen
	public void display(){
		
		/* y x---->
		 * | (r,g,b,z), (r,g,b,z), ...
		 * | (r,g,b,z), (r,g,b,z), ...
		 * v
		 */
		
		//y, x
		
		//reset screen
		screen = new double[screenSize][screenSize][5];
		
		
		for(int i=0;i<points.size();i++){
			//[x, y, z, r, g, b]
			if(points.get(i)[2]>-screenSize/2-perspective){
				
				//rasterize returns new double[]
				double[] tempPoint = rasterize(points.get(i));
				//map to screen modifies original double
				double[] screenPoint =mapToScreen(tempPoint);
				this.addToScreen(screenPoint);
			}
		}
		
		//be sure to get rid of flicker
		g.setColor(new Color(100,100,100));
		g.fillRect(0,0,screenSize,screenSize);
		this.writeScreen();
	}
	
	public BufferedImage displayImage(){
		
		/* y x---->
		 * | (r,g,b,z), (r,g,b,z), ...
		 * | (r,g,b,z), (r,g,b,z), ...
		 * v
		 */
		
		//y, x
		
		//reset screen
		screen = new double[screenSize][screenSize][5];
		
		
		for(int i=0;i<points.size();i++){
			//[x, y, z, r, g, b]
			if(points.get(i)[2]>-screenSize/2-perspective){
				
				//rasterize returns new double[]
				double[] tempPoint = rasterize(points.get(i));
				//map to screen modifies original double
				double[] screenPoint =mapToScreen(tempPoint);
				this.addToScreen(screenPoint);
			}
		}
		
		//be sure to get rid of flicker
		return this.screenToImage();
	}

/* inner workings //////////////////////////////////////////////////////
 * */
	private void writeScreen(){
		for(int row=0;row<screenSize;row++){
			for(int col=0;col<screenSize;col++){
				if(screen[row][col][4]==1.0){//if point has been changed
					//make new color with rgb values
					g.setColor(new Color((int)screen[row][col][1],(int)screen[row][col][2],(int)screen[row][col][3]));
					//draw a pixel
					g.drawRect(col,row,1,1);
				}
			}
		}
	}	
	private BufferedImage screenToImage(){
		BufferedImage output = new BufferedImage(screenSize,screenSize,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = output.createGraphics();
		g.setColor(new Color(200,200,200));
		g.fillRect(0,0,screenSize,screenSize);
		for(int row=0;row<screenSize;row++){
			for(int col=0;col<screenSize;col++){
				if(screen[row][col][4]==1.0){//if point has been changed
					//make new color with rgb values
					g.setColor(new Color((int)screen[row][col][1],(int)screen[row][col][2],(int)screen[row][col][3]));
					//draw a pixel
					g.drawRect(col,row,1,1);
				}
			}
		}
		return output;
	}
	/*private
	 * z is originally -size/2 to size/2 --> convert to 0 to size
	 * [x, y, z, r, g, b]
	*divide x and y by z
	*return [x', y', z', r, g, b]
	make sure that z is positive
	*/
	private double[] rasterize(double[] a){
		double [] output=new double[a.length];
		
		output[2]=a[2]+(double)(screenSize/2)+perspective;
		output[0]=a[0]/output[2]*perspective;
		output[1]=a[1]/output[2]*perspective;
		
		for(int i=3;i<a.length;i++){
			output[i]=a[i];
		}
		
		return output;
	}
	/*private
	 * input [x', y', z, r, g, b]
	/*map to 0-->rows, 0-->cols
	*return [int xmap, int ymap, z, r, g, b]
	*/
	private double[] mapToScreen(double [] a){
		a[0]=(int)((a[0]+screenSize)/2+.5);
		a[1]=(int)((a[1]+screenSize)/2+.5);
		return a;
	}	
	/* private
	 * input point [int xmap, int ymap, z, r, g, b]
	 * boolean if point has been changed
	 * 
	 * screen
	 * x y---->
	 * | (z,r,g,b,boolean), (z,r,g,b,boolean), ...
	 * | (z,r,g,b,boolean), (z,r,g,b,boolean), ...
	 * v
	 * 
	 * boolean --> 1.0=true; 0.0=false
	 * 
	 * 1. write point to screen
	 * 2. prioritize points by z
	 */
	private void addToScreen(double[] a){
		//row, column
		int x=(int)a[0];
		int y=(int)a[1];
		
		if(x>=screenSize||x<0||y>=screenSize||y<0){
			return;
		}
		
		//if point z is less than screen z
		if(screen[y][x][0]==0||a[2]<screen[y][x][0]){
			for(int i=0;i<4;i++){
				//copies data to screen
				screen[y][x][i]=a[i+2];
			}
			screen[y][x][4]=1.0;
		}
	}
}
