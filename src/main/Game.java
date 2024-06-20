package main;


import Entities.Player;
import Levels.LevelManager;

import java.awt.*;
import java.util.Arrays;

public class Game implements Runnable {

	private final GameWindow gameWindow;
	private final GamePanel gamePanel;
	private final Player player;
	private LevelManager levelManager;

	public static final int TILES_DEFAULT_SIZE = 32;
	public static final float SCALE = 1f;
	public static final int TILES_IN_WIDTH = 26;
	public static final int TILES_IN_HEIGHT = 14;
	public static final int PLAYER_WIDTH = 64;
	public static final int PLAYER_HEIGHT = 40;

	public static final int TILE_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public static final int GAME_WIDTH = TILE_SIZE * TILES_IN_WIDTH;
	public static final int GAME_HEIGHT = TILE_SIZE * TILES_IN_HEIGHT;

	public Game() {
		levelManager = new LevelManager(this);
		player = new Player(200,200, (int) (PLAYER_WIDTH*SCALE), (int) (PLAYER_HEIGHT*SCALE));
		player.loadLevelData(levelManager.getLevelOne().getLevelData());

		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
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
		levelManager.drawLevel(g);
		player.render(g);
//		i++;
//		if(i>=5) {
//			System.out.println(Arrays.deepToString(levelManager.getLevelOne().getLevelData()));
//			System.exit(0);
//		}
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
