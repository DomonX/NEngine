package domonx.zoo.game.cards;

import java.util.Arrays;
import java.util.List;

import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.game.NeCard;
import domonx.zoo.state.INeGameStateController;
import domonx.zoo.window.NeGraphicsModule;

public class CardFactory {
NeGraphicsModule graphics;
	INeActionListener listener;
	INeGameStateController state;

	public CardFactory(NeGraphicsModule graphics, INeActionListener listener, INeGameStateController state) {
		super();
		this.graphics = graphics;
		this.listener = listener;
		this.state = state;
	}	public NeCard get(String cardCode, String GUID) {
		
		List<Class<?>> classes = Arrays.asList(NeCard.class.getClasses());
		classes.forEach((Class<?> item) -> System.out.println(item.getCanonicalName()));

		if(cardCode == NeCardCodes.Anthelope) {
			return new CardAnthelope(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Bat) {
			return new CardBat(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Bear) {
			return new CardBear(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.BlackPanther) {
			return new CardBlackPanther(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.BlueWhale) {
			return new CardBlueWhale(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Chamaeleon) {
			return new CardChamaeleon(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Cheetah) {
			return new CardCheetah(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Chimpanzee) {
			return new CardChimpanzee(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Cobra) {
			return new CardCobra(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Cougar) {
			return new CardCougar(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Crocodile) {
			return new CardCrocodile(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Crow) {
			return new CardCrow(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Dolphin) {
			return new CardDolphin(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Eagle) {
			return new CardEagle(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Elephant) {
			return new CardElephant(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.EuropeanBison) {
			return new CardEuropeanBison(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Fox) {
			return new CardFox(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Girafee) {
			return new CardGirafee(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Gorilla) {
			return new CardGorilla(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Horse) {
			return new CardHorse(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Hyena) {
			return new CardHyena(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Jaguar) {
			return new CardJaguar(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Leopard) {
			return new CardLeopard(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Lion) {
			return new CardLion(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Meerkat) {
			return new CardMeerkat(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Owl) {
			return new CardOwl(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Panda) {
			return new CardPanda(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Parrot) {
			return new CardParrot(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Python) {
			return new CardPython(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Rabbit) {
			return new CardRabbit(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Raccoon) {
			return new CardRaccoon(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Rat) {
			return new CardRat(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Raven) {
			return new CardRaven(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Rewers) {
			return new CardRewers(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Rhyno) {
			return new CardRhyno(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Scorpio) {
			return new CardScorpio(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.SeaTurtle) {
			return new CardSeaTurtle(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Shark) {
			return new CardShark(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Sloth) {
			return new CardSloth(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Spider) {
			return new CardSpider(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Tiger) {
			return new CardTiger(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Vulture) {
			return new CardVulture(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Weasel) {
			return new CardWeasel(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.WhiteRhyno) {
			return new CardWhiteRhyno(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.WhiteTiger) {
			return new CardWhiteTiger(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Wolf) {
			return new CardWolf(GUID, graphics, listener, state);
		}
		if(cardCode == NeCardCodes.Zebra) {
			return new CardZebra(GUID, graphics, listener, state);
		}
		return null;
	}
}
