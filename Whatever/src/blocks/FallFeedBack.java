package blocks;

/**
 * 落在这个方块上会有相应的反应，可以用于设计一些碎砖之类的陷阱。
 * @author Hosine
 *
 */
public interface FallFeedBack {
	public void fallToThisBlock();
}
