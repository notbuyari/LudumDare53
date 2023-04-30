package game.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	
	public static final Bitmap[] faces = loadAndCut("/faces.png", 16);
	public static final Bitmap[] gui = loadAndCut("/gui.png", 16);
	public static final Bitmap[] sprites = loadAndCut("/sprites.png", 16);
	public static final Bitmap[] tiles = loadAndCut("/tiles.png", 16);
	public static final Bitmap[] missiles = loadAndCut("/missiles.png", 8);
	public static final Bitmap[] font = loadAndCut("/font.png", 8);
	public static final Bitmap[] bigFont = loadAndCut("/big_font.png", 8);
	public static final Bitmap[] pickups = loadAndCut("/pickups.png", 8);
	public static final Bitmap[] particles = loadAndCut("/particles.png", 8);
	public static final Bitmap[] groundToExplosion = TileGen.gen(TileGen.ground, TileGen.explosion, 16);
	public static final Bitmap title = load("/title.png");
	
	public static Bitmap load(String path) {
		BufferedImage image;
		try {
			image = ImageIO.read(Art.class.getResourceAsStream(path));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load " + path);
		}
		
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return new Bitmap(result);
	}

	public static Bitmap[] loadAndCut(String path, int size) {
		BufferedImage sheet;
		try {
			sheet = ImageIO.read(Art.class.getResourceAsStream(path));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load " + path);
		}
		
		int sw = sheet.getWidth() / size;
		int sh = sheet.getHeight() / size;
		Bitmap[] result = new Bitmap[sw * sh];
		for(int y = 0; y < sh; y++) {
			for(int x = 0; x < sw; x++) {
				BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				g.drawImage(sheet, -x * size, -y * size, null);
				g.dispose();
				result[x + y * sw] = new Bitmap(image);
			}
		}
		
		return result;
	}
	
	public static Bitmap recolor(Bitmap b, int c0, int c1) {
		Bitmap result = new Bitmap(b.w, b.h);
		for(int i = 0; i < b.w * b.h; i++) {
			int c = b.pixels[i];
			if(c == 0xFFFFBC8C) result.pixels[i] = 0xFF000000 | c0;
			else if(c == 0xFF896B58) result.pixels[i] = 0xFF000000 | c1;
			else result.pixels[i] = c;
		}
		
		return result;
	}
	
}