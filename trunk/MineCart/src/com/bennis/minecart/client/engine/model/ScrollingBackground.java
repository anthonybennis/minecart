package com.bennis.minecart.client.engine.model;
import java.util.ArrayList;
import java.util.List;

import com.bennis.minecart.client.GUIConstants;
import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.logic.LocationImage;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;

/**
 * This class represents the background/Map.
 * @author abennis
 */
abstract public class ScrollingBackground extends BasicSprite
{
	/*
	 * TODO AB Refactor out ScrollManager?
	 */
	private List<LocationImage> _tiledImages;
	
	public ScrollingBackground(ImageLoader imageLoader)
	{
		super(Layers.BACKGROUND, imageLoader);
		this.loadImages();
	}
	
	/**
	 * Returns the number of tiles needed to fill the
	 * canvas.
	 * 
	 * @return WidthOfCanvas/widthOfTile
	 */
	private int calculateNumberOfTilesNeeded()
	{
		ImageElement image = this.getImageElements()[0];
		double numberOfTilesNeeded = GUIConstants.WIDTH/image.getWidth();
		long numberOfTilesNeededRounded = Math.round(numberOfTilesNeeded);
		numberOfTilesNeededRounded = numberOfTilesNeededRounded + 2;// Round up to cover full width
		return (int)numberOfTilesNeededRounded;
	}
	
	/**
	 * Create all Background tiles.
	 * @return List of all background tile images
	 */
	private List<LocationImage> createImageList()
	{
		List<LocationImage> listOfImages = new ArrayList<LocationImage>();
		int numberOfTiles = this.calculateNumberOfTilesNeeded();
		int x = GUIConstants.WIDTH;
		int y = 0;
		LocationImage locationImage;
		ImageElement image = this.getImageElements()[0];
		for (int i = 0; i < numberOfTiles; i++) 
		{
			locationImage = new LocationImage(image);
			locationImage.setLocation(x, y);
			listOfImages.add(locationImage);
			x = x - image.getWidth();
		}
		
		return listOfImages;
	}

	@Override
	public void handleCollision(ISprite collisionSprite) 
	{
		// Nothing to do here. Background doesn't collide with anything.
	}

	@Override
	public void dispose() 
	{
		/*
		 * TODO AB - Free any resources this may have.
		 */
	}
	

	@Override
	public void draw(Canvas canvas) 
	{		
		try
		{	
			/*
			 * Ensure image is loaded before painting anything
			 */
			if (!this.haveAllImagesLoaded()) 
			{
				this.loadImages();
				
				if (this.haveAllImagesLoaded()) // Check again... might still not be loaded.
				{
					_tiledImages = this.createImageList();
				}
			}
			else // Draw Tile
			{
				canvas.getContext2d().save();
				
				for (LocationImage image: _tiledImages) 
				{
					canvas.getContext2d().drawImage(image.getImage(),image.getX(), image.getY());	
				}
				
				canvas.getContext2d().restore();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void update() 
	{
		if (_tiledImages != null)
		{
			int x;
			ImageElement backgroundimage = this.getImageElements()[0];
			for (LocationImage image:_tiledImages) 
			{
				x = image.getX();
				x = x - 1; // TODO AB Should we base this on time? Use a ScrollManager to synch?
				
				/*
				 * Reset image location if it's scrolled to the end
				 */
				if (x < (0 - backgroundimage.getWidth()))
				{
					x = GUIConstants.WIDTH;
				}
				
				image.setX(x);
			}
		}
	}
}