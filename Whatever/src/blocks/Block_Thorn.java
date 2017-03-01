package blocks;

public class Block_Thorn extends Block implements TouchFeedBack{

	public Block_Thorn() {
		super(Block.BlockId.THORN);
	}
	
	public Block_Thorn(int r90){
		this();
		rotate90 = r90;
	}
	@Override
	public void touch() {
		type = Block.BlockId.THORN_T;
		gm.man.kill();
	}
	
}
