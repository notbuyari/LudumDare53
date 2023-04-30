package game.particle;

import game.gfx.Art;
import game.gfx.Bitmap;

public class SmokeParticle extends Particle {

	public SmokeParticle(double x, double y, double z) {
		super(x, y, z);
		maxLifeTime = lifeTime /= 2;
		drag = 0.92;
		gravity = -0.02;
		sprite = random.nextInt(3) + 8;
	}
	
	public void renderShadow(Bitmap screen) {}
	
	public void render(Bitmap screen) {
		int x = (int)this.x;
		int y = (int)this.y;
		screen.draw(Art.particles[sprite], x, y - (int)z);
	}

}