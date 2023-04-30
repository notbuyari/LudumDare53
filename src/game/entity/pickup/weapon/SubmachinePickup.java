package game.entity.pickup.weapon;

import game.entity.mob.Mob;
import game.entity.pickup.Pickup;
import game.weapon.Submachine;

public class SubmachinePickup extends Pickup {

	public SubmachinePickup(double x, double y) { 
		super(x, y);
		sprite = 1;
	}
	
	public boolean take(Mob m) {
		if(!(m.weapon instanceof Submachine)) m.weapon = new Submachine(m);
		else m.weapon.takeAmmo((int)(m.weapon.getAmmoCapacity() * 1));
		return true;
	}

}