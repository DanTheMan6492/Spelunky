package object;

import Entities.Entity;

public class Item extends Entity
{
	public boolean pickedUp;

	public Item(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
		pickedUp = false;
	}
	
	public void pickup(){}
}
