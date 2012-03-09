package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.AGame;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
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
	private enum BUTTON_TYPE{LEFT, LEFT_JUMP,RIGHT, RIGHT_JUMP, PAUSE, EXIT};
	private final String BUTTON_WIDTH = "0px"; 
	private final String BUTTON_HEIGHT = "0px";
	private final AGame _game;
	
	public ButtonPanel(AGame game)
	{
		_game = game;
		
		AbsolutePanel flowPanel = new AbsolutePanel();
		RootPanel.get(GUIConstants.BUTTON_CONTAINER_NAME).add(flowPanel);
		flowPanel.setSize(GUIConstants.WIDTH_PX, "144px");
		
		/*
		 * Create LEFT button
		 */
		Resources resources = GWT.create(Resources.class);
		Image image = new Image(resources.leftButton());
		PushButton leftButton = new PushButton(image);
		leftButton.setWidth(BUTTON_WIDTH);
		leftButton.setHeight(BUTTON_HEIGHT);
		leftButton.addClickHandler(new MineCartButtonListener(BUTTON_TYPE.LEFT));
		int x = 0;
		flowPanel.add(leftButton, x, 0);
		
		/*
		 * Create LEFT Jump button
		 */
		image = new Image(resources.jumpButton());
		PushButton leftJumpButton = new PushButton(image);
		leftJumpButton.addClickHandler(new MineCartButtonListener(BUTTON_TYPE.LEFT_JUMP));
		leftJumpButton.setWidth(BUTTON_WIDTH);
		leftJumpButton.setHeight(BUTTON_HEIGHT);
		x = x + 162;
		flowPanel.add(leftJumpButton, x, 0);
		
		/*
		 * Create Pause Button
		 */
		image = new Image(resources.pauseButton());
		PushButton pauseButton = new PushButton(image);
		pauseButton.addClickHandler(new MineCartButtonListener(BUTTON_TYPE.RIGHT));
		pauseButton.setWidth(BUTTON_WIDTH);
		pauseButton.setHeight(BUTTON_HEIGHT);
		x = x + 162;
		flowPanel.add(pauseButton, x, 0);
		
		/*
		 * Create Exit button
		 */
		image = new Image(resources.pauseButton());
		PushButton exitButton = new PushButton(image); 
		exitButton.addClickHandler(new MineCartButtonListener(BUTTON_TYPE.RIGHT));
		exitButton.setWidth(BUTTON_WIDTH);
		exitButton.setHeight(BUTTON_HEIGHT);
		x = x + 162;
		flowPanel.add(exitButton, x, 0);
		
		/*
		 * Create RIGHT JUMP button
		 */
		image = new Image(resources.jumpButton());
		PushButton rightJumpButton = new PushButton(image); 
		rightJumpButton.addClickHandler(new MineCartButtonListener(BUTTON_TYPE.RIGHT));
		rightJumpButton.setWidth(BUTTON_WIDTH);
		rightJumpButton.setHeight(BUTTON_HEIGHT);
		x = x + 162;
		flowPanel.add(rightJumpButton, x, 0);
		
		/*
		 * Create RIGHT button
		 */	
		image = new Image(resources.rightButton());
		PushButton rightButton = new PushButton(image);
		rightButton.addClickHandler(new MineCartButtonListener(BUTTON_TYPE.RIGHT));
		rightButton.setWidth(BUTTON_WIDTH);
		rightButton.setHeight(BUTTON_HEIGHT);
		x = x + 162;
		flowPanel.add(rightButton, x, 0);
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
