package weapon;

import java.util.ArrayList;

public class Particle_System {
	private ArrayList<Particle> plist = new ArrayList<Particle>();
	public void addPar(Particle p){
		plist.add(p);
	}
	public void update(){
		for(Particle p:plist){
			p.update();
		}
		dieCheck();
	}
	public void dieCheck(){
		for(int i = plist.size()-1;i>=0;i--){
			Particle par = plist.get(i);
			if(par.life <= 0){
				plist.remove(i);
			}
		}
	}
}
