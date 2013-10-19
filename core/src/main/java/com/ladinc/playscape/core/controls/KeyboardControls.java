package com.ladinc.playscape.core.controls;

import com.badlogic.gdx.Gdx;
import com.ladinc.playscape.core.PlayScape;

public class KeyboardControls implements IControls{

	public int controlUp;
	public int controlDown;
	public int controlLeft;
	public int controlRight;
	
	public int attack;
	
	public int pause;
	
	public boolean active;
	private PlayScape game;
	
	
	public KeyboardControls(int controlUp, int controlDown, int controlLeft,
			int controlRight, int attack, int pause, PlayScape game) {
		super();
		this.controlUp = controlUp;
		this.controlDown = controlDown;
		this.controlLeft = controlLeft;
		this.controlRight = controlRight;
		this.attack = attack;
		this.pause = pause;
		
		this.game = game;
		
		this.active = false;
	}
	
	private void activateController()
	{
		active = true;
		
		this.game.inActiveControls.remove(this);
		this.game.controls.add(this);
	}
	
	@Override
	public float getXMovement() {
		
		if(Gdx.input.isKeyPressed(this.controlLeft))
		{
			if(!active)
				activateController();
			return -1f;
		}
		else if (Gdx.input.isKeyPressed(this.controlRight))
		{
			if(!active)
				activateController();
			return 1f;
		}
		else
		{
			return 0f;
		}
		
	}

	@Override
	public float getYMovement() {
		if(Gdx.input.isKeyPressed(this.controlUp))
		{
			if(!active)
				activateController();
			return 1f;
		}
		else if (Gdx.input.isKeyPressed(this.controlDown))
		{
			if(!active)
				activateController();
			return -1f;
		}
		else
		{
			return 0f;
		}
	}

	@Override
	public boolean getAttackPressed() {
		// TODO Auto-generated method stub
		active = true;
		return Gdx.input.isKeyPressed(this.attack);
	}

}
