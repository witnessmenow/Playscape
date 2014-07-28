package com.ladinc.playscape.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Font 
{
	public static enum FontsName { SWIS_721_50_BOLD, SWIS_721_32_BOLD}
	
	public static BitmapFont loadFont(FontsName fontsName)
	{
		switch(fontsName)
		{
			case SWIS_721_50_BOLD:
				return new BitmapFont(Gdx.files.internal("Fonts/Swis-721-50-Bold.fnt"), Gdx.files.internal("Fonts/Swis-721-50-Bold.png"), false);
			case SWIS_721_32_BOLD:
				return new BitmapFont(Gdx.files.internal("Fonts/Swis-721-32-Bold.fnt"), Gdx.files.internal("Fonts/Swis-721-32-Bold.png"), false);

			default:
				return null;
		}
	}
}
