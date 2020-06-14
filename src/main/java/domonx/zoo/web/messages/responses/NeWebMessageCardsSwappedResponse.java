package domonx.zoo.web.messages.responses;

import domonx.zoo.web.messages.NeWebMessageTypeResponse;

public class NeWebMessageCardsSwappedResponse extends NeWebMessageResponse {
	public String[] cardsRemoved;
	public String playerGuid = "";
	public NeWebMessageCardsSwappedResponse() {
		super(NeWebMessageTypeResponse.CARDS_SWAPPED);
	}
}
