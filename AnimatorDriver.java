import javax.swing.*;
import java.awt.*;
import java.applet.Applet;
import java.io.*;

public class AnimatorDriver extends JApplet{
	//be sure to fix flicker
	
	//Called when this applet is loaded into the browser.
	public void init(){
	}
	
	 public void update(Graphics g) {
		Graphics offgc;
		Image offscreen = null;
		Rectangle box = g.getClipRect();

		// create the offscreen buffer and associated Graphics
		offscreen = createImage(box.width, box.height);
		offgc = offscreen.getGraphics();
		// clear the exposed area
		offgc.setColor(getBackground());
		offgc.fillRect(0, 0, box.width, box.height);
		offgc.setColor(getForeground());
		// do normal redraw
		offgc.translate(-box.x, -box.y);
		paint(offgc);
		// transfer offscreen to window
		g.drawImage(offscreen, box.x, box.y, this);
    }
	
	public void paint(Graphics g){
	
		//should be same as applet size
		//this means that dimensions can be from -(size) to size
		//z can be from -size/2 to size/2
		Animator s=new Animator(400,g);
		
		s.fillRectPrism(-150.0,-150.0,-150.0,250.0,250.0,250.0,Color.blue,Color.red,Color.green);
		
		
		s.display();
		//s.display();
		//s.rotateX(-Math.PI/2);
		//s.display();
		//s.rotateZ(Math.PI);
		
		for(int i=0;i<1000;i++){
			s.rotateX(Math.PI/1000);
			if(i%50==0){
				s.display();
			}
		}
		
		for(int i=0;i<1000;i++){
			s.rotateY(Math.PI/1000);
			if(i%50==0){
				s.display();
			}
		}
		
		for(int i=0;i<1000;i++){
			s.rotateZ(Math.PI/1000);
			if(i%50==0){
				s.display();
			}
		}
		
	}
	
	
	public void start(){
		
	}
	public void stop(){
		
	}
	public void destroy(){
		
	}
}
