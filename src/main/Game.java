package main;


public class Game implements Runnable {

	private final GameWindow gameWindow;
	private final GamePanel gamePanel;

	public Game() {

		gamePanel = new GamePanel();
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();

	}

	private void startGameLoop() {
		Thread gameThread = new Thread(this);
		gameThread.start();
	}
	private void update() {
		gamePanel.updateGame();
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

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + update);
				frames = 0;
				update = 0;
			}
		}

	}
}
