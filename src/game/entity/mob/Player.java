package game.entity.mob;

import game.InputHandler;
import game.entity.Team;

public class Player extends Mob {
	
	public Player(double x, double y) { 
		super(x, y); 
		team = Team.PLAYER;
	}
	
	public void tick() {
		super.tick();
		if(tickCount % 480 == 0 && hurtTime <= 0 && health < maxHealth) health += random.nextInt(2) + 1;
	}
		
	public void updateInput(InputHandler input) {
		double xxa = 0.0;
		double yya = 0.0;
		
		if(input.leftPressed && weapon.canUse()) {
			double xd = input.x - x + level.xScroll;
			double yd = input.y - y + level.yScroll;
			double dd = Math.sqrt(xd * xd + yd * yd);
			
			if(turnTowards(Math.atan2(yd, xd))) {				
				xd /= dd;
				yd /= dd;
				
				if(weapon.ammoLoaded > 0 || weapon.canUse()) weapon.shoot(xd, yd, 0);
			}
		} else if(input.leftClicked && weapon.ammoLoaded < weapon.maxAmmoCarried && weapon.ammoLoaded <= 0) weapon.reload();
		
		if(input.reload.clicked) {
			weapon.reload();
		}
				
		if(!input.leftPressed || weapon.ammoLoaded <= 0) {
			boolean sprinting = input.sprint.down;
			if(input.up.down) yya -= moveSpeed;
			if(input.down.down) yya += moveSpeed;
			if(input.left.down) xxa -= moveSpeed;
			if(input.right.down) xxa += moveSpeed;
			if(input.jump.down) jump();
			
			moveSpeed = sprinting ? 0.2 : 0.1;
			
			if(xxa * xxa + yya * yya > 0.00001 && turnTowards(Math.atan2(yya, xxa))) {
				xa += xxa;
				ya += yya;
			}
		}
		
	}
		
}