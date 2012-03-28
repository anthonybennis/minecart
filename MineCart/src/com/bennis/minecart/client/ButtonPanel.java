package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.AGame;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Creates Button Panel for user input to game.
 *
 * @author abennis
 */
public class ButtonPanel 
{
	public enum ButtonClickEventType{LEFT, LEFT_JUMP,RIGHT, RIGHT_JUMP, PAUSE, EXIT, MOUSE_UP};
	private final String BUTTON_WIDTH = "100px"; 
	private final AGame _game;
	
	public ButtonPanel(AGame game)
	{
		_game = game;
		
		HorizontalPanel flowPanel = new HorizontalPanel();
		RootPanel.get(GUIConstants.BUTTON_CONTAINER_NAME).add(flowPanel);
		flowPanel.setSize(GUIConstants.WIDTH_PX, "144px");
		Resources resources = GWT.create(Resources.class);
		/*
		 * Create LEFT button
		 */
		this.createButton(resources.leftButton(), ButtonClickEventType.LEFT, flowPanel);
		
		/*
		 * Create LEFT Jump button
		 */
		this.createButton(resources.jumpButton(), ButtonClickEventType.LEFT_JUMP, flowPanel);
		
		/*
		 * Create Pause Button
		 */
		this.createButton(resources.pauseButton(), ButtonClickEventType.PAUSE, flowPanel);
		
		/*
		 * Create Exit button
		 */
		this.createButton(resources.pauseButton(), ButtonClickEventType.EXIT, flowPanel);
		
		/*
		 * Create RIGHT JUMP button
		 */
		this.createButton(resources.jumpButton(), ButtonClickEventType.RIGHT_JUMP, flowPanel);
		
		/*
		 * Create RIGHT button
		 */	
		this.createButton(resources.rightButton(), ButtonClickEventType.RIGHT, flowPanel);
	}
	
	private void createButton(ImageResource imageResource, ButtonClickEventType type, HorizontalPanel panel)
	{
		Image image = new Image(imageResource);
		PushButton button = new PushButton(image);
		button.setWidth(BUTTON_WIDTH);
		button.addMouseDownHandler(new MineCardMouseDownHandler(type));
		button.addMouseUpHandler(new MineCartMouseUpHandler());
		// TODO AB - Add TouchHandler (Swipe Left, Swipe Right, Swipe Up)
		
		panel.add(button); 
	}
	
	class MineCartButtonListener implements ClickHandler
	{
		private ButtonClickEventType _type;
		
		private MineCartButtonListener(ButtonClickEventType type)
		{
			_type = type;
		}
		
		@Override
		public void onClick(ClickEvent event) 
		{			
			InputEvent inputEvent = new InputEvent(_type.name());
			_game.setInput(inputEvent);
		}
	}
	
	/**
	 * 
	 * @author abennis
	 *
	 */
	class MineCardMouseDownHandler implements MouseDownHandler
	{
		private ButtonClickEventType _type;
		
		private MineCardMouseDownHandler(ButtonClickEventType type)
		{
			_type = type;
		}
		
		@Override
		public void onMouseDown(MouseDownEvent event) 
		{
			InputEvent inputEvent = new InputEvent(_type.name());
			_game.setInput(inputEvent);
		}
	}
	
	/**
	 * 
	 * @author abennis
	 *
	 */
	class MineCartMouseUpHandler implements MouseUpHandler
	{
		@Override
		public void onMouseUp(MouseUpEvent event) 
		{
			InputEvent inputEvent = new InputEvent(ButtonClickEventType.MOUSE_UP.name());
			_game.setInput(inputEvent);
		}
	}
}
