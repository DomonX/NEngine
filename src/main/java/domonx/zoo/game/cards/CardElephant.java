// File is generated via card generator
// File can be changed and will not be regenerated
package domonx.zoo.game.cards;

import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.game.NeCard;
import domonx.zoo.state.INeGameStateController;
import domonx.zoo.window.NeGraphicsModule;

public class CardElephant extends NeCard{
	
	private static String code = NeCardCodes.Elephant;
	private static String src = "assets\\EN\\Elephant.png";

	public CardElephant(String GUID, NeGraphicsModule graphics, INeActionListener listener, INeGameStateController state) {
		super(GUID, src, graphics, listener, state);
	}

}

