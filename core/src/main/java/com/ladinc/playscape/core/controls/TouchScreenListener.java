package com.ladinc.playscape.core.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class TouchScreenListener implements InputProcessor {
	
	public TouchPadControls controls;
	private final int screenWidth;
	private final int screenHeight;
	
	public TouchScreenListener() {
		this.controls = new TouchPadControls();
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
	}
	
	private void processTouch(int screenX, int screenY, int pointer,
			boolean touched, boolean moveEvent)
	{

	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		Gdx.app.error(
				"TouchScreenListener",
				"touchDown: screenX=" + String.valueOf(screenX) + ", screenY="
						+ String.valueOf(screenY) + ", pointer="
						+ String.valueOf(pointer) + ", button="
						+ String.valueOf(button));
		
		processTouch(screenX, screenY, pointer, true, false);
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		Gdx.app.error(
				"TouchScreenListener",
				"touchUp: screenX=" + String.valueOf(screenX) + ", screenY="
						+ String.valueOf(screenY) + ", pointer="
						+ String.valueOf(pointer) + ", button="
						+ String.valueOf(button));
		
		processTouch(screenX, screenY, pointer, false, false);
		
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		Gdx.app.debug("TouchScreenListener",
				"touchDragged: screenX=" + String.valueOf(screenX)
						+ ", screenY=" + String.valueOf(screenY) + ", pointer="
						+ String.valueOf(pointer));
		
		processTouch(screenX, screenY, pointer, true, true);
		
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
