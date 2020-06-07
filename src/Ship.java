import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Ship extends Shape {

	public Main main;
	public ArrayList<Shot> shots;
	public ArrayList<Flame> flames;
	public Shild protector;

	final double a = 1.25F;
	final double rv = 5.00F;
	final double t = 0.50F;

	private double fuel, energy, regeneration;

	public boolean forward;
	public boolean right;
	public boolean left;
	public boolean shild;
	
	private double vx, vy, ax, ay;
	private int ships, score, flame;

	public Ship(Main main, int x, int y, int w, int h) {
		super(main, x, y, w, h);
		this.main = main;
		this.ships = 3;		

		Polygon shape = new Polygon();
		shape.addPoint(-w/2, -h/2);
		shape.addPoint(w/2, 0);
		shape.addPoint(-w/2, h/2);
		setShape(shape);

		init();
	}

	public void init() {
		setVisible(true);
		setDestroy(false);
		setColor(Color.white);
		x = main.xSize/2-w/2;
		y = main.ySize/2-h/2;
		vx = 0;
		vy = 0;
		ax = 0;
		ay = 0;
		fuel = 100;
		energy = 100;
		regeneration = 0.1F;
		this.shots = new ArrayList<Shot>();
		this.flames = new ArrayList<Flame>();
		this.protector = new Shild(this, x, y, w);
	}

	public void move() {
		if(right)
			alpha = alpha - (int)rv;
		if(left)
			alpha = alpha + (int)rv;

		
		if(forward) {
			if(fuel > 0) {
				ax = a * Math.cos(Math.toRadians(alpha));
				ay = a * -Math.sin(Math.toRadians(alpha));
				vx = vx + ax * t;
				vy = vy + ay * t;
				fuel = fuel - a/5;
				generateFlame();
			}
		} else {
			flame = 0;
			if(vx <= 0.01 && vx >= -0.01) {
				vx = 0;
			} else {
				if(vx > 0)
					vx = vx - vx/100;
				if(vx < 0)
					vx = vx - vx/100;
			}
			if(vy <= 0.01 && vy >= -0.01) {
				vy = 0;
			} else {
				if(vy > 0)
					vy = vy - vy/100;
				if(vy < 0)
					vy = vy - vy/100;
			}
		}

		if(shild) {
			if(energy-5 >= 0) {
				energy = energy - 5;
				protector.setActivation(true);
			} else {
				protector.setActivation(false);
			}
		} else {
			protector.setActivation(false);
		}

		protector.move();

		x = x + vx * t;
		y = y + vy * t;

		if(energy+regeneration <= 100)
			energy = energy + regeneration;

		for(int i = 0; i < shots.size(); i++) {
			if(shots.get(i) != null) {
				shots.get(i).move();
				if(shots.get(i).getVisible() == false)
					shots.remove(i);
			}
		}

		for(int i = 0; i < flames.size(); i++) {
			if(flames.get(i) != null) {
				flames.get(i).move();
				if(flames.get(i).getVisible() == false)
					flames.remove(i);
			}
		}

		physicalBehavior();
		collision();
	}

	public void collision() {
		for(int i = 0; i < main.items.size(); i++) {
			if(main.items.get(i) != null && main.items.get(i).isDestroy() == false) {
				if(getDistance(main.items.get(i).x, main.items.get(i).y) < 100) {
					if(shapeCollision(getShape(), main.items.get(i).getShape())) {
						if(main.items.get(i).getType() == 1) {
							if(ships < 3)
								ships++;
						}
						if(main.items.get(i).getType() == 2)
							fuel = 100;
						if(main.items.get(i).getType() == 3)
							regeneration = regeneration + 0.1F;

						main.items.get(i).setDestroy(true);
					}
				}
			}		
		}

		if(protector.getVisible() == false) {
			for(int i = 0; i < main.asteroids.size(); i++) {
				if(main.asteroids.get(i) != null && main.asteroids.get(i).isDestroy() == false) {
					if(getDistance(main.asteroids.get(i).x, main.asteroids.get(i).y) < 100) {
						if(shapeCollision(main.asteroids.get(i).getShape(), getShape()))
							setDestroy(true);
					}
				}		
			}
		}
		for(int i = 0; i < shots.size(); i++) {
			if(shots.get(i) != null && shots.get(i).getVisible() == true) {
				if(getDistance(shots.get(i).getX(), shots.get(i).getY()) < 100) {
					if(getShape().contains(shots.get(i).getX(), shots.get(i).getY())) {
						shots.get(i).setVisible(false);
						if(protector.getVisible() == false)
							setDestroy(true);
					}
				}
			}		
		}
		
	}

	public void generateShot() {
		shots.add(new Shot(this, x+Math.cos(Math.toRadians(alpha))*w/2, y-Math.sin(Math.toRadians(alpha))*h/2, alpha));
		energy = energy - 10;
	}

	public void generateFlame() {
		if(flame == 0) {
			flames.add(new Flame(this, x-Math.cos(Math.toRadians(alpha))*w, y+Math.sin(Math.toRadians(alpha))*h, alpha));
		}
		flame++;
		if(flame == 3)
			flame = 0;
	}

	public void setScore(int score) {
		this.score = this.score + score;
	}

	public int getScore() {
		return score;
	}

	public double getEnergy() {
		return energy;
	}

	public double getFuel() {
		return fuel;
	}

	public int getShips() {
		return ships;
	}

	public void setShips(int ships) {
		this.ships = ships;
	}
}

