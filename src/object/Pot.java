package object;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Entities.Camera;
import Entities.Entity;
import General.Frame;

import java.net.URL;

public class Pot extends object{
	
    public Pot(int x, int y) {
    	super(x, y);
		tx = AffineTransform.getTranslateInstance(x, y);
		fragile = false;
		w = 50;
		h = 50;
    }

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Pot.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}
}