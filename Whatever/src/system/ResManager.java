package system;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PImage;

public class ResManager {
	public static Minim minim = null;
	public GameManager gm;
	private AudioPlayer[] aplist = null;
	private int apPos = 0;
	private PImage[] imglist = null;
	private int imgPos = 0;

	public ResManager(GameManager m) {
		gm = m;
		if (minim == null)
			minim = new Minim(gm.parent);
		aplist = new AudioPlayer[216];
		imglist = new PImage[512];
	}

	public void loadAll() {
		////// SOUND
		String soundPath = "./res/bgm/gun_00.wav," + "./res/bgm/gun_01.wav," + "./res/bgm/gun_02.wav,";
		/////////////////// IMG
		// MATERIAL
		String imgPath = "./res/map/Material_00.png,"
				+ "./res/map/Material_01.png,"
				+ "./res/map/Material_02.png,"
				+ "./res/map/Material_03.png,"
				+ "./res/map/Material_03_t.png,";
		// BULLET
		imgPath += "./res/obj/bullet/bullet_00.png," + "./res/obj/bullet/bullet_01.png,";
		// BODY
		imgPath += "./res/obj/mainChar/body/body_00.png," + "./res/obj/mainChar/body/body_01.png,"
				+ "./res/obj/mainChar/body/body_02.png," + "./res/obj/mainChar/body/body_03.png,"
				+ "./res/obj/mainChar/body/body_04.png," + "./res/obj/mainChar/body/body_05.png,";
		// FACE
		imgPath += "./res/obj/mainChar/face/face_00.png," + "./res/obj/mainChar/face/face_01.png,"
				+ "./res/obj/mainChar/face/face_02.png," + "./res/obj/mainChar/face/face_03.png,"
				+ "./res/obj/mainChar/face/face_04.png," + "./res/obj/mainChar/face/face_05.png,";
		// MONSTER
		imgPath += "./res/obj/monster/monster_00.png," + "./res/obj/monster/monster_01.png,";
		// WEAPON
		imgPath += "./res/obj/weapon/weapon_00.png," + "./res/obj/weapon/weapon_01.png,";

		loadAllSound(soundPath);
		loadAllImage(imgPath);
	}

	public void loadAllSound(String str) {
		String[] strlist = str.split(",");
		for (int i = 0; i < strlist.length; i++) {
			loadSound(strlist[i]);
		}
	}

	public void loadAllImage(String str) {
		String[] strlist = str.split(",");
		for (int i = 0; i < strlist.length; i++) {
			loadImage(strlist[i]);
		}
	}

	public PImage getImage(int id) {
		return imglist[id];
	}

	public AudioPlayer getSound(int id) {
		return aplist[id];
	}

	public int getImageLength() {
		return imgPos;
	}

	public int getSoundLength() {
		return apPos;
	}

	public void loadImage(String path) {
		imglist[imgPos++] = gm.parent.loadImage(path);
	}

	public void loadSound(String path) {
		aplist[apPos++] = minim.loadFile(path);
	}
	
	public enum imgMaterialId {
		NULL, MATERIAL_00, MATERIAL_01, MATERIAL_02, MATERIAL_03, MATERIAL_03_T,
	}
	
	public enum imgBulletId {
		NULL, BULLET_00, BULLET_01,
	}

	public enum imgBodyId {
		NULL, BODY_00, BODY_01, BODY_02, BODY_03, BODY_04, BODY_05,
	}

	public enum imgFaceId {
		NULL, FACE_00, FACE_01, FACE_02, FACE_03, FACE_04, FACE_05,
	}

	public enum imgMonsterId {
		NULL, MONSTER_00, MONSTER01,
	}

	public enum imgWeaponId {
		NULL, WEAPON00, WEAPON01,
	}

	

	

}
