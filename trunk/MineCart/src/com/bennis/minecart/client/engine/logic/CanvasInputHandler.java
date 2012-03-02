package com.bennis.minecart.client.engine.logic;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.GestureStartEvent;
import com.google.gwt.event.dom.client.GestureStartHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;

/**
 * This class adds all input handlers to a given canvas.
 * These events are then delegated to interested Sprites
 * via the Sprite Manager.
 * 
 * @author abennis
 *
 */
public class CanvasInputHandler 
{
	private static final int OUTSIDE_RANGE = -200;
	private int _mouseX;
	private int _mouseY;
	
	public CanvasInputHandler()
	{
		
	}
	
	protected int getMouseX()
	{
		return _mouseX;
	}
	
	protected int getMouseY()
	{
		return _mouseY;
	}
	
	public void setupInputHandlers(final Canvas canvas)
	{
		canvas.addMouseMoveHandler(new MouseMoveHandler() {
		      public void onMouseMove(MouseMoveEvent event) {
		    	  _mouseX = event.getRelativeX(canvas.getElement());
		    	  _mouseY = event.getRelativeY(canvas.getElement());
		      }
		    });

		
		    canvas.addMouseOutHandler(new MouseOutHandler() {
		      public void onMouseOut(MouseOutEvent event) {
		    	  _mouseX = OUTSIDE_RANGE;
		    	  _mouseY = OUTSIDE_RANGE;
		      }
		    });
		    
		    canvas.addMouseDownHandler(new MouseDownHandler() {
				
				@Override
				public void onMouseDown(MouseDownEvent event) 
				{
					_mouseX = event.getRelativeX(canvas.getElement());
			    	_mouseY = event.getRelativeX(canvas.getElement());
				}
			});

		    canvas.addMouseUpHandler(new MouseUpHandler() {
				
				@Override
				public void onMouseUp(MouseUpEvent event) {
					_mouseX = event.getRelativeX(canvas.getElement());
			    	_mouseY = event.getRelativeX(canvas.getElement());
				}
			});
		    
		    canvas.addTouchMoveHandler(new TouchMoveHandler() {
		      public void onTouchMove(TouchMoveEvent event) {
		        event.preventDefault();
		        if (event.getTouches().length() > 0) {
		          Touch touch = event.getTouches().get(0);
		          _mouseX = touch.getRelativeX(canvas.getElement());
		          _mouseY = touch.getRelativeY(canvas.getElement());
		        }
		        event.preventDefault();
		      }
		    });

		    
		    canvas.addTouchEndHandler(new TouchEndHandler() {
		      public void onTouchEnd(TouchEndEvent event) {
		        event.preventDefault();

		        _mouseX = OUTSIDE_RANGE;
		        _mouseY = OUTSIDE_RANGE;
		      }
		    });

		    
		    canvas.addGestureStartHandler(new GestureStartHandler() {
		      public void onGestureStart(GestureStartEvent event) {
		        event.preventDefault();
		      }
		    });
	}

}
