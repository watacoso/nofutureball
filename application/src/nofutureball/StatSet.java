package nofutureball;

public class StatSet {
	
	// Notice: Works *only* with 4 or 12 arguments.
	// longDmg, shortDmg, armor, speed, firingSpeed, range, attackSpeed, knockback, bulletSpeed, bulletSize, regen ,maxHealth
	private static final StatSet SHARPSHOOTER = new StatSet(8, 1, 5, 6, 4, 800, 15, 20, 0, 0, 0, 100);
	private static final StatSet AOEGUY = new StatSet(5, 5, 5, 5);
	private static final StatSet MEELE = new StatSet(0, 7, 10, 4);

	private static final StatSet ENEMY = new StatSet(0, 0, 1, 10);
	
	public static StatSet getSharpShooterStats()
	{
		return SHARPSHOOTER.copy();
	}
	public static StatSet getAoEGuyStats()
	{
		return AOEGUY.copy();
	}
	public static StatSet meele()
	{
		return MEELE.copy();
	}
	public static StatSet enemy()
	{
		return ENEMY.copy();
	}
	
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
		this.maxHealth = health = maxHealth;
		this.regen = regen;
	}
	
	public StatSet()
	{
		
	}
	
	public int longDmg;
	public int shortDmg;
	public int armor;
	public int normalSpeed;
	public int firingSpeed;
	
	public int range;
	public int attackSpeed;
	public int knockback;
	
	public int bulletSpeed;
	public int bulletSize;
	
	public int regen;
	public int maxHealth;
	
	// Values that are dependent upon other static values. 
	public int health;
	public int damage;
	
	public StatSet copy()
	{
		return new StatSet(longDmg, shortDmg, armor, normalSpeed, firingSpeed, range, attackSpeed, knockback, bulletSpeed, bulletSize, regen, maxHealth);
	}
}
