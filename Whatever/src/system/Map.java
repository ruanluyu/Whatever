package system;

import blocks.Block;
import blocks.Block_Air;
import processing.core.PApplet;

public class Map implements RenderableFromCamera, NeedUpdate {
	public Block[] blist;
	public Block[] renderBlist;
	public int width = 0;
	public int height = 0;
	public int renderW = 0;
	public int renderH = 0;
	public static final int BLOCKSIZE = 40;
	public Rectangle renderArea = null;
	public GameManager gm;

	Map(GameManager gm) {
		this.gm = gm;
		renderArea = new Rectangle(0, 0, gm.parent.width, gm.parent.height);
		renderArea.setCenterPointToCenter();
	}

	Map(int w, int h, GameManager gm) {
		this(gm);
		initialize(w, h);
	}

	private void initialize(int idw, int idh) {
		blist = null;
		blist = new Block[idw * idh];
		renderW = (gm.parent.width - gm.parent.width % Map.BLOCKSIZE + Map.BLOCKSIZE);
		renderH = (gm.parent.height - gm.parent.height % Map.BLOCKSIZE + Map.BLOCKSIZE);
		renderBlist = new Block[renderW * renderH];
		width = idw;
		height = idh;
		for (int j = 0; j < idh; j++) {
			for (int i = 0; i < idw; i++) {
				blist[j * idw + i] = Block.Builder(Block.BlockId.AIR, i, j);
			}
		}
		initializeRBList();
	}

	private void initializeRBList() {
		for (int i = 0; i < renderBlist.length; i++) {
			renderBlist[i] = null;
		}
	}

	public void set(int idx, int idy, Block.BlockId blockId) {
		blist[idx + idy * width] = Block.Builder(blockId, idx, idy);
	}

	public void set(int idx, int idy, Block.BlockId blockId, int rotate90) {
		Block tar = Block.Builder(blockId, idx, idy);
		tar.rotate90 = rotate90;
		blist[idx + idy * width] = tar;
	}

	public void set(int idx1, int idy1, int idx2, int idy2, Block.BlockId blockId) {
		set(idx1, idy1, idx2, idy2, blockId, 0);
	}

	public void set(int idx1, int idy1, int idx2, int idy2, Block.BlockId blockId, int rotate90) {
		if (idx1 > idx2) {
			int c = idx1;
			idx1 = idx2;
			idx2 = c;
		}
		if (idy1 > idy2) {
			int c = idy1;
			idy1 = idy2;
			idy2 = c;
		}
		for (int i = idx1; i <= idx2; i++) {
			for (int j = idy1; j <= idy2; j++) {
				set(i, j, blockId, rotate90);
			}
		}
	}

	public int getIdFromPos(float x, float y) {
		int id = 0;
		if (x < 0 || x >= width * BLOCKSIZE || y < 0 || y >= height * BLOCKSIZE) {
			return -1;
		}
		id = (int) Math.floor(x / BLOCKSIZE);
		id += width * (int) Math.floor(y / BLOCKSIZE);
		return id;
	}

	public Block getBlockFromPos(float x, float y) {
		int id = getIdFromPos(x, y);
		if (id == -1)
			return null;
		return blist[id];
	}

	public int getIdFromIdPos(int idx, int idy) {
		if (idx < 0 || idx >= width || idy < 0 || idy >= height) {
			return -1;
		}
		return idy * width + idx;
	}

	public Block getBlockFromIdPos(int idx, int idy) {
		int id = getIdFromIdPos(idx, idy);
		if (id == -1)
			return null;
		return blist[id];
	}

	public int getIdFromOneFloat(float a) {
		return (int) Math.floor(a / BLOCKSIZE);
	}

	public void renderFromCamera() {
		for (Block bl : renderBlist) {
			if (bl == null)
				continue;
			if (!(bl instanceof Block_Air)) {
				if (bl.rotate90 != 0) {
					gm.parent.pushMatrix();
					gm.parent.rotate(bl.rotate90 * PApplet.HALF_PI);
					gm.parent.translate(-BLOCKSIZE / 2f, -BLOCKSIZE / 2f);
				}
				gm.parent.image(bl.getTexture(), bl.idx * BLOCKSIZE, bl.idy * BLOCKSIZE);
				if (bl.rotate90 != 0)
					gm.parent.popMatrix();
			}
			if (bl instanceof RenderableFromCamera)
				((RenderableFromCamera) bl).renderFromCamera();

		}
	}

	@Override
	public void update() {
		initializeRBList();
		renderArea.moveCenterTo(gm.cam.p);
		int startIdX = getIdFromOneFloat(renderArea.p1x());
		int endIdX = getIdFromOneFloat(renderArea.p2x());
		int startIdY = getIdFromOneFloat(renderArea.p1y());
		int endIdY = getIdFromOneFloat(renderArea.p2y());
		Block bl = null;
		for (int j = startIdY; j <= endIdY; j++) {
			for (int i = startIdX; i <= endIdX; i++) {
				bl = getBlockFromIdPos(i, j);
				if (bl == null)
					continue;
				renderBlist[i - startIdX + (j - startIdY) * renderW] = bl;
				if (bl instanceof NeedUpdate)
					((NeedUpdate) bl).update();
			}
		}
	}

}
