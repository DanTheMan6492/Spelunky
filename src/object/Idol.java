package object;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Blocks.Block;
import Blocks.LevelBuilder;
import Entities.Camera;
import Entities.Entity;
import General.Frame;

import java.net.URL;

public class Idol extends object{
	
	public int value;
	public boolean collected;
	
    public Idol(int x, int y) {
    	super(x,y);
    	grounded = false;
    	collected = false;
    	value = 5000 + LevelBuilder.levelNum * 5000;
    	Sprite = getImage("/imgs/Items/Objects/gold_idol.png");
    }
    
    public void checkCollision() {
    	int X = (int) x / 128;
		int Y = (int) y / 128;
		
    	if(LevelBuilder.level[Y][X].id == 2) {
    		collected = true;
    		Frame.Ana.money += value;
    	}
    }
}