package com.ladinc.playscape.core.controls;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.ladinc.mcp.interfaces.MCPContorllersListener;
import com.ladinc.playscape.core.PlayScape;

public class MCPListenerClient implements MCPContorllersListener
{
	
	private PlayScape game;
	
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean upPressed;
	private boolean downPressed;
	
	private HashMap<String, GamePadControls> controls;
	
	private final float TRIGGER_DEADZONE = 0.50f;

	public MCPListenerClient(PlayScape game)
	{
		this.game = game;
		
		controls = new HashMap<String, GamePadControls>();
	}
	
	private GamePadControls getController(String contId)
	{
		
		if(this.controls.containsKey(contId))
		{
			return this.controls.get(contId);
		}
		else
		{	
			GamePadControls g = new GamePadControls(0.25f, this.game);
			//If we get here the controler is already active
			g.active = true;
			g.contName = contId;
			this.controls.put(contId, g);
			this.game.controls.add(g);
			
			Gdx.app.log("MCP Listener", "adding:" + contId);
			
			return g;
		}
	}
	
	@Override
	public void analogMove(int controllerId, String analogCode, float x, float y) {

		System.out.println("Recieved Analog Event");
		System.out.println(String.valueOf(x) + ", "+ String.valueOf(y));
		
		String contId = String.valueOf(controllerId);	
		GamePadControls g = getController(contId);
		
		g.setAnalogMovment(x, y);
		
	}

	@Override
	public void buttonUp(int arg0, String arg1) {
		System.out.println("Recieved Button Up " + arg1);
		
		String contId = String.valueOf(arg0);	
		GamePadControls g = getController(contId);
		
		if(arg1.contains("keyRight"))
		{
			this.rightPressed = false;
			
			if(!this.leftPressed)
				g.setXMovement(0f);
		}
		else if(arg1.contains("keyLeft"))
		{
			this.leftPressed = false;
			
			if(!this.rightPressed)
				g.setXMovement(0f);
		}
		
		if(arg1.contains("keyUp"))
		{
			
			this.upPressed = false;
			
			if(!this.downPressed)
				g.setYMovement(0f);
		}
		else if(arg1.contains("keyDown"))
		{
			this.downPressed = false;
			
			if(!this.upPressed)
				g.setYMovement(0f);
		}
	}

	@Override
	public void buttonDown(int arg0, String arg1) {
		System.out.println("Recieved Button Down " + arg1);
		
		String contId = String.valueOf(arg0);	
		GamePadControls g = getController(contId);
		
		if(arg1.contains("keyRight"))
		{
			this.rightPressed = true;
			g.setXMovement(1f);
		}
		else if(arg1.contains("keyLeft"))
		{
			this.leftPressed = true;
			g.setXMovement(-1f);
		}
		
		if(arg1.contains("keyUp"))
		{
			this.upPressed = true;
			g.setYMovement(-1f);
		}
		else if(arg1.contains("keyDown"))
		{
			this.downPressed = true;
			g.setYMovement(1f);
		}
	}

	@Override
	public void orientation(int controllerId, float gamma, float beta,
			float alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pass(Map<String, String> header, Map<String, String> parms,
			Map<String, String> files) {
		// TODO Auto-generated method stub
		
	}
	

}
