/*******************************************************************************
 * Copyright (c) 2012 Anthony Bennis
 * All rights reserved. This program and the accompanying materials
 * are fully owned by Anthony Bennis and cannot be used for commercial projects without prior permission from same.
 * http://www.anthonybennis.com
 *
 * Contributors:
 * Anthony Bennis.
 *******************************************************************************/
package com.anthonybennis.runplanner.client;

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
 */
public class ImageLoader 
{
	private Map<String, ImageElement> _allImageElements = new HashMap<String,ImageElement>();
	private Map<String, Image> _allImages = new HashMap<String,Image>();
	
	private static ImageLoader _imageLoader;
	
	
	private ImageLoader()
	{
		
	}
	
	public static ImageLoader getInstance()
	{
		if (_imageLoader == null)
		{
			_imageLoader = new ImageLoader();
		}
		
		return _imageLoader;
	}
	
	public ImageElement getImageElement(String imagePath)
	{
		ImageElement image = _allImageElements.get(imagePath);
		
		if (image == null)
		{
			this.loadImage(imagePath);
			image = _allImageElements.get(imagePath);
		}
		
		return image;
	}
	
	public Image getImage(String imagePath)
	{
		Image image = _allImages.get(imagePath);
		
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
	public void loadImage(final String imagePath)
	{
		final Image image = new Image(imagePath);
		
		image.addLoadHandler(new LoadHandler() {
		      public void onLoad(LoadEvent event) {
		        // once image is loaded, put into Map and make available for future.
		        ImageElement imageElement = (ImageElement)image.getElement().cast();
		        _allImageElements.put(imagePath, imageElement);
		        _allImages.put(imagePath, image);
		        }
		      });
		
		/*
		 * Image must be on page to fire load
		 */
		image.setVisible(false);
		RootPanel.get().add(image);
	}
	
	/*
	 * TODO Test that this methods removes "flicker" when
	 * pressing button with image for first time.
	 */
	public static void loadAllResourceImages()
	{
		Resources.INSTANCE.get5kmEnabledButtonImage();
		Resources.INSTANCE.get5kmDisabledButtonImage();
		Resources.INSTANCE.get10kmDisabledButtonImage();
		Resources.INSTANCE.get10kmEnabledButtonImage();
		Resources.INSTANCE.get21kmEnabledButtonImage();
		Resources.INSTANCE.get2kmDisabledButtonImage();
		Resources.INSTANCE.get42kmEnabledButtonImage();
		Resources.INSTANCE.get42kmDisabledButtonImage();
		Resources.INSTANCE.getBeginnerButtonImage();
		Resources.INSTANCE.getBeginnerGreyButtonImage();
		Resources.INSTANCE.getIntermediateButtonImage();
		Resources.INSTANCE.getIntermediateGreyButtonImage();
	}
}
