package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.gfx.Bitmap;
import game.gfx.Font;

public class GameOverScreen extends Screen {

	public GameScreen gameScreen;
	
	public GameOverScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	
	public void tick(InputHandler input) {
		gameScreen.tick(input);
		if(input.leftClicked) setScreen(new FadeScreen(60, new GameScreen()));
	}

	public void render(Bitmap screen) {
		gameScreen.render(screen);
		
		for(int i = 0; i < screen.w * screen.h; i++) {
			int a = (screen.pixels[i] >> 24) & 0xFF;
			int r = (screen.pixels[i] >> 16) & 0xFF;
			int g = (screen.pixels[i] >> 8) & 0xFF;
			int b = (screen.pixels[i] >> 0) & 0xFF;
		
			int rr = r * 100 / 255;
			int gg = g * 100 / 255;
			int bb = b * 100 / 255;
		
			screen.pixels[i] = a << 24 | rr << 16 | gg << 8 | bb;
		}
		
		String text = "Game Over!";
		Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 6) / 2, GameComponent.HEIGHT / 3);
		
		text = "Click to restart";
		Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 6) / 2, GameComponent.HEIGHT - 64);
	}

}