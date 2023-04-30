package game.tools;

public class MathUtils {
	
	public static double normalize(double angle) {
		while(angle < -Math.PI) angle += Math.PI * 2;
		while(angle > Math.PI) angle -= Math.PI * 2;
		return angle;
	}

	public static int lerpRGB(int c0, int c1, double p) {
		int r0 = (c0 >> 16) & 0xFF;
		int g0 = (c0 >> 8) & 0xFF;
		int b0 = (c0 >> 0) & 0xFF;
		int r1 = (c1 >> 16) & 0xFF;
		int g1 = (c1 >> 8) & 0xFF;
		int b1 = (c1 >> 0) & 0xFF;
		int r = (int)(r0 + (r1 - r0) * p);
		int g = (int)(g0 + (g1 - g0) * p);
		int b = (int)(b0 + (b1 - b0) * p);
		return r << 16 | g << 8 | b;
	}
	
}