package domonx.zoo.web.main;

import java.util.HashMap;
import java.util.Map;

import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.structures.NeRow;

public class WebPlayer {
	public Map<String, String> hand;
	public Map<String, NeRow> rows;
	public String guid;
	
	public WebPlayer() {
		hand = new HashMap<>();
		rows = new HashMap<>();
		guid = "";
		addRow(GUIDGenerator.get());
		addRow(GUIDGenerator.get());
		addRow(GUIDGenerator.get());
		addRow(GUIDGenerator.get());
	}
	
	public void addRow(String guidRow) {
		rows.put(guidRow, new NeRow(guidRow));
	}
}
