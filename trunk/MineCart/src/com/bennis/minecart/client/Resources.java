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
		  @Source("images/Left.png")
		  ImageResource leftButton();
		  
		  @Source("images/Right.png")
		  ImageResource rightButton();
		  
		  @Source("images/Jump.png")
		  ImageResource jumpButton();
		  
		  @Source("images/Pause.png")
		  ImageResource pauseButton();
	}

