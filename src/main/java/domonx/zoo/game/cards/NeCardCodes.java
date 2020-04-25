package domonx.zoo.game.cards;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class NeCardCodes {
	
	private NeCardCodes() {
		
	}

	public static final String Anthelope = "NE_0_ANTHELOPE";
	public static final String Bat = "NE_1_BAT";
	public static final String Bear = "NE_2_BEAR";
	public static final String BlackPanther = "NE_3_BLACKPANTHER";
	public static final String BlueWhale = "NE_4_BLUEWHALE";
	public static final String Chamaeleon = "NE_5_CHAMAELEON";
	public static final String Cheetah = "NE_6_CHEETAH";
	public static final String Chimpanzee = "NE_7_CHIMPANZEE";
	public static final String Cobra = "NE_8_COBRA";
	public static final String Cougar = "NE_9_COUGAR";
	public static final String Crocodile = "NE_10_CROCODILE";
	public static final String Crow = "NE_11_CROW";
	public static final String Dolphin = "NE_12_DOLPHIN";
	public static final String Eagle = "NE_13_EAGLE";
	public static final String Elephant = "NE_14_ELEPHANT";
	public static final String EuropeanBison = "NE_15_EUROPEANBISON";
	public static final String Fox = "NE_16_FOX";
	public static final String Girafee = "NE_17_GIRAFEE";
	public static final String Gorilla = "NE_18_GORILLA";
	public static final String Horse = "NE_19_HORSE";
	public static final String Hyena = "NE_20_HYENA";
	public static final String Jaguar = "NE_21_JAGUAR";
	public static final String Leopard = "NE_22_LEOPARD";
	public static final String Lion = "NE_23_LION";
	public static final String Meerkat = "NE_24_MEERKAT";
	public static final String Owl = "NE_25_OWL";
	public static final String Panda = "NE_26_PANDA";
	public static final String Parrot = "NE_27_PARROT";
	public static final String Python = "NE_28_PYTHON";
	public static final String Rabbit = "NE_29_RABBIT";
	public static final String Raccoon = "NE_30_RACCOON";
	public static final String Rat = "NE_31_RAT";
	public static final String Raven = "NE_32_RAVEN";
	public static final String Rewers = "NE_33_REWERS";
	public static final String Rhyno = "NE_34_RHYNO";
	public static final String Scorpio = "NE_35_SCORPIO";
	public static final String SeaTurtle = "NE_36_SEATURTLE";
	public static final String Shark = "NE_37_SHARK";
	public static final String Sloth = "NE_38_SLOTH";
	public static final String Spider = "NE_39_SPIDER";
	public static final String Tiger = "NE_40_TIGER";
	public static final String Vulture = "NE_41_VULTURE";
	public static final String Weasel = "NE_42_WEASEL";
	public static final String WhiteRhyno = "NE_43_WHITERHYNO";
	public static final String WhiteTiger = "NE_44_WHITETIGER";
	public static final String Wolf = "NE_45_WOLF";
	public static final String Zebra = "NE_46_ZEBRA";
	
	public static String getCard(int index) {
		List<Field> fields = Arrays.asList(NeCardCodes.class.getFields());
		Object fieldValue = null;
		try {
			fieldValue = fields.get(index).get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return "";
		}
		if(fieldValue instanceof String) {
			return (String) fieldValue;
		}
		return "";
	}
	public static int getCardNumber() {
		return NeCardCodes.class.getFields().length;
	}
}

