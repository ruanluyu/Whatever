package blocks;

public class Block_Air extends Block implements Penetrable{

	public Block_Air() {
		super(Block.BlockId.AIR);
	}

	@Override
	public void penetrate() {
	}
	
	

}