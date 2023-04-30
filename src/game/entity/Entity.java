package game.entity;

import java.util.List;
import java.util.Random;

import game.entity.projectile.Bullet;
import game.gfx.Bitmap;
import game.gfx.Renderable;
import game.level.Level;

public abstract class Entity implements Renderable {

	protected static final Random random = new Random();
	public double x;
	public double y;
	public double z;
	public double xa;
	public double ya;
	public double za;
	public double xr = 4;
	public double yr = 4;
	public double rot;
	public int sprite;
	public double movement;
	public Level level;
	public double gravity = 0.08;
	public double friction = 0.9;
	public double bounce = 0.01;
	public boolean onGround;
	public boolean removed;
	public Team team = Team.ENEMY;
	
	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void init(Level level) { this.level = level; }
	
	public void tick() {}
	public void renderShadow(Bitmap screen) {}
	public void render(Bitmap screen) {}
	
	public void attemptMove() {		
		int steps = (int)(Math.sqrt(xa * xa + ya * ya + za * za) + 1);
		for(int i = 0; i < steps; i++) {
			move(xa / steps, 0, 0);
			move(0, ya / steps, 0);
			move(0, 0, za / steps);
		}
	}
	
	public void move(double xxa, double yya, double zza) {
		double xn = x + xxa;
		double yn = y + yya;
		double zn = z + zza;
		
		if(xn < 0 || yn < 0 || xn >= level.w * 16 || yn >= level.h * 16) {
			collide(null, xxa, yya, zza);
			return;
		}
		
		if(zn < 0) {
			z = 0;
			return;
		}
	
		List<Entity> wasInside = level.getEntities(x - xr, y - yr, x + xr, y + yr);
		List<Entity> isInside = level.getEntities(xn - xr, yn - yr, xn + xr, yn + yr);
		for(int i = 0; i < isInside.size(); i++) {
			Entity e = isInside.get(i);
			if(e == this) continue;
			e.touchedBy(this, xxa, yya, zza);
		}
		
		isInside.removeAll(wasInside);
		for(int i = 0; i < isInside.size(); i++) {
			Entity e = isInside.get(i);
			if(e == this || !e.blocks(this) || !blocks(e)) continue;
			collide(e, xxa, yya, zza);
			return;
		}
		
		x = xn;
		y = yn;
		z = zn;
		return;
	}
		
	public boolean intersects(double x0, double y0, double x1, double y1) {
		return !(x + xr <= x0 || y + yr <= y0 || x - xr >= x1 || y - yr >= y1);
	}
	
	public void collide(Entity e, double xxa, double yya, double zza) {
		if(xxa != 0) xa = 0;
		if(yya != 0) ya = 0;
		if(zza != 0) za = 0;
	}
	
	public double distanceToSqr(Entity e) {
		double xd = x - e.x;
		double yd = y - e.y;
		return xd * xd + yd * yd;
	}
	
	public double distanceToSqr(double xt, double yt) {
		double xd = xt - x;
		double yd = yt - y;
		return xd * xd + yd * yd;
	}
	
	public double angleTo(double xt, double yt) { return Math.atan2(yt - y, xt - x); }
	public double angleTo(Entity e) { return angleTo(e.x, e.y); }
	public void hitBy(Bullet b, double xxa, double yya, double zza) {}
	public void touchedBy(Entity e, double xxa, double yya, double zza) {}
	public void hurt(Entity e, double xxa, double yya, double zza) {}
	public boolean blocks(Entity e) { return true; }
	public void remove() { removed = true; }
	public void handleExplosion(Bullet b, int dmg, double xd, double yd, double zd) {}
	public boolean isAlive() { return !removed;}
	
}