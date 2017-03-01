package weapon;

import processing.core.PImage;

public abstract class Weapon {
	public PImage img = null;
	public Particle_System parSystem = null;
	public WeaponProperties pro = null;
	public int coolTime;

	public Weapon() {
		pro = buildProperties();
		parSystem = new Particle_System();
		coolTime = pro.coolTime;
	}

	public abstract WeaponProperties buildProperties();

	public void shoot(float theta) {
		if (coolTime != 0 || pro.numOfBullets <= 0)
			return;
		shootFeedBack(theta);
		pro.numOfBullets -= pro.numPerShoot;
		pro.numOfBullets = pro.numOfBullets <= 0 ? 0 : pro.numOfBullets;
		coolTime = pro.coolTime;
	}

	protected abstract void shootFeedBack(float theta);

	private void updateCoolTime() {
		if (--coolTime < 0) {
			coolTime = 0;
		}
	}

	public void update() {
		parSystem.update();
		updateCoolTime();
	}

	public class WeaponProperties {
		public int numPerShoot = 1;
		public float speed = 10f;
		public int coolTime = 5;

		/**
		 * 精准度 不准(0.0-1.0)准
		 */
		public float accuracy = 0.8f;
		/**
		 * 散射范围扇形角度(角度制)
		 */
		public int angle = 5;
		public int numOfBullets = 100;
		public int bulletLife = 100;

		/**
		 * 穿透次数
		 */
		public int strike = 1;
		/**
		 * 后坐力
		 */
		public float recoil = 1.0f;
	}
}
