package system;

import java.util.ArrayList;

import blocks.Block;
import processing.core.PApplet;
import processing.core.PImage;
import system.GameManager.Mode;
import object.Character;
import object.NewtonObject;

public class GameSystem {
	GameManager gm;

	GameSystem(PApplet p) {
		gm = new GameManager();
		gm.system = this;
		Rectangle.gm = gm;
		NewtonObject.gm = gm;
		gm.parent = p;
		gm.res = new ResManager(gm);
		gm.res.loadAll();
		Block.loadGameManager(gm);
		gm.map = new Map(50, 20, gm);
		gm.cam = new Camera(gm);
		gm.akp = new AKeyPress(gm);

		// Cur
		gm.map.set(10, 10, 18, 10, Block.BlockId.BLOCK_NORMAL);
		gm.map.set(8, 11, 25, 15, Block.BlockId.BLOCK_SUSPENDED);
		gm.map.set(25, 13, 49, 15, Block.BlockId.THORN);

		gm.controlable = true;

		// Load NeedUpdate objects
		gm.loadNeedUpdateList();
		gm.loadRenderFCList();
		gm.loadRenderList();
	}

	public void render() {
		synchronized (this) {
			switch (gm.gameMode) {
			case RUN:
				renderRUN();
				break;
			case PAUSE:
				renderPAUSE();
				break;
			case DIE:
				renderDIE();
				break;
			case LOAD:
				renderLOAD();
				break;
			case OP:
				renderOP();
				break;
			}
		}
	}

	public PImage scShoot;

	public void screenShoot() {
		scShoot = gm.parent.g.copy();
	}

	public static void moveNullObject(ArrayList<?> list) {
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i) == null)
				list.remove(i);
		}
	}

	public void createCharacter() {
		gm.man = new Character(500, 100);
		gm.die = false;
		gm.controlable = true;
		gm.loadRenderFCList();
		gm.loadNeedUpdateList();
		gm.loadRenderList();
	}

	private void renderRUN() {
		gm.cam.tp.x = gm.man.p.x;
		gm.cam.tp.y = gm.man.p.y;
		for (NeedUpdate nu : gm.updatelist)
			nu.update();
		gm.parent.background(240, 240, 255);
		gm.parent.pushMatrix();
		gm.parent.translate(gm.parent.width / 2f - gm.cam.p.x, gm.parent.height / 2f - gm.cam.p.y);
		for (RenderableFromCamera nu : gm.renderFClist)
			nu.renderFromCamera();
		gm.parent.popMatrix();
		for (Renderable nu : gm.renderlist)
			nu.render();
		if (!gm.parent.focused) {
			screenShoot();
			gm.gameMode = Mode.PAUSE;
			gm.parent.frameRate(4);
		}
		if (gm.die) {
			screenShoot();
		}
	}

	private int dieTime = 100;

	private void renderDIE() {
		gm.parent.image(scShoot, 0, 0);
		gm.parent.textAlign(PApplet.CENTER);
		gm.parent.fill(255, 0, 0);
		gm.parent.text("What a loser!", gm.parent.width / 2f, gm.parent.height / 2f);
		dieTime--;
		if (dieTime <= 0) {
			dieTime = 200;
			startGame();
		}
	}

	private void renderPAUSE() {
		gm.parent.image(scShoot, 0, 0);
		gm.parent.textAlign(PApplet.CENTER);
		gm.parent.fill(0, 0, 255);
		gm.parent.text("- PAUSE -", gm.parent.width / 2f, gm.parent.height / 2f);
		if (gm.parent.focused) {
			gm.gameMode = Mode.RUN;
			gm.parent.frameRate(60);
			gm.akp.init();
		}

	}

	private int loadTime = 100;

	private void renderLOAD() {
		gm.parent.background(0);
		gm.parent.textAlign(PApplet.CENTER);
		gm.parent.fill(255);
		gm.parent.text("AssHole " + gm.level, gm.parent.width / 2f, gm.parent.height / 2f - 50);
		gm.parent.text("Shits " + gm.life, gm.parent.width / 2f, gm.parent.height / 2f + 50);
		loadTime--;
		if (loadTime <= 0) {
			loadTime = 100;
			gm.gameMode = Mode.RUN;
		}
	}

	private int opTime = 5;
	private boolean solecable = true;

	private void renderOP() {
		gm.parent.frameRate(30);
		gm.parent.background(0);
		gm.parent.textAlign(PApplet.CENTER);
		gm.parent.fill(255);
		gm.parent.text("Whatever", gm.parent.width / 2f, gm.parent.height / 2f - 100);
		gm.parent.text("Start", gm.parent.width / 2f, gm.parent.height / 2f);
		gm.parent.text("Load", gm.parent.width / 2f, gm.parent.height / 2f + 50);
		gm.parent.text("Exit", gm.parent.width / 2f, gm.parent.height / 2f + 100);
		gm.parent.ellipse(gm.parent.width / 2f - 50, gm.parent.height / 2f - 10 + 50 * gm.selection, 20, 20);

		if (!solecable && opTime <= 0) {
			opTime = 20;
			solecable = true;
		} else if (!solecable)
			opTime--;
		if (solecable) {
			if (gm.akp.keyW()) {
				gm.selection--;
				solecable = false;
			} else if (gm.akp.keyS()) {
				solecable = false;
				gm.selection++;
			} else if (gm.akp.keyBlank()) {
				solecable = false;
				switch (gm.selection) {
				case 0:
					startGame();
					break;
				case 1:
					loadGame();
					break;
				case 2:
					gm.parent.exit();
				}
			}
		}
		if (gm.selection < 0)
			gm.selection = 2;
		else if (gm.selection > 2)
			gm.selection = 0;
	}

	private void startGame() {
		gm.parent.frameRate(60);
		gm.gameMode = Mode.LOAD;
		createCharacter();
	}

	private void loadGame() {
		gm.parent.frameRate(3);
		// TODO
	}

}