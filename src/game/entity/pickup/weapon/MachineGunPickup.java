package game.entity.pickup.weapon;

import game.entity.mob.Mob;
import game.entity.pickup.Pickup;
import game.weapon.MachineGun;

public class MachineGunPickup extends Pickup {

	public MachineGunPickup(double x, double y) { 
		super(x, y);
		sprite = 5;
	}
	
	public boolean take(Mob m) {
		if(!(m.weapon instanceof MachineGun)) m.weapon = new MachineGun(m);
		else m.weapon.takeAmmo((int)(m.weapon.getAmmoCapacity() * 1));
		return true;
	}

}