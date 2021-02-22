import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;

class PaintSurface extends JComponent
{
	public static Font myFont = new Font("Monospaced", Font.BOLD, 30);
	public static Font logoFont = new Font("Crillee Italic", Font.ITALIC, 30);
	public static ArrayList<Bricks> bricks = new ArrayList<Bricks>();
	public static Shape line;
	public static Polygon triangleRight;
	public static Polygon triangleLeft;
	public static Rectangle2D userLeft;
	public static Rectangle2D userRight;
	public static Rectangle2D user;
	public static Rectangle2D ballHitBox;
	public static RoundRectangle2D border;
	public static RoundRectangle2D borderScore;
	public static RoundRectangle2D borderCombo;
	public static Ellipse2D ball;
	public static Ellipse2D ballLives;
	public static String scoreFormat = "00000";
	public static String comboFormat = "00";
	public static boolean stageCompletedTextClear = false;
	public static boolean playLock = false;
	static boolean newLife = true;
	static int lives = 3;
	static int livesDisplaySpacing = 33;
	static int comboScore = 000;
	static int totalScore = 0000;
	static int multiplier = 1;
	static double speedB_x = 0;
	static double speedB_y = 0;
	static int box_x = 250;
	static double box_y=0;
	static double ball_x = box_x + 30;
	static double ball_y = 635;
	static Color[] colors = {Color.BLUE,
			  Color.PINK, Color.YELLOW,
			  Color.MAGENTA, Color.CYAN};
	static int colorIndex = 0;
	static Random gen = new Random();
	
	

	public PaintSurface(){
		
		
		addMouseListener (new MouseAdapter()
		{
	public void mouseClicked(MouseEvent e)
	{
		/*
		 * Playlock engaged when user loses
		 */
		if(playLock == false){
		stageCompletedTextClear = false;
		newLife = false;
		if(box_x > 222.5)
		speedB_x = -.3;
		
		if(box_x < 222.5)
			speedB_x = .3; 
		
		speedB_y = -2.5;
		}
	}
		});
		
		addMouseMotionListener (new MouseMotionAdapter()
		{
	public void mouseMoved(MouseEvent e)
	{
		/*
		 * Tracks users input of mouse movement and assigns it to paddle
		 */
		box_x = e.getX()-35;
		box_y = e.getY();
		
		/*
		 * Makes sure the paddles cannot move out of bounds
		 */
		while(box_x +80 > 435){
			box_x= 355;
		}
		while(box_x <= 25){
			box_x= 30;
		}
		
	}
		});
		
	}
	
	/**
	 * Determines how the ball moves not in relation to the paddle
	 */
	public void Move()
	{
		
		
		if (ball_y < 0)
			speedB_y = -speedB_y;
			
		ball_x += speedB_x;
		ball_y += speedB_y;
		

		if(ball_x < 5)
		{
			ball_x+=1;
			speedB_x = -speedB_x;
		}
		if(ball_x > 435)
		{
			ball_x-=1;
			speedB_x = -speedB_x;	
		}
	}
	
	/**
	 * Determines how the ball is moving of the collision of the paddle
	 */
	public void PaddleCollision()
	{
			if(user.intersects(ballHitBox) || userRight.intersects(ballHitBox) || userLeft.intersects(ballHitBox) ){
		
			if(user.intersects(ballHitBox)){
			
				if(speedB_x>0)
				speedB_x = .1;//Change the direction of the ball
				
				if(speedB_x<0)
					speedB_x = -.1;//Change the direction of the ball
				
				ball_y -= 1; //Change the direction of the ball
				
				multiplier = 1;
				comboScore = 0;
			}
			
			/*
			 * Angles the ball right if it hits the right side of the paddle
			 */
			if(userRight.intersects(ballHitBox)){
				double j = gen.nextDouble()+.4;
				speedB_x = j;
				
				
				ball_y -= 1;
				multiplier = 1;
				comboScore = 0;
			}
			
			/*
			 * Angles the ball left if it hits the left side of the paddle
			 */
			if(userLeft.intersects(ballHitBox)){
				double j = gen.nextDouble()+.4;
				speedB_x = j;
				speedB_x= -speedB_x;
				
				ball_y -= 1;
				multiplier = 1;
				comboScore = 0;
			}
		
			
			speedB_y = -speedB_y;
			ball_y += speedB_y;
			}
	}
	
