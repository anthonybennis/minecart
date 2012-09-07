package com.anthonybennis.runplanner.client;

import com.anthonybennis.runplanner.client.storage.Persistance;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class DistancePanelManager 
{
	/*
	 * Distance enum
	 * Note: Internally we store metric, but we support showing Imperial on the UI.
	 */
	public enum DISTANCE{FIVE_KM,TEN_KM,TWNETY_ONE_KM,FORTY_TWO_KM,CUSTOM};
	protected enum DISTANCE_UNIT{METRIC,IMPERIAL};
	/*
	 * Enabled Metric Button Image urls
	 */
//	private static final String FIVE_KM_ENABLED_IMAGE_URL= "images/5km.png";
//	private static final String TEN_KM_ENABLED_IMAGE_URL= "images/10km.png";
//	private static final String TWENTY_ONE_KM_ENABLED_IMAGE_URL= "images/21km.png";
//	private static final String FORTY_TWO_KM_ENABLED_IMAGE_URL= "images/42km.png";
//	/*
//	 * Disabled Metric Button Image urls
//	 */
//	private static final String FIVE_KM_DISABLED_IMAGE_URL= "images/5kmGrey.png";
//	private static final String TEN_KM_DISABLED_IMAGE_URL= "images/10kmGrey.png";
//	private static final String TWENTY_ONE_KM_DISABLED_IMAGE_URL= "images/21kmGrey.png";
//	private static final String FORTY_TWO_KM_DISABLED_IMAGE_URL= "images/42kmGrey.png";
	
	
	protected DISTANCE _userDefinedDistance;
	private DistanceButton[] _imageButtonWrappers = new DistanceButton[0];

	public DistancePanelManager()
	{
			
	}
	
	protected Panel createDistancePanel()
	{
		VerticalPanel distancePanel = new VerticalPanel();
		distancePanel.setSpacing(15);
		
		if (this.getDistanceUnits() == DISTANCE_UNIT.METRIC)
		{
			_imageButtonWrappers = this.createMetricButtons();
		}
		else
		{
			_imageButtonWrappers = this.createImperialButtons();
		}
		
		/*
		 * Highlight/Select button
		 */
		DISTANCE userSetDistance = this.getUserDistance();
		this.highlightDistanceButton(userSetDistance);
		
		/*
		 * Add Click Handler to each button
		 */
		for (DistanceButton image : _imageButtonWrappers) 
		{
			image.getImage().addClickHandler(new DistanceButtonClickHandler(image));
			distancePanel.add(image.getImage());
		}
		
		/*
		 * TODO AB - FUTURE ENHANCEMENT: Imperial/Metric Toggle
		 */

		return distancePanel;
	}
	
	private DistanceButton[] createMetricButtons()
	{
		DistanceButton[] images = new DistanceButton[4];
		
		images[0] = new DistanceButton(DISTANCE_UNIT.METRIC, DISTANCE.FIVE_KM, Resources.INSTANCE.get5kmEnabledButtonImage(),Resources.INSTANCE.get5kmDisabledButtonImage());
		images[1] = new DistanceButton(DISTANCE_UNIT.METRIC,DISTANCE.TEN_KM,Resources.INSTANCE.get10kmEnabledButtonImage(),Resources.INSTANCE.get10kmDisabledButtonImage());
		images[2] = new DistanceButton(DISTANCE_UNIT.METRIC,DISTANCE.TWNETY_ONE_KM,Resources.INSTANCE.get21kmEnabledButtonImage(),Resources.INSTANCE.get2kmDisabledButtonImage());
		images[3] = new DistanceButton(DISTANCE_UNIT.METRIC,DISTANCE.FORTY_TWO_KM,Resources.INSTANCE.get42kmEnabledButtonImage(),Resources.INSTANCE.get42kmDisabledButtonImage());
		
		return images;
	}
	
	private DistanceButton[] createImperialButtons()
	{
		DistanceButton[] images = new DistanceButton[4];
		
		/*
		 * TODO FUTURE ENHANCEMENT Use mile images.
		 */
		images[0] = new DistanceButton(DISTANCE_UNIT.METRIC, DISTANCE.FIVE_KM, Resources.INSTANCE.get5kmEnabledButtonImage(),Resources.INSTANCE.get5kmDisabledButtonImage());
		images[1] = new DistanceButton(DISTANCE_UNIT.METRIC,DISTANCE.TEN_KM,Resources.INSTANCE.get10kmEnabledButtonImage(),Resources.INSTANCE.get10kmDisabledButtonImage());
		images[2] = new DistanceButton(DISTANCE_UNIT.METRIC,DISTANCE.TWNETY_ONE_KM,Resources.INSTANCE.get21kmEnabledButtonImage(),Resources.INSTANCE.get2kmDisabledButtonImage());
		images[3] = new DistanceButton(DISTANCE_UNIT.METRIC,DISTANCE.FORTY_TWO_KM,Resources.INSTANCE.get42kmEnabledButtonImage(),Resources.INSTANCE.get42kmDisabledButtonImage());
		
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
	

	/**
	 * 
	 * @return
	 */
	private DISTANCE getUserDistance()
	{
		DISTANCE distance = null;
		
		String userpersistedTargetDistance = Persistance.get(Persistance.TARGET_DISTANCE);
		
		if (userpersistedTargetDistance != null)
		{
			distance = DistancePanelManager.convertPreferenceStringToDistance(userpersistedTargetDistance);
		}
		else
		{
			distance = DISTANCE.FIVE_KM; // Default
		}
		
		return distance;
	}
	
	/*
	 *  TODO FUTURE ENHANCEMENT Get user preffered distance unit.
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
	
	/**
	 * 
	 * @author Anthony
	 *
	 */
	class DistanceButtonClickHandler implements ClickHandler
	{
		private DistanceButton _wrapper;
		
		private DistanceButtonClickHandler(DistanceButton wrapper)
		{
			_wrapper = wrapper;
		}
		
		@Override
		public void onClick(ClickEvent event) 
		{
			Audio.playButtonClick();
			_userDefinedDistance = _wrapper.getDistance();
			 Persistance.store(Persistance.TARGET_DISTANCE, _userDefinedDistance.toString());
			highlightDistanceButton(_userDefinedDistance);
		}	
	}
	
	/**
	 * 
	 * @return
	 */
	public static DISTANCE convertPreferenceStringToDistance(String userPreferenceDistance)
	{
		DISTANCE userPrefferedDistance = DISTANCE.FIVE_KM;
		
		if (userPreferenceDistance != null)
		{
			if (userPreferenceDistance.equals(DISTANCE.FIVE_KM.toString()))
			{
				userPrefferedDistance = DISTANCE.FIVE_KM;
			}
			else if (userPreferenceDistance.equals(DISTANCE.TEN_KM.toString()))
			{
				userPrefferedDistance = DISTANCE.TEN_KM;
			}
			else if (userPreferenceDistance.equals(DISTANCE.TWNETY_ONE_KM.toString()))
			{
				userPrefferedDistance = DISTANCE.TWNETY_ONE_KM;
			}
			else if (userPreferenceDistance.equals(DISTANCE.FORTY_TWO_KM.toString()))
			{
				userPrefferedDistance = DISTANCE.FORTY_TWO_KM;
			}
		}
		
		return userPrefferedDistance;
	}
}
