package game.task;

import game.entity.mob.Mob;

public abstract class Task {

	protected Mob owner;
	
	public void init(Mob owner) { this.owner = owner; }
	
	public abstract boolean finished();
	public abstract void tick();
	
}