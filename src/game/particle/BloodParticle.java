package game.particle;

import game.gfx.Bitmap;
import game.tools.MathUtils;

public class BloodParticle extends Particle {

	public int c0 = 0x3A0000;
	public int c1 = 0x7F0000;	
	public int col;
	
	public BloodParticle(double x, double y, double z) {
		super(x, y, z);
		drag = 0.98;
		bounce = 0.1;
		col = c1;
	}
	
	public void tick() {
		super.tick();
		double p = lifeTime / (double)maxLifeTime;
		col = MathUtils.lerpRGB(c0, c1, p);
	}
	
	public void renderShadow(Bitmap screen) {
		int x = (int)this.x;
		int y = (int)this.y;
		screen.fill(x, y, x, y, 0x7F0000);
	}
	
	public void render(Bitmap screen) {
		int x = (int)this.x;
		int y = (int)this.y;
		screen.fill(x, y - (int)z, x, y - (int)z, col);
	}
	
}