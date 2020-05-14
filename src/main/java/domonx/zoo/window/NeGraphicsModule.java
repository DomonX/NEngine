package domonx.zoo.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.core.configuration.NeConstantsRegistry;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.INeTickListener;
import domonx.zoo.core.storage.INeImageStorage;
import domonx.zoo.core.storage.NeImageStorage;
import domonx.zoo.core.util.GUIDGenerator;

public class NeGraphicsModule implements INeTickListener {

	public NeContainer screen = null;

	protected INeImageStorage storage = null;

	private NeGameDataStorage game = null;
	
	private JFrame window = null;

	public NeGraphicsModule(NeGameDataStorage game, JFrame window) {
		this.game = game;
		this.window = window;
		createStorage();
		screen = new NeContainer(GUIDGenerator.get());
		screen.connectStorage(storage);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screen.setHeight(screenSize.getHeight());
		screen.setWidth(screenSize.getWidth());
	}

	public void render(Graphics g) {
		clearScreen(g);
		screen.render(g);
		if (NeConfiguration.isShowFps()) {
			paintFps(g);
		}
	}
	
	public INeImageStorage getStorage() {
		return storage;
	}

	@Override
	public void tick(int hertz) {
		screen.tick(hertz);
	}
	
	public JFrame getWindow() {
		return window;
	}
	
	protected void createStorage() {
		this.storage = new NeImageStorage();
	}
	
	
	protected void clearScreen(Graphics g) {
		g.setColor(Color.BLACK);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		g.fillRect(0, 0, d.width, d.height);
	}

	protected void paintFps(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(Color.BLACK);
		g.setFont(NeConstantsRegistry.baseFont);
		g.drawString(Integer.toString(game.getLastFps()), 0, NeConstantsRegistry.baseFont.getSize());
		g.setColor(oldColor);
	}

}
