//Davecat Pesso
//Completed (Not Polished): 22 January 2017 11:49 PM
//Side Swipe (Brick Breaker)

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class BrickBreaker extends JApplet {
	
	public static final int WIDTH = 650;//Width of window
	public static final int HEIGHT = 700;//Height of window
	static boolean brickCreate = true;
	private PaintSurface canvas;//Where graphic will be painted
	
	/*
	 * Various images used for the project
	 */
	public static Image interfaceBackgroundPic;
	public static Image paddleBackgroundPic;
	public static Image gameLogo;

public void init()
{
   //The code bellow extracts the picture from computer and allows it to become a variable to be added later below.
	try {
		
        URL pic1 = new URL(getDocumentBase(), "Colored_Newton_Fractal.png");
        paddleBackgroundPic = ImageIO.read(pic1);
        
        URL pic2 = new URL(getDocumentBase(), "planet_venus_png_by_ravenmaddartwork-d2zavun.png");
        gameLogo = ImageIO.read(pic2);     
        
        URL pic4 = new URL(getDocumentBase(), "Fractal-Free-Download-PNG.png");
        interfaceBackgroundPic = ImageIO.read(pic4);
        
    } catch(Exception e) {
        // tells if anything goes wrong!
        e.printStackTrace();
    }

	
	/*
	 * Creates the bricks upon startup
	 */
	if(brickCreate == true){
		for(int j = 15; j < 450; j+=15)
		{
			
			for(int i = 35; i< getSize().width+200; i+=35)
		{
			
				PaintSurface.bricks.add( new Bricks(i,j, j%5));
		}
		}
		}

	brickCreate = false;//Stops creation of bricks
	Frame title = (Frame)this.getParent().getParent();
    title.setTitle("Side Swipe");
	this.setSize(WIDTH,HEIGHT);
	getContentPane().setBackground( Color.BLACK);	
	canvas = new PaintSurface();	
	//Adds the canvas to the frame.
	this.add(canvas, BorderLayout.CENTER);
	ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
	executor.scheduleAtFixedRate(new AnimationThread(this), 0L, 7000, TimeUnit.MICROSECONDS);
}
}




