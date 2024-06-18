package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private BufferedImage image;
	private float xDelta = 100, yDelta = 100;

	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
		setPanelSize();
		importImage(); // Ensure the image is loaded
	}

	public void setPanelSize() {
		Dimension size = new Dimension(1280, 800);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
	}

	public void importImage() {
		InputStream iS = getClass().getResourceAsStream("/player_sprites.png");
		try {
			image = ImageIO.read(iS);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally {
			try {
				iS.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	public void changeXDelta(int value) {
		this.xDelta += value;
	}

	public void changeYDelta(int value) {
		this.yDelta += value;
	}

	public void setRectPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image.getSubimage(0, 0, 64, 40), 0, 0, 512, 320, null);
		} else {
			System.err.println("Image is not loaded.");
		}
	}

	public void updateGame() {
	}
}
