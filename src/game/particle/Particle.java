package game.particle;

import java.util.Random;

import game.gfx.Bitmap;
import game.gfx.Renderable;
import game.level.Level;

public abstract class Particle implements Renderable {

	protected static final Random random = new Random();
	public double x;
	public double y;
	public double z;
	public double xa;
	public double ya;
	public double za;
	public int lifeTime;
	public int maxLifeTime;
	public Level level;
	public double drag = 0.998;
	public double bounce = 0.6;
	public double gravity = 0.08;
	public int sprite;
	public boolean onGround;
	public boolean removed;
	
	public Particle(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		lifeTime = maxLifeTime = random.nextInt(20) + 100;
		
		do {
			xa = random.nextDouble() * 2 - 1;
			ya = random.nextDouble() * 2 - 1;
			za = random.nextDouble() * 2 - 1;
		} while(xa * xa + ya * ya + za * za < 1);
		double dd = Math.sqrt(xa * xa + ya * ya + za * za);
		double speed = 1.5;
		
		xa = xa / dd * speed;
		ya = ya / dd * speed;
		za = (za / dd + 0.4) * speed;
	}
	
	public void init(Level level) { this.level = level; }
	
	public void tick() {
		if(lifeTime-- <= 0) {
			remove();
			return;
		}
		
		onGround = z <= 1;
		
		xa *= onGround ? 0.5 : drag;
		ya *= onGround ? 0.5 : drag;
		
		za -= gravity;
		
		attemptMove();
	}
	
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
			collide(xxa, yya, zza);
			return;
		}
		
		if(zn < 0) {
			z = 0;
			return;
		}
		
		x = xn;
		y = yn;
		z = zn;
		return;
	}
	
	public void collide(double xxa, double yya, double zza) {
		if(xxa != 0) xa = 0;
		if(yya != 0) ya = 0;
		if(zza != 0) za = 0;
	}
	
	public void remove() { removed = true; }
	
}