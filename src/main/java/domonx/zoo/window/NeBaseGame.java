package domonx.zoo.window;

import javax.swing.JFrame;

import domonx.zoo.state.NeGameState;

public class NeBaseGame extends NeGame {
	
	private NeBasePanel panel;
	
	@SuppressWarnings("unused")
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
		state = new NeGameState(panel.getGraphicsModule());
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
	}

}
