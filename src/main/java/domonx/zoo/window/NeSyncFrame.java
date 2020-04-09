package domonx.zoo.window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.core.configuration.NeConstantsRegistry;

public class NeSyncFrame extends JFrame implements KeyListener {

	protected int lastFps = 0;
	private long currentNanoTimeFps = 0;
	private long currentNanoTimeHertz = 0;
	private long currentNanoTimeCounter = 0;
	private int currentFps = 0;
	private static final long serialVersionUID = 8590211693728083963L;

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public int getLastFps() {
		return lastFps;
	}

	public void enterMainLoop() {
		long lastNanoTime = System.nanoTime();
		while (true) {
			mainLoop();
			long currentNanoTime = System.nanoTime();
			long timeElapsed = currentNanoTime - lastNanoTime;
			lastNanoTime = currentNanoTime;
			currentNanoTimeFps += timeElapsed;
			currentNanoTimeHertz += timeElapsed;
			currentNanoTimeCounter += timeElapsed;
		}
	}

	protected NeSyncFrame() {
		NeConfiguration.loadConfigTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addKeyListener(this);
	}

	protected void updateScreen() {
	}
	protected void updateGame(int hertzPassed) {
	}

	private void mainLoop() {
		if (currentNanoTimeFps > NeConfiguration.getFpsTime() || !NeConfiguration.isFpsCapped()) {
			updateScreen();
			currentNanoTimeFps -= NeConfiguration.getFpsTime();
			currentFps++;
		}
		if (currentNanoTimeHertz > NeConfiguration.getHertzTime()) {
			int fullHertzPassed = (int) (currentNanoTimeHertz / NeConfiguration.getHertzTime());
			updateGame(fullHertzPassed);
			currentNanoTimeHertz -= fullHertzPassed * NeConfiguration.getHertzTime();
		}
		if (currentNanoTimeCounter > NeConstantsRegistry.SECOND_IN_NANO) {
			lastFps = currentFps;
			currentNanoTimeCounter -= NeConstantsRegistry.SECOND_IN_NANO;
			currentFps = 0;
		}
	}

}
