/*******************************************************************************
 * Copyright (c) 2012 Anthony Bennis
 * All rights reserved. This program and the accompanying materials
 * are fully owned by Anthony Bennis and cannot be used for commercial projects without prior permission from same.
 * http://www.anthonybennis.com
 *
 * Contributors:
 * Anthony Bennis.
 *******************************************************************************/
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
