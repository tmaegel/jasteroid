import java.awt.*;
import java.awt.geom.*;

public class Shape {

	private Main main;
	private Polygon shape, sprite;

	private boolean destroy;
	private boolean visible = true;

	private int i, r, g, b;
	public int w, h, alpha;

	public double x, y;

	public Shape(Main main, double x, double y, int w, int h) {
		this.main = main;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	protected void paint(Graphics2D g2d) {
		if(destroy) {
			r = r - 10;
			g = g - 10;
			b = b - 10;

			if(r < 0)
				r = 0;
			if(g < 0)
				g = 0;
			if(b < 0)
				b = 0;

			if(r == 0 && g == 0 && b == 0)
				visible = false;
		}
		g2d.setColor(new Color(r, g, b));
		g2d.draw(sprite);
	}

	public void physicalBehavior() {
		if(x+w < 0)
			x = main.xSize;
		if(x > main.xSize)
			x = 0-w;
		if(y+h < 0)
			y = main.ySize;
		if(y > main.ySize)
			y = 0-h;

		sprite = Vector2D.rotate(shape, alpha, (int)x, (int)y);
	}

	public void setColor(Color color) {
		this.r = color.getRed();
		this.g = color.getGreen();
		this.b = color.getBlue();
	}

	public void setShape(Polygon shape) {
		this.shape = shape;
		this.sprite = shape;
	}

	public boolean shapeCollision(Polygon p1, Polygon p2) {
		boolean value = false;
		for (int i = 0; i < p2.npoints; i++) {
			if(p1.contains(p2.xpoints[i], p2.ypoints[i])) {
				value = true;
				break;
			}
		}
		return value;
	}

	public int getDistance(double px, double py) {
		return (int)Math.sqrt((x-px)*(x-px)+(y-py)*(y-py));
	}

	public Polygon getShape() {
		return sprite;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean getVisible() {
		return visible;
	}
}
