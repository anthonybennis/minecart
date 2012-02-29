package com.bennis.minecart.client.engine.model;

import java.util.List;

import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.google.gwt.canvas.client.Canvas;

/**
 * This class represents the ground a sprite can walk on.
 * It's an array of lines. This can be one straight line,
 * or it can be a number of line segments joined together
 * to form one platform.
 * 
 * @author abennis
 */
abstract public class Platform implements ISprite 
{
	private Vector _location = new Vector();
	private boolean _selected = false;
	private boolean _disposed = false;
	List<Line> _lineSegments; // TODO AB Use 2D int array instead of objs.
	
	public Platform()
	{
		_lineSegments = this.createLineSegments();
	}
	
	@Override
	public Vector getLocation() 
	{
		return _location;
	}

	@Override
	public void setLocation(Vector vector) 
	{
		_location = vector;
	}
	
	public void setLocation(double x, double y) 
	{
		_location.x = x;
		_location.y = y;
	}

	@Override
	public boolean isSelected() 
	{		
		return _selected;
	}
	
	@Override
	public boolean isDisposed() 
	{
		return _disposed;
	}
	
	@Override
	public void dispose() 
	{
		_disposed = true;	
	}

	@Override
	public void draw(Canvas canvas) 
	{
		for (Line line: _lineSegments) 
		{
			canvas.getContext2d().beginPath();
			canvas.getContext2d().setStrokeStyle("white");
			canvas.getContext2d().setLineWidth(3);
			canvas.getContext2d().moveTo(line.getX(), line.getY());
			canvas.getContext2d().lineTo(line.getX1(), line.getY1());
			canvas.getContext2d().closePath();
			canvas.getContext2d().stroke();
		}
	}

	@Override
	public void update() 
	{
		// TODO AB - Update it's location.
	}

	@Override
	public void handleCollision(ISprite collisionSprite) 
	{
		/*
		 * Nothing to do here. Platform doesn't collide with anything,
		 * Spites collide with this.
		 */
	}

	@Override
	public Layers getLayer() 
	{
		return Layers.MIDDLE;
	}
	
	/**
	 * Create a List<Line>. These
	 * will then be used to draw lines, and be used by
	 * other Sprites to find if they contact the platform.
	 * 
	 * @return List<Vector>
	 */
	abstract public List<Line> createLineSegments();

	@Override
	public boolean doSpritesCollide() 
	{
		// TODO AB
		return false;
	}
	
	@Override
	public Type getType() 
	{
		return Type.PLATFORM;
	}

	@Override
	public Rectangle getBounds() 
	{
		/*
		 * A Platform does not have any bounds
		 */
		return new Rectangle();
	}
}