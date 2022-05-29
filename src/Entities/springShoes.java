package Entities;

import General.Frame;

public class springShoes extends Item{

	public springShoes(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		Sprite = getImage("/imgs/Items/HUD/item_2.png");
		// TODO Auto-generated constructor stub
	}

	public void pickup()
	{
		if (Frame.Ana.x + Frame.Ana.w > x && Frame.Ana.x < x + w && Frame.Ana.y + Frame.Ana.h > y && Frame.Ana.y < y + w)
		{
			pickedUp = true;
			Frame.Ana.equipables[2] = true;
		}
	}
}
