package domonx.zoo.game.cards;

import java.util.ArrayList;

import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.game.INeCard;
import domonx.zoo.game.NeCard;
import domonx.zoo.state.INeGameStateController;
import domonx.zoo.window.NeGraphicsModule;

public class CardAnthelope extends NeCard{
	
	private static String code = NeCardCodes.Anthelope;
	private static String src = "assets\\EN\\Anthelope.png";

	public CardAnthelope(String GUID, NeGraphicsModule graphics, INeActionListener listener, INeGameStateController state) {
		super(GUID, src, graphics, listener, state);
		this.strenght = 3;
	}
	
	@Override
	public void onActivate() {
	}
	
	@Override
	public void onSelfPlay() {
		String[] b = {GUID};
		state.pickFromFriendlyRow(2, this, b);
	}
	
	@Override
	public void afterPick() {
		ArrayList<INeCard> pickBuffer = state.getPickBuffer();
		for(INeCard temp: pickBuffer) {
			state.moveCardFromRowToTemp(temp.getGUID(), temp.getRowGUID());
			state.moveCardFromTempToHand(temp.getGUID(), temp.getOwnerGUID());
		}
	}

}
