package domonx.zoo.window;

import javax.swing.JFrame;

import domonx.zoo.state.NeBaseGameState;
import domonx.zoo.state.NeCustomGameState;
import domonx.zoo.state.NeGameStateController;

public class NeBaseGame extends NeGame {
	
	private NeBasePanel panel;
	private NeCustomGameState state;
	
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
		NeGameStateController stack = new NeGameStateController();
		state = new NeCustomGameState(panel.getGraphicsModule(), stack);
		stack.connectState(state);
	}
	
	@Override
	protected void updateScreen() {
		super.updateScreen();
		panel.repaint();
	}
	
	@Override
	protected void updateGame(int hertzPassed) {
		super.updateGame(hertzPassed);
		panel.tick(hertzPassed);
		state.tick(hertzPassed);
	}

}
