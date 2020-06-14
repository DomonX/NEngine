package domonx.zoo.window;

import javax.swing.JFrame;

import domonx.zoo.game.controller.NeGameStateController;
import domonx.zoo.game.state.NeGameState;

import domonx.zoo.web.module.NeWebModule;

public class NeBaseGame extends NeGame {

	private NeBasePanel panel;
	private NeGameState state;

	private Thread webThread;

	public NeBaseGame(JFrame window) {
		super(window);
		createPanel();
		createGameState();
	}

	protected void createPanel() {
		window.setTitle("Application");
		panel = new NeBasePanel(data, window);
		window.add(panel);
	}

	protected void createGameState() {
		NeGameStateController controller = new NeGameStateController();
		NeWebModule web = new NeWebModule(controller);
		controller.connectWeb(web);
		webThread = new Thread(web);
		webThread.start();
		state = new NeGameState(panel.getGraphicsModule(), controller, controller);
		web.connect();
		controller.connectState(state);
	}

	@Override
	protected void updateScreen() {
		panel.repaint();
	}

	@Override
	protected void updateGame(int hertzPassed) {
		panel.tick(hertzPassed);
		state.tick(hertzPassed);
	}

}
