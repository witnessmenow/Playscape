package com.ladinc.playscape.core.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ladinc.playscape.core.PlayScape;
import com.ladinc.playscape.core.assets.Art;
import com.ladinc.playscape.core.assets.Font;
import com.ladinc.playscape.core.assets.Font.FontsName;
import com.ladinc.playscape.core.controls.IControls;
import com.ladinc.playscape.core.objects.Arena;
import com.ladinc.playscape.core.objects.Warrior;
import com.ladinc.playscape.core.utilities.MusicHelper;

public class ArenaScreen implements Screen 
{
	
	private static float BG_CHANGE_TIME = 1f;
	private static float GAME_TIME = 3 * 60f;
	//private static float GAME_TIME = 10f;
	
	private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    /**
     * This is the main box2d "container" object. All bodies will be loaded in
     * this object and will be simulated through calls to this object.
     */
    private World world;
    
    private Box2DDebugRenderer debugRenderer;
	
	private PlayScape game;
	
	private Vector2 center;
	private int screenWidth;
    private int screenHeight;
    private float worldWidth;
    private float worldHeight;
    private static int PIXELS_PER_METER = 10;      //how many pixels in a meter
    
    private float timeLeftToChangeBG;
    private float gameTimeLeft;
    
    private Arena arena;
    
    private BitmapFont bitmapFont;
    private BitmapFont smallerFont;
    
    public List<Warrior> warriorsList;
    
    private Sprite bgSprite;
    
    private int currentBGNum;
    
    private int[] bgOrder = {0,1,2,2,1,0,3,4,5,5,4,3};

    public ArenaScreen(PlayScape game)
    {
    	this.game = game;
    	
    	this.screenWidth = 1920;
    	this.screenHeight = 1080;
    	
    	this.worldHeight = this.screenHeight / PIXELS_PER_METER;
    	this.worldWidth = this.screenWidth / PIXELS_PER_METER;
    	
    	this.center = new Vector2(worldWidth / 2, worldHeight / 2);
    	
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, this.screenWidth, this.screenHeight);
        
        this.debugRenderer = new Box2DDebugRenderer();
        
        currentBGNum = 0;
        this.bgSprite = Art.BackgroundSprites.get(bgOrder[currentBGNum]);
        
