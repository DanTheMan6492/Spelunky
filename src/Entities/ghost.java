package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import General.Frame;

public class ghost extends Entity{

	public ghost(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		Sprite = getImage("/imgs/Monsters/ghost.gif");
		// TODO Auto-generated constructor stub
		dir = 1;
	}
		
	public void follow() {
		if(Frame.Ana.x + Frame.Ana.w/2 > x + 1.5 * w) {
			vx = 4;
		}else if(Frame.Ana.x + Frame.Ana.w/2 < x + 1.5 * w) {
			vx = -4;
		}else {
			vx = 0;
		}
		
		if(Frame.Ana.y + Frame.Ana.h/2 > y + 1.5 * h) {
			vy = 4;
		}else if(Frame.Ana.y + Frame.Ana.h/2 < y + 1.5 * h) {
			vy = -4;
		}else {
			vy = 0;
		}
	}
	
	public void update() {
		if(vx > 0) {
			dir = 1;
		}else {
			dir = -1;
		}
		
		if(dir == -1)
			tx.setToTranslation(x-Camera.x + 3 * w, y-Camera.y);
		else
			tx.setToTranslation(x-Camera.x, y-Camera.y);
		
		tx.scale(1.5 * dir, 1.5);
		
		follow();
		
		if(Frame.Ana.x + Frame.Ana.w > x
		&& Frame.Ana.x < x + w
		&& Frame.Ana.y + Frame.Ana.y > y
		&& Frame.Ana.y < y + h) {
			//Frame.Ana.die();
		}
		
		x += vx;
		y += vy;
	}

	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);
		g.drawRect((int)(x - Camera.x), (int)(y - Camera.y), (int)(3 * w), (int)(3 * h));
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
