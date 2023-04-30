package game.entity.pickup;

import game.entity.Entity;
import game.entity.mob.Mob;
import game.entity.projectile.Bullet;
import game.gfx.Art;
import game.gfx.Bitmap;

public class Pickup extends Entity {

	public int takeTime;
	public int time;
	public int size;
	public boolean canPickupAgain;
	
	public Pickup(double x, double y) { 
		super(x, y); 
		time = random.nextInt(62);
	}
	
	public boolean isNeeded(Mob m) { return false; }
	
	public void tick() {
		if(takeTime > 0 && canPickupAgain) {
			takeTime--;
			time = random.nextInt(62);
			return;
		}
		
		time++;
		
		double r = 3;
		for(Entity e : level.getEntities(x - r, y - r, x + r, y + r)) {
			if(e instanceof Mob && e != this) {
				Mob m = (Mob)e;
				if(m.isAlive()) {
					if(take(m)) takeTime = 600;
				}				
			}
			
		}
		
		z = Math.cos(time / 10.0) * 1.5 + 4;
	}
	
	public void renderShadow(Bitmap screen) {
		if(isTaken()) return;
		int x = (int)this.x;
		int y = (int)this.y;
		screen.fill(x - 1 - size, y, x + 1 + size, y, 1);
	}
	
	public void render(Bitmap screen) {
		if(isTaken()) return;
		int x = (int)this.x - 4;
		int y = (int)this.y - 5;
		screen.draw(Art.pickups[sprite], x, y - (int)z);
	}
	
	public boolean blocks(Entity e) { return false; }
	public boolean isTaken() { return takeTime > 0; }
	public boolean take(Mob m) { return false; }
	public void handleExplosion(Bullet b, int dmg, double xd, double yd, double zd) {}
	
}