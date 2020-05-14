package domonx.zoo.game.structures;

import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.window.NeGraphicsModule;

public class NeEnemy extends NePlayer{

	public NeEnemy(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener) {
		super(guid, graphics, listener);
	}
	
	@Override
	protected void prepareHolderAndHand() {
		hand = new NeHand(GUIDGenerator.get(), graphics, listener, 0, 0);
		rowsHolder = new NeRowsHolder(GUIDGenerator.get(), graphics, listener, 0, (int)(hand.guiElement.getHeight()));
	}
	
	@Override
	protected void setupPositions() {
		guiElement.setWidth(1300);
		guiElement.setHeight(700);
		guiElement.moveRelatively(310, -160);
	}

}
