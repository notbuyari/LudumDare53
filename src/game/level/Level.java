package game.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import game.entity.Entity;
import game.entity.projectile.Bullet;
import game.gfx.Bitmap;
import game.gfx.Renderable;
import game.level.tile.Tile;
import game.particle.Explosion;
import game.particle.Particle;

public class Level {
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Renderable> renderList = new ArrayList<Renderable>();
	public static final Random random = new Random();
	public int w;
	public int h;
	public int[] tiles;
	public int xScroll;
	public int yScroll;
	public int shakeTime;
	private Comparator<Renderable> renderComparator = new Comparator<Renderable>() {
		public int compare(Renderable r0, Renderable r1) {
			double y0 = r0 instanceof Particle ? ((Particle)r0).y : ((Entity)r0).y;
			double y1 = r1 instanceof Particle ? ((Particle)r1).y : ((Entity)r1).y;
			return Double.compare(y0, y1);
		}
		
	};
	
	public Level(int w, int h) {
		this.w = w;
		this.h = h;
		this.tiles = new int[w * h];
	}
	
	public void spawnEntity(Entity e) {
		int x = random.nextInt(w);
		int y = random.nextInt(h);
		
		e.x = (x << 4) + 8;
		e.y = (y << 4) + 8;
		addEntity(e);
	}
		
	public void addEntity(Entity e) {
		entities.add(e);
		e.init(this);
	}
	
	public void addParticle(Particle p) {
		particles.add(p);
		p.init(this);
	}
	
	public void tick() {
		for(int i = 0; i < w * h / 50; i++) {
			int xt = random.nextInt(w);
			int yt = random.nextInt(h);
			getTile(xt, yt).tick(this, xt, yt);
		}
		
		for(int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			
			if(!p.removed) {
				p.tick();
				continue;
			}
			
			particles.remove(i--);
		}
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			
			if(!e.removed) {
				e.tick();
				continue;
			}
			
			entities.remove(i--);
		}
		
	}
	
	public void renderBackground(Bitmap screen) {
		int x0 = xScroll >> 4;
		int y0 = yScroll >> 4;
		int x1 = x0 + w;
		int y1 = y0 + h;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 > w) x1 = w;
		if(y1 > h) y1 = h;
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				Tile tile = getTile(x, y);
				if(tile == null) continue;		
				tile.render(screen, this, x << 4, y << 4, xScroll, yScroll);
			}
		}
	}
	
	public void renderShadows(Bitmap screen) {
		renderList.clear();
		renderList.addAll(entities);
		renderList.addAll(particles);
		
		screen.xOffs = -xScroll;
		screen.yOffs = -yScroll;
		
		for(int i = 0; i < renderList.size(); i++) {
			renderList.get(i).renderShadow(screen);
		}
		
		
		screen.xOffs = 0;
		screen.yOffs = 0;
	}
	
	public void renderSprites(Bitmap screen) {
		renderList.clear();
		renderList.addAll(entities);
		renderList.addAll(particles);
		Collections.sort(renderList, renderComparator);
	
		screen.xOffs = -xScroll;
		screen.yOffs = -yScroll;
		
		for(int i = 0; i < renderList.size(); i++) {
			renderList.get(i).render(screen);
		}
		
		
		screen.xOffs = 0;
		screen.yOffs = 0;
	}
	
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= w || y >= h) return null;
		return Tile.tiles[tiles[x + y * w]];
	}
	
	public void setTile(Tile tile, int x, int y) {
		if(x < 0 || y < 0 || x >= w || y >= h) return;
		tiles[x + y * w] = tile.id;
	}
	
	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}
		
	public List<Entity> getEntities(double x0, double y0, double x1, double y1) {
		List<Entity> result = EntityListCache.getCache();
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if(!e.intersects(x0, y0, x1, y1)) continue;
			result.add(e);
		}
		
		return result;
	}
	
	public void explode(Bullet b, double x, double y, double z, int dmg, double radius) {
		double r = radius;
		List<Entity> entities = getEntities(x - r, y - r, x + r, y + r);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			double xd = e.x - x;
			double yd = e.y - y;
			double zd = (e.z + 5 / 2) - z;
			if (xd * xd + yd * yd + zd * zd < r * r) {
				double dd = Math.sqrt(xd * xd + yd * yd + zd * zd);
				xd /= dd;
				yd /= dd;
				zd /= dd;
				dd /= r;
				double falloff = (1 - dd) * 0.5 + 0.5;
				falloff *= 0.5;
				e.handleExplosion(b, (int) (dmg * falloff), xd * 5 * (1 - dd), yd * 5 * (1 - dd), zd * 5 * (1 - dd));
			}
		}
		
		addParticle(new Explosion(x, y, z));
	}
	
}