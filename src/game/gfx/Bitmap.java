package game.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Bitmap {

	public final int w;
	public final int h;
	public int[] pixels;
	
	public int xOffs;
	public int yOffs;
	public boolean flipped;
	
	public Bitmap(BufferedImage image) {
		this.w = image.getWidth();
		this.h = image.getHeight();
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}

	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		pixels = new int[w * h];
	}
	
	public Bitmap(int w, int h, int[] pixels) {
		this.w = w;
		this.h = h;
		this.pixels = pixels;
	}
	
	public void clear(int col) {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = col;
		}
	}
	
	public void draw(Bitmap b, int xp, int yp) {
		xp += xOffs;
		yp += yOffs;
		
		int x0 = xp;
		int y0 = yp;
		int x1 = xp + b.w;
		int y1 = yp + b.h;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 > w) x1 = w;
		if(y1 > h) y1 = h;
		
		if(flipped) {
			for(int y = y0; y < y1; y++) {
				for(int x = x1 - 1; x >= x0; x--) {
					int src = b.pixels[b.w - 1 - (x - xp) + (y - yp) * b.w];
					if(src == 0) continue;
					pixels[x + y * w] = src;
				}
			}
		} else {
			for(int y = y0; y < y1; y++) {
				for(int x = x0; x < x1; x++) {
					int src = b.pixels[x - xp + (y - yp) * b.w];
					if(src == 0) continue;
					pixels[x + y * w] = src;
				}
			}
		}
	}
	
	public void scaleDraw(Bitmap b, int xp, int yp, int scale) {
		xp += xOffs;
		yp += yOffs;
		
		int x0 = xp;
		int y0 = yp;
		int x1 = xp + b.w * scale;
		int y1 = yp + b.h * scale;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 > w) x1 = w;
		if(y1 > h) y1 = h;
		
		if(flipped) {
			for(int y = y0; y < y1; y++) {
				for(int x = x1 - 1; x >= x0; x--) {
					int src = b.pixels[(b.w - 1 - (x - xp) / scale) + ((y - yp) / scale) * b.w];
					if(src == 0) continue;
					pixels[x + y * w] = src;
				}
			}
		} else {
			for(int y = y0; y < y1; y++) {
				for(int x = x0; x < x1; x++) {
					int src = b.pixels[((x - xp) / scale) + ((y - yp) / scale) * b.w];
					if(src == 0) continue;
					pixels[x + y * w] = src;
				}
			}
		}
	}
	
	public void fill(int x0, int y0, int x1, int y1, int col) {
		x0 += xOffs;
		y0 += yOffs;
		x1 += xOffs;
		y1 += yOffs;

		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 >= w) x1 = w - 1;
		if(y1 >= h) y1 = h - 1;
		
		for(int y = y0; y <= y1; y++) {
			for(int x = x0; x <= x1; x++) {
				pixels[x + y * w] = col;
			}			
		}
	}
	
	public void setPixel(int xp, int yp, int col) {
		xp += xOffs;
		yp += yOffs;
		
		if(xp >= 0 && yp >= 0 && xp < w && yp < h) {
			pixels[xp + yp * w] = col;
		}
	}
	
	public void shade(Bitmap b) {
		for(int i = 0; i < pixels.length; i++) {
			if(b.pixels[i] <= 0) continue;
			int rr = (pixels[i] & 0xFF0000) * 200 >> 8 & 0xFF0000;
			int gg = (pixels[i] & 0xFF00) * 200 >> 8 & 0xFF00;
			int bb = (pixels[i] & 0xFF) * 200 >> 8 & 0xFF;
			pixels[i] = 0xFF000000 | rr | gg | bb;
		}
	}
	
	public Bitmap rotate(double angle) {
		if(angle == 0) return this;
		
		double cos = -Math.cos(angle);
		double sin = -Math.sin(angle);
		
		int cx = w / 2;
		int cy = h / 2;
		
		int newW = (int)(Math.abs(w * cos) + Math.abs(h * sin) + cx);
		int newH = (int)(Math.abs(w * sin) + Math.abs(h * cos) + cy);
		
		int[] result = new int[newW * newH];
		
		for(int y = 0; y < newH; y++) {
			for(int x = 0; x < newW; x++) {
				double xo = (x - cx) * cos + (y - cy) * sin;
				double yo = -(x - cx) * sin + (y - cy) * cos;
			
				int xx = (int)Math.round(xo) + cx;
				int yy = (int)Math.round(yo) + cy;
				
				if(xx >= 0 && yy >= 0 && xx < w && yy < h) {
					int src = pixels[xx + yy * w];
					result[x + y * newW] = src;
				}
			}
		}
		
		return new Bitmap(newW, newH, result);
		
	}
	
}