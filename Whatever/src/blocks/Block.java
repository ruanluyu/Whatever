package blocks;

import processing.core.PImage;
import system.GameManager;
import system.ImgIdMessage;
import system.Rectangle;
import system.ResManager;

public abstract class Block {
	public BlockId type = BlockId.ERROR;
	public int idx = -1;
	public int idy = -1;
	public Rectangle area = null;
	public int rotate90 = 0;
	protected static PImage[] imgList = null;
	public static GameManager gm = null;

	public enum BlockId {
		ERROR, AIR, BLOCK_NORMAL, BLOCK_SUSPENDED, THORN, THORN_T
	}

	public Block(BlockId bid) {
		type = bid;
	}

	public static void loadGameManager(GameManager g) {
		gm = g;
		imgList = new PImage[ResManager.imgMaterialId.values().length - 1];
		ImgIdMessage imgMessage = new ImgIdMessage();
		imgMessage.materialId = ResManager.imgMaterialId.MATERIAL_00;
		int startId = imgMessage.getId();
		for (int i = startId; i < startId + imgList.length; i++) {
			imgList[i] = gm.res.getImage(i);
		}
	}

	public static Block Builder(BlockId type,int idx,int idy) {
		
		Block block = null;
		switch (type) {
		case AIR:
			block = new Block_Air();
			break;
		case BLOCK_NORMAL:
			block = new Block_Normal();
			break;
		case BLOCK_SUSPENDED:
			block = new Block_Suspended();
			break;
		case THORN:
			block = new Block_Thorn();
			break;
		default:
			block = new Block_Error();
		}
		block.idx = idx;
		block.idy = idy;
		return block;
	}

	public PImage getTexture() {
		if (type.ordinal() - 1 >= 0)
			return imgList[type.ordinal() - 1];
		return null;
	}
}
