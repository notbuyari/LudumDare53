package game.weapon;

import game.entity.mob.Mob;
import game.entity.projectile.Bullet;

public class Submachine extends Weapon {

	public Submachine(Mob owner) {
		super(owner);
		ammoLoaded = maxAmmoLoaded = 30;
		ammoCarried = maxAmmoCarried = 32;
		
		shootDelayTime = 0.08;
		startReloadDelayTime = 0.5;
		reloadDelayTime = 0.1;
		
		highRamp = 87.5;
		lowRamp = 50;
	}
	
	public void shoot(double xa, double ya, double za) {
		super.shoot(xa, ya, za);
		double spread = 0.2;
		for(int i = 0; i < 1; i++) {
			double xxa = xa + (random.nextDouble() - 0.5) * spread;
			double yya = ya + (random.nextDouble() - 0.5) * spread;
			owner.level.addEntity(new Bullet(owner, this, owner.x, owner.y, xxa, yya, 10, 15));
		}
		
		shootDelay = shootDelayTime;
	}	
	
	public int getSprite() { return owner.sprite + 2; }
	public int getGUISprite() { return 2; }

}