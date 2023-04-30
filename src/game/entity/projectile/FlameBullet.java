package game.entity.projectile;

import game.entity.mob.Mob;
import game.gfx.Bitmap;
import game.particle.FireParticle;
import game.weapon.Weapon;

public class FlameBullet extends Bullet {

	public FlameBullet(Mob owner, Weapon weapon, double x, double y, double xa, double ya, int dmg) {
		super(owner, weapon, x, y, xa, ya, dmg, 0.5);
		lifeTime = 45;
	}
	
	public void tick() {
		FireParticle fire = new FireParticle(x - xa * 2, y - ya * 2, z - za * 2);
		fire.xa *= 0.5;
		fire.ya *= 0.5;
		fire.za *= 0.5;
		fire.xa += xa * 1.5;
		fire.ya += ya * 1.5;
		fire.za += za * 1.5;
		fire.lifeTime = fire.maxLifeTime / 2;
		fire.noSmoke = true;
		level.addParticle(fire);
		super.tick();
	}
	
	public void renderShadow(Bitmap screen) {}
	public void render(Bitmap screen) {}
	
	public void applyHitEffect(Mob mob) { mob.burnTime = 600; }
	
}