package nofutureball;

public class StatSet {
	
	// Notice: Works *only* with 4 or 12 arguments.
	// longDmg, shortDmg, armor, speed, firingSpeed, range, attackSpeed, knockback, bulletSpeed, bulletSize, regen ,maxHealth
	public static final StatSet SHARPSHOOTER = new StatSet(8, 1, 5, 6, 4, 800, 15, 20, 0, 0, 0, 100);
	public static final StatSet AOEGUY = new StatSet(5, 5, 5, 5);
	public static final StatSet MEELE = new StatSet(0, 7, 10, 4);
	
	public static final StatSet ENEMY = new StatSet(0, 0, 1, 10);
	
	public StatSet(int longDmg, int shortDmg, int armor, int normalSpeed) {
		this.longDmg = longDmg;
		this.shortDmg = shortDmg;
		this.armor = armor;
		this.normalSpeed = normalSpeed;
	}
	public StatSet(int longDmg, int shortDmg, int armor, int normalSpeed, int firingSpeed, int range, int attackSpeed, int knockback, int bulletSpeed, int bulletSize, int regen, int maxHealth) {
		this(longDmg, shortDmg, armor, normalSpeed);
		this.range = range;
		this.attackSpeed = attackSpeed;
		this.knockback = knockback;
		this.maxHealth = maxHealth;
		this.regen = regen;
	}
	
	
	/**
	 * Copy the _actual_ stats of a game object into a new StatSet. Intended for use with Bullet.
	 */
	public StatSet(GameObject o)
	{
		this(o.longDmg(), o.shortDmg(), o.armor(), o.normalSpeed(), o.firingSpeed(), o.range(), o.attackSpeed(), o.knockback(), o.bulletSpeed(), o.bulletSize(), o.regen(), o.maxHealth());
	}
	
	private int longDmg;
	private int shortDmg;
	private int armor;
	private int normalSpeed;
	private int firingSpeed;
	
	private int range;
	private int attackSpeed;
	private int knockback;
	
	private int bulletSpeed;
	private int bulletSize;
	
	private int regen;
	private int maxHealth;
	
	// Values that are dependent upon other static values. 
	
	public int longDmg() { return longDmg; }
	public int shortDmg() { return shortDmg; }
	public int armor() { return armor; }
	public int normalSpeed() { return normalSpeed; }
	public int firingSpeed() { return firingSpeed; }
	public int range() { return range(); }
	public int attackSpeed() { return attackSpeed; }
	public int knockback() { return knockback; }
	public int bulletSpeed() { return bulletSpeed; }
	public int bulletSize() { return bulletSize; }
	public int regen() { return regen; }
	public int maxHealth() { return maxHealth; }
	
	public StatSet copy()
	{
		return new StatSet(longDmg, shortDmg, armor, normalSpeed, firingSpeed, range, attackSpeed, knockback, bulletSpeed, bulletSize, regen, maxHealth);
	}
}
