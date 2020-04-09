package domonx.zoo.core.util;

import domonx.zoo.core.entity.INeEntity;

public class NeEntityUtils {
	public static boolean isPointInside(INeEntity entity, int x, int y) {
		return 
			entity.getX() <= x && 
			entity.getX() + entity.getWidth() >= x && 
			entity.getY() <= y && 
			entity.getY() + entity.getHeight() >= y;
	}
}
