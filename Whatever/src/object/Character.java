package object;

import blocks.Block;
import blocks.StandFeedBack;
import blocks.TouchFeedBack;
import blocks.WalkFeedBack;
import processing.awt.PGraphicsJava2D;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import system.ImgIdMessage;
import system.Map;
import system.ResManager;
import system.GameManager.Mode;

public class Character extends NewtonObject {
	public float acc = .2f;
	public float jumpAcc = 5;
	public float maxSpeed = 5;
	public boolean faceToRight = true;
	public boolean drunk = false;
	public boolean hold = false;
	public PGraphics g = new PGraphicsJava2D();
	public PImage copyG = null;
	public PImage[] facelist;
	public PImage[] bodylist;

	public void loadFaceList() {
		facelist = new PImage[ResManager.imgFaceId.values().length - 1];
		ImgIdMessage imgMessage = new ImgIdMessage();
		imgMessage.faceId = ResManager.imgFaceId.FACE_00;
		int startId = imgMessage.getId();
		for (int i = startId; i < startId + facelist.length; i++) {
			facelist[i - startId] = gm.res.getImage(i);
		}
	}

	public void loadBodyList() {
		bodylist = new PImage[ResManager.imgBodyId.values().length - 1];
		ImgIdMessage imgMessage = new ImgIdMessage();
		imgMessage.bodyId = ResManager.imgBodyId.BODY_00;
		int startId = imgMessage.getId();
		for (int i = startId; i < startId + bodylist.length; i++) {
			bodylist[i - startId] = gm.res.getImage(i);
		}
	}

	private float walkDist = 0;
	private int walkTimer = 0;

	private void walkTime() {
		walkDist += PApplet.abs(v.x);
		walkTimer = (int) ((walkDist / 20) % 3);
		if (walkDist > 10000)
			walkDist = 0;
	}

	@Override()
	public void renderFromCamera() {
		renderMan();
		if (flashCoolTime > 0) {
			gm.parent.blendMode(PApplet.MULTIPLY);
			gm.parent.image(copyG, ox - body.getWidth() / 2f, oy - body.getHeight());
			gm.parent.blendMode(PApplet.NORMAL);
		}
		gm.parent.image(g, p.x - body.getWidth() / 2f, p.y - body.getHeight());
	}

	private void renderMan() {
		g.beginDraw();
		g.clear();
		if (!drunk) {
			if (!hold) {
				if (onFloor) {
					if (v.x == 0)
						renderMan(0, 0);
					else
						renderMan(walkTimer, walkTimer);
				} else
					renderMan(1, 1);
			} else {
				if (onFloor) {
					if (v.x == 0)
						renderMan(3, 0);
					else
						renderMan(walkTimer + 3, walkTimer);
				} else
					renderMan(4, 1);
			}
		} else {
			if (!hold) {
				if (onFloor) {
					if (v.x == 0)
						renderMan(0, 3);
					else
						renderMan(walkTimer, walkTimer + 3);
				} else
					renderMan(1, 4);
			} else {
				if (onFloor) {
					if (v.x == 0)
						renderMan(3, 3);
					else
						renderMan(walkTimer + 3, walkTimer + 3);
				} else
					renderMan(4, 4);
			}
		}
		if (!faceToRight) {
			PImage mirro = g.copy();
			g.loadPixels();
			for (int j = 0; j < g.height; j++) {
				for (int i = 0; i < g.width; i++) {
					g.pixels[i + j * g.width] = mirro.pixels[g.width - i - 1 + j * g.width];
				}
			}
			g.updatePixels();
		}
		g.endDraw();
	}

	private void renderMan(int indexBody, int indexFace) {
		g.image(bodylist[indexBody], 0, 0);
		g.image(facelist[indexFace], 0, 0);
	}

	public Character(int x, int y) {
		super(40, 80);
		setP(x, y);
		g.setSize(40, 80);
		loadBodyList();
		loadFaceList();
		bottomCPDis = 5;
	}

	private float flashDist = 40;
	private final int flashCoolTimeLength = 60;
	private int flashCoolTime = 0;
	private float ox = -1;
	private float oy = -1;

	public void flash() {
		if (flashCoolTime <= 0) {
			if (gm.akp.keyBlank() && gm.controlable) {
				ox = p.x;
				oy = p.y;
				p.x += (faceToRight ? 1 : -1) * flashDist;
				a.x += (faceToRight ? 1 : -1) * 3;
				if (faceToRight)
					this.rightCheck();
				else
					this.leftCheck();
				this.fallCheck();
				flashCoolTime = flashCoolTimeLength;
				copyG = g.copy();
			}
		} else {
			flashCoolTime--;
		}
	}

	public void runRight() {
		if (gm.akp.keyD() && gm.controlable) {
			a.add(acc, 0);
			faceToRight = true;
		}
	}

	public void runLeft() {
		if (gm.akp.keyA() && gm.controlable) {
			a.add(-acc, 0);
			faceToRight = false;
		}
	}

	public void jump() {
		if (gm.akp.keyW() && onFloor && gm.controlable) {
			a.add(0, -jumpAcc);
			onFloor = false;
		}
	}

	public void speedLimit() {
		if (PApplet.abs(v.x) > maxSpeed) {
			v.x = v.x > 0 ? 1 : -1;
			v.x *= maxSpeed;
		}
	}

	public void dieCheck() {
		if (p.y > (gm.map.height + 5) * Map.BLOCKSIZE || p.y < -10 * Map.BLOCKSIZE)
			kill();
	}

	public void standCheck() {
		if (onFloor && v.x == 0f) {
			Block block = gm.map.getBlockFromPos(p.x, p.y + .5f);
			if (block instanceof StandFeedBack)
				((StandFeedBack) block).stand();
			if (block instanceof TouchFeedBack)
				((TouchFeedBack) block).touch();
		}
	}

	public void walkCheck() {
		if (onFloor && v.x != 0f) {
			Block block = gm.map.getBlockFromPos(p.x, p.y + .5f);
			if (block instanceof WalkFeedBack)
				((WalkFeedBack) block).walk();
			if (block instanceof TouchFeedBack)
				((TouchFeedBack) block).touch();
		}
	}

	/**
	 * ɱ��С�˲���-1s~~ ~_~
	 */
	public void kill() {
		if (gm.die)
			return;
		v.set(0, 0);
		gm.life--;
		gm.die = true;
		gm.controlable = false;
		gm.gameMode = Mode.DIE;
	}

	@Override
	public void otherControl() {
		// force
		runRight();
		runLeft();
		jump();
		flash();
		// speed
		speedLimit();
		// checker
		standCheck();
		walkCheck();
		dieCheck();
		walkTime();
	}
}
