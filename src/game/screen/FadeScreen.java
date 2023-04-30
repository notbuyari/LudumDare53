package game.screen;

import game.Game;
import game.InputHandler;
import game.gfx.Bitmap;

public class FadeScreen extends Screen {

	private final int duration;
	private Screen parent;
	private int tickCount;
	
	public FadeScreen(int duration, Screen parent) {
		this.duration = duration;
		this.parent = parent;
	}
	
	public void init(Game game) {
		super.init(game);
		parent.init(game);
	}
	
	public void tick(InputHandler input) {
		if(tickCount++ >= duration) setScreen(parent);
		parent.tick(input);
	}

	public void render(Bitmap screen) {
		parent.render(screen);
	
		for(int i = 0; i < screen.w * screen.h; i++) {
			int a = (screen.pixels[i] >> 24) & 0xFF;
			int r = (screen.pixels[i] >> 16) & 0xFF;
			int g = (screen.pixels[i] >> 8) & 0xFF;
			int b = (screen.pixels[i] >> 0) & 0xFF;
		
			int rr = r * tickCount / duration;
			int gg = g * tickCount / duration;
			int bb = b * tickCount / duration;
		
			screen.pixels[i] = a << 24 | rr << 16 | gg << 8 | bb;
		}
	}
	
}