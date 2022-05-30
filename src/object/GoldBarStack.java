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

public class GoldBarStack extends Treasure{
	
	public int value;
	public boolean collected;
	
    public GoldBarStack(int x, int y) {
    	super(x,y);
    	value = 500 + LevelBuilder.levelNum * 125;
    	Sprite = getImage("/imgs/Items/Objects/gold_bar_stack.png");
    }
}