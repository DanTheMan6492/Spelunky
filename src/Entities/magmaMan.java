package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;

public class magmaMan extends Entity{
	
	public int liveTimer;
	public int waitTimer;
	public int moveTimer;
	public boolean interactable;
	
	public magmaMan(int x, int y, int w, int h, boolean visible, String path) 
	{
		super(x, y, w, h, visible, path);
		liveTimer = 780;
	}
	
	public void jump() 
	{
		vy = -30;
		Sprite = getImage("/imgs/Monsters/Magmar/magmar_spawn.gif");
	}
	
	public void detect() {
		int mapX = x/128, mapY = y/128;
		
		if(LevelBuilder.level[mapY][mapX].solid) {
			
		}
	}

	public void update() {
		if(dir == -1)
			tx.setToTranslation(x-Camera.x+128, y-Camera.y);
		else
			tx.setToTranslation(x-Camera.x, y-Camera.y);
		tx.scale(dir, 1);
		
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					Sprite = getImage("/imgs/Monsters/Magmar/magmar_walk.gif");
					dir *= -1;
					break;
	
				case 2:
					Sprite = getImage("/imgs/Monsters/Magmar/magmar_walk.gif");
					dir *= -1;
					break;
	
				case 3:
					grounded = true;
					flag = true;
				    break;
	
				case 4:
					break;
					
				case 0:
					if(flag == false) {
						grounded = false;
					}
					break;
				}
			}
		}
				
		if(!grounded) 
		{
			vy += 2;
			Sprite = getImage("/imgs/Monsters/Magmar/magmar_spawn.gif");
			waitTimer = 20;
		}
		else
		{
			liveTimer--;
			if (liveTimer <= 0)
			{
				interactable = false;
			}
			else
			{
				Sprite = getImage("/imgs/Monsters/Magmar/magmar_walk.gif");
				if(waitTimer > 0)
				{
					waitTimer--;
					if(waitTimer == 0)
					{
						jump();
					}
				}
				
				
				if(waitTimer > 0){
					waitTimer--;
					if(waitTimer <= 0){
						vx = 8*dir;
						moveTimer = 22;
						Sprite = getImage("/imgs/Monsters/Caveman/cavemanWalk.gif");

					}
				} else if(moveTimer > 0){
					moveTimer--;
				} else{
					vx = 0;
					waitTimer = 20;
					Sprite = getImage("/imgs/Monsters/Caveman/cavemanStand.gif");

				}
				
			}
			
		}
		
		
		
		x += vx;
		y += vy;
	}
	
	public void paint(Graphics g) {
		if(!interactable && liveTimer <= 0)
			visible = false;
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
