package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;

public class bomb extends Entity{

	public int timer;
	
	public bomb(int x, int y) {
		super(x, y, 60, 60, true, "");
		// TODO Auto-generated constructor stub
		timer = 120;
		Sprite = getImage("/imgs/Items/Objects/bomb_1.png");
		grounded = false;
		vy = 0;
	}
	
	public void update() {
		tx.setToTranslation(x - Camera.x - 33, y - Camera.y - 39);
		
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					dir *= -1;
					break;
	
				case 2:
					dir *= -1;
					break;
	
				case 3:
					vy = 0;
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

		if(timer <= 140 && timer > 20) {
			if(!grounded) {
				vy += 2;
			}
		}
		
		x += vx;
		y += vy;
		
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
					int mapX = x /128, mapY = y /128;
					int count = 0, offset = 2;
					
					//starts from the top
					for(int i = mapY - 2; i <= mapY + 2; i ++) {
						for(int j = mapX - 2; j <= mapX + 2; j ++) {
							if(i >= 0 && i < LevelBuilder.level.length
							&& j >= 0 && j < LevelBuilder.level[0].length
							&& LevelBuilder.level[i][j] != null) {
								LevelBuilder.level[i][j].Break();
							}
						}
					}
				}
				tx.setToTranslation(x - Camera.x - 128/2, y - Camera.y - 128/2);
				Sprite = getImage("/imgs/Items/Objects/bomb_explode.gif");
			}
		}
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
