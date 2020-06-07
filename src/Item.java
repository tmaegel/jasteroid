import java.awt.*;

public class Item extends Shape {

	private int v, rv, beta, type, time;

	public Item(Main main, int x, int y, int w, int h, int v, int rv, int beta, int type) {
		super(main, x, y, w, h);
		
		this.v = v;
		this.rv = rv;
		this.beta = beta;
		this.type = type;

		Polygon shape = new Polygon();
		shape.addPoint(-w/2, -h/2);
		shape.addPoint(w/2, 0);
		shape.addPoint(-w/2, h/2);
		setShape(shape);

		if(type == 1)
			setColor(Color.red);
		if(type == 2)
			setColor(Color.orange);
		if(type == 3)
			setColor(Color.yellow);
	}

	public void move() {
		alpha = alpha + rv;

		if(time <= 1200) {
			time++;
		} else {
			setDestroy(true);
		}

		x = x + Math.cos(Math.toRadians(beta)) * v;
		y = y + -Math.sin(Math.toRadians(beta)) * v;

		physicalBehavior();
	}

	public int getType() {
		return type;
	}
}
