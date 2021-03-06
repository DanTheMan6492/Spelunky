//me likes
//cool game
//very interesting
//fun more instructions pls
//crazy game!
//cool game, don't really get the objective tho

package Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Transition;

import java.awt.Graphics2D;

public class Player extends Entity{

	
	static int frame;
	String state = "Walk";
	static BufferedImage spriteSheet;
	public static int character = 14;
	public boolean debug = false;
	public boolean ready = false;
	public boolean carrying;
	public double vxBuffer;
	public int itemHeld;
	public int bombs = 4;
	public int ropes = 4;
	public int money = 0;
	public int blood = 0;
	public static int max = 0;
	public int invincibleTimer;
	public boolean[] equipables = {false, false, false,
								   false, false, false,
								   false, false, false};
	
	public boolean parachuting = false;
	public static boolean whipping = false;
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
	
	public static void whip(){
		frame = 0;
		whipping = true;
	}
	public ArrayList<Integer> items = new ArrayList<Integer>();
	public Player(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		for(int i = 0; i < 7; i++){
			items.add(i);
		}
		dir = -1;
		grounded = false;
		frame = 0;
		health = 4;
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
	
	public void takeDamage(int damage) {
		if(health == 0 || invincibleTimer > 0) {return;}
		
		if(health - damage > 0) {
			health -= damage;
			invincibleTimer = 30;
		}else {
			die();
		}
	}
	
	public void die() {
		health = 0;
		stunTimer = 60;
		max = 0;
		File myObj;
		try {
			myObj = new File("Highscores");
			Scanner myReader = new Scanner(myObj);
			max = myReader.nextInt();
			myReader.close();
			if(money > max){
				max = money;
				try {
					PrintWriter writer = new PrintWriter(myObj);
					writer.print(money);
					writer.close();
				} catch (IOException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		
	}

	public void updateD() {
		Sprite = splice(0, 0);
		visible = true;
		x += vxBuffer*5;
		y -= vy*5;
		tx.setToTranslation(x-Camera.x, y-Camera.y);
		frame++;
	}
	
	public void update() {
		ready = false;

		//accelerate downwards if not on ground
		if(!grounded && !state.equals("Climbing")) { 
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
		if(vx < 0) 
			dir = -1;
		else if(vx > 0)
			dir = 1;
		
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
		if(!state.equals("Climbing"))
			x += vx;
		y += vy;
		
		if(stunned) {
			if(vx > 0) {
				vx --;
			}else if(vx < 0) {
				vx ++;
			}
			
			if(dir == 1) {
				if(vx > 0) {
					Sprite = splice(2, 0);
				}else if(vx < 0) {
					Sprite = splice(2, 1);
				}else {
					Sprite = splice(0, 9);
				}
			}else { 
				if(vx < 0) {
					Sprite = splice(2, 0);
				}else if(vx > 0) {
					Sprite = splice(2, 1);
				}else {
					Sprite = splice(0, 9);
				}
			}
			
			if(vy < -20) {
				Sprite = splice(2, 3);
			}else if(vy > 20) {
				Sprite = splice(2, 4);
			}
			
			if(stunTimer > 0 && health > 0) {
				stunTimer --;
				if(stunTimer == 0) {
					stunned = false;
				}
			}
		}else {
			//update player animation and state
			if(!whipping){
				if(!grounded) {
					state = "Falling";

					//reset animation frame if when switching animations
					if((vy+2)/vy <= 0) 
						frame = 0;

					if(vy > 0) 
						state = "Falling";
					else
						state = "Jumping";

				} else {
					if(Math.abs(vx) < 2)
						state = "Standing";
					else
						state = "Walking";
				}
			} else{
				state = "Whipping";
				if(frame == 10)
					whipping = false;
			}
			
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
			case "Whipping":
				if(frame < 5){
					Sprite = splice(4, frame);
				} else{
					Sprite = splice(4, 5);
				}
				break;
			}
			vx = vxBuffer;
		}
		
		if(invincibleTimer > 0) {
			invincibleTimer --;
		}
		
		if(dir == -1)
			tx.setToTranslation(x-Camera.x+128, y-Camera.y);
		else
			tx.setToTranslation(x-Camera.x, y-Camera.y);
		tx.scale(dir, 1);
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
		if(invincibleTimer > 0 && invincibleTimer % 4 == 0) {
			
		}else {
			g2.drawImage(Sprite, tx, null);
		}
		g.drawRect((int)(x - Camera.x), (int)(y - Camera.y), w, h);
	}
	
	
	public static BufferedImage splice(int row, int col) {
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

		if(LevelBuilder.level[Y][X] == null)
			return;
		
		Thread transition = new Thread(new Transition());
		if(LevelBuilder.level[Y][X].id == 2)
			transition.start();
		if(LevelBuilder.level[Y+1][X].id == 2)
			transition.start();
		if(LevelBuilder.level[Y][X+1].id == 2)
			transition.start();
		if(LevelBuilder.level[Y+1][X+1].id == 2)
			transition.start();

	}

	public void climb(float yDelta) {
		int X = (int) (x+64) / 128;
		int Y = (int) (y+64) / 128;

		System.out.println("poggersq");
		if(LevelBuilder.level[Y][X] == null || LevelBuilder.level[Y][X].id != 4){
			System.out.println("poggersn");
			if(state.equals("Climbing")){
				state = "Falling";
			}
			return;
		} else{
			System.out.println("poggers1");
			state = "Climbing";
			vy -= yDelta * 5;
		}

	}

}