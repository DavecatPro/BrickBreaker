import javax.swing.JApplet;

class AnimationThread implements Runnable
{
	JApplet c;
	public AnimationThread(JApplet c){
		this.c=c;
	}
	public void run(){
		c.repaint();
	}
}
