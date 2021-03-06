package weapon;

import weapon.Weapon.WeaponProperties;

public abstract class Bullet extends Particle{
	public int strike ;
	public Bullet(int x, int y,float theta, WeaponProperties pro) {
		super(x, y, pro.bulletLife);
		strike = pro.strike;
		v.set(pro.speed,0);
		v.rotate(theta);
	}
	public abstract void strike();
}
