package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class Olmec extends Entity
{
	public int tickTimer, stompTimer;
	public boolean jumping;
	
	public Olmec(double x, double y, double w, double h, boolean visible, String path) 
	{
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
	}
	
	public void detect() {
		if(Math.abs(x - Player.x) <= 800) {
			jump();
		}
	}
	
	public void jump() {
		if(x > Player.x) {
			//change velocity
		}else if(x < Player.x){
			
		}
		jumping = true;
	}
	
	public void stomp() {
		stompTimer = 10;
		//velocity is equal to zero
	}
	
	public void update() {
		
		
		if(tickTimer > 0) {
			tickTimer --;
			if(tickTimer == 0) {
				detect();
			}
		}
		
		if(stompTimer > 1) {
			stompTimer --;
			if(stompTimer == 0){
				stompTimer --;
				//yv = 10;
			}
		}
		
		if(jumping == true && Player.y > y) {
			stomp();
		}
		
		tx.setToTranslation(x, y);
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