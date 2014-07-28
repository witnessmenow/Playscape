package com.ladinc.playscape.core;

import java.util.ArrayList;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ladinc.mcp.MCP;
import com.ladinc.playscape.core.assets.Assets;
import com.ladinc.playscape.core.collision.CollisionHelper;
import com.ladinc.playscape.core.controls.IControls;
import com.ladinc.playscape.core.controls.KeyboardControls;
import com.ladinc.playscape.core.controls.MCPListenerClient;
import com.ladinc.playscape.core.controls.OuyaListener;
import com.ladinc.playscape.core.screens.ArenaScreen;
import com.ladinc.playscape.core.screens.TitleScreen;
import com.ladinc.playscape.core.screens.WinScreen;

public class PlayScape extends Game {
	
	public ArenaScreen arenaScreen;
	public TitleScreen titleScreen;
	public WinScreen winScreen;

	public boolean isOuya;
	
	private MCP moreControllers;
	
	public ArrayList<IControls> inActiveControls = new ArrayList<IControls>();
	public ArrayList<IControls> controls = new ArrayList<IControls>();
	
	public CollisionHelper colHelper;
	
	public Music music;
	
	public int winner;
	
	public String ipAddr;
	@Override
	public void create () 
	{
		this.isOuya = Ouya.runningOnOuya;
		setUpMCP();
		
		Assets.load();
		
		createScreens();
		setUpControls();
		
		//this.colHelper = new CollisionHelper(Gdx.audio.newSound(Gdx.files.internal("SoundFX/THUD.mp3")));
		
		if(this.isOuya)
			this.colHelper = new CollisionHelper(Gdx.audio.newSound(Gdx.files.internal("SoundFX/THUD.mp3")), Gdx.audio.newSound(Gdx.files.internal("SoundFX/fall.mp3")));
		else
			this.colHelper = new CollisionHelper();
		
		setScreen(titleScreen);
		
	}
	
	private void setUpMCP()
	{
		this.moreControllers = MCP.tryCreateAndStartMCPWithPort(8888);
		
		 ipAddr = moreControllers.getAddressForClients();
		 Gdx.app.log("Main-MCP", "Connection Address: " + ipAddr);
		 
	}
	
	private void createScreens()
	{
		arenaScreen = new ArenaScreen(this);
		this.titleScreen =  new TitleScreen(this);
		this.winScreen =  new WinScreen(this);
	}
	
	private void setUpControls()
	{
		this.controls.clear();
		this.inActiveControls.clear();
    	
        for (Controller controller : Controllers.getControllers()) {
            Gdx.app.log("Main", controller.getName());
            addControllerToList(controller);
            

        }
		
		inActiveControls.add(new KeyboardControls(Input.Keys.DPAD_UP, Input.Keys.DPAD_DOWN, Input.Keys.DPAD_LEFT, Input.Keys.DPAD_RIGHT, Input.Keys.SPACE, Input.Keys.ESCAPE, this));
		inActiveControls.add(new KeyboardControls(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D, Input.Keys.SPACE, Input.Keys.ESCAPE, this));
		
		this.moreControllers.addMCPListener(new MCPListenerClient(this));
	}
	
	public void addControllerToList(Controller controller)
    {
        if(this.isOuya)
        {
        	Gdx.app.log("Main", "Added Listener for Ouya Controller");
        	
        	OuyaListener listener = new OuyaListener(this);
            controller.addListener(listener);
            
            inActiveControls.add(listener.controls);
        }
    }
}
