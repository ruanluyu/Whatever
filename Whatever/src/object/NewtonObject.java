package object;

import blocks.Block;
import blocks.BottomKnockable;
import blocks.FallFeedBack;
import blocks.Penetrable;
import blocks.TouchFeedBack;
import processing.core.PApplet;
import processing.core.PVector;
import system.GameManager;
import system.Map;
import system.NeedUpdate;
import system.Rectangle;
import system.RenderableFromCamera;

public abstract class NewtonObject implements RenderableFromCamera, NeedUpdate {
	public static PVector g = new PVector(0, .2f);
	public PVector lastP = new PVector(0, 0);
	public PVector p = new PVector(0, 0);
	public PVector v = new PVector(0, 0);
	public PVector a = new PVector(0, 0);
	public float f = .05f;// resistance
	public static GameManager gm;
	public Rectangle lastBody = null;
	public Rectangle body = null;
	public boolean onFloor = false;

	public float bottomCPDis = 10;
	public float upCPDis = 10;

	public NewtonObject(int width, int height) {
		body = new Rectangle(0, 0, width, height);
		body.translate(-width / 2f, -height);
		body.setCneterPoint(.5f, 1);
		lastBody = body.clone();
		bottomCPDis = (width - 1) / 2f;
		upCPDis = bottomCPDis;
	}

	public void addGravity() {
		if (!onFloor)
			a.add(g);
	}

	private int numOfBodyPoints = 7;

	public void rightCheck() {
		if (v.x <= 0)
			return;

		int numOfCPoints = PApplet.max(numOfBodyPoints, 2 + (int) (body.getHeight() * 2f / Map.BLOCKSIZE));
		float r = 0;
		for (int i = 1; i < numOfCPoints; i++) {
			r = (float) i / numOfCPoints;
			checkBlockFromLine(lastP.x + body.getWidth() / 2f, lastP.y - body.getHeight() * r,
					p.x + body.getWidth() / 2f, p.y - body.getHeight() * r, fixFunc_R);
			if (v.x == 0) {
				return;
			}
		}
	}

	public void leftCheck() {
		if (v.x >= 0)
			return;

		int numOfCPoints = PApplet.max(numOfBodyPoints, 2 + (int) (body.getHeight() * 2f / Map.BLOCKSIZE));
		float r = 0;
		for (float i = 1; i < numOfCPoints; i++) {
			r = i / numOfCPoints;
			checkBlockFromLine(lastP.x - body.getWidth() / 2f, lastP.y - body.getHeight() * r,
					p.x - body.getWidth() / 2f, p.y - body.getHeight() * r, fixFunc_L);
			if (v.x == 0) {
				return;
			}
		}
	}

	public void upCheck() {
		if (onFloor)
			return;
		if (v.y > 0)
			return;

		checkBlockFromLine(lastP.x, lastP.y - body.getHeight(), p.x, p.y - body.getHeight(), fixFunc_U);
		if (v.y > 0)
			return;
		checkBlockFromLine(lastP.x + upCPDis, lastP.y - body.getHeight(), p.x + upCPDis, p.y - body.getHeight(),
				fixFunc_U);
		if (v.y > 0)
			return;
		checkBlockFromLine(lastP.x - upCPDis, lastP.y - body.getHeight(), p.x - upCPDis, p.y - body.getHeight(),
				fixFunc_U);
	}

	public void fallCheck() {
		if (onFloor)
			return;
		if (v.y < 0)
			return;
		checkBlockFromLine(lastP, p, fixFunc_F);
		if (onFloor)
			return;
		checkBlockFromLine(lastP.x + bottomCPDis, lastP.y, p.x + bottomCPDis, p.y, fixFunc_F);
		if (onFloor)
			return;
		checkBlockFromLine(lastP.x - bottomCPDis, lastP.y, p.x - bottomCPDis, p.y, fixFunc_F);
	}

	public void onFloorCheck() {
		if (!onFloor)
			return;
		if (!(gm.map.getBlockFromPos(lastP.x + bottomCPDis, lastP.y) instanceof Penetrable))
			return;
		if (!(gm.map.getBlockFromPos(lastP.x - bottomCPDis, lastP.y) instanceof Penetrable))
			return;
		onFloor = false;
	}

	public int numOfCheckPoints = 3;

	private void checkBlockFromLine(float x1, float y1, float x2, float y2, PositionFixer fun) {
		float dis = PApplet.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		int numOfPoints = numOfCheckPoints;
		if (dis > numOfPoints * Map.BLOCKSIZE / 2f) {
			numOfPoints = (int) (dis * 2 / Map.BLOCKSIZE);
		}
		float x, y, r;
		Block block = null;
		for (float i = 0; i <= numOfPoints; i++) {
			r = i / numOfPoints;
			x = x1 * (1 - r) + x2 * r;
			y = y1 * (1 - r) + y2 * r;
			block = gm.map.getBlockFromPos(x, y);
			if (block != null && !(block instanceof Penetrable)) {
				fun.fix(x, y, block);
				return;
			}
		}
	}

	private void checkBlockFromLine(PVector p1, PVector p2, PositionFixer fun) {
		checkBlockFromLine(p1.x, p1.y, p2.x, p2.y, fun);
	}

	/**
	 * Method that would be used to fix position ,feedback acts with Checker.
	 * 
	 * @author ZzStarSound
	 *
	 */

	/**
	 * ����
	 */
	public void resistance() {
		if (PApplet.abs(v.x) > f)
			a.add((v.x > 0 ? -1 : 1) * f, 0);
		else
			v.x = 0;
	}

