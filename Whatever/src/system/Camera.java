package system;

import processing.core.PVector;

public class Camera implements NeedUpdate{
	public GameManager gm;
	public float easing = 0.1f;
	public PVector p = new PVector();
	public PVector s = new PVector();
	public PVector tp = new PVector();

	/**
	 * target active area extension X 目标自由活动范围（相对于中心点）向x轴两侧扩展的像素数
	 */
	public Rectangle targetArea = null;

	/**
	 * 摄像机活动范围
	 */
	public Rectangle area = null;

	public Camera(Rectangle area) {
		this.area = area;
	}

	public Camera(GameManager m) {
		gm = m;
		float w = gm.parent.width;
		float h = gm.parent.height;
		float mw = gm.map.width * Map.BLOCKSIZE;
		float mh = gm.map.height * Map.BLOCKSIZE;
		area = new Rectangle(w / 2, h / 2, mw - w, mh - h);
		targetArea = new Rectangle(0, 0, w / 5, h / 2);
		targetArea.setCenterPointToCenter();
		s.set(GameSystem.WIDTH,GameSystem.HEIGHT);
	}

	public void setTarget(int tx, int ty) {
		tp.set(tx, ty);
	}

	public void setActiveArea(Rectangle r) {
		targetArea = r;
		targetArea.setCenterPointToCenter();
	}

	public void update() {
		targetArea.moveCenterTo(p);
		if (tp.x <= targetArea.p1x()) {
			p.x += easing * (tp.x - targetArea.p1x());
		} else if (tp.x >= targetArea.p2x()) {
			p.x += easing * (tp.x - targetArea.p2x());
		}
		if (tp.y <= targetArea.p1y()) {
			p.y += easing * (tp.y - targetArea.p1y());
		} else if (tp.y >= targetArea.p2y()) {
			p.y += easing * (tp.y - targetArea.p2y());
		}
		limitCheck();
	}

	private void limitCheck() {
		if (p.x < area.p1x()) {
			p.x = area.p1x();
		} else if (p.x > area.p2x()) {
			p.x = area.p2x();
		}
		if (p.y < area.p1y()) {
			p.y = area.p1y();
		} else if (p.y > area.p2y()) {
			p.y = area.p2y();
		}
	}
	public void startCam() {
		gm.parent.pushMatrix();
		gm.parent.translate(gm.parent.width / 2f - gm.cam.p.x, gm.parent.height / 2f - gm.cam.p.y);
	}

	public void endCam() {
		gm.parent.popMatrix();
	}
	
	public float getRealMouseX(){
		return gm.parent.mouseX+area.p1x();
	}
	public float getRealMouseY(){
		return gm.parent.mouseY+area.p1y();
	}
}
