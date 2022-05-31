package object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import Entities.Camera;
import Entities.Entity;
import General.Frame;

public class bomb extends object{

	public int timer;
	public double rotationAngle;
	
	public bomb(int x, int y, int vx, int vy) {
		super(x, y);
		this.vx = vx;
		this.vy = vy;
		w = 60;
		h = 60;
		// TODO Auto-generated constructor stub
		timer = 120;
		rotationAngle = 0.0;
		Sprite = getImage("/imgs/Items/Objects/bomb_1.png");
		grounded = false;
		fragile = false;
	}
	
	public void update() {
		tx.setToTranslation(x - Camera.x - 33, y - Camera.y - 39);
		
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				collide(block);
			}
		}

		if(timer <= 140 && timer > 20) {
			if(!grounded) {
				vy += 2;
			}
			if(vx > 0) {
				vx --;
			}else if(vx < 0) {
				vx ++;
			}
		}
		
		x += vx;
		rotationAngle += vx;
		y += vy;
		
		if(carried) {
			carried();
		}
		
		if(timer > 0) {
			timer --;
			if(timer <= 140 && timer > 110) {
				if(timer + 1 % 10 == 0) {
					Sprite = getImage("/imgs/Items/Objects/bomb_2.png");
				}else if(timer % 10 == 0) {
					Sprite = getImage("/imgs/Items/Objects/bomb_3.png");
				}else {
					Sprite = getImage("/imgs/Items/Objects/bomb_1.png");
				}
			}else if(timer <= 110 && timer > 80) {
				if(timer + 1 % 7 == 0) {
					Sprite = getImage("/imgs/Items/Objects/bomb_2.png");
				}else if(timer % 7 == 0) {
					Sprite = getImage("/imgs/Items/Objects/bomb_3.png");
				}else {
					Sprite = getImage("/imgs/Items/Objects/bomb_1.png");
				}
			}else if(timer <= 80 && timer > 50){
				if(timer + 1 % 5 == 0) {
					Sprite = getImage("/imgs/Items/Objects/bomb_2.png");
				}else if(timer % 5 == 0) {
					Sprite = getImage("/imgs/Items/Objects/bomb_3.png");
				}else {
					Sprite = getImage("/imgs/Items/Objects/bomb_1.png");
				}
			}else if(timer <= 50 && timer > 20) {
				if(timer + 1 % 3 == 0) {
					Sprite = getImage("/imgs/Items/Objects/bomb_2.png");
				}else if(timer % 3 == 0) {
					Sprite = getImage("/imgs/Items/Objects/bomb_3.png");
				}else {
					Sprite = getImage("/imgs/Items/Objects/bomb_1.png");
				}
			}else {
				if(timer == 20) {
					vx = 0;
					vy = 0;
					
					int mapX = x /128, mapY = y /128;
					
					//starts from the top
					for(int i = mapY - 1; i <= mapY + 1; i ++) {
						for(int j = mapX - 1; j <= mapX + 1; j ++) {
							if(i >= 0 && i < LevelBuilder.level.length
							&& j >= 0 && j < LevelBuilder.level[0].length
							&& LevelBuilder.level[i][j] != null) {
								LevelBuilder.level[i][j].Break();
							}
						}
					}
					
					int hitBoxX = x - 128/2, hitBoxY = y - 128/2, hitBoxW = 256, hitBoxH = 256;
					
					for(Entity e : LevelBuilder.enemies) {
						if(hitBoxX + hitBoxW > e.x
						&& hitBoxX < e.x + e.w
						&& hitBoxY + hitBoxH > e.y
						&& hitBoxY < e.y + e.h) {
							e.takeDamage(11);
							e.stunned = true;
							e.stunTimer = 240;
						}
					}
					
					if(hitBoxX + hitBoxW > Frame.Ana.x
					&& hitBoxX < Frame.Ana.x + Frame.Ana.w
					&& hitBoxY + hitBoxH > Frame.Ana.y
					&& hitBoxY < Frame.Ana.y + Frame.Ana.h) {
						Frame.Ana.takeDamage(11);
						Frame.Ana.stunned = true;
						Frame.Ana.stunTimer = 240;
						Frame.Ana.carrying = false;
					}
				}
				tx.setToTranslation(x - Camera.x - 128/2, y - Camera.y - 128/2);
				Sprite = getImage("/imgs/Items/Objects/bomb_explode.gif");
			}
		}
		
		//tx.rotate(rotationAngle * Math.PI / 180);
	}

	public void paint(Graphics g) {
		if(timer > 0) {
			update();
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(Sprite, tx, null);
			g.drawRect((int) (x - Camera.x), (int) (y - Camera.y), w, h);
		}
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