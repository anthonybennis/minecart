/*******************************************************************************
 * Copyright (c) 2012 Anthony Bennis
 * All rights reserved. This program and the accompanying materials
 * are fully owned by Anthony Bennis and cannot be used for commercial projects without prior permission from same.
 * http://www.anthonybennis.com
 *
 * Contributors:
 * Anthony Bennis.
 *******************************************************************************/
package com.bennis.minecart.client.engine.logic;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This class loads all images needed.
 * @author abennis
 *
 */
public class ImageLoader 
{
	private Map<String, ImageElement> _allImages = new HashMap<String,ImageElement>();
	
	public ImageElement getImage(String imagePath)
	{
		ImageElement image = _allImages.get(imagePath);
		
		if (image == null)
		{
			this.loadImage(imagePath);
			image = _allImages.get(imagePath);
		}
		
		return image;
	}
	
	/**
	 * Loads an image.
	 * @param imagePath
	 * @return
	 */
	private void loadImage(final String imagePath)
	{
		final Image image = new Image(imagePath);
		
		image.addLoadHandler(new LoadHandler() {
		      public void onLoad(LoadEvent event) {
		        // once image is loaded, put into Map and make available for future.
		        ImageElement imageElement = (ImageElement)image.getElement().cast();
		        _allImages.put(imagePath, imageElement);
		        }
		      });
		
		/*
		 * Image must be on page to fire load
		 */
		image.setVisible(false);
		RootPanel.get().add(image);
	}
}