        bitmapFont = Font.loadFont(FontsName.SWIS_721_50_BOLD);
        smallerFont = Font.loadFont(FontsName.SWIS_721_32_BOLD);

    }
    
    private void changeBG()
    {
    	if (bgOrder.length - 1 == currentBGNum)
    		currentBGNum = 0;
    	else
    		currentBGNum++;
    	
    	this.bgSprite = Art.BackgroundSprites.get(bgOrder[currentBGNum]);
    	
    	timeLeftToChangeBG = BG_CHANGE_TIME;
    	
    }
    
	@Override
	public void render(float delta) {
		
		checkForNewActivePlayers();
		checkForNewPlayers();
		
		if(timeLeftToChangeBG >= 0)
		{
			timeLeftToChangeBG = timeLeftToChangeBG - delta;
		}
		else
		{
			changeBG();
		}
		
		if(gameTimeLeft >= 0)
		{
			gameTimeLeft = gameTimeLeft - delta;
		}
		else
		{
			handleWin();
		}
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
        world.clearForces();
        
        for(Warrior w: warriorsList)
        {
        	w.updateWarriorMovement(delta);
        }
		
		this.spriteBatch.begin();
		
		renderSprites(this.spriteBatch);
		
		this.spriteBatch.end();
		
		//debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,PIXELS_PER_METER,PIXELS_PER_METER));
		
	}
	
	private void handleWin()
	{
		int currentHighScore = 0;
		this.game.winner = 1;
		
		for(Warrior w : this.warriorsList)
		{
			if(w.score > currentHighScore)
			{
				this.game.winner = w.warriorNumber;
				currentHighScore = w.score;
			}
		}
		
		
		this.game.setScreen(this.game.winScreen);
	}
	
	private void checkForNewActivePlayers()
	{
		ArrayList<IControls> inactive = (ArrayList<IControls>) this.game.inActiveControls.clone();
		
		for (IControls ic : inactive)
		{
			ic.getXMovement();
			ic.getYMovement();
		}
	}
	
	private void renderSprites(SpriteBatch spriteBatch)
	{
		Gdx.gl.glClearColor(0, 0f, 0f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    this.bgSprite.draw(spriteBatch);
	    
	    this.arena.arenaImage.draw(spriteBatch);
	    
	    drawWarriors(spriteBatch);
	    drawScores(spriteBatch);
	    drawTimer();
	
	}
	
	//Warriors need to be sorted by which one is closest to the bottom so his sprite overlays others
	private void drawWarriors(SpriteBatch spriteBatch)
	{
		int c,d, n;
        Warrior swap;
        
        n = warriorsList.size();
        
        Object[] array = warriorsList.toArray(); 
        
        for (c = 0; c < ( n - 1 ); c++) 
		{
		    for (d = 0; d < n - c - 1; d++) 
		    {
		    	
			    Warrior a = (Warrior)array[d];
			    Warrior b = (Warrior)array[d+1];
		    	
				if (a.body.getPosition().y < b.body.getPosition().y) /* For descending order use < */
				{
					swap       = (Warrior) array[d];
					array[d]   = array[d+1];
					array[d+1] = swap;
				}
			}
		}
        
        for(Object w: array)
        {
        	Warrior each = (Warrior)w;
        	each.updateWarriorSprite(spriteBatch);
        }
	}
	 
	private void drawScores(SpriteBatch spriteBatch)
	{
		int c,d, n;
        Warrior swap;
        
        n = warriorsList.size();
        
        Object[] array = warriorsList.toArray(); 
        
        for (c = 0; c < ( n - 1 ); c++) 
		{
		    for (d = 0; d < n - c - 1; d++) 
		    {
		    	
			    Warrior a = (Warrior)array[d];
			    Warrior b = (Warrior)array[d+1];
		    	
				if (a.score < b.score) /* For descending order use < */
				{
					swap       = (Warrior) array[d];
					array[d]   = array[d+1];
					array[d+1] = swap;
				}
			}
		}
        
        bitmapFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        
        for(int i = 0; i < array.length; i++)
        {
        	Warrior each = (Warrior)array[i];
        	each.updateWarriorSprite(spriteBatch);
        	Art.ScoreIconSprites.get(each.warriorNumber).setPosition(this.screenWidth/14, 930f - (70f * i));
        	Art.ScoreIconSprites.get(each.warriorNumber).draw(spriteBatch);
        	
        	String tempScore = String.valueOf(each.score);
        	
        	bitmapFont.draw(spriteBatch, tempScore,
        			this.screenWidth/14 + 100f,
        			980f - (70f * i));
        }
	}
	
	private void drawTimer()
	{
		bitmapFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		int minutes = (int) gameTimeLeft / 60;
		int seconds = (int) gameTimeLeft % 60;
		
		String secondPrepend = (seconds < 10) ? "0" : "";
		
		String timeLeft =  minutes + ":" + secondPrepend + seconds;
		
		
		//Taking away the 30f as it doesnt seem like the monkey is centered on the screen ...
		bitmapFont.draw(spriteBatch, timeLeft,
				(screenWidth / 2) - 30f- bitmapFont.getBounds(timeLeft).width / 2,
				screenHeight - 55);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		 world = new World(new Vector2(0.0f, 0.0f), true);
		 world.setContactListener(this.game.colHelper);
		 arena = new Arena(world);
		 
		 gameTimeLeft = GAME_TIME;
		 
		 spriteBatch = new SpriteBatch();
		 
		 warriorsList = new ArrayList<Warrior>();
		 
		 checkForNewPlayers();
		 
		 timeLeftToChangeBG = BG_CHANGE_TIME;
		 
		 if(this.game.isOuya)
			 this.game.music = MusicHelper.playGameMusic(this.game.music);
		
	}
	
	private void checkForNewPlayers()
	{
		int nextPlayerNumber = this.warriorsList.size();
		
		if(this.game.controls.size() != this.warriorsList.size())
		{			
			for(IControls ic : this.game.controls)
			{
				boolean controllerHasPlayer = false;
				for(Warrior w: warriorsList)
				{
					 if(w.controller == ic)
						 controllerHasPlayer = true;
					 
				 }
				if(!controllerHasPlayer)
				{
					warriorsList.add(new Warrior(world, this.arena, nextPlayerNumber, ic));
					nextPlayerNumber++;
				}
	        }
			
		}
		
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
