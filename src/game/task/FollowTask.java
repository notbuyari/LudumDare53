package game.task;

import game.entity.mob.Mob;

public class FollowTask extends Task {

	protected int time;
	protected Mob target;
	protected double minDist;
	
	public FollowTask(Mob target, int time, double minDist) {
		this.target = target;
		this.time = time;
		this.minDist = minDist;
	}
	
	public boolean finished() { return time <= 0; }

	public void tick() {
		time--;
		
		if(owner.distanceToSqr(target) > minDist * minDist) {
			if(owner.turnTowards(owner.angleTo(target))) {
				owner.moveForward();
			}
		} else idle();
	}	
	
	public void idle() {}

}