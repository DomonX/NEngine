package domonx.zoo.state;

import java.util.ArrayList;
import java.util.HashMap;

import domonx.zoo.core.interfaces.INePickListener;
import domonx.zoo.game.INeCard;
import domonx.zoo.game.cards.CardFactory;

public class NeGameStateController implements INeGameStateController {
	
	public ArrayList<INeCard> pickBuffer = new ArrayList<INeCard>();
	
	public String[] bannedGUIDs;
	
	public HashMap<String, INeCard> temporaryZone = new HashMap<String, INeCard>();
	
	private NeCustomGameState gameState;
	
	private INePickListener listener;
	
	protected boolean isPicking = false;
	
	protected ENePickModes pickMode;
	
	@Override
	public ENePickModes getPickMode() {
		return pickMode;
	}

	protected int cardsToPick = 0;
	
	public NeGameStateController() {

	}
	
	public void connectState(NeCustomGameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void pickFromFriendlyRow(int number, INePickListener listener, String[] bannedGUIDs) {
		setupPickMode(number, listener, bannedGUIDs);
		pickMode = ENePickModes.Row;
	}

	@Override
	public void pickFromEnemyRow(int number, INePickListener listener, String[] bannedGUIDs) {
		setupPickMode(number, listener, bannedGUIDs);
		pickMode = ENePickModes.OpponentRow;
	}

	@Override
	public void pickFromDeck(int number, INePickListener listener, String[] bannedGUIDs) {
		setupPickMode(number, listener, bannedGUIDs);
		pickMode = ENePickModes.Deck;
	}

	@Override
	public void pickFromDeckFromN(int number, int cardsShown, INePickListener listener, String[] bannedGUIDs) {
		setupPickMode(number, listener, bannedGUIDs);
		pickMode = ENePickModes.DeckN;
	}

	@Override
	public void pickFromFriendlyHand(int number, INePickListener listener, String[] bannedGUIDs) {
		setupPickMode(number, listener, bannedGUIDs);		
		pickMode = ENePickModes.Hand;
	}

	@Override
	public void pickFromEnemyHand(int number, INePickListener listener, String[] bannedGUIDs) {
		setupPickMode(number, listener, bannedGUIDs);	
		pickMode = ENePickModes.OpponentHand;
	}

	@Override
	public void moveCardFromRowToTemp(String GUID, String RowGUID) {
		INeCard temp = gameState.mainPlayer.rowsHolder.rows.get(RowGUID).cards.get(GUID);
		gameState.mainPlayer.rowsHolder.rows.get(RowGUID).removeCard(GUID);
		temporaryZone.put(GUID, temp);
	}

	@Override
	public void moveCardFromHandToTemp(String GUID, String playerGUID) {
		INeCard temp = gameState.mainPlayer.hand.cards.get(GUID);
		gameState.mainPlayer.hand.cards.remove(GUID);
		temporaryZone.put(temp.getGUID(), temp);
	}

	@Override
	public void moveCardFromGraveyardToTemp(String GUID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveCardFromDeckToTemp(int index) {
		INeCard temp = gameState.deck.cards.get(index);	
//		temporaryZone.put
	}

	@Override
	public void moveCardFromTempToRow(String GUID, String rowGUID) {
		INeCard temp = temporaryZone.get(GUID);
		temporaryZone.remove(GUID);
		gameState.mainPlayer.putCardOnRow(temp, rowGUID);
	}

	@Override
	public void moveCardFromTempToHand(String GUID, String playerGUID) {
		INeCard temp = temporaryZone.get(GUID);
		if(gameState.mainPlayer.GUID == playerGUID) {
			gameState.mainPlayer.hand.addCard(temp);
		}
		if(gameState.enemy.GUID == playerGUID) {
			gameState.enemy.hand.addCard(temp);
		}
		temporaryZone.remove(GUID);
	}

	@Override
	public void moveCardFromTempToGraveyard(String GUID) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void moveCardFromTempToDeck(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createCardInTemp(String type, String GUID) {
		CardFactory cf = new CardFactory(gameState.graphics, gameState, this);
		INeCard temp = cf.get(type, GUID);
		temporaryZone.put(temp.getGUID(), temp);		
	}

	@Override
	public ArrayList<INeCard> getPickBuffer() {
		return pickBuffer;
	}

	@Override
	public void addToPickBuffer(INeCard newCard) {
		int pickedIndex = -1;
		int i = 0;
		for(String bannedGuid : bannedGUIDs) {
			if(bannedGuid == newCard.getGUID()){
				return;
			}
		}
		for(INeCard c : pickBuffer) {
			pickedIndex = newCard.getGUID() == c.getGUID() ? i : pickedIndex;
			i++;
		}
		if(pickedIndex != -1) {
			pickBuffer.remove(pickedIndex);
			newCard.setSelected(false);
			cardsToPick++;
			return;
		}
		if(cardsToPick < 1) {
			return;
		}
		pickBuffer.add(newCard);
		cardsToPick--;
		newCard.setSelected(true);
	}

	@Override
	public boolean isInPickMode() {
		return isPicking;
	}
	
	@Override
	public void submitPicks() {
		this.isPicking = false;
		for(INeCard temp : pickBuffer) {
			temp.setSelected(false);
		}
		if(listener != null) {
			listener.afterPick();
		}
	}
	
	protected void setupPickMode(int cardsToPick, INePickListener listener, String[] bannedGUIDs) {
		pickBuffer.clear();
		this.cardsToPick = cardsToPick;
		if(cardsToPick > 0) {
			isPicking = true;
			this.bannedGUIDs = bannedGUIDs;
			this.listener = listener;
		} else {
			isPicking = false;
		}
	}

}
