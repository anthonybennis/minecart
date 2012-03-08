package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.Playlist;

public class MineCartPlayList implements Playlist 
{

	@Override
	public String getBackingTrack(int level) 
	{
		return "audio/DST-Travel.ogg";
	}

}
