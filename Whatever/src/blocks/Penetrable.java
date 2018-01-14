package blocks;

/**
 * 把这个方块设置成可以穿透的。例如空气，或者用于设计一些其他的特殊方块。
 * @author Hosine
 *
 */
public interface Penetrable {
	public void penetrate();
}
