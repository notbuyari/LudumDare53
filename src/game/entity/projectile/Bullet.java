package game.entity.projectile;

import game.entity.Entity;
import game.entity.mob.Mob;
import game.gfx.Bitmap;
import game.tools.MathUtils;
import game.weapon.Weapon;

public class Bullet extends Entity {

	public double xo;
	public double yo;
	public double zo;
	public double xStart;
	public double yStart;
	public double zStart;
	public Mob owner;
	public int dmg;
	public int lifeTime;
	public Weapon weapon;
	
	public Bullet(Mob owner, Weapon weapon, double x, double y, double xa, double ya, int dmg, double speed) {
		super(x, y);
		this.owner = owner;
		this.weapon = weapon;
		xo = this.x;
		yo = this.y;
		xr = yr = 2;
		xStart = x;
		yStart = y;
		zStart = z;
		this.xa = xa * speed;
		this.ya = ya * speed;
		this.za = za * speed;
		this.dmg = dmg;
		lifeTime = random.nextInt(20) + 40;
	}
	
	public void tick() {
		if(lifeTime-- <= 0) {
			remove();
			return;
		}
		
		xo = x;
		yo = y;
		attemptMove();
	}
	
	public void render(Bitmap screen) {		
		double xd = x - xo;
		double yd = y - yo;
		
		int c0 = 0xFFFF00;
		int c1 = 0xFF6A00;
		
		int steps = (int)(Math.sqrt(xd * xd + yd * yd) + 1);
		for(int i = 0; i < steps; i++) {
			if(Math.random() * steps < i) continue;
			
			double p = i / (double)steps;
			
			int col = MathUtils.lerpRGB(c0, c1, p);
			
			screen.fill((int)(x + xd * p), (int)(y + yd * p), (int)(x + xd * p), (int)(y + yd * p), col);
		}
	}
	
	public void collide(Entity e, double xxa, double yya, double zza) { 
		if(e != null) e.hitBy(this, xxa, yya, zza);
		remove(); 
	}
	
	public boolean blocks(Entity e) {
		if(e == owner || (e instanceof Bullet && !(e instanceof Rocket))) return false;
		return true;
	}
	
	public int getDamage(Mob m) { return getDamage(m.x, m.y); }
	
	public int getDamage(double xx, double yy) {
		double xd = xStart - xx;
		double yd = yStart - yy;
		
		double distanceTravelled = Math.sqrt(xd * xd + yd * yd) * 5;
		double dmg = this.dmg;
		
		if(distanceTravelled < weapon.nearDistance) dmg *= weapon.highRamp / 100.0;
		else if(distanceTravelled > weapon.farDistance) dmg *= weapon.lowRamp / 100.0;
		else if(distanceTravelled < weapon.midDistance) {
			double fraction = 1 - (distanceTravelled - weapon.nearDistance) / (weapon.midDistance - weapon.nearDistance);
			dmg *= (weapon.highRamp * fraction + 100 * (1 - fraction)) / 100.0;
		} else {
			double fraction = 1 - (distanceTravelled - weapon.midDistance) / (weapon.farDistance - weapon.midDistance);
			dmg *= (weapon.lowRamp * fraction + 100 * (1 - fraction)) / 100.0;			
		}
		
		int d = (int)(dmg + Weapon.random.nextDouble());
		return d;
	}
	
	public void applyHitEffect(Mob mob) {}
	
}