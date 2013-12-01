package com.github.nofutureball.main;


public enum Augmentation {
	/** Weapon: Trippleshot */
	W_TRIPLESHOT	(0, 0, 0, 0),
	/** Weapon: Increase Range */
	W_RANGE			(4, 2, 0, 0),
	/** Weapon: Meele */
	W_MEELE			(2, 4, 0, 0),
	/** Weapon Impact: Explosives */
	WI_EXPLOSIVES	(-2, -2, 0, 0),
	/** Weapon Impact: Bullet */
	WI_HEALING		(0, 0, 0, 0),
	/** Weapon Impact: Shield */
	WI_SHIELD		(0, 0, 0, 0),
	/** Armour: Spiked */
	A_SPIKED		(0, 0, 2, -1),
	/** Armour: Nano Healing */
	A_NANOHEALING	(0, 0, -2, 2),
	/** Armour: Heavy */
	A_HEAVY			(0, 0, 5, -2);
	
	// Arguments: long range damage, short range damage, armor, speed
	Augmentation(int longDmg, int shortDmg, int armor, int speed) {
		stats = new StatSet(longDmg, shortDmg, armor, speed);
	}
	public StatSet stats;
}
