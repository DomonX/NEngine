package domonx.zoo.game.cards;

import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.game.structures.NeCard;
import domonx.zoo.window.NeGraphicsModule;
public class CardFactory {
	NeGraphicsModule graphics;
	NeAbstractActionListener listener;
	NeAbstractGameStateController state;

	public CardFactory(NeGraphicsModule graphics, NeAbstractActionListener listener, NeAbstractGameStateController state) {
		super();
		this.graphics = graphics;
		this.listener = listener;
		this.state = state;
	}	public NeCard get(String cardCode, String GUID) {

		if(cardCode.equals(NeCardCodes.Anthelope)) {
			return new CardAnthelope(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Bat)) {
			return new CardBat(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Bear)) {
			return new CardBear(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.BlackPanther)) {
			return new CardBlackPanther(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.BlueWhale)) {
			return new CardBlueWhale(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Chamaeleon)) {
			return new CardChamaeleon(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Cheetah)) {
			return new CardCheetah(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Chimpanzee)) {
			return new CardChimpanzee(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Cobra)) {
			return new CardCobra(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Cougar)) {
			return new CardCougar(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Crocodile)) {
			return new CardCrocodile(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Crow)) {
			return new CardCrow(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Dolphin)) {
			return new CardDolphin(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Eagle)) {
			return new CardEagle(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Elephant)) {
			return new CardElephant(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.EuropeanBison)) {
			return new CardEuropeanBison(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Fox)) {
			return new CardFox(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Girafee)) {
			return new CardGirafee(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Gorilla)) {
			return new CardGorilla(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Horse)) {
			return new CardHorse(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Hyena)) {
			return new CardHyena(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Jaguar)) {
			return new CardJaguar(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Leopard)) {
			return new CardLeopard(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Lion)) {
			return new CardLion(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Meerkat)) {
			return new CardMeerkat(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Owl)) {
			return new CardOwl(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Panda)) {
			return new CardPanda(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Parrot)) {
			return new CardParrot(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Python)) {
			return new CardPython(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Rabbit)) {
			return new CardRabbit(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Raccoon)) {
			return new CardRaccoon(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Rat)) {
			return new CardRat(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Raven)) {
			return new CardRaven(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Rhyno)) {
			return new CardRhyno(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Scorpio)) {
			return new CardScorpio(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.SeaTurtle)) {
			return new CardSeaTurtle(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Shark)) {
			return new CardShark(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Sloth)) {
			return new CardSloth(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Spider)) {
			return new CardSpider(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Tiger)) {
			return new CardTiger(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Vulture)) {
			return new CardVulture(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Weasel)) {
			return new CardWeasel(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.WhiteRhyno)) {
			return new CardWhiteRhyno(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.WhiteTiger)) {
			return new CardWhiteTiger(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Wolf)) {
			return new CardWolf(GUID, graphics, listener, state);
		}
		if(cardCode.equals(NeCardCodes.Zebra)) {
			return new CardZebra(GUID, graphics, listener, state);
		}
		if(cardCode.equals("NE_33_REWERS")) {
			return new CardRewers(GUID, graphics, listener, state);
		}
		return null;
	}
}
