package playerPackage;

/**
 * Melee guy with some kind of club
 */

import mainPackage.KeySet;
import mainPackage.StatSet;
import mapElements.Room;

public class Meele extends Player {

	public Meele(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, keySet);
		base = StatSet.MEELE;
	}
}
