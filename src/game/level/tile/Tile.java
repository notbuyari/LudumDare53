package game.level.tile;

import java.util.Random;

import game.gfx.Bitmap;
import game.level.Level;

public abstract class Tile {
	
	public static final Random random = new Random();
	public static final Tile[] tiles = new Tile[256];
	public static final Tile ground = new GroundTile(0);
	public static final Tile explosion = new ExplosionTile(1);
	public final byte id;
	
	public Tile(int id) {
		if(tiles[id] != null) throw new RuntimeException("Duplicate Tile IDs");
		
		tiles[id] = this;
		this.id = (byte)id;
	}
	
	public void tick(Level level, int xt, int yt) {}
	public abstract void render(Bitmap screen, Level level, int xt, int yt, int xScroll, int yScroll);
	
}