package game.entity.pickup.weapon;

import game.entity.mob.Mob;
import game.entity.pickup.Pickup;
import game.weapon.SniperRifle;

public class SniperRiflePickup extends Pickup {

	public SniperRiflePickup(double x, double y) { 
		super(x, y);
		sprite = 4;
	}
	
	public boolean take(Mob m) {
		if(!(m.weapon instanceof SniperRifle)) m.weapon = new SniperRifle(m);
		else m.weapon.takeAmmo((int)(m.weapon.getAmmoCapacity() * 1));
		return true;
	}

}