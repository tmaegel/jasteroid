import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Space {

	private Main main;
	private Random r;

	private Point[] background;

	public Space(Main main) {
		this.main = main;
		this.r = new Random();
		this.init(300);
	}

	public void init(int size) {
		background = new Point[size];
		for(int i = 0; i < size; i++) {
			background[i] = new Point(r.nextInt(main.xSize), r.nextInt(main.ySize));
		}
	}

	protected void paint(Graphics2D g2d) {
		for(int i = 0; i < background.length; i++) {
			int x = (int)background[i].getX();
			int y = (int)background[i].getY();
			if(i >= 0 && i < 100)
				g2d.setColor(Color.white);
			if(i >= 100 && i < 200)
				g2d.setColor(new Color(150, 150, 150));
			if(i >= 200 && i <= 300)
				g2d.setColor(new Color(100, 100, 100));
			g2d.draw(new Line2D.Double(x, y, x, y));
		}
	}
}
