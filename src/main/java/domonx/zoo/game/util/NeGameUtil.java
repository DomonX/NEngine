package domonx.zoo.game.util;

import java.util.Arrays;

import domonx.zoo.core.entity.NeEntity;
import domonx.zoo.core.entity.container.NeContainer;

public class NeGameUtil {
	
	private NeGameUtil() {
		
	}
	
	public static NeEntity getEntityByPath(String path, NeEntity current) {
		return getEntityByPath(getArrayFromPath(path), current);
	}
	
	public static String[] getArrayFromPath(String path) {
		return path.split("/");
	}
	
	public static NeEntity getEntityByPath(String[] path, NeEntity current) {
		if(current == null) {
			return null;
		}
		if(path.length == 1) {
			return path[0].equals(current.getGUID()) ? current : null;
		}
		if(!(current instanceof NeContainer)) {
			return null;
		}
		current = ((NeContainer)(current)).getContent().get(path[1]);
		path = Arrays.copyOfRange(path, 1, path.length);
		return getEntityByPath(path, current);
}
}
