import java.awt.*;
import java.util.*;

public class Asteroid extends Shape {

	private int v, rv, beta;

	public Asteroid(Main main, int x, int y, int w, int h, int v, int rv, int beta) {
		super(main, x, y, w, h);

		this.v = v;
		this.rv = rv;
		this.beta = beta;

		Random r = new Random();
		Polygon shape = new Polygon();

		for(int a = -1; a <= 1; a++) {
			if(a != 1)
				shape.addPoint(a*(w/2), -h/2+r.nextInt(h/3));
			else
				shape.addPoint(a*(w/2), 0);
		}

		for(int b = 1; b >= -1; b--) {
			if(b != -1)
				shape.addPoint(b*(w/2), +h/2-r.nextInt(h/3));
			else
				shape.addPoint(b*(w/2), 0);
		}

		setShape(shape);
		setColor(Color.white);
		r = null;
	}

	public void move() {
		alpha = alpha + rv;

		x = x + Math.cos(Math.toRadians(beta)) * v;
		y = y + -Math.sin(Math.toRadians(beta)) * v;

		physicalBehavior();
	}
}
