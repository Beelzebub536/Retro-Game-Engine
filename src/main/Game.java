package main;


import Entities.Player;
import Levels.LevelManager;

import java.awt.*;

public class Game implements Runnable {

	private final GameWindow gameWindow;
	private final GamePanel gamePanel;
	private final Player player;
	private LevelManager levelManager;

	public static final int TILES_DEFAULT_SIZE = 32;
	public static final float SCALE = 1.5f;
	public static final int TILES_IN_WIDTH = 26;
	public static final int TILES_IN_HEIGHT = 14;

	public static final int TILE_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public static final int GAME_WIDTH = TILE_SIZE * TILES_IN_WIDTH;
	public static final int GAME_HEIGHT = TILE_SIZE * TILES_IN_HEIGHT;

	public Game() {
		player = new Player(100,100);
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		levelManager = new LevelManager(this);
		gamePanel.requestFocus();
		startGameLoop();
	}

	public Player getPlayer() {
		return player;
	}

	private void startGameLoop() {
		Thread gameThread = new Thread(this);
		gameThread.start();
	}
	private void update() {
		player.updateGame();
		levelManager.update();
	}
	public void render(Graphics g){
		player.render(g);
		levelManager.drawLevel(g);
	}

	@Override
	public void run() {
		int FPS_SET = 120;
		int UPS_SET = 200;

		double timePerFrame = 1_000_000_000.0 / FPS_SET;
		double timePerUpdate = 1_000_000_000.0 / UPS_SET;

		long lastFrame = System.nanoTime();

		int frames = 0;
		int update = 0;

		double deltaF=0;
		double deltaU=0;
		long previousTime=System.nanoTime();
		long lastCheck = System.nanoTime();

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				update++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (currentTime - lastCheck >= 1_000_000_000) {
				lastCheck = currentTime;
				System.out.println("FPS: " + frames + " || UPS: " + update);
				frames = 0;
				update = 0;
			}
		}

	}
}
