package game.weapon;

import game.entity.mob.Mob;
import game.entity.projectile.Bullet;

public class Pistol extends Weapon {

	public Pistol(Mob owner) {
		super(owner);
		ammoLoaded = maxAmmoLoaded = 6;
		ammoCarried = maxAmmoCarried = 24;
		
		shootDelayTime = 0.58;
		startReloadDelayTime = 1.16;
		reloadDelayTime = 1.16;
		
		highRamp = 150;
		lowRamp = 52;
	}
	
	public void shoot(double xa, double ya, double za) {
		super.shoot(xa, ya, za);
		double spread = 0.001;
		for(int i = 0; i < 1; i++) {
			double xxa = xa + (random.nextDouble() - 0.5) * spread;
			double yya = ya + (random.nextDouble() - 0.5) * spread;
			owner.level.addEntity(new Bullet(owner, this, owner.x, owner.y, xxa, yya, 10, 6));
		}
		
		shootDelay = shootDelayTime;
	}
	
	public void reload() {
		while(ammoLoaded < maxAmmoLoaded && maxAmmoCarried > 0) {
			ammoLoaded++;
			ammoCarried--;
			reloadDelay = !wasReloading ? startReloadDelayTime : reloadDelayTime;
		}
	}

}