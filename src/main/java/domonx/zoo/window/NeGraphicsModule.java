package domonx.zoo.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import domonx.zoo.core.configuration.NeConfiguration;
import domonx.zoo.core.configuration.NeConstantsRegistry;
import domonx.zoo.core.controller.NeDraggableController;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.INeTickListener;
import domonx.zoo.core.storage.NeImageStorage;
import domonx.zoo.core.util.GUIDGenerator;

public class NeGraphicsModule implements INeTickListener {

	public NeContainer screen = null;

	protected NeImageStorage storage = null;

	private NeSyncFrame owner = null;

	public NeGraphicsModule(NeImageStorage storage, NeSyncFrame owner) {
		this.storage = storage;
		this.owner = owner;
		screen = new NeContainer(storage, GUIDGenerator.get());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screen = new NeContainer(storage, GUIDGenerator.get());
		screen.setHeight(screenSize.getHeight());
		screen.setWidth(screenSize.getWidth());
		
		NeContainer c = new NeContainer(storage, GUIDGenerator.get());
		c.setHeight(900);
		c.setWidth(1920/2);
		screen.add(c);
		c.move(100, 0);		
		NeDraggableController con = new NeDraggableController(c, owner);
		
		NeImage i = new NeImage(storage, GUIDGenerator.get());
		i.setWidth(100);
		i.setHeight(100);
		i.load("1.png");
		c.add(i);
		System.out.println(c.getX());
		System.out.println(i.getGUIDPath());
	}

	public void render(Graphics g) {
		clearScreen(g);
		if (NeConfiguration.showFps) {
			paintFps(g);
		}
		screen.render(g);
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
		g.drawString(Integer.toString(owner.getLastFps()), 0, NeConstantsRegistry.baseFont.getSize());
		g.setColor(oldColor);
	}

	@Override
	public void tick(int hertz) {
		screen.tick(hertz);
	}
}