class Shot {

	private Ship ship;

	private boolean visible = true;

	private int lifetime = 150;
	private int alpha;
	private double x, y;

	public Shot(Ship ship, double x, double y, int alpha) {
		this.ship = ship;
		this.x = x;
		this.y = y;
		this.alpha = alpha;
	}

	protected void paint(Graphics2D g2d) {
		if(visible) {
			g2d.setColor(Color.white);
			g2d.draw(new Ellipse2D.Double(x, y, 2, 2));
		}
	}

	public void move() {
		if(visible) {
			x = x + Math.cos(Math.toRadians(alpha)) * 10.00F;
			y = y + -Math.sin(Math.toRadians(alpha)) * 10.00F;

			if(x < 0)
				x = ship.main.xSize;
			if(x > ship.main.xSize)
				x = 0;
			if(y < 0)
				y = ship.main.ySize;
			if(y > ship.main.ySize)
				y = 0;

			lifetime--;

			if(lifetime == 0)
				visible = false;

			collision();
		}
	}

	public void collision() {
		for(int i = 0; i < ship.main.asteroids.size(); i++) {
			if(ship.main.asteroids.get(i) != null && ship.main.asteroids.get(i).isDestroy() == false) {
				if(ship.main.asteroids.get(i).getShape().contains(x, y)) {
					visible = false;
					ship.setScore(50);
					ship.main.asteroids.get(i).setDestroy(true);
					if(ship.main.asteroids.get(i).w-20 != 0 || ship.main.asteroids.get(i).h-20 != 0)
						ship.main.addAsteroids(4, (int)ship.main.asteroids.get(i).x, (int)ship.main.asteroids.get(i).y, ship.main.asteroids.get(i).w-20, ship.main.asteroids.get(i).h-20);
				}
			}
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean getVisible() {
		return visible;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}

class Shild {

	private Ship ship;

	private boolean visible = true;
	private boolean activated;

	private int radius;
	private int i = 255;
	private double x, y;

	public Shild(Ship ship, double x, double y, int radius) {
		this.ship = ship;
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	protected void paint(Graphics2D g2d) {
		if(activated == false) {
			i = i - 10;
			if(i < 0) {
				i = 0;
				visible = false;
			}
		}
		g2d.setColor(new Color(i, 0, 0));
		g2d.draw(new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2));
	}

	public void move() {
		if(activated) {
			visible = true;
			i = 255;
		}
		this.x = ship.x;
		this.y = ship.y;
	}

	public void setActivation(boolean activated) {
		this.activated = activated;
	}

	public boolean getActivation() {
		return activated;
	}

	public boolean getVisible() {
		return visible;
	}
}

class Flame {

	private Ship ship;

	private boolean visible = true;

	private double x, y, radius;
	private int alpha;
	private int r = 255;
	private int g = 255;

	public Flame(Ship ship, double x, double y, int alpha) {
		this.ship = ship;
		this.x = x;
		this.y = y;
		this.alpha = alpha;
	}

	protected void paint(Graphics2D g2d) {
		g2d.setColor(new Color(r, g, 0));
		g2d.draw(new Ellipse2D.Double(x-radius, y-radius, (int)radius*2, (int)radius*2));
	}

	public void move() {
		if(g > 10)
			g = g - 10;
		else {
			g = 0;
			r = r - 30;
			if(r < 30) {
				r = 0;
				visible = false;
			}			
		}

		if(radius < 15)
			radius = radius + 0.5F;

		x = x - Math.cos(Math.toRadians(alpha)) * 1.50F;
		y = y - -Math.sin(Math.toRadians(alpha)) * 1.50F;
	}

	public boolean getVisible() {
		return visible;
	}
}
