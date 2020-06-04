package domonx.zoo.game.interfaces;

import java.util.List;

import domonx.zoo.core.interfaces.INePickListener;
import domonx.zoo.game.enums.ENePickModes;

public interface NeAbstractGameStateController {
	public void pickFromFriendlyRow(int number, INePickListener listener, String[] bannedGuids);
	public void pickFromEnemyRow(int number, INePickListener listener,String[] bannedGuids);
	public void pickFromDeck(int number, INePickListener listener, String[] bannedGuids);
	public void pickFromDeckFromN(int number, int cardsShown, INePickListener listener, String[] bannedGuids);
	public void pickFromFriendlyHand(int number, INePickListener listener, String[] bannedGuids);
	public void pickFromEnemyHand(int number, INePickListener listener, String[] bannedGuids);
	public void moveCardFromRowToTemp(String guid, String rowGuid);
	public void moveCardFromHandToTemp(String guid, String playerGuid);
	public void moveCardFromGraveyardToTemp(String guid);
	public void moveCardFromDeckToTemp(int index);
	public void moveCardFromTempToRow(String guid, String rowGuid);
	public void moveCardFromTempToHand(String guid, String playerGuid);
	public void moveCardFromTempToGraveyard(String guid);
	public void moveCardFromTempToTopOfDeck(String guid);
	public void moveCardFromTempToBottomOfDeck(String guid);
	public void createCardInTemp(String type, String guid);
	public void drawCard(String playerGuid);
	public void afterDrawCard(String cardGuid, String cardType);
	public void getDeckSize(String playerGuid);
	public void afterGetDeckSize(int size);
	public void afterConnected(String playerGuid);
	public void afterOpponentHandChange(String cardGuid, boolean isRemoved);
	public void afterOpponentPlayCard(String cardType, int rowNumber);
	
	public void endPlayerTurn(String playerGuid);
	public void startPlayerTurn(String playerGuid);
	
	public boolean isInPickMode();
	
	public List<INeCard> getPickBuffer();
	public void submitPicks();
	
	public ENePickModes getPickMode();
	public void addToPickBuffer(INeCard newCard);
	
	public void startGame();
	
}
