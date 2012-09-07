package com.anthonybennis.runplanner.client.handlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.PushButton;

/**
 * Handler for navigating the Calander widget.
 * @author abennis
 */
public class ButtonNavigationHandler implements ClickHandler 
{
	private DeckPanel _deckPanel;
	private boolean _moveForward;
	private PushButton _leftButton;
	private PushButton _rightButton;
	
	/**
	 * 
	 */
	public ButtonNavigationHandler(DeckPanel deckPanel, boolean moveForward, PushButton leftButton, PushButton rightButton)
	{
		_moveForward = moveForward;
		_deckPanel = deckPanel;	
		_leftButton = leftButton;
		_rightButton = rightButton;
	}
	
	@Override
	public void onClick(ClickEvent event) 
	{
		int numerOfMonthPanels = _deckPanel.getWidgetCount();
		int visbleMonthIndex = _deckPanel.getVisibleWidget();
		
		if (_moveForward)
		{
			this.moveForward(numerOfMonthPanels,visbleMonthIndex);
		}
		else
		{
			this.moveBackward(numerOfMonthPanels,visbleMonthIndex);
		}		
	}
	
	/**
	 * 
	 * @param numberOfMonthPanels
	 * @param currentShownIndex
	 */
	private void moveForward(int numberOfMonthPanels, int currentShownIndex)
	{
		int indexToShow = -1;
		
		if (currentShownIndex < (numberOfMonthPanels -1))
		{
			indexToShow++;
			
			if (!_leftButton.isEnabled())
			{
				_leftButton.setEnabled(true);
			}
		}
		else
		{
			/*
			 * Disable button
			 */
			_rightButton.setEnabled(false);
		}
		
		_deckPanel.showWidget(indexToShow);
	}
	
	/**
	 * 
	 * @param numberOfMonthPanels
	 * @param currentShownIndex
	 */
	private void moveBackward(int numberOfMonthPanels, int currentShownIndex)
	{
		int indexToShow = -1;
		
		if (currentShownIndex > 0)
		{
			indexToShow--;
			
			if (!_rightButton.isEnabled())
			{
				_rightButton.setEnabled(true);
			}
		}
		else
		{
			/*
			 * Disable button
			 */
			_leftButton.setEnabled(false);
		}
		
		_deckPanel.showWidget(indexToShow);
	}
}
