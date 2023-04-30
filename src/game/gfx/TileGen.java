package game.gfx;

import java.util.Random;

import game.level.NoiseMap;
import game.tools.MathUtils;

public class TileGen {

	public static final int[] ground = Art.tiles[0].pixels;
	public static final int[] explosion = genTexture(16, 0x111111, 0x333333, 0.001);
	
	public static int[] genTexture(int size, int baseCol, int col, double rnd) {
		Random random = new Random(3271);
		int[] result = new int[size * size];
		for(int i = 0; i < size * size; i++) {
			result[i] = baseCol;
			if(!(random.nextDouble() <= rnd)) continue;
			result[i] = col;
		}
		
		return result;
	}
	
	public static Bitmap[] gen(int[] t0, int[] t1, int size) {
		int[] hm = new NoiseMap().getNoise(1019, 4, 2);
		double iSize = 1.0 / size;
		Bitmap[] result = new Bitmap[16];
		for(int i = 0; i < 16; i++) {
			int[] data = new int[size * size];
			
			int a = (i >> 0) & 1;
			int b = (i >> 1) & 1;
			int c = (i >> 2) & 1;
			int d = (i >> 3) & 1;
			
			for(int y = 0; y < size; y++) {
				double yy = y * iSize;
				for(int x = 0; x < size; x++) {
					double xx = x * iSize;
					
					double ab = a + (b - a) * xx;
					double cd = c + (d - c) * xx;
					
					double val = ab + (cd - ab) * yy;
					val = val * 4.0 - 0.4 + hm[(int)(xx * 16) + (int)(yy * 16) * 16] * 0.005;
					if(val < 0) val = 0;
					if(val > 1) val = 1;
					
					int c0 = t0[x + y * size];
					int c1 = t1[x + y * size];
					int col = MathUtils.lerpRGB(c0, c1, val);
					
					data[x + y * size] = col;
				}
			}
		
			result[i] = new Bitmap(size, size, data);
		}
		
		return result;
	}
	
}