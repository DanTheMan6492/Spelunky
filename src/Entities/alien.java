package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class alien extends Entity
{
	public alien(int x, int y, int w, int h, boolean visible, String path) 
	{
		super(x, y, w, h, visible, path);
	}
	
	public void update() {
		tx.setToTranslation(x, y);
		
		if(vx < -0.5) 
			dir = -1;
		else if(vx > 0.5)
			dir = 1;
		
		
		if(!grounded) {
			vy -= 2;
			y -= vy;
		}
		
		x += vx;
		y += vy;
	}
	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, (int) (x-Camera.x), (int) (y-Camera.y), dir * (int) w, (int) h, null);
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Entity.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}	
}