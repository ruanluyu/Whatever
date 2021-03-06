package blocks;

import processing.core.PImage;
import system.GameManager;
import system.Rectangle;

public class Item implements TouchAreaFeedBack {
	public ItemId type = ItemId.ERROR;
	protected static PImage[] imgList = null;
	public static GameManager gm = null;
	public Rectangle area = null;
	
	public static enum ItemId {
		ERROR, BEER,
	}
	
	public Item(ItemId iid) {
		type = iid;
	}

	public static void loadGameManager(GameManager g) {
		gm = g;
		imgList = gm.res.itemImgList;
	}

	public PImage getTexture() {
		int id = type.ordinal() - 1;
		if (id >= 0 && id < imgList.length)
			return imgList[id];
		return null;
	}

	public void touchArea(Rectangle rec) {
		
	}
}
