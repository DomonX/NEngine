// File is generated via card generator
// File can be changed and will not be regenerated
package domonx.zoo.game.cards;

import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.game.NeCard;
import domonx.zoo.state.INeGameStateController;
import domonx.zoo.window.NeGraphicsModule;

public class CardOwl extends NeCard{
	
	private static String code = NeCardCodes.Owl;
	private static String src = "assets\\EN\\Owl.png";

	public CardOwl(String GUID, NeGraphicsModule graphics, INeActionListener listener, INeGameStateController state) {
		super(GUID, src, graphics, listener, state);
	}

}
