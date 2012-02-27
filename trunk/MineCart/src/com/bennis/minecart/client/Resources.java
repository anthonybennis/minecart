package com.bennis.minecart.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * API for all images used by Mine Cart.
 * @author Anthony
 *
 */
	public interface Resources extends ClientBundle
	{
		  @Source("images/BackgroundTile01.jpg")
		  ImageResource backgroundTile01();
		  
		  @Source("images/BackgroundTile02.jpg")
		  ImageResource backgroundTile02();
	}

