package game.entity.pickup.weapon;

import game.entity.mob.Mob;
import game.entity.pickup.Pickup;
import game.weapon.RocketLauncher;

public class RocketLauncherPickup extends Pickup {

	public RocketLauncherPickup(double x, double y) { 
		super(x, y); 
		sprite = 2;
	}
	
	public boolean take(Mob m) {
		if(!(m.weapon instanceof RocketLauncher)) m.weapon = new RocketLauncher(m);
		else m.weapon.takeAmmo((int)(m.weapon.getAmmoCapacity() * 1));
		return true;
	}
	
}