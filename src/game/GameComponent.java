package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import game.gfx.Bitmap;
import game.level.EntityListCache;

public class GameComponent extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final String TITLE = "Delivery[LD53]";
	public static final int WIDTH = 160;
	public static final int HEIGHT = 120;
	public static final int SCALE = 4;
	private boolean running;
	
	private BufferedImage screenImage;
	private Bitmap screenBitmap;
	private Game game;
	private InputHandler input;
	
	public synchronized void start() {
		if(running) return;
		running = true;
		new Thread(this).start();
	}
	
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double nsPerTick = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int ticks = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			while(delta >= 1) {
				delta--;
				tick();
				ticks++;
				EntityListCache.reset();
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("frames: " + frames + " ticks: " + ticks);
				frames = 0;
				ticks = 0;
			}
			
			try {
				Thread.sleep(2L);
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}
	
	public void init() {
		screenImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		screenBitmap = new Bitmap(screenImage);
		game = new Game();
		input = new InputHandler(this);
	}
	
	public void tick() {
		input.tick();
		game.tick(input);
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		
		screenBitmap.clear(0);
		game.render(screenBitmap);
		
		Graphics g = bs.getDrawGraphics();

		g.drawImage(this.screenImage, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		GameComponent game = new GameComponent();
		frame.setTitle(TITLE);
		game.setMinimumSize(d);
		game.setMaximumSize(d);
		game.setPreferredSize(d);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		game.start();
	}
	
}