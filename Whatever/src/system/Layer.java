package system;

import processing.awt.PGraphicsJava2D;
import processing.core.PApplet;
import processing.core.PGraphics;

public class Layer {
	public String name = null;
	private static int untitledLayerCounter = 0;
	public GameManager gm = null;
	public PGraphics pg = null;
	public boolean visiable = true;
	public Camera cam = null;
	
	public Layer(GameManager gm) {
		this(gm,"untitled " + untitledLayerCounter);
		untitledLayerCounter++;
	}
	
	public Layer(GameManager gm,String name) {
		this(gm,name,true);
	}
	
	public Layer(GameManager gm,String name,boolean visiable) {
		this.gm = gm;
		this.name = name;
		this.visiable = visiable;
		cam = gm.cam;
		pg = new PGraphicsJava2D();
		pg.setSize(GameSystem.WIDTH, GameSystem.HEIGHT);
	}
	
	public void clear() {
		pg.clear();
	}
	
	public void renderStart() {
		pg.beginDraw();
		pg.pushMatrix();
		pg.translate(gm.parent.width / 2f - gm.cam.p.x, gm.parent.height / 2f - gm.cam.p.y);
	}
	
	public void renderEnd() {
		pg.popMatrix();
		pg.endDraw();
	}
	
}
