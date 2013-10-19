package com.ladinc.playscape.core.controls;

public interface IControls 
{
	//Should be -1 for fully left to 1 for fully right
	public float getXMovement();
	
	//Should be 1 for fully Down to -1 for fully Up
	public float getYMovement();
	
	public boolean getAttackPressed();

}
