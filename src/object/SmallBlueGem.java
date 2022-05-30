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

public class SmallBlueGem extends Treasure{
	
	public int value;
	public boolean collected;
	
    public SmallBlueGem(int x, int y) {
    	super(x,y);
    	value = 300 + LevelBuilder.levelNum * 75;
    	Sprite = getImage("/imgs/Items/Objects/small_gem_blue.png");
    }
}