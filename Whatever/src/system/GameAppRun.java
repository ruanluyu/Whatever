package system;

import java.awt.Font;

import processing.core.PApplet;
import processing.core.PFont;

public class GameAppRun extends PApplet {
	GameSystem gs;

	public static void main(String[] args) {
		GameAppRun app = new GameAppRun();
		app.runSketch();
	}

	@Override
	public void setup() {
		textFont(new PFont(new Font("Arial", Font.PLAIN, 20), true));
		gs = new GameSystem(this);
		frameRate(60);
		textAlign(LEFT, TOP);
		textSize(30);
	}

	public void draw() {
		gs.render();
		//fill(255, 0, 0);
		//text("fps : " + round(frameRate), 0, 0);
	}

	@Override
	public void settings() {
		size(GameSystem.WIDTH, GameSystem.HEIGHT);
	}
	
	@Override
	public void keyPressed() {
		gs.gm.akp.checkKeyPressed();
		if(key == ESC){
			key = '/';
			gs.escPressed();
		}
	}

	public void keyReleased() {
		gs.gm.akp.checkKeyRelease();
	}
}
