package domonx.zoo.state;

import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.core.interfaces.INeTickListener;
import domonx.zoo.window.NeGraphicsModule;

public class NeGameState implements INeActionListener, INeTickListener{
	
	private NeGraphicsModule graphics;	
	
	public NeGameState(NeGraphicsModule graphics) {
		this.graphics = graphics;
	}

	@Override
	public void handleAction(Object payload) {
		
	}

	@Override
	public void tick(int hertz) {
		
	}
}
