package Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;

public class Player extends Entity{

	
	int frame;
	String state = "Walk";
	BufferedImage spriteSheet;
	int character = 1;
	
	public Player(double x, double y, double w, double h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		grounded = false;
		frame = 0;
	}
	
	@Override
	public void update() {
		if(!grounded) {
			System.out.println(vy);
			vy-= 2;
			y -= vy;
			
		}
		
		double ratiox = -vy/(vy+vx);
		double ratioy = -vx/(vy+vx);
		while(checkClipping() != 0) {
			x += ratiox;
			y += ratioy;
		}
		
		frame++;
		if(state.equals("Idle")) {
			Sprite = splice(0, 2);
		}
		else {
			Sprite = splice(0, 1 + frame%7);
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
