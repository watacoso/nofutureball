package com.github.nofutureball.main;

import org.newdawn.slick.Input;


public enum KeySet {
	
	ONE(Input.KEY_W, Input.KEY_D, Input.KEY_S, Input.KEY_A, Input.KEY_I, Input.KEY_J, Input.KEY_K, Input.KEY_L,Input.KEY_SPACE),
	TWO(Input.KEY_UP, Input.KEY_RIGHT, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_NUMPAD5, Input.KEY_NUMPAD1, Input.KEY_NUMPAD2, Input.KEY_NUMPAD3, Input.KEY_B);
	//THREE(Input.KEY_I, Input.KEY_L, Input.KEY_K, Input.KEY_J,Input.KEY_P),
	//FOUR(Input.KEY_T, Input.KEY_H, Input.KEY_G, Input.KEY_F,Input.KEY_ENTER);
	
	KeySet(int up, int right, int down, int left,int aimUp,int aimLeft,int aimDown, int aimRight, int action1)
	{
		this.up = up;
		this.right = right;
		this.down = down;
		this.left = left;
		this.aimUp=aimUp;
		this.aimLeft=aimLeft;
		this.aimRight=aimRight;
		this.aimDown=aimDown;
		this.action1=action1;
	}
	public int up = 0;
	public int right = 0;
	public int down = 0;
	public int left = 0;
	public int action1= 0;
	public int aimUp = 0;
	public int aimRight = 0;
	public int aimDown = 0;
	public int aimLeft = 0;
	
}
