package com.ladinc.playscape.core.objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.ladinc.playscape.core.assests.Art;
import com.ladinc.playscape.core.bel.BodyEditorLoader;
import com.ladinc.playscape.core.collision.CollisionInfo;
import com.ladinc.playscape.core.collision.CollisionInfo.CollisionObjectType;
import com.ladinc.playscape.core.controls.IControls;

public class Warrior {
	
	public int score;
	
	public Warrior lastPersonToTouch;
	
	private static int PIXELS_PER_METER = 10;
	private static float RESPAWN_DELAY = 2f; //3 seconds
	
	private float respawnCountdown;
	
	public Sprite warriorSprite;
	
	public Body body;
	public Body sensorBody;
	
	public Directions currentDirection;
	
	public int warriorNumber;
	
	public IControls controller;
	
	private float power;
	
	private Vector2 warriorOrigin;
	private Vector2 warriorPos;
	
	public boolean inPlay;
	public boolean bodiesDestroyed;
	public float spriteSize;
	
	private BodyEditorLoader loader;
	
	private float startX, startY;
	
	private World world;
	
	private Arena arena;
	
	public Warrior(World world, Arena arena, int number, IControls controller)
	{
		score = 0;
		
		loader = new BodyEditorLoader(Gdx.files.internal("playscapePBE.json"));
		this.warriorOrigin = loader.getOrigin("Warrior", 12.8f).cpy();
		
		this.arena = arena;
		
		if(number > 8)
			number = number % 8;
		this.warriorNumber = number;
		this.world = world;
		
		this.controller = controller;
		
		this.power = 800f;
		
		
		resetWarrior();
	}
	
	private void resetWarrior()
	{
		this.lastPersonToTouch = null;
		
		Vector2 startingPos = this.arena.getStartPos();
		
		startingPos = startingPos.sub(warriorOrigin);
		this.startX = startingPos.x;
		this.startY = startingPos.y;
		
		currentDirection = Directions.Down;
		spriteSize = 1f;
		this.inPlay = true;
		
		createBody();
	}
	
	private void createBody()
	{
		//Dynamic Body  
	    BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(this.startX, this.startY);  
	    this.body = world.createBody(bodyDef);  

	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
	    fixtureDef.friction = 0.3f;  
	    fixtureDef.restitution = 1f;
	     
	    this.body = world.createBody(bodyDef);
		
		loader.attachFixture(body, "Warrior", fixtureDef, 12.8f);
		//this.warriorOrigin = loader.getOrigin("Warrior", 12.8f).cpy();
		
		this.body.setUserData(new CollisionInfo("", CollisionObjectType.player, this));
		
		this.body.setLinearDamping(0.2f);
		
		addSensor(world);
		this.bodiesDestroyed = false;
		
	}
	
	private void addSensor(World world)
	{
		Vector2 warriorPos = this.body.getPosition().add(warriorOrigin);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(warriorPos);
		
		this.sensorBody = world.createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		fixtureDef.isSensor=true;
		
		CircleShape dynamicCircle = new CircleShape();  
	    dynamicCircle.setRadius(0.1f);
	    fixtureDef.shape = dynamicCircle;
	    fixtureDef.density = 0f;
	    
	    this.sensorBody.createFixture(fixtureDef);
	    
	    PrismaticJointDef jointdef=new PrismaticJointDef();
        jointdef.initialize(this.body, this.sensorBody, this.sensorBody.getWorldCenter(), new Vector2(1, 1));
        jointdef.enableLimit=true;
        jointdef.lowerTranslation=jointdef.upperTranslation=0;
	    world.createJoint(jointdef);
	    
	    this.sensorBody.setUserData(new CollisionInfo("Warrior " + this.warriorNumber + " fell", CollisionObjectType.playerSensor, this));
		
	}
	
	public void updateWarriorMovement(float delta)
	{
		if(this.inPlay)
		{
			float xMovement = this.controller.getXMovement();
			float yMovement = this.controller.getYMovement();
			
			
			
			Vector2 forceVector= new Vector2(this.power*xMovement, this.power*yMovement);
			Vector2 position= this.body.getWorldCenter();
			this.body.applyForce(this.body.getWorldVector(new Vector2(forceVector.x, forceVector.y)), position, true );
			
			calculateDirection(xMovement, yMovement);
			
			this.warriorPos = this.body.getPosition();
		}
		else
		{
			if(!bodiesDestroyed)
			{
				this.warriorPos = this.body.getPosition();
			
				this.world.destroyBody(this.body);
				this.world.destroyBody(this.sensorBody);
				bodiesDestroyed = true;
				respawnCountdown = RESPAWN_DELAY;
			}
			else
			{
				if(respawnCountdown >= 0)
				{
					respawnCountdown = respawnCountdown - delta;
				}
				else
				{
					resetWarrior();
				}
			}
		}
		
	}
	
	private void calculateDirection(float xMovement, float yMovement)
	{
		float xPower = xMovement;
		float yPower = yMovement;
		
		if (xMovement < 0)
			xPower = xMovement*(-1);
		
		if (yMovement < 0)
			yPower = yMovement*(-1); 
		
		if(xPower == 0 && yPower == 0)
		{
			//Dont change direction
			return;
		}
		

		//going right
		if(xPower/3 > yPower)
		{
			//X movement out weighs y over 3 to 1, ignoring y influences
			if(xMovement > 0)
			{
				this.currentDirection = Directions.Right;
			}
			else
			{
				this.currentDirection = Directions.Left;
			}
			
			return;
		}
		else if (yPower/3 > xPower)
		{
			//Y movement out weighs x over 3 to 1, ignoring x influences
			if(yMovement < 0)
			{
				this.currentDirection = Directions.Down;
			}
			else
			{
				this.currentDirection = Directions.Up;
			}
			
			return;
		}
		else
		{
			//its somewhere in the middle
			if(xMovement > 0)
			{
				//Right
				if(yMovement < 0)
				{
					this.currentDirection = Directions.DiagDownRight;
				}
				else
				{
					this.currentDirection = Directions.DiagUpRight;
				}
			}
			else
			{
				//Left
				if(yMovement < 0)
				{
					this.currentDirection = Directions.DiagDownLeft;
				}
				else
				{
					this.currentDirection = Directions.DiagUpLeft;
				}
			}
		}
		
		this.body.setLinearDamping(0.2f);
	}
	
	public void updateWarriorSprite(SpriteBatch sb)
	{
		if(!this.inPlay)
		{
			if(this.spriteSize > 0)
			{
				this.spriteSize = this.spriteSize -0.05f;
			}
			
		}
		drawWarriorSprite(sb);
	}
	
	private void drawTheSprite(Sprite sprite, SpriteBatch sb)
	{
		sprite.setPosition(warriorPos.x * PIXELS_PER_METER , warriorPos.y * PIXELS_PER_METER);
		sprite.setOrigin(warriorOrigin.x , warriorOrigin.y);
		sprite.setScale(this.spriteSize);
		sprite.draw(sb);
	}
	
	private void drawWarriorSprite(SpriteBatch sb)
    {
		drawTheSprite(Art.WarriorSprites.get(warriorNumber).get(currentDirection), sb);
		
    }
	
	public static enum Directions{Down, Up, Left, Right, DiagUpRight, DiagUpLeft, DiagDownLeft, DiagDownRight}

}


