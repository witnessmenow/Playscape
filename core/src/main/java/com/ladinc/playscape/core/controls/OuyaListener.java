package com.ladinc.playscape.core.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.math.Vector3;
import com.ladinc.playscape.core.PlayScape;

public class OuyaListener implements ControllerListener{

	private PlayScape game;
	public GamePadControls controls;
	public OuyaListener(PlayScape game)
	{
		this.game = game;
		this.controls = new GamePadControls(0.2f, this.game);
	}
	
	@Override
	public boolean accelerometerMoved(Controller arg0, int arg1, Vector3 arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean axisMoved(Controller arg0, int arg1, float arg2) 
	{	
	
		Gdx.app.log("OuyaListener", "Controller Axis Moved: " + arg1);
		
		if(arg1 == Ouya.AXIS_LEFT_X)
		{
			this.controls.setXMovement(arg2);
		}
		else if (arg1 == Ouya.AXIS_LEFT_Y)
		{
			this.controls.setYMovement(arg2);
		}
		return false;
	}

	@Override
	public boolean buttonDown(Controller arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean buttonUp(Controller arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void connected(Controller arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected(Controller arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean povMoved(Controller arg0, int arg1, PovDirection arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return false;
	}

}
