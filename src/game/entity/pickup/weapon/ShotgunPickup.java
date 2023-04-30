package game.entity.pickup.weapon;

import game.entity.mob.Mob;
import game.entity.pickup.Pickup;
import game.weapon.Shotgun;

public class ShotgunPickup extends Pickup {

	public ShotgunPickup(double x, double y) { 
		super(x, y); 
	}
	
	public boolean take(Mob m) {
		if(!(m.weapon instanceof Shotgun)) m.weapon = new Shotgun(m);
		else m.weapon.takeAmmo((int)(m.weapon.getAmmoCapacity() * 1));
		return true;
	}

}