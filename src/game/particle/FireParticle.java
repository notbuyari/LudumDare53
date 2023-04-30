package game.particle;

import game.gfx.Art;
import game.gfx.Bitmap;

public class FireParticle extends Particle {

	public boolean noSmoke;
	
	public FireParticle(double x, double y, double z) {
		super(x, y, z);
		maxLifeTime = lifeTime /= 2;
		drag = 0.92;
		sprite = random.nextInt(3);
		gravity = 0;
	}
	
	public void tick() {
		super.tick();
		
		if(removed && random.nextInt(5) == 0 && !noSmoke) {
			SmokeParticle smoke = new SmokeParticle(x, y, z);
			smoke.xa *= 0.1;
			smoke.ya *= 0.1;
			smoke.za *= 0.1;
			smoke.xa += xa * 1;
			smoke.ya += ya * 1;
			smoke.za += za * 1;
			level.addParticle(smoke);
		}
	}
	
	public void renderShadow(Bitmap screen) {}

	public void render(Bitmap screen) {
		int x = (int)this.x;
		int y = (int)this.y;
		sprite = (int)(lifeTime / (double)maxLifeTime * 4.0);
		screen.draw(Art.particles[sprite], x, y - (int)z);
	}
	
}