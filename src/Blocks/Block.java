package Blocks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Entities.Camera;
import Entities.Entity;

import java.net.URL;

public class Block {
	
	public int x, y, width, height, killX, killY;
	public int id;
	boolean breakable, climbable;
	public Entity item;
    public Image Sprite;
	public boolean solid;
    public AffineTransform tx;
    public static ArrayList<Block> blocks = new ArrayList<Block>();
	
    public Block(int id, int x, int y) {
		int index = (int)(Math.random() * 6) + 1;
		this.id = id;
		switch(id){
			case 0:
			Sprite = getImage("/imgs/Tiles/" + Integer.toString(1 + LevelBuilder.levelNum/4) + "/1_" + index + ".png");
			solid = true;
			break;
			case 1:
			Sprite = getImage("/imgs/Tiles/" + Integer.toString(1 + LevelBuilder.levelNum/4) + "/" + id + "_" + index + ".png");
			solid = true;
			break;
			default:
			Sprite = getImage("/imgs/Tiles/" + Integer.toString(1 + LevelBuilder.levelNum/4) + "/" + id  + ".png");
			solid = false;
			break;
		}
    	this.x = x;
    	this.y = y;
    	width = 128;
    	height = 128;
    	killX = x + width/2;
    	killY = y + height/2;
		tx = AffineTransform.getTranslateInstance(x, y);
    	blocks.add(this);
    }

	public Block(int id, int x, int y, int world) {
		int index = (int)(Math.random() * 6) + 1;
		this.id = id;
		switch(id){
			case 0:
			Sprite = getImage("/imgs/Tiles/" + world + "/1_" + index + ".png");
			solid = true;
			break;
			case 1:
			Sprite = getImage("/imgs/Tiles/" + world + "/" + id + "_" + index + ".png");
			solid = true;
			break;
			default:
			Sprite = getImage("/imgs/Tiles/" + world + "/" + id  + ".png");
			solid = false;
			break;
		}
    	this.x = x;
    	this.y = y;
    	width = 128;
    	height = 128;
    	killX = x + width/2;
    	killY = y + height/2;
		tx = AffineTransform.getTranslateInstance(x, y);
    	blocks.add(this);
    }
    
    public int[] getGrid() {
    	int[] result = {x/64, y/64};
    	return result;
    }
    
	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);

	}
	
	public void Break() {
		//something to spawn the item that it carries
	}
	
	public void update() {
		tx.setToTranslation((int)(x-Camera.x), (int)(y-Camera.y));
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Block.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}
	
	public String toString() {
		return "block";
	}
	
}
