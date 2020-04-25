package domonx.zoo.state;

import java.util.ArrayList;

import domonx.zoo.core.interfaces.INePickListener;
import domonx.zoo.game.INeCard;

public interface INeGameStateController {
	public void pickFromFriendlyRow(int number, INePickListener listener, String[] bannedGUIDs);
	public void pickFromEnemyRow(int number, INePickListener listener,String[] bannedGUIDs);
	public void pickFromDeck(int number, INePickListener listener, String[] bannedGUIDs);
	public void pickFromDeckFromN(int number, int cardsShown, INePickListener listener, String[] bannedGUIDs);
	public void pickFromFriendlyHand(int number, INePickListener listener, String[] bannedGUIDs);
	public void pickFromEnemyHand(int number, INePickListener listener, String[] bannedGUIDs);
	public void moveCardFromRowToTemp(String GUID, String rowGUID);
	public void moveCardFromHandToTemp(String GUID, String playerGUID);
	public void moveCardFromGraveyardToTemp(String GUID);
	public void moveCardFromDeckToTemp(int index);
	public void moveCardFromTempToRow(String GUID, String rowGUID);
	public void moveCardFromTempToHand(String GUID, String playerGUID);
	public void moveCardFromTempToGraveyard(String GUID);
	public void moveCardFromTempToDeck(int index);
	public void createCardInTemp(String type, String GUID);
	
	public ArrayList<INeCard> getPickBuffer();
	public void submitPicks();
	
	public ENePickModes getPickMode();
	public void addToPickBuffer(INeCard newCard);
	
	public boolean isInPickMode();
	
}
