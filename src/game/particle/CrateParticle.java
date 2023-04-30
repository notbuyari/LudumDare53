package game.particle;

import game.gfx.Art;
import game.gfx.Bitmap;

public class CrateParticle extends Particle {

	public double rot;
	public double rotA;
	
	public CrateParticle(double x, double y, double z) {
		super(x, y, z);
		sprite = 16;
	}
	
	public void tick() {
		super.tick();
		
		rot += rotA;
		
		rotA += xa * 0.2;
		rotA *= 0.3;
	}
	
	public void render(Bitmap screen) {
		int x = (int)this.x;
		int y = (int)this.y;
		screen.draw(Art.particles[sprite].rotate(rot), x, y - (int)z);
	}

}