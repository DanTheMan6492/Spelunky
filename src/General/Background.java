package General;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

import Blocks.LevelBuilder;
import Entities.Camera;

public class Background{
	
	//add location attributes
	private Image img; 	
	private AffineTransform tx;

	public Background(int x, int y) {
		img = getImage("/imgs/Background/1.png");
		tx = AffineTransform.getTranslateInstance(x, y );
		init(128, 128); 				//initialize the location of the image
									//use your variables
	}

	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		
		//call update to update the actualy picture location
		update();
		
		g2.drawImage(img, tx, null);
		
		

	}
	/* update the picture variable location */
	private void update() {

		img = getImage("/imgs/Background/"  + Integer.toString(1+(LevelBuilder.levelNum/4)) +".png");
		tx.setToTranslation(128-Camera.x, 128-Camera.y);
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Background.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
