package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.AGame;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Creates Button Panel for user input to game.
 *
 * TODO AB - Add listener to canvas and catch left/right inputs easier. Double press for jump?
 *
 * @author abennis
 */
public class ButtonPanel 
{
	public enum BUTTON_TYPE{LEFT, LEFT_JUMP,RIGHT, RIGHT_JUMP, PAUSE, EXIT};
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
		this.createButton(resources.leftButton(), BUTTON_TYPE.LEFT, flowPanel);
		
		/*
		 * Create LEFT Jump button
		 */
		this.createButton(resources.jumpButton(), BUTTON_TYPE.LEFT_JUMP, flowPanel);
		
		/*
		 * Create Pause Button
		 */
		this.createButton(resources.pauseButton(), BUTTON_TYPE.PAUSE, flowPanel);
		
		/*
		 * Create Exit button
		 */
		this.createButton(resources.pauseButton(), BUTTON_TYPE.EXIT, flowPanel);
		
		/*
		 * Create RIGHT JUMP button
		 */
		this.createButton(resources.jumpButton(), BUTTON_TYPE.RIGHT_JUMP, flowPanel);
		
		/*
		 * Create RIGHT button
		 */	
		this.createButton(resources.rightButton(), BUTTON_TYPE.RIGHT, flowPanel);
	}
	
	private void createButton(ImageResource imageResource, BUTTON_TYPE type, HorizontalPanel panel)
	{
		Image image = new Image(imageResource);
		PushButton button = new PushButton(image);
		button.setWidth(BUTTON_WIDTH);
		button.addClickHandler(new MineCartButtonListener(type));
		panel.add(button); 
	}
	
	class MineCartButtonListener implements ClickHandler
	{
		private BUTTON_TYPE _type;
		
		private MineCartButtonListener(BUTTON_TYPE type)
		{
			_type = type;
		}
		
		@Override
		public void onClick(ClickEvent event) 
		{
			InputEvent inputEvent = new InputEvent(event, _type.name());
			_game.setInput(inputEvent);
		}
	}
}
