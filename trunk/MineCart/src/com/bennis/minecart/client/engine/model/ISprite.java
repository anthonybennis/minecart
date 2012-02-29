package com.bennis.minecart.client.engine.model;

import com.google.gwt.canvas.client.Canvas;


/**
 * Interface for anything that can be any object that appears in a Scene. A Sprite
 * can be manipulated by a user or AI control.
 * @author ABennis
 */
public interface ISprite extends IDynamicPart
{
	/*
	 * By defining types, we hope to optimise collision detection.
	 */
	public enum Type{OBSTACLE, GOODIE, USER_MOVEABLE, ENEMY, DECORATION, PLATFORM};
	/**
	 * Find out the current Position of this Sprite.
	 * @return Point GWT Point location object.
	 */
	public Vector getLocation();
	/**
	 * Inform the Sprite of it's new location
	 * @param point
	 */
	public void setLocation(Vector location);
	
	/**
	 * A flag to determine a Sprites selection state.
	 * @return
	 */
	public boolean isSelected();
	
	/**
	 * Render this Sprite.
	 * We have a back and front context to facilitate double buffering.
	 * 
	 * @param context
	 */
	public void draw(Canvas canvas);
	
	/**
	 * Update this Sprites model data.
	 */
	public void update();
	
	/*
	 * Every ISprite type will handle obstacles differently.
	 * Examples: <Comments here to help design implementation>
	 * 
	 * Obstacle: Prevents colliding sprite from moving in one direction.
	 * Goodie: Increases points, disposes
	 * Enemy: Removes a life or kills user sprite
	 * Decoration: Does nothing
	 * Platform: Sprites falling down wards are stopped. Sprites can jump through (up).
	 */
	public void handleCollision(ISprite collisionSprite);	
	
	public boolean doSpritesCollide();
	
	/**
	 * A layer dictates a Sprites paint order. Those at the back are painted
	 * first. Those in the from painted last. A Z order in a way.
	 * @return
	 */
	public Layer.Layers getLayer();
	
	public void dispose();
	
	public boolean isDisposed();
	
	public Type getType();
	
	public Rectangle getBounds();
}