package domonx.zoo.state;

import java.util.Arrays;

import domonx.zoo.actions.NeAction;
import domonx.zoo.actions.NeActionEntityBlured;
import domonx.zoo.actions.NeActionEntityClicked;
import domonx.zoo.actions.NeActionEntityHovered;
import domonx.zoo.actions.NeActionEntityMoved;
import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.container.NeContainer;
import domonx.zoo.core.interfaces.INeActionListener;
import domonx.zoo.core.interfaces.INeTickListener;
import domonx.zoo.window.NeGraphicsModule;

public abstract class NeBaseGameState implements INeActionListener, INeTickListener{
	
	protected NeGraphicsModule graphics;	
	
	public NeBaseGameState(NeGraphicsModule graphics) {
		this.graphics = graphics;
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
		if(payload instanceof NeActionEntityClicked) {
			handleEntityClicked((NeActionEntityClicked) payload);
		}
	}
	
	protected abstract void handleEntityMoved(NeActionEntityMoved payload);
	
	protected abstract void handleEntityClicked(NeActionEntityClicked payload);
	
	protected abstract void handleEntityHovered(NeActionEntityHovered payload);
	
	protected abstract void handleEntityBlured(NeActionEntityBlured payload);
	
	public String[] getArrayFromPath(String path) {
		return path.split("/");
	}
	
	protected NeEntity getEntityByGUIDPath(String[] guid, NeEntity current) {
			if(current == null) {
				return null;
			}
			if(guid.length == 1) {
				return guid[0].equals(current.getGUID()) ? current : null;
			}
			if(!(current instanceof NeContainer)) {
				return null;
			}
			current = ((NeContainer)(current)).content.get(guid[1]);
			guid = Arrays.copyOfRange(guid, 1, guid.length);
			return getEntityByGUIDPath(guid, current);
	}
}
