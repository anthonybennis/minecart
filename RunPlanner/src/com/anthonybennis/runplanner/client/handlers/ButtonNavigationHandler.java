package com.anthonybennis.runplanner.client.handlers;

import java.util.List;

import com.anthonybennis.runplanner.client.Audio;
import com.anthonybennis.runplanner.client.controls.MessageBox;
import com.anthonybennis.runplanner.client.controls.calendar.MonthPanel;
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
	private List<MonthPanel> _monthPanels;
	
	/**
	 * 
	 */
	public ButtonNavigationHandler(DeckPanel deckPanel, List<MonthPanel> monthPanels, boolean moveForward, PushButton leftButton, PushButton rightButton)
	{
		_moveForward = moveForward;
		_monthPanels = monthPanels;
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
		
		if (currentShownIndex < (numberOfMonthPanels -1)
				&& numberOfMonthPanels >1)
		{
			indexToShow = currentShownIndex + 1;
			
			Audio.playButtonClick();
			_deckPanel.showWidget(indexToShow);
			_monthPanels.get(indexToShow).updateMonthNameLabel();
			
		}
	}
	
	/**
	 * 
	 * @param numberOfMonthPanels
	 * @param currentShownIndex
	 */
	private void moveBackward(int numberOfMonthPanels, int currentShownIndex)
	{
		int indexToShow = -1;
		
		if (currentShownIndex > 0 && numberOfMonthPanels >1)
		{
			indexToShow = (currentShownIndex -1);
			
			Audio.playButtonClick();
			_deckPanel.showWidget(indexToShow);
			_monthPanels.get(indexToShow).updateMonthNameLabel();
		}
	}
}
