package com.ladinc.playscape.core.assests;

import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.ladinc.playscape.core.objects.Warrior.Directions;

public class Art {
	public static HashMap<String, Texture> textureTable = new HashMap<String, Texture>();
	
	public static HashMap<String, Sprite> spriteTable = new HashMap<String, Sprite>();
	
	public static ArrayList<Sprite> SplashScreenSprites;
	
	public static ArrayList<Sprite> BackgroundSprites;
	
	public static ArrayList<HashMap<Directions, Sprite>> WarriorSprites;
	
	public static ArrayList<Sprite> ScoreIconSprites;
	
	public static void load()
	{
		loadTextures();
	}
	
	private static void loadTextures()
	{
		
		Gdx.app.debug("Art",
				"LoadTextures - Begin");

		loadSplashScreenSprites();
		loadBackgroundSprites();
		loadWarriorSprites();
		
		Gdx.app.debug("Art",
				"LoadTextures - End");
		
	}
	
	private static void loadSplashScreenSprites()
	{
		SplashScreenSprites = new ArrayList<Sprite>();
		
		SplashScreenSprites.add(new Sprite(new Texture(Gdx.files.internal("title/Back1.jpg"))));
		SplashScreenSprites.add(new Sprite(new Texture(Gdx.files.internal("title/Back2.jpg"))));
	    SplashScreenSprites.add(new Sprite(new Texture(Gdx.files.internal("title/Back3.jpg"))));
	    SplashScreenSprites.add(new Sprite(new Texture(Gdx.files.internal("title/Back4.jpg"))));
	}
	
	private static void loadBackgroundSprites()
	{
		BackgroundSprites = new ArrayList<Sprite>();

        BackgroundSprites.add( new Sprite(new Texture(Gdx.files.internal("background/1.jpg"))));
        BackgroundSprites.add(new Sprite(new Texture(Gdx.files.internal("background/2.jpg"))));
        BackgroundSprites.add(new Sprite(new Texture(Gdx.files.internal("background/3.jpg"))));
        BackgroundSprites.add(new Sprite(new Texture(Gdx.files.internal("background/7.jpg"))));
        BackgroundSprites.add(new Sprite(new Texture(Gdx.files.internal("background/8.jpg"))));
        BackgroundSprites.add(new Sprite(new Texture(Gdx.files.internal("background/9.jpg"))));
	}
	
	private static void loadWarriorSprites()
	{
		WarriorSprites = new ArrayList<HashMap<Directions,Sprite>>();
		ScoreIconSprites = new ArrayList<Sprite>();
		
		int maxSprites = 8;
		for(int i = 1; i <= maxSprites; i++ )
		{
			HashMap<Directions, Sprite> tempMap = new HashMap<Directions, Sprite>();
			
			
			tempMap.put(Directions.Up, new Sprite(new Texture(Gdx.files.internal("warriors/" + i + "/Up.png"))));
			tempMap.put(Directions.Down, new Sprite(new Texture(Gdx.files.internal("warriors/" + i + "/Down.png"))));
			
			tempMap.put(Directions.Left, new Sprite(new Texture(Gdx.files.internal("warriors/" + i + "/Left.png"))));
			tempMap.put(Directions.Right, new Sprite(new Texture(Gdx.files.internal("warriors/" + i + "/Left.png"))));
			tempMap.get(Directions.Right).flip(true, false);
			
			tempMap.put(Directions.DiagDownLeft, new Sprite(new Texture(Gdx.files.internal("warriors/" + i + "/DiagDown.png"))));
			tempMap.get(Directions.DiagDownLeft).flip(true, false);
			tempMap.put(Directions.DiagDownRight, new Sprite(new Texture(Gdx.files.internal("warriors/" + i + "/DiagDown.png"))));
			
			
			tempMap.put(Directions.DiagUpLeft, new Sprite(new Texture(Gdx.files.internal("warriors/" + i + "/DiagUp.png"))));
			tempMap.put(Directions.DiagUpRight, new Sprite(new Texture(Gdx.files.internal("warriors/" + i + "/DiagUp.png"))));
			tempMap.get(Directions.DiagUpRight).flip(true, false);
			
			WarriorSprites.add(tempMap);
			
			Sprite temp = new Sprite(new Texture(Gdx.files.internal("warriors/" + i + "/Down.png")));
			temp.setSize(64f, 64f);
			ScoreIconSprites.add(temp);
			
		}
	}
	
	public static void updateSprite(Sprite sprite, SpriteBatch spriteBatch, int PIXELS_PER_METER, Body body)
	{
		if(sprite != null && spriteBatch != null && body != null)
		{
			setSpritePosition(sprite, PIXELS_PER_METER, body);
	
			sprite.draw(spriteBatch);
		}
	}
	
	public static void setSpritePosition(Sprite sprite, int PIXELS_PER_METER, Body body)
	{
		
		sprite.setPosition(PIXELS_PER_METER * body.getPosition().x - sprite.getWidth()/2,
				PIXELS_PER_METER * body.getPosition().y  - sprite.getHeight()/2);
		sprite.setRotation((MathUtils.radiansToDegrees * body.getAngle()));
	}
	
	public static Sprite getSprite(String str)
	{
		Gdx.app.debug("Art",
				"getSprite - Start");
		
		if(!spriteTable.containsKey(str))
		{
			spriteTable.put(str, new Sprite(Art.textureTable.get(str)));
			Gdx.app.debug("Art",
					"getSprite - Loading " + str);
		}
		
		return spriteTable.get(str);
	}
	
}