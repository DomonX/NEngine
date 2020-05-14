package domonx.zoo.game.configuration;

public class NeGameConfiguration {
	private NeGameConfiguration() {
		
	}
	private static int baseRowsPerPlayer = 4;
	private static int baseCardsInHand = 10;

	public static int getBaseCardsInHand() {
		return baseCardsInHand;
	}

	public static void setBaseCardsInHand(int baseCardsInHand) {
		NeGameConfiguration.baseCardsInHand = baseCardsInHand;
	}

	public static int getBaseRowsPerPlayer() {
		return baseRowsPerPlayer;
	}

	public static void setBaseRowsPerPlayer(int baseRowsPerPlayer) {
		NeGameConfiguration.baseRowsPerPlayer = baseRowsPerPlayer;
	}
	
}
