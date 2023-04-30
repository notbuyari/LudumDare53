package game.entity.pickup.weapon;

import game.entity.mob.Mob;
import game.entity.pickup.Pickup;
import game.weapon.Flamethrower;

public class FlamethrowerPickup extends Pickup {

	public FlamethrowerPickup(double x, double y) { 
		super(x, y); 
		sprite = 3;
	}

	public boolean take(Mob m) {
		if(!(m.weapon instanceof Flamethrower)) m.weapon = new Flamethrower(m);
		else m.weapon.takeAmmo((int)(m.weapon.getAmmoCapacity() * 1));
		return true;
	}
	
}