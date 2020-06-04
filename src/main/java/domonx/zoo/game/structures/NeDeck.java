package domonx.zoo.game.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import domonx.zoo.controls.NeText;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.NeAbstractActionListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.cards.CardFactory;
import domonx.zoo.game.cards.NeCardCodes;
import domonx.zoo.game.consts.ImagePaths;
import domonx.zoo.game.enums.ENeStructureType;
import domonx.zoo.game.interfaces.NeAbstractGameStateController;
import domonx.zoo.window.NeGraphicsModule;

public class NeDeck extends NeStructureElement<NeContainer>{
	
	public List<String> cards;
	
	private NeText counterGui;

	public NeDeck(String guid, NeGraphicsModule graphics, NeAbstractActionListener listener) {
		super(guid, ENeStructureType.NONE, graphics, listener);
		cards = new ArrayList<>();
		prepareGUI();
	}
	
	public void fillUnknown(int size) {
		cards.clear();
		for(int i = 0; i < size; i++) {
			cards.add("NE_UNKNOWN");
		}
	}

	@Override
	protected void prepareGUI() {
		guiElement = new NeContainer(guid);
		guiElement.connectStorage(graphics.getStorage());
		guiElement.setWidth(300);
		guiElement.setHeight(430);
		guiElement.setScale(1);
		guiElement.move(1620, 600);
		guiElement.load(ImagePaths.REVERSE);
		counterGui = new NeText(guid + "_counter");
		counterGui.setWidth(50);
		counterGui.setHeight(50);
		counterGui.setScale(1);
		counterGui.setValue("0");
		guiElement.add(counterGui);
		counterGui.moveRelatively(125, 175);
	}
	
}
