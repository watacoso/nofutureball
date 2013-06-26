package nofutureball;

import org.newdawn.slick.Input;


public enum KeySet {
	
	ONE(Input.KEY_UP, Input.KEY_RIGHT, Input.KEY_DOWN, Input.KEY_LEFT),
	TWO(Input.KEY_W, Input.KEY_D, Input.KEY_S, Input.KEY_A),
	THREE(Input.KEY_I, Input.KEY_L, Input.KEY_K, Input.KEY_J),
	FOUR(Input.KEY_T, Input.KEY_H, Input.KEY_G, Input.KEY_F);
	
	KeySet(int up, int right, int down, int left)
	{
		this.up = up;
		this.right = right;
		this.down = down;
		this.left = left;
	}
	public int up = 0;
	public int right = 0;
	public int down = 0;
	public int left = 0;
}
