package com.ladinc.playscape.core.collision;

import com.ladinc.playscape.core.objects.Warrior;

public class CollisionInfo {
	
	public String text;
	public CollisionObjectType type;
	
	public Warrior warrior;
	
	
	public CollisionInfo(String text, CollisionObjectType type)
	{
		this.text = text;		
		this.type = type;
	}
	
	public CollisionInfo(String text, CollisionObjectType type, Warrior warrior)
	{
		this.text = text;		
		this.type = type;
		this.warrior = warrior;
	}
	
	public static enum CollisionObjectType{Arena, playerSensor, player};

}
