package domonx.zoo.game.cards;

import java.util.List;

import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.interfaces.INeCard;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.game.structures.NeCard;
import domonx.zoo.window.NeGraphicsModule;

public class CardAnthelope extends NeCard{

	public CardAnthelope(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener, NeAbstractGameStateController state) {
		super(guid, "assets\\EN\\Anthelope.png", graphics, listener, state);
		code = NeCardCodes.Anthelope;
		this.strenght = 3;
	}
	
	@Override
	public void onSelfPlay() {
		String[] b = {guid};
		controller.pickFromFriendlyRow(1, this, b);
	}
	
	@Override
	public void afterPick() {
		List<INeCard> pickBuffer = controller.getPickBuffer();
		for(INeCard temp: pickBuffer) {
			controller.moveCardFromRowToTemp(temp.getGuid(), temp.getRowGUID());
			controller.moveCardFromTempToHand(temp.getGuid(), temp.getOwnerGUID());
			String guid = GUIDGenerator.get();
			controller.createCardInTemp(temp.getCode(), guid);
			controller.moveCardFromTempToHand(guid, temp.getOwnerGUID());
		}
	}

}
