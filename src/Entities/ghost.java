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
	}
		
	public void follow() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);

		if(mapX < spelunkerX) {
			vx = 4;
		}else if(mapX > spelunkerX) {
			vx = -4;
		}else {
			vx = 0;
		}
		
		if(mapY < spelunkerY) {
			vy = 4;
		}else if(mapY > spelunkerY) {
			vy = -4;
		}else {
			vy = 0;
		}
	}
	
	public void update() {
		if(dir == -1)
			tx.setToTranslation(x-Camera.x+128, y-Camera.y);
		else
			tx.setToTranslation(x-Camera.x, y-Camera.y);
		tx.scale(dir, 1);
		
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
