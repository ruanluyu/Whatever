package blocks;


import system.Rectangle;

public class Block_Thorn extends Block implements TouchAreaFeedBack, NeedInitialize, Penetrable {

	public Block_Thorn(int idx, int idy) {
		super(Block.BlockId.THORN, idx, idy);
		//buildArea(.3f, .5f);
		buildArea(.1f, .1f);
	}

	@Override
	public void touchArea(Rectangle rec) {
		if (!rec.intersected(area)) {
			return;
		}
		type = Block.BlockId.THORN_T;
		gm.man.kill();
	}

	@Override
	public void initialize() {
		type = Block.BlockId.THORN;
	}

	@Override
	public void penetrate() {

	}

	/*@Override
	public void renderFromCamera() {
		gm.parent.noFill();
		gm.parent.rect(area.p1x(), area.p1y(), area.getWidth(), area.getHeight());
	}*/

}
