package com.bennis.minecart.client.engine.model;

import com.bennis.minecart.client.engine.logic.InputEvent;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.google.gwt.canvas.client.Canvas;

/**
 * This class represents a textural counter.
 * The counter can be incremented or decremented, it's font
 * changed and/or an image set.
 * 
 * It's designed for use as a Score counter, or Life value counter.
 * 
 * @author abennis
 *
 */
public class GamePointCounterSprite implements ISprite 
{
	private Vector _location;
	private double _value;
	private String _counterName;
	private boolean _isDisposed;
	private Rectangle _bounds;
	private String _fontColour = "white";
	private String _font = "bold 16px sans-serif";
	
	/**
	 * Constructor.
	 */
	public GamePointCounterSprite(String name, double x, double y)
	{
		_location = new Vector(x,y);
		_counterName = name;
		_value = 0;
		_isDisposed = false;
		_bounds = new Rectangle();
	}
	
	@Override
	public Vector getLocation() 
	{
		return _location;
	}

	@Override
	public void setLocation(Vector location) 
	{
		_location = location;
	}

	@Override
	public boolean isSelected() 
	{
		return false;
	}

	@Override
	public void draw(Canvas canvas) 
	{
		canvas.getContext2d().setFillStyle(_fontColour);
		canvas.getContext2d().setFont(_font); 
		
		canvas.getContext2d().fillText(_counterName + " " + _value, this.getLocation().x, this.getLocation().y);
	}

	@Override
	public void update(InputEvent event) 
	{
		// Nothing done here on user input.
	}

	@Override
	public void handleCollision(ISprite collisionSprite, Collision collisionType) 
	{
		// You can't collide with this sprite.
	}

	@Override
	public Collision getCollisionType(ISprite sprite) 
	{
		return Collision.NONE;
	}

	@Override
	public Layers getLayer() 
	{
		return Layers.GLASS;
	}

	@Override
	public void dispose() 
	{
		// TODO AB Dispose font, graphics?
		_isDisposed = true;
	}

	@Override
	public boolean isDisposed() 
	{
		return _isDisposed;
	}

	@Override
	public Type getType() 
	{
		return Type.DECORATION;
	}

	@Override
	public Rectangle getBounds() 
	{
		return _bounds;
	}
	
	/**
	 * Update the value of this point
	 * @param value
	 */
	public void incrementValue(double increment)
	{
		_value = _value + increment;
	}
	
	public void decrementValue(double decreaseValue)
	{
		_value = _value - decreaseValue;
	}
	
	public String getName()
	{
		return _counterName;
	}
	
	public double getValue()
	{
		return _value;
	}
}