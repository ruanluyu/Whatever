package weapon;

import processing.core.PVector;

public class Particle {
	public PVector p = new PVector();
	public PVector v = new PVector();
	public PVector a = new PVector();
	public int life;
	public Particle(int x,int y,int lf){
		p.set(x, y);
		life = lf;
	}
	public void update(){
		v.add(a);
		p.add(v);
		a.set(0,0);
		life--;
	}
}
