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

public class BigBlueGem extends Treasure{
	
	public int value;
	public boolean collected;
	
    public BigBlueGem(int x, int y) {
    	super(x,y);
    	value = 1200 + LevelBuilder.levelNum * 300;
    }
}