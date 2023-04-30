package game.weapon;

import game.entity.mob.Mob;
import game.entity.projectile.Bullet;

public class SniperRifle extends Weapon {

	public SniperRifle(Mob owner) {
		super(owner);
		ammoLoaded = maxAmmoLoaded = 25;
		ammoCarried = maxAmmoCarried = 0;
		
		shootDelayTime = 1.5;
		startReloadDelayTime = 1.0;
		reloadDelayTime = 0.5;
		
		highRamp = 100;
		lowRamp = 100;
	}
	
	public void shoot(double xa, double ya, double za) {
		super.shoot(xa, ya, za);
		double spread = 0.001;
		for(int i = 0; i < 1; i++) {
			double xxa = xa + (random.nextDouble() - 0.5) * spread;
			double yya = ya + (random.nextDouble() - 0.5) * spread;
			owner.level.addEntity(new Bullet(owner, this, owner.x, owner.y, xxa, yya, 50, 16));
		}
		
		shootDelay = shootDelayTime;
	}	

	public int getSprite() { return owner.sprite + 5; }
	public int getGUISprite() { return 5; }
	
}