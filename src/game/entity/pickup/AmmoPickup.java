package game.entity.pickup;

import game.entity.mob.Mob;

public class AmmoPickup extends Pickup {
	
	public AmmoPickup(double x, double y, int size) {
		super(x, y);
		this.size = size;
		sprite = size + 9;
	}
	
	public boolean take(Mob m) {
		if(!m.weapon.canPickupAmmo()) return false;
		if(size == 0) m.weapon.takeAmmo((int)(m.weapon.getAmmoCapacity() * 0.205));
		if(size == 1) m.weapon.takeAmmo((int)(m.weapon.getAmmoCapacity() * 0.50));
		if(size == 2) m.weapon.takeAmmo((int)(m.weapon.getAmmoCapacity() * 1));
		return true;
	}

}