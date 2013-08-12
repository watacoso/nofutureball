package nofutureball;

public enum Augmentation {
	W_TRIPLESHOT("passive"),
	W_RANGE("passive"),
	W_MEELE("passive"),
	WI_EXPLOSIVES("active"),
	WI_HEALING("bullet"),
	WI_SHIELD("passive"),
	A_SPIKED("passive"),
	A_NANOHEALING("passive"),
	A_HEAVY("passive");
	
	
	/*
	public static void exec_aug(Augmentation aug,GameObject source){
		switch (aug){
		case W_TRIPLESHOT:
			tripleShot(source);
			break;
		}
	}
	*/
	
	public String type;				//shoot, bullet, 
	
	Augmentation(String type) {
		this.type=type;
	}
}
