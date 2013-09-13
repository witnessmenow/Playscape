package com.ladinc.playscape.html;

import com.ladinc.playscape.core.PlayScape;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class PlayScapeHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new PlayScape();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
