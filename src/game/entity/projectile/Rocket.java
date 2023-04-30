package game.entity.projectile;

import game.entity.Entity;
import game.entity.mob.Mob;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.particle.FireParticle;
import game.weapon.Weapon;

public class Rocket extends Bullet {
	
	private double angle;
	
	public Rocket(Mob owner, Weapon weapon, double x, double y, double xa, double ya, int dmg) {
		super(owner, weapon, x, y, xa, ya, dmg, 1);
		angle = Math.atan2(ya, xa);
	}
	
	public void tick() {
		FireParticle fire = new FireParticle(x - xa * 2, y - ya * 2, z - za * 2);
		fire.xa *= 0.1;
		fire.ya *= 0.1;
		fire.za *= 0.1;
		fire.xa += xa * 1;
		fire.ya += ya * 1;
		fire.za += za * 1;
		fire.lifeTime = fire.maxLifeTime / 2;
		level.addParticle(fire);
	
		super.tick();
	}

	public void render(Bitmap screen) {
		int x = (int)this.x;
		int y = (int)this.y;
		screen.draw(Art.missiles[sprite].rotate(angle), x, y);
	}
	
	public void collide(Entity e, double xxa, double yya, double zza) {
		if(e != null) e.hitBy(this, xxa, yya, zza);
		if(e instanceof Bullet) level.explode(this, x, y, z, dmg, 32);
		remove();
	}
	
	public void hitBy(Bullet b, double xxa, double yya, double zza) {
		if(b instanceof Bullet && b != this) level.explode(this, x, y, z, dmg, 32);
		level.explode(this, x, y, z, dmg, 32);
	}

}