package com.ladinc.playscape.core.controls;

import com.ladinc.playscape.core.PlayScape;

public class GamePadControls implements IControls{

	private float TRIGGER_DEADZONE = 0.350f;
	
	public String contName;
	
	private float xMovement;
	private float yMovement;
	private boolean attackPressed;
	
	public boolean active;
	private PlayScape game;
	
	public GamePadControls(float deadzone, PlayScape game)
	{
		this.TRIGGER_DEADZONE = deadzone;
		this.game = game;
	}
	
	private void activateController()
	{
		active = true;
		
		this.game.inActiveControls.remove(this);
		this.game.controls.add(this);
	}
	
	public void setAnalogMovment(float x, float y)
	{
		
		setXMovement(x);
		setYMovement(y);
		
	}
	
	public void setXMovement(float x)
	{
		float xPower = x;
		
		if (x < 0)
			xPower = x*(-1);
	
		if(xPower <= TRIGGER_DEADZONE)
			xMovement = 0f;
		else
		{
			if(!active)
				activateController();
			
			//have a meaningful value for X
			float xAbovePower = xPower - TRIGGER_DEADZONE;
			float availableXPower = 1f - TRIGGER_DEADZONE;
			xAbovePower = xAbovePower/availableXPower;
			
			if(x > 0)
			{
				this.xMovement = xAbovePower;
			}
			else
			{
				this.xMovement = (-1)*xAbovePower;
			}
		}
	}
	
	public void setYMovement(float y)
	{
		float yPower = y;
		
		if (y < 0)
			yPower = y*(-1);
		
		if(yPower <= TRIGGER_DEADZONE)
			yMovement = 0f;
		else
		{
			if(!active)
				activateController();
			
			//have a meaningful value for X
			float yAbovePower = yPower - TRIGGER_DEADZONE;
			float availableYPower = 1f - TRIGGER_DEADZONE;
			yAbovePower = yAbovePower/ availableYPower;
			
			
			//Y is inverted coming back from MCP
			if(y > 0)
			{
				this.yMovement = (-1)*yAbovePower;
			}
			else
			{
				this.yMovement = yAbovePower;
			}
		}
	} 
	
	@Override
	public float getXMovement() {
		// TODO Auto-generated method stub
		return this.xMovement;
	}

	@Override
	public float getYMovement() {
		// TODO Auto-generated method stub
		return this.yMovement;
	}

	@Override
	public boolean getAttackPressed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
