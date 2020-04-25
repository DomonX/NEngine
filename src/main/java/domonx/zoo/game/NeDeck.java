package domonx.zoo.game;

import java.util.ArrayList;
import java.util.Collections;

import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.cards.CardFactory;
import domonx.zoo.game.cards.NeCardCodes;
import domonx.zoo.state.INeGameStateController;
import domonx.zoo.window.NeGraphicsModule;

public class NeDeck extends NeStructureElement{
	
	public ArrayList<NeCard> cards = new ArrayList<NeCard>();
	
	public INeGameStateController state;

	public NeDeck(String GUID, NeGraphicsModule graphics, INeActionListener listener, INeGameStateController state) {
		super(GUID, ENeStructureType.none, graphics, listener);
		this.state = state;
		prepareGUI();
		loadAndShuffle();		
	}
	
	protected void loadAndShuffle() {
		CardFactory cf = new CardFactory(graphics, listener, state);
		int i = 0;
		for(i = 0; i < 20; i++) {
			cards.add(cf.get(NeCardCodes.Chamaeleon, GUIDGenerator.get()));
		}
		for(i = 0; i < 20; i++) {
			cards.add(cf.get(NeCardCodes.Spider, GUIDGenerator.get()));
		}
		Collections.shuffle(cards);
	}

	@Override
	protected void prepareGUI() {
		
	}
	
}
