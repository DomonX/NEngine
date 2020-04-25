package domonx.zoo.game;

import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.window.NeGraphicsModule;

public class NeEnemy extends NePlayer{

	public NeEnemy(String GUID, NeGraphicsModule graphics, INeActionListener listener) {
		super(GUID, graphics, listener);
	}
	
	@Override
	protected void prepareHolderAndHand() {
		hand = new NeHand(GUIDGenerator.get(), graphics, listener, 0, 0);
		rowsHolder = new NeRowsHolder(GUIDGenerator.get(), graphics, listener, 0, (int)(hand.GUIElement.getHeight()));
	}
	
	@Override
	protected void setupPositions() {
		GUIElement.setWidth(1300);
		GUIElement.setHeight(700);
		GUIElement.moveRelatively(310, -160);
	}

}
