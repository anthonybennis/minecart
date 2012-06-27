package com.anthonybennis.runplanner.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DistancePanelManager 
{
	/*
	 * Distance enum
	 * Note: Internally we store metric, but we support showing Imperial on the UI.
	 */
	protected enum DISTANCE{FIVE_KM,TEN_KM,TWNETY_ONE_KM,FORTY_TWO_KM,CUSTOM};
	protected enum DISTANCE_UNIT{METRIC,IMPERIAL};
	/*
	 * Enabled Metric Button Image urls
	 */
	private static final String FIVE_KM_ENABLED_IMAGE_URL= "5km.png";
	private static final String TEN_KM_ENABLED_IMAGE_URL= "10km.png";
	private static final String TWENTY_ONE_KM_ENABLED_IMAGE_URL= "21km.png";
	private static final String FORTY_TWO_KM_ENABLED_IMAGE_URL= "42km.png";
	/*
	 * Disabled Metric Button Image urls
	 */
	private static final String FIVE_KM_DISABLED_IMAGE_URL= "5kmGrey.png";
	private static final String TEN_KM_DISABLED_IMAGE_URL= "10kmGrey.png";
	private static final String TWENTY_ONE_KM_DISABLED_IMAGE_URL= "21kmGrey.png";
	private static final String FORTY_TWO_KM_DISABLED_IMAGE_URL= "42kmGrey.png";
	
	
	protected DISTANCE _userDefinedDistance;
	private ImageButtonWrapper[] _imageButtonWrappers = new ImageButtonWrapper[0];

	public DistancePanelManager()
	{
			
	}
	
	protected Panel createDistancePanel()
	{
		VerticalPanel distancePanel = new VerticalPanel();
		
		Label distanceLabel = new Label("Target Distance:");
		distanceLabel.getElement().getStyle().setFontSize(25.0, Unit.PX);
		distancePanel.add(distanceLabel);
		distancePanel.setSpacing(25);
		
		if (this.getDistanceUnits() == DISTANCE_UNIT.METRIC)
		{
			_imageButtonWrappers = this.createMetricButtons();
		}
		else
		{
			_imageButtonWrappers = this.createImperialButtons();
		}
		
		for (ImageButtonWrapper image : _imageButtonWrappers) 
		{
			image.getImage().addClickHandler(new DistanceButtonClickHandler());
			distancePanel.add(image.getImage());
		}
		
		return distancePanel;
	}
	
	private ImageButtonWrapper[] createMetricButtons()
	{
		ImageButtonWrapper[] images = new ImageButtonWrapper[4];
		
		images[0] = new ImageButtonWrapper(DISTANCE_UNIT.METRIC, DISTANCE.FIVE_KM, FIVE_KM_ENABLED_IMAGE_URL,FIVE_KM_DISABLED_IMAGE_URL);
		images[1] = new ImageButtonWrapper(DISTANCE_UNIT.METRIC,DISTANCE.TEN_KM,TEN_KM_ENABLED_IMAGE_URL,TEN_KM_DISABLED_IMAGE_URL);
		images[2] = new ImageButtonWrapper(DISTANCE_UNIT.METRIC,DISTANCE.TWNETY_ONE_KM,TWENTY_ONE_KM_ENABLED_IMAGE_URL,TWENTY_ONE_KM_DISABLED_IMAGE_URL);
		images[3] = new ImageButtonWrapper(DISTANCE_UNIT.METRIC,DISTANCE.FORTY_TWO_KM,FORTY_TWO_KM_ENABLED_IMAGE_URL,FORTY_TWO_KM_DISABLED_IMAGE_URL);
		
		/*
		 * Get button to highlight.
		 */
		DISTANCE userSetDistance = this.getUserDistance();
		
		/*
		 * Disable all by default, and only highlight if the user has pressed
		 * a distance button.
		 */
		this.highlightDistanceButton(userSetDistance);
		
		return images;
	}
	
	private ImageButtonWrapper[] createImperialButtons()
	{
		ImageButtonWrapper[] images = new ImageButtonWrapper[4];
		
		/*
		 * TODO AB Use mile images.
		 */
		images[0] = new ImageButtonWrapper(DISTANCE_UNIT.METRIC, DISTANCE.FIVE_KM, FIVE_KM_ENABLED_IMAGE_URL,FIVE_KM_DISABLED_IMAGE_URL);
		images[1] = new ImageButtonWrapper(DISTANCE_UNIT.METRIC,DISTANCE.TEN_KM,TEN_KM_DISABLED_IMAGE_URL,TEN_KM_DISABLED_IMAGE_URL);
		images[2] = new ImageButtonWrapper(DISTANCE_UNIT.METRIC,DISTANCE.TWNETY_ONE_KM,TWENTY_ONE_KM_DISABLED_IMAGE_URL,TWENTY_ONE_KM_DISABLED_IMAGE_URL);
		images[3] = new ImageButtonWrapper(DISTANCE_UNIT.METRIC,DISTANCE.FORTY_TWO_KM,FORTY_TWO_KM_DISABLED_IMAGE_URL,FORTY_TWO_KM_DISABLED_IMAGE_URL);
		
		/*
		 * Get button to highlight.
		 */
		DISTANCE userSetDistance = this.getUserDistance();
		
		/*
		 * Disable all by default, and only highlight if the user has pressed
		 * a distance button.
		 */
		this.highlightDistanceButton(userSetDistance);
		
		return images;
	}
	
	/**
	 * Highlights a Distance button if it matches the distance param.
	 * @param distance
	 */
	private void highlightDistanceButton(DISTANCE distance)
	{
		_imageButtonWrappers[0].disable();
		_imageButtonWrappers[1].disable();
		_imageButtonWrappers[2].disable();
		_imageButtonWrappers[3].disable();
		
		switch (distance) 
		{
			case FIVE_KM:
			{	
				_imageButtonWrappers[0].enable();
				break;
			}
			case TEN_KM:
			{	
				_imageButtonWrappers[1].enable();
				break;
			}
			case TWNETY_ONE_KM: 
			{	
				_imageButtonWrappers[2].enable();
				break;
			}
			case FORTY_TWO_KM:
			{	
				_imageButtonWrappers[3].enable();
				break;
			}
			default: // Custom
			{
				// All Image buttons are disabled.
				break;
			}
		}
	}
	
	/*
	 * TODO AB get distance from persisted preferences
	 */
	private DISTANCE getUserDistance()
	{
		DISTANCE distance = DISTANCE.FIVE_KM; // Default
		
		return distance;
	}
	
	/*
	 *  TODO AB Get user preffered distance unit.
	 */
	private DISTANCE_UNIT getDistanceUnits()
	{
		return DISTANCE_UNIT.METRIC;
	}
	
	protected DISTANCE getUserDefinedDistance() 
	{
		return _userDefinedDistance;
	}

	protected void setUserDefinedDistance(DISTANCE userDefinedDistance) 
	{
		this._userDefinedDistance = userDefinedDistance;
	}
	
	class DistanceButtonClickHandler implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) 
		{
			ImageButtonWrapper wrapper = (ImageButtonWrapper)event.getSource();
			_userDefinedDistance = wrapper.getDistance();
			highlightDistanceButton(_userDefinedDistance);
		}	
	}
}
