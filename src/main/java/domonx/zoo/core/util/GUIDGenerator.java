package domonx.zoo.core.util;

import java.util.Random;

public class GUIDGenerator {
	private static int generatedGUIDS = 0;
	private static final String signs = "39tLk7PiluX8cOJI50wE6KbfRTDzHAo1GhxNSQjndY4VrvZmpe02FCMygUBWsqa";
	private final static int counterSigns = 10;
	private final static int randomSigns = 10;
	
	public static String get() {
		int temp = generatedGUIDS;
		int counterCharactersGenerated = 0;
		String GUID = "";
		while(temp != 0) {
			char characterNumber = signs.charAt(temp%signs.length());
			GUID += characterNumber;
			counterCharactersGenerated++;
			temp = temp/signs.length();
		}
		while(counterCharactersGenerated < counterSigns) {
			GUID = signs.charAt(0) + GUID;
			counterCharactersGenerated++;
		}
		int randomCharactersGenerated = 0;
		Random generator = new Random();
		while(randomCharactersGenerated < randomSigns) {
			char nextChar = signs.charAt(generator.nextInt(signs.length()));
			GUID = GUID + nextChar;
			randomCharactersGenerated++;
		}
		generatedGUIDS++;
		return GUID;
	}
}
