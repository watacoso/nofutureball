package oppression;

public class Vector2d {
	
	public double x = 0;
	public double y = 0;
	
	public Vector2d()
	{
		
	}
	public Vector2d(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double length()
	{
		return Math.sqrt(x*x + y*y);
	}
}
