package Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Blocks.Block;

import java.awt.Graphics2D;

public class Player extends Entity{

	
	int frame;
	String state = "Walk";
	BufferedImage spriteSheet;
	int character = 4;
	public boolean debug = false;
	
	public Player(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		grounded = false;
		frame = 0;
	}
	
	public void jump() {
		if(debug){
			return;
		}
		if(grounded) {
			frame = 0;
			vy = -35;
		}
		grounded = false;
	}

	public void updateD() {
		Sprite = splice(0, 0);
		visible = true;
		x += vx*5;
		y -= vy*5;
		Camera.update();
		tx.setToTranslation(x-Camera.x, y-Camera.y);
		frame++;
	}
	
	public void update() {

		//accelerate downwards if not on ground
		if(!grounded)
			vy += 2;

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
			if(Math.abs(vx) < 1)
				state = "Standing";
			else
				state = "Walking";
		}


		correctClipping();
		
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
		tx.setToTranslation(x-Camera.x, y-Camera.y);
		//tx.scale(-1, 1);
		frame++;
	}

	@Override
	public void paint(Graphics g) {
		if(debug){
			updateD();
		}
		else{
			update();
		}
		if(!visible){
			return;
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);
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

}
