package Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Transition;

import java.awt.Graphics2D;

public class Player extends Entity{

	
	int frame;
	String state = "Walk";
	BufferedImage spriteSheet;
	int character = 14;
	public boolean debug = false;
	public boolean ready = false;
	public double vxBuffer;
	public boolean[] equipables = {false, false, false,
								   false, false, false,
								   false, false, false};
	public boolean parachuting = false;
	/*Indexes:
	 * 0 climbingGloves
	 * 1 pitchersMitt
	 * 2 springShoes
	 * 3 spikeShoes
	 * 4 bombPaste
	 * 5 spectacles
	 * 6 compass
	 * 7 parachute
	 * 8 kapala
	 */
	
	public ArrayList<Integer> items = new ArrayList<Integer>();
	public Player(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		dir = -1;
		grounded = false;
		frame = 0;
	}
	
	public void jump() {
		if(!LevelBuilder.ready){
			Thread transition = new Thread(new Transition());
			transition.start();
		}
		if(debug){
			return;
		}
		if(grounded) {
			frame = 0;
			y--;
			if(!equipables[2]) {
				vy = -35; //30
			}else {
				vy = -45;
			}
		}
		grounded = false;
	}

	public void updateD() {
		Sprite = splice(0, 0);
		visible = true;
		x += vxBuffer*5;
		y -= vy*5;
		Camera.update();
		tx.setToTranslation(x-Camera.x, y-Camera.y);
		frame++;
	}
	
	public void update() {
		ready = false;

		//accelerate downwards if not on ground
		if(!grounded) { 
			if(parachuting == false || (parachuting && vy < 2)) {
				vy += 2;
			}else {
				vy = 5;
			}
		}
		
		if(!grounded && vy > 40 && equipables[7]) {
			parachuting =true;
			vy = -4;
		}

		//update player direction
		if(vx < -0.5) 
			dir = -1;
		else if(vx > 0.5)
			dir = 1;

		//update player animation and state
		if(!grounded) {
			state = "Falling";

			//reset animation frame if when switching animations
			if((vy+2)/vy <= 0) 
				frame = 0;
			
			if(vy < 0) 
				state = "Falling";
			else
				state = "Jumping";

		} else {
			if(Math.abs(vx) < 2)
				state = "Standing";
			else
				state = "Walking";
		}
		
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					if(equipables[0]) {
						vy = 0;
						grounded = true;
						flag = true;
					}
					break;
	
				case 2:
					if(equipables[0]) {
						vy = 0;
						grounded = true;
						flag = true;
					}
					break;
	
				case 3:
					vy = 0;
					grounded = true;
					flag = true;
					if(parachuting) {
						parachuting = false;
					}
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
		
		if(!flag) {
			grounded = false;
		}
		x += vx;
		y += vy;

		
		switch(state) {
			case "Standing":
				Sprite = splice(0, 0);
				break;
			case "Falling":
				if(frame < 6) {
					Sprite = splice(9, 4+frame/2);
				} else {
					Sprite = splice(9, 7);
				}
				break;
			case "Walking":
				Sprite = splice(0, 1 + (frame/2)%7);
				break;
			case "Jumping":
				if(frame < 3) {
					Sprite = splice(9, frame);
				} else {
					Sprite = splice(9, 3);
				}
				break;
		}
		Camera.update();
		if(dir == -1)
			tx.setToTranslation(x-Camera.x+128, y-Camera.y);
		else
			tx.setToTranslation(x-Camera.x, y-Camera.y);
		tx.scale(dir, 1);
		vx = vxBuffer;
		frame++;
		ready = true;
	}

	@Override
	public void paint(Graphics g) {
		if(LevelBuilder.buildRoom)
			return;
		
		if(LevelBuilder.ready){
			if(debug){
				updateD();
			}
			else{
				update();
			}
		}
		if(!visible){
			return;
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);
		g.drawRect((int)(x - Camera.x), (int)(y - Camera.y), w, h);
	}
	
	
	public BufferedImage splice(int row, int col) {
		int width = 128;
		int border = 0;
		BufferedImage sprite = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
		String path = "src/imgs/Characters/Spliced/"+String.valueOf(character) + "-" + String.valueOf(row) + "-" + String.valueOf(col) + ".png";
		try {
			BufferedImage result = ImageIO.read(new File(path));
			return result;
		} catch(IOException e) {
		}
		try {
			spriteSheet = ImageIO.read(new File("src/imgs/Characters/"+String.valueOf(character) + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int xOffset = border + col*(width);
		int yOffset = border + row*(width);
		
        for(int y = 0; y < 128; y++){
        	for(int x = 0; x < 128; x++){
        		sprite.setRGB(x, y, spriteSheet.getRGB(x+xOffset, y+yOffset));
            }
        }
        File output = new File(path);
        
        try {
            ImageIO.write(sprite, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
		
	}

	public void door() {

		if(!LevelBuilder.ready)
			return;
		int X = (int) x / 128;
		int Y = (int) y / 128;

		Thread transition = new Thread(new Transition());
		if(LevelBuilder.level[Y][X].id == 6)
			transition.start();
		if(LevelBuilder.level[Y+1][X].id == 6)
			transition.start();
		if(LevelBuilder.level[Y][X+1].id == 6)
			transition.start();
		if(LevelBuilder.level[Y+1][X+1].id == 6)
			transition.start();

	}

}