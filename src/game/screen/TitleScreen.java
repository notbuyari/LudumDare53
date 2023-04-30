package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Font;

public class TitleScreen extends Screen {

	private int tickCount;
	
	public void tick(InputHandler input) {
		tickCount++;
		if(input.leftClicked) setScreen(new GameScreen());
	}

	public void render(Bitmap screen) {
		screen.draw(Art.title, 0, 0);
		
		if(tickCount / 10 % 4 == 0) return;
		String text = "CLICK TO PLAY";
		Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 6) / 2, GameComponent.HEIGHT - 30);
	}

}