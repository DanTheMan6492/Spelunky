package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Transition;

public class damsel extends Entity
{
	
	public boolean panic;
	public boolean exit;
	public int exitTimer;

	public damsel(int x, int y, int w, int h, boolean visible, String path) 
	{
		super(x, y, w, h, visible, path);
		panic = false;
	}
	
	public void detect() 
	{
		
		int X = (int) x / 128;
		int Y = (int) y / 128;
		
		if(LevelBuilder.level[Y][X] == null)
			return;
		
		if(LevelBuilder.level[Y][X].id == 2 || LevelBuilder.level[Y+1][X].id == 2 || LevelBuilder.level[Y+1][X].id == 2 || LevelBuilder.level[Y][X+1].id == 2 || LevelBuilder.level[Y+1][X+1].id == 2)
		{
			LevelBuilder.damsel = true;
			exit = true;
		}
	}
	
	public void update() {
		if(dir == -1)
			tx.setToTranslation(x-Camera.x+128, y-Camera.y);
		else
			tx.setToTranslation(x-Camera.x, y-Camera.y);
		tx.scale(dir, 1);
		
		if(!grounded) { 
			vy += 2;
		}
		
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					vx *= -1;
					break;
	
				case 2:
					vx *= -1;
					break;
	
				case 3:
					vy = 0;
					grounded = true;
					flag = true;
				    break;
	
				case 4:
					vy = 0;
					break;
					
				case 0:
					if(flag == false) {
						grounded = false;
					}
					break;
				}
			}
		}
		
		detect();
		if(exit == true)
		{
			exitTimer = 30;
			vx = 0;
			vy = 0;
			Sprite = getImage("/imgs/Monsters/Damsel/damsel_enter.gif");
			if(exitTimer > 0)
			{
				exitTimer--;
			} 
		}
		
		
		
		if(panic) {
			if(dir == 1) {
				vx = 8;
			}else if(dir == -1) {
				vx = -8;
			}
		}
		
		x += vx;
		y += vy;
	}
	
	public void paint(Graphics g) {
		if(exit && exitTimer <= 0)
			return;
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