	/**
	 * ���������������ſ�ļ��ٶȻ����ٶȻ���λ�õĿ��ƣ� �ú����ᱻ����update���м䣬��ײ�жϾ�ȫ������NewtonObject��~~
	 * д����ô�����ķ������Ҿ����Լ���Щ����~
	 * 
	 * @author ZzStarSound
	 */
	public abstract void otherControl();

	public void renderFromCamera() {
		gm.parent.fill(0, 25, 0);
		lastBody.render();
		gm.parent.fill(255, 0, 0);
		body.render();
	}

	protected void nobjUpdate() {
		// force
		onFloorCheck();
		addGravity();
		otherControl();
		resistance();
		// calculate
		v.add(a);
		setP(v.x + p.x, v.y + p.y);
		a.set(0, 0);
		// check
		upCheck();
		fallCheck();
		leftCheck();
		rightCheck();

		// update last data
		lastP.set(p);
		lastBody.set(body);
	}

	public void update() {
		nobjUpdate();
	}

	public void setP(float x, float y) {
		p.set(x, y);
		body.moveCenterTo(x, y);
	}

	public void setP(PVector vec) {
		p.set(vec);
		body.moveCenterTo(vec.x, vec.y);
	}

	public interface PositionFixer {
		public void fix(float x, float y, Block block);
	}

	private PositionFixer fixFunc_R = new PositionFixer() {
		@Override
		public void fix(float x, float y, Block block) {
			/*
			 * float x1 = x - x % Map.BLOCKSIZE ; float y1 = y - y %
			 * Map.BLOCKSIZE; float x2 = x1; float y2 = y1 + Map.BLOCKSIZE; if
			 * (!linesIntersected(x1, y1, x2, y2, lastP.x + x - p.x, lastP.y + y
			 * - p.y, x, y)) return;
			 */
			float rx = x - x % Map.BLOCKSIZE - body.getWidth() / 2.0f;
			setP(rx, p.y);
			v.x = 0;
			if (block instanceof TouchFeedBack)
				((TouchFeedBack) block).touch();
		}
	};
	private PositionFixer fixFunc_L = new PositionFixer() {
		@Override
		public void fix(float x, float y, Block block) {
			/*
			 * float x1 = x - x % Map.BLOCKSIZE + +Map.BLOCKSIZE; float y1 = y -
			 * y % Map.BLOCKSIZE; float x2 = x1; float y2 = y1 + Map.BLOCKSIZE;
			 * if (!linesIntersected(x1, y1, x2, y2, lastP.x + x - p.x, lastP.y
			 * + y - p.y, x, y)) return;
			 */
			float rx = x - x % Map.BLOCKSIZE + Map.BLOCKSIZE + body.getWidth() / 2.0f;
			setP(rx, p.y);
			v.x = 0;
			if (block instanceof TouchFeedBack)
				((TouchFeedBack) block).touch();
		}
	};
	private PositionFixer fixFunc_U = new PositionFixer() {
		@Override
		public void fix(float x, float y, Block block) {
			float x1 = x - x % Map.BLOCKSIZE;
			float y1 = y - y % Map.BLOCKSIZE + Map.BLOCKSIZE;
			float x2 = x1 + Map.BLOCKSIZE;
			float y2 = y1;
			if (!linesIntersected(x1, y1, x2, y2, lastP.x + x - p.x, lastP.y + y - p.y, x, y))
				return;
			float ry = y + body.getHeight();
			setP(p.x, ry - ry % Map.BLOCKSIZE + Map.BLOCKSIZE);
			v.y = -v.y;
			if (block instanceof BottomKnockable)
				((BottomKnockable) block).bottomKnocked();
			if (block instanceof TouchFeedBack)
				((TouchFeedBack) block).touch();
		}
	};
	private PositionFixer fixFunc_F = new PositionFixer() {
		@Override
		public void fix(float x, float y, Block block) {
			float x1 = x - x % Map.BLOCKSIZE;
			float y1 = y - y % Map.BLOCKSIZE;
			float x2 = x1 + Map.BLOCKSIZE;
			float y2 = y1;
			if (!linesIntersected(x1, y1, x2, y2, lastP.x + x - p.x, lastP.y + y - p.y, x, y))
				return;
			onFloor = true;
			int idy = block.idy;
			while (true) {
				Block curBl = gm.map.getBlockFromIdPos(block.idx, --idy);
				if (curBl == null || (curBl instanceof Penetrable)) {
					setP(p.x, (idy + 1) * Map.BLOCKSIZE);
					break;
				}
			}

			v.y = 0;
			if (block instanceof FallFeedBack)
				((FallFeedBack) block).fallToThisBlock();
			if (block instanceof TouchFeedBack)
				((TouchFeedBack) block).touch();
		}
	};

	public static boolean linesIntersected(float x1, float y1, float x2, float y2, float x3, float y3, float x4,
			float y4) {
		if ((x1 * y2 + x3 * y1 + x2 * y3 - x3 * y2 - x1 * y3 - x2 * y1)
				* (x1 * y2 + x4 * y1 + x2 * y4 - x4 * y2 - x1 * y4 - x2 * y1) <= 0
				&& (x3 * y4 + x1 * y3 + x4 * y1 - x1 * y4 - x3 * y1 - x4 * y3)
						* (x3 * y4 + x2 * y3 + x4 * y2 - x2 * y4 - x3 * y2 - x4 * y3) <= 0)
			return true;
		return false;
	}
}