package game.entity.mob.enemy;

public class BaldEnemy extends Enemy {

	public BaldEnemy(double x, double y) {
		super(x, y);
		noHair = true;
		sprite = 7;
	}
	
}