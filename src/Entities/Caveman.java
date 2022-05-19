package Entities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import Blocks.Block;
import Blocks.LevelBuilder;

public class Caveman extends Entity
{
	public int frame;
	public boolean frenzy;
	public long oldTime;
	public Caveman(double x, double y, double w, double h, boolean visible, String path) 
	{
		super(x, y, w, h, visible, path);
		frenzy = false;
		oldTime = 0;
		frame = 0;
	}
	
	public void detects()
	{
		if (frenzy == false && entities.get(0).y == this.y && Math.abs(entities.get(0).x-this.x) <= 5)
		{
			if ((dir == 1 && entities.get(0).x > this.x || dir == -1 && entities.get(0).x < this.x))
			{
				boolean blockinbetween = false;
				for (Block[] b : LevelBuilder.level)
				{
					for(Block c : b)
					{
						if (c.y == this.y && (Math.abs(this.x - c.x) < Math.abs(this.x - entities.get(0).x)))
						{
							blockinbetween = true;
						}
					}
					
				}
				if (blockinbetween == false)
				{
					frenzy = true;
				}
			}	
		
		}
	}
	
	public void update() {
		tx.setToTranslation(x, y);
		double temp = vy;
		long newTime = System.nanoTime();
		long deltaT = newTime - oldTime;
		
		if(vx < -0.5) 
			dir = -1;
		else if(vx > 0.5)
			dir = 1;
		
		
		if(!grounded) {
			vy -= 2;
			y -= vy;
			if(temp/vy <= 0) 
			{
				frame = 0;
			}
		}
		
		if (frenzy = true)
		{
			while (deltaT != 3000)
			{
				vx = dir*50;
				newTime = System.nanoTime();
				deltaT = newTime - oldTime;
			}
			oldTime = newTime;
			vx = 0;
			frenzy = false;
		}
		else
		{			
			int behavior = (int) Math.random()*5 + 1;
			if (behavior == 1)
			{
				while (deltaT != 2000)
				{
					vx = 30;
					newTime = System.nanoTime();
					deltaT = newTime - oldTime;
				}
				oldTime = newTime;
				vx = 0;
			}
			else if (behavior == 2)
			{
				while (deltaT != 2000)
				{
					vx = -30;
					newTime = System.nanoTime();
					deltaT = newTime - oldTime;
				}
				oldTime = newTime;
				vx = 0;
			}
			else
			{
				while (deltaT != 2000)
				{
					vx = 0;
					newTime = System.nanoTime();
					deltaT = newTime - oldTime;
				}
				oldTime = newTime;
			}
			detects();
		}	
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