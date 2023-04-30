package game.weapon;

import game.entity.mob.Mob;
import game.entity.projectile.Rocket;

public class RocketLauncher extends Weapon {

	public RocketLauncher(Mob owner) {
		super(owner);
		
		ammoLoaded = maxAmmoLoaded = 4;
		ammoCarried = maxAmmoCarried = 20;
		
		shootDelayTime = 0.8;
		startReloadDelayTime = 0.8;
		reloadDelayTime = 0.92;
		
		highRamp = 125;
		lowRamp = 53;
	}

	public void shoot(double xa, double ya, double za) {
		super.shoot(xa, ya, za);
		owner.level.addEntity(new Rocket(owner, this, owner.x, owner.y, xa, ya, 90));
		shootDelay = shootDelayTime;
	}
	
	public int getSprite() { return owner.sprite + 3; }
	public int getGUISprite() { return 3; }
}