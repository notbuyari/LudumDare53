package game;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

	public List<Key> keys = new ArrayList<Key>();
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key action = new Key();
	public Key jump = new Key();
	public Key reload = new Key();
	public Key sprint = new Key();
	
	public int x;
	public int y;
	private int mx;
	private int my;
	
	public boolean leftClicked;
	public boolean leftPressed;
	private boolean m0p;
	
	public boolean middleClicked;
	public boolean middlePressed;
	private boolean m1p;
	
	public boolean rightClicked;
	public boolean rightPressed;
	private boolean m2p;
	
	public InputHandler(Canvas canvas) {
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}
	
	public void tick() {
		leftClicked = !leftPressed && m0p;
		middleClicked = !middlePressed && m1p;
		rightClicked = !rightPressed && m2p;
		x = mx;
		y = my;
		leftPressed = m0p;
		middlePressed = m1p;
		rightPressed = m2p;
		
		for(int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}
	
	public void releaseAll() {
		for(int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		mx = e.getX() / GameComponent.SCALE;
		my = e.getY() / GameComponent.SCALE;
	}

	public void mouseMoved(MouseEvent e) {
		mx = e.getX() / GameComponent.SCALE;
		my = e.getY() / GameComponent.SCALE;
	}

	public void mouseClicked(MouseEvent e) {
		mx = e.getX() / GameComponent.SCALE;
		my = e.getY() / GameComponent.SCALE;	
	}

	public void mousePressed(MouseEvent e) {
		mx = e.getX() / GameComponent.SCALE;
		my = e.getY() / GameComponent.SCALE;
		
		m0p = e.getButton() == 1;
		m1p = e.getButton() == 2;
		m2p = e.getButton() == 3;
	}

	public void mouseReleased(MouseEvent e) {
		mx = e.getX() / GameComponent.SCALE;
		my = e.getY() / GameComponent.SCALE;

		m0p = m1p = m2p = false;
	}

	public void mouseEntered(MouseEvent e) {
		mx = e.getX() / GameComponent.SCALE;
		my = e.getY() / GameComponent.SCALE;
	}

	public void mouseExited(MouseEvent e) {
		mx = e.getX() / GameComponent.SCALE;
		my = e.getY() / GameComponent.SCALE;		
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) { toggle(e, true); }
	public void keyReleased(KeyEvent e) { toggle(e, false); }
	
	private void toggle(KeyEvent e, boolean pressed) {
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) up.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) down.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD4 || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) left.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) right.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_E) action.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_SPACE) jump.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) sprint.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_R) reload.toggle(pressed);
	}
	
	public class Key {
		private int absorbs;
		private int presses;
		
		public boolean down;
		public boolean clicked;
		
		public Key() { InputHandler.this.keys.add(this); }
		
		public void tick() {
			if(absorbs < presses) {
				absorbs++;
				clicked = true;
			} else clicked = false;
		}
		
		public void toggle(boolean pressed) {
			down = pressed;
			if(pressed) presses++;
		}
		
	}

}