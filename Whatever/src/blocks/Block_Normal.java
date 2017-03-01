package blocks;

import system.Map;
import system.RenderableFromCamera;

public class Block_Normal extends Block implements FallFeedBack,RenderableFromCamera{

	public Block_Normal() {
		super(Block.BlockId.BLOCK_NORMAL);
	}
	
	boolean flag = false;

	@Override
	public void fallToThisBlock() {
		flag = true;
	}

	@Override
	public void renderFromCamera() {
		if(flag){
			gm.parent.fill(255,0,0);
			gm.parent.text("Och!",idx*Map.BLOCKSIZE,idy*Map.BLOCKSIZE-40);
		}
	}

}