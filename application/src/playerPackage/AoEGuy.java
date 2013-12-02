package playerPackage;

import mainPackage.KeySet;
import mainPackage.StatSet;
import mapElements.Room;

/**
 * Area of Attack - Guy
 * @author watacoso
 *
 */

public class AoEGuy extends Player {

	public AoEGuy(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, keySet);
		base = StatSet.AOEGUY;
	}
}
