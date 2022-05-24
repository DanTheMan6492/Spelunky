package General;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.awt.Color;

import Blocks.LevelBuilder;
import Entities.Camera;

public class Fade{
	
	//add location attributes
	private Image img;
    public static int alpha = 0;
    public static boolean transition = false;
    public static Color color = new Color(0, 0, 0, alpha);


	
	public static void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
        color = new Color(0, 0, 0, alpha);
		g2.setColor(color);

        g2.fillRect(0, 0, 1920, 1080);


		
		

	}
	/* update the picture variable location */


    public static void fade(){

        alpha = 0;
        
        while(alpha < 255){
            alpha++;
            wait(1);
        }
        LevelBuilder.loadTransitionRoom();
        while(alpha > 0){
            alpha--;
            wait(1);
        }

    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
