package nofutureball;

import java.awt.Point;

/*
 * Currently open for all 4 directions.
 */
public enum Facing implements StringEnum{

	RIGHT("RIGHT"), LEFT("LEFT");
	
	Facing(String str)
	{
		this.str = str;
	}
	private String str = "";
	
	public String toString()
	{
		return str;
	}
	/**
	 * 
	 * @returns Point where x is direction on x-axis [and y is direction on y-axis]. Take in mind I have not implemented y-axis.
	 */
	// I'l just let this method be here, even though it's not currenlty used.
	public static Facing deriveDirection(Point facing)
	{
		if (facing.x < 0) {
			return LEFT;
		} else {
			return RIGHT;
		}
	}
	
	
}