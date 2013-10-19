package com.ladinc.playscape.core.objects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.playscape.core.bel.BodyEditorLoader;
import com.ladinc.playscape.core.collision.CollisionInfo;
import com.ladinc.playscape.core.collision.CollisionInfo.CollisionObjectType;

public class Arena 
{
	public Sprite arenaImage;
	
	public Body body;
	
	private int nextStartingPos;
	
	public Arena(World world)
	{
		nextStartingPos = startingPosList.length;
		
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("playscapePBE.json"));
		arenaImage = getArenaSprite();
		arenaImage.setPosition(0, 0);
		
		BodyDef bd = new BodyDef();
	    bd.position.set(0, 0);
	    bd.type = BodyType.DynamicBody;
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		fixtureDef.isSensor=true;
		
		this.body = world.createBody(bd);
		
		loader.attachFixture(body, "Arena", fixtureDef, 192);
		
		this.body.setUserData(new CollisionInfo("", CollisionObjectType.Arena));
	}
	
	private Sprite getArenaSprite()
    {
    	Texture arenaTexture = new Texture(Gdx.files.internal("Background.png"));
    	
    	return new Sprite(arenaTexture);
    }
	
	public Vector2 getStartPos()
	{
		
		if(nextStartingPos + 1 >=  startingPosList.length)
		{
			nextStartingPos = 0;
		}
		else
		{
			nextStartingPos++;
		}
	    
	    return startingPosList[nextStartingPos].cpy();
	}
	
	private static float diff = 15f;
	private static float diagDiff = 12f;
	
	private static float centerX = 96f;
	private static float centerY = 50f;
	
	private static Vector2[] startingPosList = {
												new Vector2(centerX, centerY + diff), //Top
												new Vector2(centerX, centerY - diff), //Bot
												new Vector2(centerX - diff, centerY), //Left
												new Vector2(centerX + diff, centerY), // Right
												new Vector2(centerX - diagDiff, centerY + diagDiff), //Top Left
												new Vector2(centerX + diagDiff, centerY - diagDiff), //Bot Right
												new Vector2(centerX + diagDiff, centerY + diagDiff), //Top Right
												new Vector2(centerX - diagDiff, centerY - diagDiff), // Bot Left
												};
	
//	private static Vector2[] startingPosList = {
//		new Vector2(centerX, centerY + diff), //Top
//		};

}
