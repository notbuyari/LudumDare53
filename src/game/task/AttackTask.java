package game.task;

import game.entity.mob.Mob;

public class AttackTask extends FollowTask {

	public AttackTask(Mob target, int time, double minDist) {
		super(target, time, minDist);
	}
	
	public void idle() { 
		if(owner.weapon.canUse()) owner.updateWeapon(target); 
	}

}