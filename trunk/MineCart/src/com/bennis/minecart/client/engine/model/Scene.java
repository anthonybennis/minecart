package com.bennis.minecart.client.engine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Data store for all ISprites. The Scene is aware of layers,
 * and stores ISprites in their appropriate layer.
 * @author Anthony
 *
 */
public class Scene 
{
	private List<ISprite> _glassLayer = new ArrayList<ISprite>(); // No collision detection in this layer.
	private List<ISprite> _frontLayer = new ArrayList<ISprite>();
	private List<ISprite> _middleLayer = new ArrayList<ISprite>();
	private List<ISprite> _backLayer = new ArrayList<ISprite>();
	private List<ISprite> _backgroundLayer = new ArrayList<ISprite>(); // No collision detection in this layer.
	
	public List<ISprite> getGlassLayer()
	{
		return _glassLayer;
	}
	
	public List<ISprite> getFrontLayer()
	{
		return _frontLayer;
	}
	
	public List<ISprite> getMiddleLayer()
	{
		return _middleLayer;
	}
	
	public List<ISprite> getBackLayer()
	{
		return _backLayer;
	}
	
	public List<ISprite> getBackgroundLayer()
	{
		return _backgroundLayer;
	}
	
	/**
	 * After creation, you should call this function to
	 * store the Srpite in a Scene.
	 * @param sprite
	 */
	public void storeSprite(ISprite sprite)
	{
		List<ISprite> layer;
		
		switch (sprite.getLayer()) 
		{
			case BACKGROUND:
			{	
				layer = _backgroundLayer;
				break;
			}
			case BACK:
			{	
				layer = _backLayer;
				break;
			}
			case MIDDLE:
			{	
				layer = _middleLayer;
				break;
			}
			case FRONT:
			{	
				layer = _frontLayer;
				break;
			}
			case GLASS:
			{	
				layer = _glassLayer;
				break;
			}
			default:
			{
				layer = _frontLayer;
				break;
			}
		}
		
		layer.add(sprite);
	}
}
