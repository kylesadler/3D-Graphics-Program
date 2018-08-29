import javax.swing.*;
import java.awt.*;
import java.applet.Applet;
import java.io.*;
import javax.imageio.ImageIO;

public class AnimatorVideoCreator{
	public static void main(String args[]) throws IOException{
		Animator s=new Animator(400);
		s.fillRectPrism(-150.0,-150.0,-150.0,300.0,300.0,300.0,Color.blue,Color.red,Color.green);
		for(int i=0;i<50;i++){
			File output = new File(i+".png");
			ImageIO.write(s.displayImage(), "png", output);
			s.rotateY(Math.PI/25);
		}
		for(int i=50;i<100;i++){
			File output = new File(i+".png");
			ImageIO.write(s.displayImage(), "png", output);
			s.rotateX(Math.PI/25);
		}
		for(int i=100;i<150;i++){
			s.rotateZ(Math.PI/25);
			File output = new File(i+".png");
			ImageIO.write(s.displayImage(), "png", output);
		}
		for(int i=150;i<200;i++){
			File output = new File(i+".png");
			ImageIO.write(s.displayImage(), "png", output);
			s.rotateX(Math.PI/25);
			s.rotateY(Math.PI/25);
		}
		for(int i=200;i<250;i++){
			File output = new File(i+".png");
			ImageIO.write(s.displayImage(), "png", output);
			s.rotateY(Math.PI/25);
			s.rotateZ(Math.PI/25);
			}
		for(int i=250;i<300;i++){
			File output = new File(i+".png");
			ImageIO.write(s.displayImage(), "png", output);
			s.rotateX(Math.PI/25);
			s.rotateZ(Math.PI/25);
		}
		for(int i=300;i<350;i++){
			File output = new File(i+".png");
			ImageIO.write(s.displayImage(), "png", output);
			s.rotateX(Math.PI/25);
			s.rotateZ(Math.PI/25);
			s.rotateY(Math.PI/25);
		}
		File output = new File(350+".png");
		ImageIO.write(s.displayImage(), "png", output);
	}
}
