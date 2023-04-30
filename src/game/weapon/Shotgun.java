package game.weapon;

import game.entity.mob.Mob;
import game.entity.projectile.Bullet;

public class Shotgun extends Weapon {

	public Shotgun(Mob owner) {
		super(owner);
		ammoLoaded = maxAmmoLoaded = 6;
		ammoCarried = maxAmmoCarried = 32;
		
		shootDelayTime = 0.625;
		startReloadDelayTime = 1.0;
		reloadDelayTime = 0.5;
		
		highRamp = 500;
		lowRamp = 50;
	}
	
	public void shoot(double xa, double ya, double za) {
		super.shoot(xa, ya, za);
		double spread = 0.2;
		for(int i = 0; i < 10; i++) {
			double xxa = xa + (random.nextDouble() - 0.5) * spread;
			double yya = ya + (random.nextDouble() - 0.5) * spread;
			owner.level.addEntity(new Bullet(owner, this, owner.x, owner.y, xxa, yya, 8, 6));
		}
		
		shootDelay = shootDelayTime;
	}	
	
	public int getSprite() { return owner.sprite + 1; }
	public int getGUISprite() { return 1; }
	
}