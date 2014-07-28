package com.ladinc.playscape.core.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.playscape.core.PlayScape;
import com.ladinc.playscape.core.assests.Art;
import com.ladinc.playscape.core.utilities.MusicHelper;

public class TitleScreen implements Screen{
	
	private int screenWidth;
    private int screenHeight;
    private float worldWidth;
    private float worldHeight;
    private static int PIXELS_PER_METER = 10;
    
    private static float BG_CHANGE_TIME = 3f;
    private float timeLeftToChangeBG;
    private Vector2 center;
    
    private static String  MESSAGE = "To Begin, Press";
    
    private World world;
    
    private SpriteBatch spriteBatch;
    
    private PlayScape game;
    
    private OrthographicCamera camera;
    
    
    private int currentBGNum;
    
    private Sprite bgSprite;
    
    public TitleScreen(PlayScape game)
    {
    	this.game = game;
    	
    	this.screenWidth = 1920;
    	this.screenHeight = 1080;
    	
    	this.worldWidth = this.screenWidth / PIXELS_PER_METER;
		this.worldHeight = this.screenHeight / PIXELS_PER_METER;
		
		this.center = new Vector2(worldWidth/2, worldHeight/2);
    	
    	this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, this.screenWidth, this.screenHeight);
        
        currentBGNum = 0;
        this.bgSprite = Art.SplashScreenSprites.get(currentBGNum);
       
     }
    
    private void changeBG()
    {
    	if (3 == currentBGNum)
    		this.game.setScreen(this.game.arenaScreen);
    	else
    		currentBGNum++;
    	
    	this.bgSprite = Art.SplashScreenSprites.get(currentBGNum);
    	
    	timeLeftToChangeBG = BG_CHANGE_TIME;
    	
    }

	@Override
	public void render(float delta) {
		
		if(timeLeftToChangeBG >= 0)
		{
			timeLeftToChangeBG = timeLeftToChangeBG - delta;
		}
		else
		{
			changeBG();
		}
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
        world.clearForces();
        
        this.spriteBatch.begin();
        
    	Gdx.gl.glClearColor(0, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.bgSprite.draw(spriteBatch);
		
		this.spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {

		world = new World(new Vector2(0.0f, 0.0f), true);
		 world.setContactListener(this.game.colHelper);
		 
		 spriteBatch = new SpriteBatch();
		 
		 timeLeftToChangeBG = BG_CHANGE_TIME;
		 
		 if(this.game.isOuya)
			 this.game.music = MusicHelper.playMenuMusic(this.game.music);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

