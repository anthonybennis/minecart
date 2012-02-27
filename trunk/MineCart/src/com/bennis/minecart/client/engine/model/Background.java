package com.bennis.minecart.client.engine.model;
import java.util.ArrayList;
import java.util.List;

import com.bennis.minecart.client.GUIConstants;
import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.logic.LocationImage;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.touch.client.Point;

/**
 * This class represents the background/Map.
 * @author abennis
 */
public class Background implements ISprite
{
	private static final int IMAGE_WIDTH = 128;
	/*
	 * TODO AB Refactor out BasicSprite class (image loading etc)
	 * TODO AB Refactor out ScrollManager
	 */
	private Point _location = new Point();
	private boolean _selected = false;
	private boolean _disposed = false;
	private ImageElement _backgroundTile01;
	private ImageLoader _imageLoader;
	private List<LocationImage> _tiledImages;
	
	public Background(ImageLoader imageLoader)
	{
		super();
		_imageLoader = imageLoader;
		this.loadImages(imageLoader);
		
	}
	
	/**
	 * Returns the number of tiles needed to fill the
	 * canvas.
	 * 
	 * @return WidthOfCanvas/widthOfTile
	 */
	private int calculateNumberOfTilesNeeded()
	{
		double numberOfTilesNeeded = GUIConstants.WIDTH/IMAGE_WIDTH;
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
		for (int i = 0; i < numberOfTiles; i++) 
		{
			locationImage = new LocationImage(_backgroundTile01);
			locationImage.setLocation(x, y);
			listOfImages.add(locationImage);
			x = x - IMAGE_WIDTH;
		}
		
		return listOfImages;
	}
	
	private void loadImages(ImageLoader imageLoader)
	{

		_backgroundTile01 = imageLoader.getImage("BackgroundTile01.jpg");
	}

	@Override
	public Layers getLayer() 
	{
		return Layers.BACKGROUND;
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
	public Point getLocation() 
	{
		return _location;
	}

	@Override
	public void setLocation(Point point) 
	{
		_location = point;
	}

	@Override
	public boolean isSelected() 
	{		
		return _selected;
	}

	@Override
	public void draw(Canvas canvas) 
	{		
		try
		{	
			/*
			 * Ensure image is loaded before painting anything
			 */
			if (_backgroundTile01 == null) 
			{
				_backgroundTile01 = _imageLoader.getImage("BackgroundTile01.jpg");
				_tiledImages = this.createImageList();
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
			for (LocationImage image:_tiledImages) 
			{
				x = image.getX();
				x = x - 1; // TODO AB Should we base this on time? Use a ScrollManager to synch?
				
				/*
				 * Reset image location if it's scrolled to the end
				 */
				if (x < (0 - IMAGE_WIDTH))
				{
					x = GUIConstants.WIDTH;
				}
				
				image.setX(x);
			}
		}
	}

	@Override
	public boolean isDisposed() 
	{
		return _disposed;
	}	
}