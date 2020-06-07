import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;

public class Main extends JPanel implements Runnable {
	
	private Thread thread;
	private Random r = new Random();

	public JFrame frame;
	public Menu menu;
	public Player player;
	public Space space;
	public Ship ship;

	public ArrayList<Asteroid> asteroids;
	public ArrayList<Item> items;

	public boolean start;

	private int loop = 50;
	private int count = 1;
	public int xSize = 800;
	public int ySize = 600;

	public Main() {
		super();
		this.setBackground(Color.black);
		frame = new JFrame("JAsteroids");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation((getToolkit().getScreenSize().width-xSize)/2, (getToolkit().getScreenSize().height-ySize)/2);
		frame.setResizable(false);
		frame.setSize(xSize, ySize);
		frame.getContentPane().add(this);

		player = new Player(this);
		space = new Space(this);
		ship = new Ship(this, xSize/2-24/2, ySize/2-16/2, 24, 16);

		menu = new Menu(this);

		thread = new Thread(this);
		thread.start();
		
		startGame();

		frame.setVisible(true);
	}

	public static void main(String[] arguments) {
		Main main = new Main();
	}

	public void startGame() {
		count = 1;
		ship = new Ship(this, xSize/2-24/2, ySize/2-16/2, 24, 16);
		asteroids = new ArrayList<Asteroid>();
		items = new ArrayList<Item>();
		init(1);
	}

	public void init(int count) {
		for(int i = 0; i < count; i++) {
			asteroids.add(new Asteroid(this, r.nextInt(xSize), r.nextInt(ySize), 60, 60, r.nextInt(5)+1, r.nextInt(10)-5, r.nextInt(360)));
		}
	}

	public void addAsteroids(int count, int x, int y, int w, int h) {
		for(int i = 0; i < count; i++) {
			asteroids.add(new Asteroid(this, x, y, w, h, r.nextInt(5)+1, r.nextInt(10)-5, r.nextInt(360)));

			int random = r.nextInt(15)+1;
			if(random >= 1 && random <= 3)
				items.add(new Item(this, x, y, 7, 7, r.nextInt(3)+1, r.nextInt(10)-5, r.nextInt(360), random));
		}
	}

	public  void run() {
		while (true) {
			try {
				thread.sleep(loop);
				if(start) {
					if(asteroids.size() == 0) {
						count++;
						init(count);
					}
					for(int i = 0; i < asteroids.size(); i++) {
						if(asteroids.get(i) != null) {
							asteroids.get(i).move();
							if(asteroids.get(i).getVisible() == false)
								asteroids.remove(i);
						}
					}
					for(int i = 0; i < items.size(); i++) {
						if(items.get(i) != null) {
							items.get(i).move();
							if(items.get(i).getVisible() == false)
								items.remove(i);
						}
					}
					if(ship != null) {
						if(ship.getVisible() == false) {
							if(ship.getShips() > 1) {
								ship.setShips(ship.getShips()-1);
								ship.init();
							} else {
								start = false;
							}
						} else {
							ship.move();
						}
					}
				}
			} catch(InterruptedException e) {
				System.out.println("ERROR: " +  e.toString());
			}
			repaint();
		}
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g2d);
		
		if(space != null)
			space.paint(g2d);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if(start) {
			for(int i = 0; i < items.size(); i++) {
				if(items.get(i) != null)
					items.get(i).paint(g2d);
			}

			for(int i = 0; i < asteroids.size(); i++) {
				if(asteroids.get(i) != null)
					asteroids.get(i).paint(g2d);
			}

			if(ship != null) {
				if(ship.protector != null)
					ship.protector.paint(g2d);
				for(int i = 0; i < ship.shots.size(); i++) {
					if(ship.shots.get(i) != null)
						ship.shots.get(i).paint(g2d);
				}
				for(int i = 0; i < ship.flames.size(); i++) {
					if(ship.flames.get(i) != null)
						ship.flames.get(i).paint(g2d);
				}
				ship.paint(g2d);
				player.paint(g2d);
			}
		} else {
			menu.paint(g2d);
		}
	}
}
