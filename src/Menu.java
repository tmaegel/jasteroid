import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Menu {

	public Main main;
	public ArrayList<MenuText> list;

	private FontMetrics metrics;

	private int id;

	public Menu(Main main) {
		this.main = main;
		Font font = new Font("Tahoma", Font.PLAIN , 12);
        	this.metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);

		init();
	}

	protected void paint(Graphics2D g2d) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).isSelected()) {
				g2d.setColor(Color.yellow);
			} else {
				g2d.setColor(Color.white);
			}
			g2d.drawString(list.get(i).getText(), main.xSize/2-metrics.stringWidth(list.get(i).getText())/2, i*25+main.ySize/2-(list.size()*25)/2);
		}
	}

	public void init() {
		this.list = new ArrayList<MenuText>();
		list.add(new MenuText("Start"));
		list.add(new MenuText("Credits"));
		list.add(new MenuText("Exit"));
		list.get(0).setSelection(true);
	}

	public void refresh() {
		if(list.get(id).getText().equals("Start")) {
			main.start = true;
			main.startGame();
		}
		if(list.get(id).getText().equals("Credits")) {
			this.list = new ArrayList<MenuText>();
			list.add(new MenuText("JAsteroids"));
			list.add(new MenuText("Copyright 2009 by Toni Mägel"));
			list.add(new MenuText("www.jtm-games.ch.vu"));
		}
		if(list.get(id).getText().equals("Exit")) {
			System.exit(0);
		}
	}	

	public void setId(int i) {
		list.get(id).setSelection(false);
		id = id + i;
		if(id < 0) {
			id = list.size()-1;
		}
		if(id > list.size()-1) {
			id = 0;
		}
		list.get(id).setSelection(true);
	}
}
