// File is generated via card generator
// File can be changed and will not be regenerated
package domonx.zoo.game.cards;

import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.game.structures.NeCard;
import domonx.zoo.window.NeGraphicsModule;

public class CardPanda extends NeCard{

	public CardPanda(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener, NeAbstractGameStateController state) {
		super(guid, "assets\\EN\\Panda.png", graphics, listener, state);
		code =  NeCardCodes.Panda;
		strenght = 1;
	}

}

