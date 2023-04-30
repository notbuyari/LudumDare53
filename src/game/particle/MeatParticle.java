package game.particle;

import game.gfx.Bitmap;
import game.tools.MathUtils;

public class MeatParticle extends Particle {

	public int c0 = 0x3A0000;
	public int c1 = 0x7F0000;	
	public int col;
	
	public MeatParticle(double x, double y, double z) { 
		super(x, y, z); 
	}
	
	public void tick() {
		super.tick();
		BloodParticle blood = new BloodParticle(x, y, z);
		blood.xa *= 0.1;
		blood.ya *= 0.1;
		blood.za *= 0.1;
		blood.xa += xa * 0.5;
		blood.ya += ya * 0.5;
		blood.za += za * 0.5;
		level.addParticle(blood);
		
		double p = lifeTime / (double)maxLifeTime;
		col = MathUtils.lerpRGB(c0, c1, p);
	}
	
	public void renderShadow(Bitmap screen) {
		int x = (int)this.x;
		int y = (int)this.y;
		screen.fill(x, y, x + 1, y + 1, 1);
	}
	
	public void render(Bitmap screen) {
		int x = (int)this.x;
		int y = (int)this.y;
		screen.fill(x, y - (int)z, x + 1, y + 1 - (int)z, col);
	}
	
	public void collide(double xxa, double yya, double zza) {
		if(za < -0.5) {
			for(int i = 0; i < 20; i++) {
				BloodParticle blood = new BloodParticle(x, y, 0);
				blood.xa *= 0.4;
				blood.ya *= 0.4;
				blood.za *= 0.2;
				blood.xa += xa * 0.5;
				blood.ya += ya * 0.5;
				blood.za = -za * 0.5;
				level.addParticle(blood);
			}
		}
		
		super.collide(xxa, yya, zza);
	}

}