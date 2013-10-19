package com.ladinc.playscape.core.collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ladinc.playscape.core.collision.CollisionInfo.CollisionObjectType;
import com.ladinc.playscape.core.objects.Warrior;

public class CollisionHelper implements ContactListener{

	private Sound warriorCollideSound;
	private Sound fall;
	
	public CollisionHelper(Sound collide, Sound fall)
	{
		this.warriorCollideSound = collide;
		this.fall = fall;
	}
	
	public CollisionHelper()
	{
	}
	
	
	
	@Override
	public void beginContact(Contact contact) 
	{
		Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        
        CollisionInfo bodyAInfo = getCollisionInfoFromFixture(fixtureA);
    	CollisionInfo bodyBInfo = getCollisionInfoFromFixture(fixtureB);

        
        if(bodyAInfo != null && bodyBInfo != null)
        {
        	
        	if(bodyAInfo.type == CollisionObjectType.player && bodyBInfo.type == CollisionObjectType.player)
        	{
        		warriorCollideIsDetected(bodyAInfo, bodyBInfo);
        	}
        	
        	if (bodyAInfo != null)
        	{
        		Gdx.app.log("beginContact", "Fixture A: " + bodyAInfo.text);
        	}
        }
        
        
        
        Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
		
	}
		
	private void warriorCollideIsDetected(CollisionInfo bodyA, CollisionInfo bodyB)
	{
		if (this.warriorCollideSound != null)
			this.warriorCollideSound.play(0.5f);
		
		bodyA.warrior.lastPersonToTouch = bodyB.warrior;
		bodyB.warrior.lastPersonToTouch = bodyA.warrior;
		
		Gdx.app.log("Collision Helper", "warrior collide");
	}

	@Override
	public void endContact(Contact contact) 
	{
		Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
		
        CollisionInfo bodyAInfo = getCollisionInfoFromFixture(fixtureA);
    	CollisionInfo bodyBInfo = getCollisionInfoFromFixture(fixtureB);
    	
    	if(bodyAInfo != null && bodyBInfo != null)
        {
    		if(bodyAInfo.type == CollisionObjectType.playerSensor)
    		{
    			if(bodyBInfo.type == CollisionObjectType.Arena)
    			{
    				handleFall(bodyAInfo.warrior);
    				Gdx.app.log("endContact", bodyAInfo.text);
    			}
    		}
    		if(bodyBInfo.type == CollisionObjectType.playerSensor)
    		{
    			if(bodyAInfo.type == CollisionObjectType.Arena)
    			{
    				handleFall(bodyBInfo.warrior);
    				
    				Gdx.app.log("endContact", bodyBInfo.text);
    			}
    		}
        }
        
	}
	
	private void handleFall(Warrior victim)
	{
		if (this.fall != null)
			this.fall.play(0.9f);
		
		victim.inPlay = false;
		
		int score = victim.score / 2;
		
		if (score < 1)
			score = 1;
		
		if (victim.lastPersonToTouch != null)
		{		
			victim.lastPersonToTouch.score = victim.lastPersonToTouch.score + score;
			
			Gdx.app.log("SCORE", "Warrior " + victim.lastPersonToTouch.warriorNumber + "Scored " + score + " points and now has " + victim.lastPersonToTouch.score);
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	private CollisionInfo getCollisionInfoFromFixture(Fixture fix)
	{	
		CollisionInfo colInfo = null;
		
		if(fix != null)
        {
			Body body = fix.getBody();
			
			if(body != null)
			{
				colInfo = (CollisionInfo) body.getUserData();
			}
        }
		
		return colInfo;
	}

}
