package game.weapon;

import game.entity.mob.Mob;
import game.entity.projectile.FlameBullet;

public class Flamethrower extends Weapon {

	public Flamethrower(Mob owner) {
		super(owner);
		
		ammoLoaded = maxAmmoLoaded = 200;
		ammoCarried = maxAmmoCarried = 0;
		
		shootDelayTime = 0.04;
		startReloadDelayTime = 0;
		reloadDelayTime = 0;
		maxRange = 60;
		aimLead = 4;
		
		highRamp = 100;
		lowRamp = 60;
		
		midDistance = 100;
		farDistance = 192;
	}
	
	public void shoot(double xa, double ya, double za) {
		super.shoot(xa, ya, za);
		owner.level.addEntity(new FlameBullet(owner, this, owner.x - 4, owner.y - 8, xa, ya, 8));
		shootDelay = shootDelayTime;
	}
	
	public int getSprite() { return owner.sprite + 4; }
	public int getGUISprite() { return 4; }
		
}