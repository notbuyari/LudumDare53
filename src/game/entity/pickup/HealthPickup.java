package game.entity.pickup;

import game.entity.mob.Mob;

public class HealthPickup extends Pickup {
	
	public HealthPickup(double x, double y, int size) {
		super(x, y);
		this.size = size;
		sprite = size + 6;
	}
	
	public boolean take(Mob m) {
		if(m.health >= m.maxHealth) return false;
		if(size == 0) m.heal((int)(m.maxHealth * 0.1));
		if(size == 1) m.heal((int)(m.maxHealth * 0.5));
		if(size == 2) m.heal((int)(m.maxHealth * 1));
		return true;
	}
	
}