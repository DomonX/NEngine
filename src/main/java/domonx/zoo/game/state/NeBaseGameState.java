package domonx.zoo.game.state;

import domonx.zoo.core.interfaces.INeTickListener;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.window.NeGraphicsModule;

public abstract class NeBaseGameState implements INeTickListener {
	protected NeAbstractGameStateController controller;
	protected NeAbstractActionListener listener;
	protected NeGraphicsModule graphics;

	public NeBaseGameState(NeGraphicsModule graphics, NeAbstractGameStateController controller,
			NeAbstractActionListener listener) {
		this.setGraphics(graphics);
		this.setController(controller);
		this.setListener(listener);
		prepare();
	}

	public NeGraphicsModule getGraphics() {
		return graphics;
	}

	protected NeAbstractActionListener getListener() {
		return listener;
	}

	protected void setListener(NeAbstractActionListener listener) {
		this.listener = listener;
	}

	protected NeAbstractGameStateController getController() {
		return controller;
	}

	protected void setController(NeAbstractGameStateController controller) {
		this.controller = controller;
	}

	protected void setGraphics(NeGraphicsModule graphics) {
		this.graphics = graphics;
	}

	protected abstract void prepare();
}
