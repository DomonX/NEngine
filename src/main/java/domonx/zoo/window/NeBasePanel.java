package domonx.zoo.window;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import domonx.zoo.core.interfaces.INeRenderable;
import domonx.zoo.core.interfaces.INeTickListener;

public class NeBasePanel extends JPanel implements INeTickListener, INeRenderable {

	private static final long serialVersionUID = -2013484543573015813L;

	protected NeGameDataStorage game;
	
	protected JFrame window;

	private NeGraphicsModule graphics;

	public NeBasePanel(NeGameDataStorage game, JFrame window) {
		super();
		this.game = game;
		this.window = window;
		createGraphics();
	}

	@Override
	public void tick(int hertz) {
		graphics.tick(hertz);
	}

	@Override
	public void render(Graphics g) {
		graphics.render(g);
	}

	@Override
	public void renderDev(Graphics g) {

	}
	
	public NeGraphicsModule getGraphicsModule() {
		return graphics;
	}
	
	protected void createGraphics() {
		this.graphics = new NeGraphicsModule(game, window);
	}

	protected void paintComponent(Graphics g) {
		render(g);
	}

}