	/**
	 * Formats the scoring numbers for neater look
	 */
	public void ScoreFormating()
	{
		if(totalScore>9 && totalScore<100)
		{
			scoreFormat = "0000";
		}
		if(totalScore>99 && totalScore<1000)
		{
			scoreFormat = "000";
		}
		if(totalScore>999 && totalScore<10000)
		{
			scoreFormat = "00";
		}
		if(totalScore>9999 && totalScore<100000)
		{
			scoreFormat = "0";
		}
		if(totalScore>99999 && totalScore<1000000)
		{
			scoreFormat = "";
		}
	}
	
	/**
	 * Formats the combo numbers for neater look
	 */
	public void ComboFormating()
	{
		if(comboScore<9)
		{
			comboFormat = "00";
		}
		if(comboScore>9 && comboScore<100)
		{
			comboFormat = "0";
		}
		if(comboScore>=100)
		{
			comboFormat = "";
		}
	}

	/**
	 * Sets up where the ball is in the beginning of the game
	 */
	public void StartAndRespawn()
	{
		if(ball_y > 700)
		{
			ball_y = 635;
			ball_x = box_x + 32;
			speedB_x = 0;
			speedB_y = 0;
			newLife = true;
			lives--;
			comboScore = 0;
			
		}
	}
	
	/**
	 * Formats the lives display
	 */
	public void LivesDisplay()
	{
		if (lives == 2)
		livesDisplaySpacing = 50;
		
		if (lives == 1)
		livesDisplaySpacing = 100;
	}
	
	/**
	 * Resets the bored and the balls
	 */
	public void BoardClear()
	{
		if (bricks.isEmpty() == true)
		{
			ball_y = 635;
			ball_x = box_x + 32;
			speedB_x = 0;
			speedB_y = 0;
		}
	}
	
