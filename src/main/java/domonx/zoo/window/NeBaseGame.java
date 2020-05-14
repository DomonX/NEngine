package domonx.zoo.window;

import javax.swing.JFrame;

import domonx.zoo.game.controller.NeGameStateController;
import domonx.zoo.game.state.NeGameState;

public class NeBaseGame extends NeGame {
	
	private NeBasePanel panel;
	private NeGameState state;
	
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
		state = new NeGameState(panel.getGraphicsModule(), controller, controller);
		controller.connectState(state);
		controller.startGame();
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
