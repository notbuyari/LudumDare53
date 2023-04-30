package game.entity;

import game.entity.projectile.Bullet;
import game.gfx.Art;
import game.gfx.Bitmap;

public class Barrell extends Entity {

	public Barrell(double x, double y) { 
		super(x, y); 
		sprite = 13;
	}

	public void hitBy(Bullet b, double xxa, double yya, double zza) {
		level.explode(b, x, y, z, 60, 32);
		remove();
	}
	
	public void render(Bitmap screen) {
		int x = (int)this.x - 8;
		int y = (int)this.y - 12;
		screen.draw(Art.sprites[sprite], x, y);
	}
	
}