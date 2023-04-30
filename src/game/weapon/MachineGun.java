package game.weapon;

import game.entity.mob.Mob;
import game.entity.projectile.Bullet;

public class MachineGun extends Weapon {

	public MachineGun(Mob owner) { 
		super(owner);
		ammoLoaded = maxAmmoLoaded = 25;
		ammoCarried = maxAmmoCarried = 0;
		
		shootDelayTime = 0.1;
		startReloadDelayTime = 0.5;
		reloadDelayTime = 0.5;
	}

	public void shoot(double xa, double ya, double za) {
		super.shoot(xa, ya, za);
		double spread = 0.05;
		for(int i = 0; i < 1; i++) {
			double xxa = xa + (random.nextDouble() - 0.5) * spread;
			double yya = ya + (random.nextDouble() - 0.5) * spread;
			owner.level.addEntity(new Bullet(owner, this, owner.x, owner.y, xxa, yya, 20, 10));
		}
		
		shootDelay = shootDelayTime;
	}	

	public int getSprite() { return owner.sprite + 6; }
	public int getGUISprite() { return 6; }
	
}