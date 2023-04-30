package game.particle;

public class SplatParticle extends Particle {

	public SplatParticle(double x, double y, double z) {
		super(x, y, z);
		drag = 0.98;
		lifeTime /= 4;
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
	}
}