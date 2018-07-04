package system;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PImage;

public class ResManager {
	public static Minim minim = null;
	public GameManager gm;
	
	public AudioPlayer[] soundList = null;
	public PImage[] materialImgList = null;
	public PImage[] bulletImgList = null;
	public PImage[] bodyImgList = null;
	public PImage[] faceImgList = null;
	public PImage[] monsterImgList = null;
	public PImage[] weaponImgList = null;
	public PImage[] itemImgList = null;
	public PImage[] effectImgList = null;

	public ResManager(GameManager m) {
		gm = m;
		if (minim == null)
			minim = new Minim(gm.parent);
		//aplist = new AudioPlayer[216];
		//imglist = new PImage[512];
	}

	public void loadAll() {
		////// SOUND
		soundList=
		loadSpecialSound("./res/bgm/welcome.wav,"
						+"./res/bgm/gun_00.wav," 
						+ "./res/bgm/gun_01.wav," 
						+ "./res/bgm/gun_02.wav,"
						);
		/////////////////// IMG
		// MATERIAL
		materialImgList=
		loadSpecialImage("./res/map/Material_00.png," 
						+ "./res/map/Material_01.png," 
						+ "./res/map/Material_02.png,"
						+ "./res/map/Material_03.png," 
						+ "./res/map/Material_03_t.png,"
						);
		// BULLET
		bulletImgList=
		loadSpecialImage("./res/obj/bullet/bullet_00.png," 
						+ "./res/obj/bullet/bullet_01.png,"
						);
		// BODY
		bodyImgList=
		loadSpecialImage("./res/obj/mainChar/body/body_00.png," 
						+ "./res/obj/mainChar/body/body_01.png,"
						+ "./res/obj/mainChar/body/body_02.png,"
						+ "./res/obj/mainChar/body/body_03.png,"
						+ "./res/obj/mainChar/body/body_04.png," 
						+ "./res/obj/mainChar/body/body_05.png,"
						);
		// FACE
		faceImgList=
		loadSpecialImage("./res/obj/mainChar/face/face_00.png," 
						+ "./res/obj/mainChar/face/face_01.png,"
						+ "./res/obj/mainChar/face/face_02.png," 
						+ "./res/obj/mainChar/face/face_03.png,"
						+ "./res/obj/mainChar/face/face_04.png," 
						+ "./res/obj/mainChar/face/face_05.png,"
						);
		// MONSTER
		monsterImgList=
		loadSpecialImage("./res/obj/monster/monster_00.png," 
						+ "./res/obj/monster/monster_01.png,"
						);
		// WEAPON
		weaponImgList=
		loadSpecialImage("./res/obj/weapon/weapon_00.png," 
						+ "./res/obj/weapon/weapon_01.png,"
						);
		// ITEM
		itemImgList=
		loadSpecialImage("./res/obj/item/item_00.png,"
						);
		// EFFECT
		effectImgList=
		loadSpecialImage("./res/obj/effect/effect_00.png,"
						+"./res/obj/effect/effect_01.png,"
						+"./res/obj/effect/effect_02.png,"
						+"./res/obj/effect/effect_03.png,"
						);
		
	}
	
	public PImage[] loadSpecialImage(String str){
		String[] scont = str.split(",");
		PImage[] cont = new PImage[scont.length];
		for (int i = 0; i < scont.length; i++) {
			cont[i] = gm.parent.loadImage(scont[i]);
		}
		return cont;
	}
	
	public AudioPlayer[] loadSpecialSound(String str){
		String[] scont = str.split(",");
		AudioPlayer[] cont = new AudioPlayer[scont.length];
		for (int i = 0; i < scont.length; i++) {
			cont[i] = minim.loadFile(scont[i]);
		}
		return cont;
	}
	
	public PImage getMirroImage(PImage img) {
		PImage mirro = gm.parent.createImage(img.width, img.height, PApplet.ARGB);
		mirro.loadPixels();
		for (int j = 0; j < mirro.height; j++) {
			for (int i = 0; i < mirro.width; i++)
				mirro.pixels[i + j * mirro.width] = img.pixels[img.width - i - 1 + j * img.width];
		}
		mirro.updatePixels();
		return mirro;
	}
}
