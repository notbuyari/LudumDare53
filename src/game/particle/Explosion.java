package game.particle;

import game.level.tile.Tile;

public class Explosion extends Particle {

	public Explosion(double x, double y, double z) {
		super(x, y, z);
		maxLifeTime = lifeTime = 5;
	}
	
	public void tick() {
		if(lifeTime-- <= 0) {
			remove();
			return;
		}
		
		int ps = lifeTime * 40 / maxLifeTime + 1;
		double dd = (maxLifeTime - lifeTime) / (double)maxLifeTime + 0.2;
		for(int i = 0; i < ps; i++) {
			double dir = random.nextDouble() * Math.PI * 2;
			double dist = random.nextDouble() * 6 * dd;
			double xx = x + Math.cos(dir) * dist;
			double yy = y + Math.sin(dir) * dist;
			double zz = z + random.nextDouble() * 10;
			
			FireParticle fire = new FireParticle(xx, yy, zz);
			if(random.nextInt(2) == 0) fire.lifeTime = fire.maxLifeTime / 2;
			fire.xa *= 0.1;
			fire.ya *= 0.1;
			fire.za *= 0.1;
			fire.xa += (xx - x) * 0.5;
			fire.ya += (yy - y) * 0.5;
			fire.gravity = 0.1;
			level.addParticle(fire);
			
			level.setTile(Tile.explosion, (int)(xx + 8) >> 4, (int)(yy + 8) >> 4);
		}
	}

}