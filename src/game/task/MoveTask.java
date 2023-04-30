package game.task;

public class MoveTask extends Task {

	private double xTarget;
	private double yTarget;
	
	public MoveTask(double xTarget, double yTarget) {
		this.xTarget = xTarget;
		this.yTarget = yTarget;
	}
	
	public void set(double xTarget, double yTarget) {
		this.xTarget = xTarget;
		this.yTarget = yTarget;		
	}
	
	public boolean finished() {
		return owner.distanceToSqr(xTarget, yTarget) < 4;
	}

	public void tick() {
		if(owner.turnTowards(owner.angleTo(xTarget, yTarget))) {
			owner.moveForward();
		}
	}

}