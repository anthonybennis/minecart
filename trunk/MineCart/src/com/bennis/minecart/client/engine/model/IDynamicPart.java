package com.bennis.minecart.client.engine.model;

import com.google.gwt.canvas.client.Canvas;

/**
 * Any part of a game who's appearance might change.
 * 
 * @author abennis
 *
 */
public interface IDynamicPart 
{
	public void update();
	public void draw(Canvas canvas);
}
