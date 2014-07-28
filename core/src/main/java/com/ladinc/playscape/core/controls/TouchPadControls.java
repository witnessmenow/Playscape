package com.ladinc.playscape.core.controls;

import com.badlogic.gdx.Gdx;
import com.ladinc.playscape.core.PlayScape;

public class TouchPadControls implements IControls
{
	public float xMovement;
	public float yMovement;
	private boolean active;

	
	
	@Override
	public float getXMovement() 
	{
		Gdx.app.error("X", "X: " + Gdx.input.getAccelerometerX());
		if(!active)
		{
			activateController();
		}
		return Gdx.input.getAccelerometerX();
	}

	@Override
	public float getYMovement() 
	{
		Gdx.app.error("Y", "Y: " + Gdx.input.getAccelerometerY());
		
		if(!active)
		{
			activateController();
		}
		return Gdx.input.getAccelerometerY();
	}

	@Override
	public boolean getAttackPressed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void activateController()
	{
		active = true;
		
		PlayScape.inActiveControls.remove(this);
		PlayScape.controls.add(this);
	}

	
}
