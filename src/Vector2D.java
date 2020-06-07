import java.awt.*;
import java.awt.geom.*;

public class Vector2D {

	final static Polygon rotate(Polygon p, int alpha, int x, int y) {
		Polygon r = new Polygon();
		for (int i = 0; i < p.npoints; i++) {
			double cos = Math.cos(Math.toRadians(alpha));
			double sin = Math.sin(Math.toRadians(alpha));
			r.addPoint((int)Math.round(p.xpoints[i]*cos+p.ypoints[i]*sin+x), (int)Math.round(p.ypoints[i]*cos-p.xpoints[i]*sin+y));
		}
		return r;
	}
}
