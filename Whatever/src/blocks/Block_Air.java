package blocks;

public class Block_Air extends Block implements Penetrable{

	public Block_Air(int idx,int idy) {
		super(Block.BlockId.AIR,idx,idy);
	}

	@Override
	public void penetrate() {
	}
	
	

}
