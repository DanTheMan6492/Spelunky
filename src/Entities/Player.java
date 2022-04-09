package Entities;

import java.awt.Graphics;
import java.awt.Image;
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
	int character = 1;
	int dir;
	
	public Player(double x, double y, double w, double h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		grounded = false;
		frame = 0;
	}
	
	public void jump() {
		if(grounded) {
			vy = 30;
		}
		grounded = false;
	}
	
	@Override
	public void update() {
		double temp = vy;
		if(vx < 0) {
			dir = -1;
		} else {
			dir = 1;
		}
		if(!grounded) {
			state = "Falling";
			vy -= 2;
			y -= vy;
			if(vy < 0) {
				state = "Falling";
			} else {
				state = "Jumping";
			}
		} else {
			if(vx == 0) {
				state = "Standing";
			} else {
				state = "Walking";
			}
		}
		x+=vx;
		
		double ratioy = -vy/(vy+vx);
		double ratiox = -vx/(vy+vx);
		
		Block block = checkClipping();
		while(checkClipping(block) != 0) {
			x += ratiox;
			y += ratioy;
		}
		
		if(checkStanding(block)) {
			if(!grounded)
				state = "Standing";
				frame = 0;
			grounded = true;
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
				Sprite = splice(0, 1 + frame%7);
				break;
		}
		
		tx.setToTranslation(x, y);
		frame++;
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
		
		int xOffset = border + col*(width+2*border);
		int yOffset = border + row*(width+2*border);
		
        for(int y = 0; y < 114; y++){
        	for(int x = 0; x < 114; x++){
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
