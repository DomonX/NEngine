package domonx.zoo.window;

import java.awt.Graphics;

import javax.swing.JPanel;

import domonx.zoo.core.interfaces.INeRenderable;
import domonx.zoo.core.interfaces.INeTickListener;

public class NeBasePanel extends JPanel implements INeTickListener, INeRenderable {

	private static final long serialVersionUID = -2013484543573015813L;

	protected NeSyncFrame owner;

	private NeGraphicsModule graphics;

	public NeBasePanel(NeSyncFrame owner, NeGraphicsModule graphics) {
		this.owner = owner;
		this.graphics = graphics;
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

	protected void paintComponent(Graphics g) {
		render(g);
	}

}
