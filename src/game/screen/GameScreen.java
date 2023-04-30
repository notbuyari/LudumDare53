package game.screen;

import java.util.Random;

import game.GameComponent;
import game.InputHandler;
import game.entity.Barrell;
import game.entity.Crate;
import game.entity.mob.Player;
import game.entity.mob.enemy.Enemy;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Font;
import game.level.Level;

public class GameScreen extends Screen {

	private static final Random random = new Random();
	public Level level;
	public Player player;

	public double xOffset;
	public double yOffset;
	public double xOffsetA;
	public double yOffsetA;
	public double xOffsetT;
	public double yOffsetT;
	public Bitmap shadows;
	
	public int enemies;
	public int max = random.nextInt(25, 60);
	
	
	public GameScreen() {
		level = new Level(random.nextBoolean() ? 128 : 64, random.nextBoolean() ? 128 : 64);
		player = new Player(GameComponent.WIDTH / 2, GameComponent.HEIGHT / 2);
		level.addEntity(player);
				
		for(int i = 0; i < max; i++) {
			level.spawnEntity(new Enemy(0, 0));			
			level.spawnEntity(new Barrell(0, 0));
			level.spawnEntity(new Crate(0, 0));
		}
		
	}
	
	public void tick(InputHandler input) {
		player.updateInput(input);
		level.tick();
		
		if(player.removed) {
			setScreen(new GameOverScreen(this));
		}
		

		xOffsetA *= 0.7;
		yOffsetA *= 0.7;
		
		double xd = xOffset + GameComponent.WIDTH / 2 - player.x;
		double yd = yOffset + GameComponent.HEIGHT / 2 - player.y;
		double dd = 36;
		
		if(xd * xd + yd * yd > dd * dd) {
			xOffsetT = player.x - GameComponent.WIDTH / 2;
			yOffsetT = player.y - GameComponent.HEIGHT / 2;
		}
		
		xd = xOffsetT - xOffset;
		yd = yOffsetT - yOffset;
		
		xOffsetA += xd * 0.015;
		yOffsetA += yd * 0.015;
		
		xOffset += xOffsetA;
		yOffset += yOffsetA;
		
		if(xOffset < 0) xOffset = 0;
		if(yOffset < 0) yOffset = 0;
		if(xOffset > level.w * 16 - GameComponent.WIDTH - 16) xOffset = level.w * 16 - GameComponent.WIDTH - 16;
		if(yOffset > level.h * 16 - GameComponent.HEIGHT - 16) yOffset = level.h * 16 - GameComponent.HEIGHT - 16;		
		
		level.setScroll((int)Math.floor(xOffset), (int)Math.floor(yOffset));
	}

	public void render(Bitmap screen) {
		if(shadows == null) shadows = new Bitmap(screen.w, screen.h);
		
		shadows.clear(0);
		level.renderBackground(screen);
		level.renderShadows(shadows);
		screen.shade(shadows);
		level.renderSprites(screen);
		screen.xOffs = 0;
		screen.yOffs = 0;
		
		renderGUI(screen);
	}
	
	public void renderGUI(Bitmap screen) {
		int faceIndex = 0;
		
		if(player.health == 100) faceIndex = 0;
		if(player.health <= 50) faceIndex = 1;
		if(player.health <= 25) faceIndex = 2;
		if(player.health <= 0) faceIndex = 3;
		
		screen.draw(Art.faces[faceIndex], 2, GameComponent.HEIGHT - 16);
		Font.drawGUIFont("" + player.health, screen, 22, GameComponent.HEIGHT - 12);
		
		
		String text = "" + player.weapon.ammoLoaded + "/" + player.weapon.maxAmmoLoaded;
		Font.drawGUIFont(text, screen, (280 - text.length() * 8) / 2, GameComponent.HEIGHT - 12);		
				
		screen.scaleDraw(Art.gui[player.weapon.getGUISprite()], 123, 85, 2);
	}
	
}