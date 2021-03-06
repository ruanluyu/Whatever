package system;

import processing.core.PApplet;
import processing.core.PVector;

public class Rectangle {
	private PVector p1 = new PVector();
	private PVector p2 = new PVector();
	public PVector center = new PVector();
	private PVector size = new PVector();
	public static GameManager gm = null;

	public Rectangle(float x, float y, float w, float h) {
		set(x, y, w, h);
	}

	public void set(float x, float y, float w, float h) {
		p1.set(x, y);
		p2.set(x + w, y + h);
		size.set(w, h);
		checkPoint();
		center.set(p1.x/2f,p1.y/2f);
	}

	public void checkPoint() {
		if (p1.x > p2.x) {
			float xc = p1.x;
			p1.x = p2.x;
			p2.x = xc;
		}
		if (p1.y > p2.y) {
			float yc = p1.y;
			p1.y = p2.y;
			p2.y = yc;
		}
	}

	public void updateSize() {
		size.set(p2.x - p1.x, p2.y - p1.y);
	}

	public Rectangle clone() {
		return new Rectangle(p1.x, p1.y, size.x, size.y);
	}

	public boolean inside(float x, float y) {
		if (x >= p1.x && x <= p2.x && y >= p1.y && y <= p2.y) {
			return true;
		}
		return false;
	}

	public void set(Rectangle r) {
		p1.set(r.p1);
		p2.set(r.p2);
		center.set(r.center);
		updateSize();
	}

	public void setCenterPointByRatio(float ratioX, float ratioY) {
		center.set(p1.x + size.x * ratioX, p1.y + size.y * ratioY);
	}

	public void setCenterPointByFloat(float posX, float posY) {
		center.set(posX, posY);
	}

	public void setCenterPointToCenter() {
		setCenterPointByRatio(.5f, .5f);
	}

	public void moveCenterTo(float x, float y) {
		translate(x - center.x, y - center.y);
	}

	public void moveCenterTo(PVector m) {
		moveCenterTo(m.x, m.y);
	}

	public void translate(float vx, float vy) {
		p1.add(vx, vy);
		p2.add(vx, vy);
		center.add(vx, vy);
	}

	public void translate(PVector p) {
		p1.add(p);
		p2.add(p);
		center.add(p);
	}

	public void rotate(int rotate90) {
		float a = p1.x - center.x;
		float b = p1.y - center.y;
		float c = PApplet.cos(rotate90 * PApplet.HALF_PI);
		float d = PApplet.sin(rotate90 * PApplet.HALF_PI);
		p1.set(a * c - b * d, a * d + b * c).add(center);
		a = p2.x - center.x;
		b = p2.y - center.y;
		p2.set(a * c - b * d, a * d + b * c).add(center);
		checkPoint();
		updateSize();
	}

	public void render() {
		gm.parent.rect(p1.x, p1.y, size.x, size.y);
	}

	public boolean intersected(Rectangle ref) {
		if (ref.p1.x > p2.x || ref.p2.x < p1.x || ref.p1.y > p2.y || ref.p2.y < p1.y) {
			return false;
		}
		return true;
	}

	public float p1x() {
		return p1.x;
	}

	public float p2x() {
		return p2.x;
	}

	public float p1y() {
		return p1.y;
	}

	public float p2y() {
		return p2.y;
	}

	public void p1x(float f) {
		p1.x = f;
		updateSize();
	}

	public void p2x(float f) {
		p2.x = f;
		updateSize();
	}

	public void p1y(float f) {
		p1.y = f;
		updateSize();
	}

	public void p2y(float f) {
		p2.y = f;
		updateSize();
	}

	public float getWidth() {
		return size.x;
	}

	public float getHeight() {
		return size.y;
	}

	public float centerX() {
		return center.x;
	}

	public float centerY() {
		return center.y;
	}
}
