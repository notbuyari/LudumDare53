package game.entity;

import game.entity.pickup.AmmoPickup;
import game.entity.pickup.HealthPickup;
import game.entity.projectile.Bullet;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.particle.CrateParticle;

public class Crate extends Entity {
		
	private boolean hasDropped;
	
	public Crate(double x, double y) {
		super(x, y);
		sprite = 12;
	}
	
	public void handleExplosion(Bullet b, int dmg, double xd, double yd, double zd) { 
		for(int i = 0; i < 5; i++) {
			level.addParticle(new CrateParticle(x, y, z + i));
		}
		
		remove(); 
		drop();
	}
	
	public void hitBy(Bullet b, double xxa, double yya, double zza) { 
		for(int i = 0; i < 5; i++) {
			level.addParticle(new CrateParticle(x, y, z + i));
		}
		
		remove(); 
		drop();
	}
		
	public void render(Bitmap screen) {
		int x = (int)this.x - 8;
		int y = (int)this.y - 12;
		screen.draw(Art.sprites[sprite], x, y);
	}
	
	public void drop() {
		if(hasDropped) return;
		if(random.nextBoolean()) level.addEntity(new HealthPickup(x, y, random.nextInt(3)));
		else level.addEntity(new AmmoPickup(x, y, random.nextInt(3)));
		hasDropped = true;
	}

}