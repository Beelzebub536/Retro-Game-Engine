package main;

import java.awt.*;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	Game game;

	public GamePanel(Game game) {
		this.game=game;
		mouseInputs = new MouseInputs(this);
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
		setPanelSize();
	}

	public Game getGame() {
		return game;
	}

	public void setPanelSize() {
		Dimension size = new Dimension(1280, 800);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}


}
