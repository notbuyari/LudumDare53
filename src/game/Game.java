package game;

import game.gfx.Bitmap;
import game.screen.FadeScreen;
import game.screen.Screen;
import game.screen.TitleScreen;

public class Game {
	
	private Screen screen;
	
	public Game() { setScreen(new FadeScreen(60, new TitleScreen())); }
	
	public void setScreen(Screen screen) {
		this.screen = screen;
		this.screen.init(this);
	}

	public void tick(InputHandler input) { screen.tick(input); }
	public void render(Bitmap screen) { this.screen.render(screen); }
	
}