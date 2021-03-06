package blocks;

import processing.core.PImage;
import system.GameManager;
import system.Map;
import system.Rectangle;

public abstract class Block {
	public BlockId type = BlockId.ERROR;
	public int idx = -1;
	public int idy = -1;
	public Rectangle area = null;
	public int rotate90 = 0;
	protected static PImage[] imgList = null;
	public static GameManager gm = null;

	public static enum BlockId {
		ERROR, AIR, BLOCK_NORMAL, BLOCK_SUSPENDED, THORN, THORN_T
	}

	public Block(BlockId bid, int idx, int idy) {
		type = bid;
		this.idx = idx;
		this.idy = idy;
	}

	public void buildArea(float ratioX, float ratioY) {
		area = new Rectangle(0, 0, Map.BLOCKSIZE * ratioX, Map.BLOCKSIZE * ratioY);
		area.setCenterPointByRatio(.5f, 1f);
		area.moveCenterTo(Map.BLOCKSIZE / 2f, Map.BLOCKSIZE);
		area.setCenterPointByFloat(Map.BLOCKSIZE / 2f, Map.BLOCKSIZE / 2f);
		area.translate(idx * Map.BLOCKSIZE, idy * Map.BLOCKSIZE);
	}

	public void rotateArea(int r90) {
		rotate90 = r90;
		if (area != null)
			area.rotate(rotate90);
	}

	public static void loadGameManager(GameManager g) {
		gm = g;
		imgList = gm.res.materialImgList;
	}

	public static Block Builder(BlockId type, int idx, int idy) {

		Block block = null;
		switch (type) {
		case AIR:
			block = new Block_Air(idx, idy);
			break;
		case BLOCK_NORMAL:
			block = new Block_Normal(idx, idy);
			break;
		case BLOCK_SUSPENDED:
			block = new Block_Suspended(idx, idy);
			break;
		case THORN:
			block = new Block_Thorn(idx, idy);
			break;
		default:
			block = new Block_Error(idx, idy);
		}
		return block;
	}

	public PImage getTexture() {
		int id = type.ordinal() - 1;
		if (id >= 0 && id < imgList.length)
			return imgList[id];
		return null;
	}
}
