package Entities;

import General.Frame;

public class spectacles extends Item{

	public spectacles(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		Sprite = getImage("/imgs/object/HUD/item_5.png");

		// TODO Auto-generated constructor stub
	}
	public void pickup()
	{
		if (Frame.Ana.x + Frame.Ana.w > x && Frame.Ana.x < x + w && Frame.Ana.y + Frame.Ana.h > y && Frame.Ana.y < y + w)
		{
			pickedUp = true;
			Frame.Ana.equipables[5] = true;
		}
	}
}
