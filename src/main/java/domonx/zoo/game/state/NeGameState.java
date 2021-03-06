package domonx.zoo.game.state;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domonx.zoo.controls.NeText;
import domonx.zoo.core.controller.EControllerSignatures;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.consts.ImagePaths;
import domonx.zoo.game.enums.EGameState;
import domonx.zoo.game.interfaces.INeCard;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.game.structures.NeDeck;
import domonx.zoo.game.structures.NeDiode;
import domonx.zoo.game.structures.NeEnemy;
import domonx.zoo.game.structures.NePlayer;
import domonx.zoo.game.structures.NePreview;
import domonx.zoo.game.structures.NeSpecializedButtonBuilder;
import domonx.zoo.game.structures.NeStructureButton;
import domonx.zoo.game.structures.gui.NeGuiRow;
import domonx.zoo.window.NeGraphicsModule;

public class NeGameState extends NeBaseGameState {
	private Map<String, NeGuiRow> rows;
	private Map<String, INeCard> temporaryZone;
	private List<INeCard> pickBuffer;

	private NePlayer mainPlayer;
	private NePlayer enemy;
	private NePreview preview;
	private NeDeck deck;
	private NeDiode pickSignaler;

	private String activePlayerGUID;

	private EGameState currentState;

	private NeStructureButton endButton;
	private NeStructureButton pickRowsButton;
	private NeStructureButton swapCardsButton;
	private NeStructureButton submitPicksButton;

	public NeText getPlayerPointsLabel() {
		return playerPointsLabel;
	}

	private NeText playerPointsLabel;
	

	public NeGameState(NeGraphicsModule graphics, NeAbstractGameStateController controller,
			NeAbstractActionListener listener) {
		super(graphics, controller, listener);
	}

	public NeStructureButton getPickRowsButton() {
		return pickRowsButton;
	}

	public String getActivePlayerGUID() {
		return activePlayerGUID;
	}

	public void setActivePlayerGUID(String activePlayerGUID) {
		this.activePlayerGUID = activePlayerGUID;
	}

	public EGameState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(EGameState currentState) {
		this.currentState = currentState;
	}

	public Map<String, NeGuiRow> getRows() {
		return rows;
	}

	public Map<String, INeCard> getTemporaryZone() {
		return temporaryZone;
	}

	public List<INeCard> getPickBuffer() {
		return pickBuffer;
	}

	public NePlayer getMainPlayer() {
		return mainPlayer;
	}

	public NePlayer getEnemy() {
		return enemy;
	}

	public NePreview getPreview() {
		return preview;
	}

	public NeDeck getDeck() {
		return deck;
	}

	public NeDiode getPickSignaler() {
		return pickSignaler;
	}

	public NeStructureButton getEndButton() {
		return endButton;
	}

	@Override
	public void tick(int hertz) {
		// To fill
	}

	@Override
	protected void prepare() {
		getGraphics().screen.load("assets\\background.jpg");
		getGraphics().screen.setScale(1);

		rows = new HashMap<>();
		temporaryZone = new HashMap<>();
		pickBuffer = new ArrayList<>();

		NeSpecializedButtonBuilder buttonBuilder = new NeSpecializedButtonBuilder(getGraphics(), getListener());

		endButton = buttonBuilder.create(GUIDGenerator.get()).pos(1820, 490).size(100, 100)
				.sign(EControllerSignatures.END_BUTTON).load(ImagePaths.END_BUTTON).connect().get();
		pickRowsButton = buttonBuilder.create(GUIDGenerator.get()).pos(1720, 490).size(100, 100)
				.sign(EControllerSignatures.PICK_ROWS_BUTTON).load(ImagePaths.PICK_ROWS).connect().get();
		submitPicksButton = buttonBuilder.create(GUIDGenerator.get()).pos(1820, 390).size(100, 100)
				.sign(EControllerSignatures.SUBMIT_PICKS_BUTTON).load(ImagePaths.SUBMIT_PICKS).connect().get();
		swapCardsButton = buttonBuilder.create(GUIDGenerator.get()).pos(1720, 390).size(100, 100)
				.sign(EControllerSignatures.SWAP_CARDS_BUTTON).load(ImagePaths.SWAP_CARDS).connect().get();

		pickSignaler = new NeDiode(GUIDGenerator.get(), getGraphics(), getListener());
		pickSignaler.setup(64, 64, 1820, 390);
		pickSignaler.connect();
		pickSignaler.getGuiElement().setVisible(false);

		mainPlayer = new NePlayer("", getGraphics(), getListener());
		enemy = new NeEnemy("", getGraphics(), getListener());
		mainPlayer.initialize(rows);
		enemy.initialize(rows);
		mainPlayer.setOpponentGuid(enemy.getGuid());
		enemy.setOpponentGuid(mainPlayer.getGuid());
		mainPlayer.connect();
		enemy.connect();

		deck = new NeDeck(GUIDGenerator.get(), getGraphics(), getListener());
		deck.connect();

		preview = new NePreview(GUIDGenerator.get(), getGraphics(), getListener());
		preview.connect();
		
		playerPointsLabel = new NeText(GUIDGenerator.get());
		playerPointsLabel.move(50, 900);
		playerPointsLabel.setBackgroundColor(new Color(255,255,255));
		playerPointsLabel.setTextColor(new Color(0,0,0));
		getGraphics().screen.add(playerPointsLabel);
		
		
		controller.informStateIsLoaded();
	}

	public NeStructureButton getSubmitPicksButton() {
		return submitPicksButton;
	}

	public NeStructureButton getSwapCardsButton() {
		return swapCardsButton;
	}

}
