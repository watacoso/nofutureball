package nofutureball;

public class Sharpshooter extends Player {

	public Sharpshooter(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, keySet);
		weapon = new Weapon();
	}
	

}
