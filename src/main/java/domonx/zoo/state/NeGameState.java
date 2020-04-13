package domonx.zoo.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

import domonx.zoo.actions.NeAction;
import domonx.zoo.actions.NeActionEntityBlured;
import domonx.zoo.actions.NeActionEntityHovered;
import domonx.zoo.actions.NeActionEntityMoved;
import domonx.zoo.core.controller.NeDraggableController;
import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.NeImage;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.core.interfaces.INeTickListener;
import domonx.zoo.core.util.GUIDGenerator;
import domonx.zoo.game.NeButton;
import domonx.zoo.game.NeCard;
import domonx.zoo.game.NePlayer;
import domonx.zoo.game.NeRow;
import domonx.zoo.window.NeGraphicsModule;

public class NeGameState implements INeActionListener, INeTickListener, MouseListener, MouseMotionListener{
	
	private NeGraphicsModule graphics;	
	
//	private ArrayList<NeRow> rows = new ArrayList<NeRow>();
	
	private NePlayer mainPlayer;

	private ArrayList<NeRow> rows = new ArrayList<NeRow>();
	
	private NeContainer hoveredPreview = new NeContainer("Preview");
	
	private NeContainer chooseBox = new NeContainer("ChooseBox");
	
	private NeButton endButton;
	
	public NeGameState(NeGraphicsModule graphics) {
		this.graphics = graphics;
		endButton = new NeButton("End", graphics);
		graphics.screen.add(endButton);
		endButton.setHeight(100);
		endButton.setWidth(100);
		endButton.load("assets\\end.png");
		endButton.moveRelatively(1820, 490);
		graphics.screen.load("assets\\background.jpg");
		graphics.screen.setScale(1);
		graphics.getWindow().addMouseListener(this);
		graphics.getWindow().addMouseMotionListener(this);
		mainPlayer = new NePlayer(graphics, this);
		graphics.screen.add(mainPlayer.GUIElement);
		mainPlayer.rowsHolder.addRow(rows);
		mainPlayer.rowsHolder.addRow(rows);
		mainPlayer.rowsHolder.addRow(rows);
		mainPlayer.rowsHolder.addRow(rows);
		mainPlayer.rowsHolder.addRow(rows);
		mainPlayer.hand.addCard(new NeCard(GUIDGenerator.get(), "assets\\EN\\Tiger.png", graphics, this));
		mainPlayer.hand.addCard(new NeCard(GUIDGenerator.get(), "assets\\EN\\Bat.png", graphics, this));
		mainPlayer.hand.addCard(new NeCard(GUIDGenerator.get(), "assets\\EN\\Horse.png", graphics, this));
		mainPlayer.hand.addCard(new NeCard(GUIDGenerator.get(), "assets\\EN\\Horse.png", graphics, this));
		mainPlayer.hand.addCard(new NeCard(GUIDGenerator.get(), "assets\\EN\\Horse.png", graphics, this));
		mainPlayer.hand.addCard(new NeCard(GUIDGenerator.get(), "assets\\EN\\Horse.png", graphics, this));
		mainPlayer.hand.addCard(new NeCard(GUIDGenerator.get(), "assets\\EN\\Horse.png", graphics, this));
		hoveredPreview.setWidth(380);
		hoveredPreview.setHeight(500);
		graphics.screen.add(hoveredPreview);
	}

	@Override
	public void tick(int hertz) {
		
	}

	@Override
	public void handleAction(NeAction payload) {
		if(payload instanceof NeActionEntityMoved) {
			handleEntityMoved((NeActionEntityMoved)(payload));
		}
		if(payload instanceof NeActionEntityHovered) {
			handleEntityHovered((NeActionEntityHovered) payload);
		}
		if(payload instanceof NeActionEntityBlured) {
			handleEntityBlured((NeActionEntityBlured) payload);
		}
	}
	
	public String[] getArrayFromPath(String path) {
		return path.split("/");
	}
	
	public NeEntity getEntityByGUIDPath(String[] GUID, NeEntity current) {
			if(current == null) {
				return null;
			}
			if(GUID.length == 1) {
				return current;
			}
			if(!(current instanceof NeContainer)) {
				return null;
			}
			current = ((NeContainer)(current)).content.get(GUID[1]);
			GUID = Arrays.copyOfRange(GUID, 1, GUID.length);
			return getEntityByGUIDPath(GUID, current);
	}
	
	protected NeImage createPreviewCard(NeContainer place, String src, String GUID) {
		String iGUID = GUID;
		NeImage i = new NeImage(iGUID);
		i.connectStorage(graphics.getStorage());
		place.add(i);
		i.setScale(1);
		i.fit();
		i.load(src);
		return i;
	}
	
	protected void handleEntityMoved(NeActionEntityMoved payload) {
		NeEntity src = getEntityByGUIDPath(getArrayFromPath(payload.srcGUIDPath), graphics.screen);
		NeContainer srcOwner = (NeContainer)(src.getOwner());
		int xPos = payload.data.x;
		int yPos = payload.data.y;
		boolean movePerformed = false;
		if(rows.isEmpty()) {
//			mainPlayer.rows;
		}
		for(NeRow row : rows) {
			if(!row.GUIElement.isPointInside(xPos, yPos)) {
				continue;
			}
			movePerformed = mainPlayer.cardMovedOnRow(src.getGUID(), row.GUID);
			break;			
		}
		if(!movePerformed) {
			src.getController().returnOldPosition();
		}
	}
	
	protected void handleEntityHovered(NeActionEntityHovered payload) {
		addToHoverPreview(payload);
	}
	
	protected void addToHoverPreview(NeActionEntityHovered payload) {
		NeEntity src = getEntityByGUIDPath(getArrayFromPath(payload.srcGUIDPath), graphics.screen);
		NeImage srcAsImage;
		if(src instanceof NeImage) {
			srcAsImage = (NeImage)(src);
		} else {
			return;
		}
		NeEntity srcClone = createPreviewCard(hoveredPreview, srcAsImage.getSrcKey(), srcAsImage.getGUID());
	}
	
	protected void handleEntityBlured(NeActionEntityBlured payload) {
		NeEntity src = getEntityByGUIDPath(getArrayFromPath(payload.srcGUIDPath), graphics.screen);
		hoveredPreview.content.remove(src.getGUID());
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		hoveredPreview.content.clear();			
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(graphics.screen.getHeight() < hoveredPreview.getHeight() + arg0.getY()) {
			hoveredPreview.move(arg0.getX(), arg0.getY() - hoveredPreview.getHeight());
		} else {
			hoveredPreview.move(arg0.getX(), arg0.getY());
		}		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub		
	}
}
