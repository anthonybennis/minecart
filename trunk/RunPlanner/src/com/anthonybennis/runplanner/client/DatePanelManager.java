package com.anthonybennis.runplanner.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.touch.TouchPanel;

/**
 * 
 * @author abennis
 */
public class DatePanelManager 
{
	private AnimationHelper _animationHelper;
	private Panel _dateViewPanel;
	private Panel _datePickerPanel;

	public DatePanelManager()
	{
		 _animationHelper = new AnimationHelper();
		 LayoutPanel containerPanel = this.createDatePanel();
		 _dateViewPanel = this.createDateViewerPanel();
		 _datePickerPanel = this.createDatePickerPanel();
		 
		 containerPanel.add(_dateViewPanel);
	}
	
	public LayoutPanel createDatePanel()
	{
		LayoutPanel datePanelContainer = new LayoutPanel();
		datePanelContainer.getElement().getStyle().setBackgroundImage("BlackWoodTexture.jpg");
		
		return datePanelContainer;
	}
	
	private Panel createDateViewerPanel()
	{
		TouchPanel touchPanel = new TouchPanel();
		touchPanel.getElement().getStyle().setBackgroundColor("red");
		
		/*
		 * Test
		 */
		Button button = new Button("Test Button");
		touchPanel.add(button);
		// End test
		
		TouchStartHandler touchStartHandler = new DatePickerClickHandler(); 
		touchPanel.addTouchStartHandler(touchStartHandler);
		touchPanel.add(_animationHelper);
		
		return touchPanel;
	}
	
	private Panel createDatePickerPanel()
	{
		TouchPanel touchPanel = new TouchPanel();
		touchPanel.getElement().getStyle().setBackgroundColor("blue");
		/*
		 * Test
		 */
		Button button = new Button("Test Button");
		touchPanel.add(button);
		// End test
		
		TouchStartHandler touchStartHandler = new DatePickerClickHandler(); 
		touchPanel.addTouchStartHandler(touchStartHandler);
		touchPanel.add(_animationHelper);
		
		return touchPanel;
	}
	
	class DatePickerClickHandler implements TouchStartHandler
	{	
		@Override
		public void onTouchStart(TouchStartEvent event) 
		{
			if (event.getSource() == _dateViewPanel)
			{
				_animationHelper.goTo(_datePickerPanel, Animation.FLIP);	
			}
			else
			{
				_animationHelper.goTo(_dateViewPanel, Animation.FLIP);
			}			
		}
	}
}
