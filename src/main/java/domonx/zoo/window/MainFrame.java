package domonx.zoo.window;

import domonx.zoo.core.storage.NeImageStorage;

public class MainFrame extends NeSyncFrame {
	
	private static final long serialVersionUID = 8590211693728083963L;
	
	private NeBasePanel panel;
	
	public MainFrame() {
		super();
		setTitle("Application");
		NeImageStorage storage = new NeImageStorage();
		NeGraphicsModule graphics = new NeGraphicsModule(storage, this);
		panel = new NeBasePanel(this, graphics);
		add(panel);
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
