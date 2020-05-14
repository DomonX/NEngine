package domonx.zoo.window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.core.configuration.NeConstantsRegistry;

public class NeGame implements KeyListener {

	protected JFrame window;
	protected NeGameDataStorage data;
	private long currentNanoTimeFps = 0;
	private long currentNanoTimeHertz = 0;
	private long currentNanoTimeCounter = 0;
	private int currentFps = 0;
	private boolean isBreak = false;
	
	public NeGame(JFrame window) {
		this.window = window;
		data = new NeGameDataStorage();
		NeConfiguration.loadConfigTo(window);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// NeGame doesn't react to keyTyped from KeyListener listener
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// NeGame doesn't react to keyReleased from KeyListener interface
	}

	public void enterMainLoop() {
		long lastNanoTime = System.nanoTime();
		while (!isBreak()) {
			mainLoop();
			long currentNanoTime = System.nanoTime();
			long timeElapsed = currentNanoTime - lastNanoTime;
			lastNanoTime = currentNanoTime;
			currentNanoTimeFps += timeElapsed;
			currentNanoTimeHertz += timeElapsed;
			currentNanoTimeCounter += timeElapsed;
		}
	}

	public boolean isBreak() {
		return isBreak;
	}
	
	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}
	
	public void show() {
		window.setVisible(true);
	}
	
	protected void updateScreen() {
		throw new UnsupportedOperationException();
	}
	
	protected void updateGame(int hertzPassed) {
		throw new UnsupportedOperationException();
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
			data.setLastFps(currentFps);
			currentNanoTimeCounter -= NeConstantsRegistry.SECOND_IN_NANO;
			currentFps = 0;
		}
	}


}
