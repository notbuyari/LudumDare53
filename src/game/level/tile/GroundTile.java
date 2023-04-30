package game.level.tile;

import game.gfx.Art;
import game.gfx.Bitmap;
import game.level.Level;

public class GroundTile extends Tile {

	public GroundTile(int id) { super(id); }

	public void render(Bitmap screen, Level level, int xt, int yt, int xScroll, int yScroll) {
		int xx = xt >> 4;
		int yy = yt >> 4;
		int t = 0;
		
		if(level.getTile(xx, yy) != ground) t++;
		if(level.getTile(xx + 1, yy) != ground) t += 2;
		if(level.getTile(xx, yy + 1) != ground) t += 4;
		if(level.getTile(xx + 1, yy + 1) != ground) t += 8;
		
		screen.draw(Art.groundToExplosion[t], xt - xScroll, yt - yScroll);
	}
	
	public void tick(Level level, int xt, int yt) {
		int xn = xt;
		int yn = yt;
		
		if(random.nextBoolean()) xn += random.nextInt(2) * 2 - 1;
		else yn += random.nextInt(2) * 2 - 1;
		
		if(level.getTile(xn, yn) == explosion) level.setTile(Tile.ground, xn, yn);
	}
	
}