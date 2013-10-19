package com.ladinc.playscape.core.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicHelper {
	
	public static Music playMenuMusic(Music music)
	{
		return MusicHelper.playMusic(music, "Music/TITLE MUSIC.mp3");
		//return MusicHelper.playMusic(music, "Music/arenamusic.wav");
	}
	
	public static Music playGameMusic(Music music)
	{		
		return MusicHelper.playMusic(music, "Music/arenamusic.wav");
	}


	public static Music playMusic(Music music, String musicFile)
	{
		MusicHelper.stopMusicSafely(music);
		music = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
		music.setLooping(true);
		music.setVolume(0.2f);
		music.play();
		
		return music;
	}

	public static void stopMusicSafely(Music music)
	{
		if(music != null)
		{
			if(music.isPlaying())
			{
				music.stop();
			}
		}
	}



}
