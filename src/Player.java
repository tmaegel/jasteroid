import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Player implements KeyListener {

	private Main main;

	public Player(Main main) {
		this.main = main;
		main.frame.addKeyListener(this);
	}

	protected void paint(Graphics2D g2d) {
		if(main.ship.getShips() >= 1) {
			g2d.setColor(Color.white);
			g2d.drawString(""+main.ship.getScore(), 20, 25);
			for(int i = 0; i < main.ship.getShips(); i++) {
				Polygon p = new Polygon();
				p.addPoint(i*20+20, main.ySize-60);
				p.addPoint(i*20+15, main.ySize-50);
				p.addPoint(i*20+25, main.ySize-50);
				g2d.draw(p);
			}
			g2d.setColor(Color.orange);
			g2d.draw(new Rectangle2D.Double(80, main.ySize-60, 100, 10));
			g2d.fill(new Rectangle2D.Double(80, main.ySize-60, main.ship.getFuel(), 10));
			g2d.setColor(Color.yellow);
			g2d.draw(new Rectangle2D.Double(200, main.ySize-60, 100, 10));
			g2d.fill(new Rectangle2D.Double(200, main.ySize-60, main.ship.getEnergy(), 10));
		}
	}

	public void keyPressed (KeyEvent k) {
		int key = k.getKeyCode();
		switch(key) {
			case KeyEvent.VK_UP:
				if(!main.start) {
					main.menu.setId(-1);
				} else {
					if(main.ship.isDestroy() == false)
						main.ship.forward = true;
				}
				break;
			case KeyEvent.VK_DOWN:
				if(!main.start) {
					main.menu.setId(1);
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(main.ship.isDestroy() == false)
					main.ship.right = true;
				break;
			case KeyEvent.VK_LEFT:
				if(main.ship.isDestroy() == false)
					main.ship.left = true;
				break;
			case KeyEvent.VK_SPACE:
				if(main.ship.isDestroy() == false) {
					if(main.ship.getEnergy()-10 >= 0)
						main.ship.generateShot();
				}
				break;
			case KeyEvent.VK_ALT:
				if(main.ship.isDestroy() == false) {
					main.ship.shild = true;
				}
				break;
			case KeyEvent.VK_ENTER:
				if(!main.start) {
					main.menu.refresh();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				main.start = false;
				main.menu.init();
				break;
		}
	}

	public void keyReleased (KeyEvent k) {
		int key = k.getKeyCode();
		switch(key) {
			case KeyEvent.VK_UP:
				main.ship.forward = false;
				break;
			case KeyEvent.VK_RIGHT:
				main.ship.right = false;
				break;
			case KeyEvent.VK_LEFT:
				main.ship.left = false;
				break;
			case KeyEvent.VK_ALT:
				main.ship.shild = false;
				break;
		}
	}

	public void keyTyped(KeyEvent e) {  }
}