	public void paintComponent(Graphics g){
		
	
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		/*
		 * For custom shapes
		 */
		int triangleLeftYCoords[] = {650,665,665}; 
		int triangleLeftXCoords[] = {box_x, box_x,box_x-20}; 
		int triangleRightYCoords[] = {650,665,665}; 
		int triangleRightXCoords[] = {box_x+70, box_x+70,box_x+90}; 
		
		user = new Rectangle2D.Double(box_x,650,70,15);
		userLeft = new Rectangle2D.Double(box_x-20,650,20, 15);
		userRight = new Rectangle2D.Double(box_x+70,650,20,15);
		
		triangleLeft = new Polygon(triangleLeftXCoords, triangleLeftYCoords , 3);//Left paddle triangle
		triangleRight = new Polygon(triangleRightXCoords, triangleRightYCoords , 3);//Right paddle triangle
		
		ballHitBox= new Rectangle2D.Double(ball_x,ball_y,15,15);//Hit box for the ball
		ball = new Ellipse2D.Double(ball_x, ball_y, 15, 15);//Display of the ball
		
		border = new RoundRectangle2D.Double(5,5,445,685, 12, 12);//Border for the game itself
		borderScore = new RoundRectangle2D.Double(467,60,117,37, 12, 12);//Border for the score counter
		borderCombo = new RoundRectangle2D.Double(513,150,67,37, 12, 12);//Border for the combo counter
		
		/*
		 * Draws background pictures
		 */
		g2.drawImage(BrickBreaker.interfaceBackgroundPic, 450, 0, 300, 700, this);
		g2.drawImage(BrickBreaker.paddleBackgroundPic, 5, 5, 445, 685, this);

		
		//When ball hits bricks
		for(int j = 0; j<=bricks.size()-1; j++)
			if((bricks.get(j)).intersects(ballHitBox)){
				ball_y +=1;
				speedB_y = -speedB_y;//Reverses ball movement
				colorIndex++;//Changes color
				bricks.remove(j);//Removes bricks from the array
				multiplier++;//Raises the multiplier
				if(multiplier>15){//Multiplier maxed out at 15
					multiplier = 15;
				}
				comboScore++;//Combo rasied
				
				totalScore += comboScore * multiplier;//Total score calculated
				
				if(colorIndex>4)//Color index reset
				colorIndex = 0;
				
			}
		
		/*
		 * If the bricks are all hit:
		 * The bored is reset
		 */
		if(bricks.isEmpty()==true){
			for(int j = 15; j < 450; j+=15)
			{
				
				for(int i = 35; i< 405; i+=35)
			{
				
					PaintSurface.bricks.add( new Bricks(i,j, j%5));
			}
			}
			stageCompletedTextClear = true;
			PaintSurface.totalScore+=100000;//Score bonus from the stage clear
		}
		
		/*
		 * Text comes up notifying user that they have cleared the board
		 */
		if(stageCompletedTextClear == true){
			g2.setFont(myFont);
			g2.setPaint(Color.WHITE);
			g2.drawString("STAGE COMPLETED!", 100, 500);
			g2.setPaint(Color.YELLOW);
			g2.drawString("+100k SCORE", 130, 535);
		}
		
		/*
		 * Draws the bricks
		 */
		for(Bricks brick: bricks){
			g2.setPaint(Color.BLACK);
			g2.setStroke(new BasicStroke(2));
			g2.draw(brick);
			g2.setPaint(Color.WHITE);
			g2.fill(brick);
		}
		if(newLife == true){
			ball_x = box_x + 30;
		}
		
		/*
		 * Formats the lives display at the bottom right
		 */
		for( int i = 600; i <= 698; i += livesDisplaySpacing){
		ballLives = new Ellipse2D.Double(460, i, 15, 15);
		g2.setStroke(new BasicStroke(3));
		g2.setPaint(Color.BLACK);
		g2.fill(ballLives);
		g2.setPaint(Color.RED);
		g2.draw(ballLives);
	}
		LivesDisplay();
		StartAndRespawn();
		ScoreFormating();
		ComboFormating();
		BoardClear();
		
		g2.setFont(myFont);
		g2.setPaint(Color.WHITE);
	
		/*
		 * When there are no lives, the player is prompted with a losing message.
		 */
		if(lives <= 0 )
		{
			g2.drawString("YOU LOSE!", 150, 500);
			playLock = true;
		}
		
		/*
		 * Formats the scoring area
		 */
		g2.drawString("SCORE", 500, 50);
		g2.drawString(scoreFormat+Integer.toString(totalScore) + " x" + Integer.toString(multiplier), 472, 90);
		g2.drawString("COMBO", 500, 140);
		g2.drawString(comboFormat + Integer.toString(comboScore), 520, 180);
		
		
		/*
		 * Formats the logo
		 */
		g2.drawImage(BrickBreaker.gameLogo, 480, 525, 170, 170, this);
		g2.setPaint(Color.WHITE);
		g2.setFont(logoFont);
		g2.drawString("SIDE", 500, 610);
		g2.drawString("SWIPE", 540, 630);

		/*
		 * Changes the paint with the color array
		 */
		g2.setPaint(colors[(colorIndex)%100]);
		
		/*
		 * Draws all items
		 */
		g2.draw(border);
		g2.setPaint(Color.CYAN);
		g2.draw(borderScore);
		g2.draw(borderCombo);
		g2.setStroke(new BasicStroke(3));
		g2.setPaint(Color.BLACK);
		g2.fill(ball);
		g2.setPaint(Color.RED);
		g2.draw(ball);
		g2.setPaint(Color.BLACK);
		g2.fill(user);
		g2.fill(triangleLeft);
		g2.fill(triangleRight);
		g2.setPaint(Color.YELLOW);
		g2.draw(user);
		g2.draw(triangleLeft);
		g2.draw(triangleRight);
		Move();
		PaddleCollision();

	}
}