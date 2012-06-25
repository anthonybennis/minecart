package com.anthonybennis.runplanner.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DistancePanelManager 
{
	protected enum DISTANCE{FIVE_KM,TEN_KM,TWNETY_ONE_KM,FORTY_TWO_KM,FIVE_M,TEN_M,THIRTEEN_POINT_5_M, TWENTY_SIX_POINT_FIVE_M,CUSTOM};
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
	
	
	protected double _userDefinedDistance = 5;

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
		
		ImageButtonWrapper[] buttonImages;
		if (this.getDistanceUnits() == DISTANCE_UNIT.METRIC)
		{
			buttonImages = this.createMetricButtons();
		}
		else
		{
//			buttonImages = this.createImperialButtons(); // TODO AB
		}
		
		for (ImageButtonWrapper image : buttonImages) 
		{
			distancePanel.add(image.getImage());
		}
		
		return distancePanel;
	}
	
	private ImageButtonWrapper[] createMetricButtons()
	{
		ImageButtonWrapper[] images = new ImageButtonWrapper[4];
		
		images[0].setImage(FIVE_KM_DISABLED_IMAGE_URL);
		images[1].setImage(TEN_KM_DISABLED_IMAGE_URL);
		images[2].setImage(TWENTY_ONE_KM_DISABLED_IMAGE_URL);
		images[3].setImage(FORTY_TWO_KM_DISABLED_IMAGE_URL);
		
		DISTANCE userSetDistance = this.getUserDistance();
		
		switch (userSetDistance) 
		{
			case FIVE_KM:
			{	
				images[0] = FIVE_KM_ENABLED_IMAGE_URL;
				break;
			}
			case TEN_KM:
			{	
				images[1] = TEN_KM_ENABLED_IMAGE_URL;
				break;
			}
			case TWNETY_ONE_KM: 
			{	
				images[2] = TWENTY_ONE_KM_ENABLED_IMAGE_URL;
				break;
			}
			case FORTY_TWO_KM:
			{	
				images[3] = FORTY_TWO_KM_ENABLED_IMAGE_URL;
				break;
			}
			default: // Custom
			{
				// All Image buttons are disabled.
				break;
			} 
		}
		
		return images;
	}
	
	private ImageButtonWrapper[][] createImperialButtons()
	{
		ImageButtonWrapper[] images = new ImageButtonWrapper[4];
		
			/*
			 * TODO AB - Use Imperial Images
			 */
			images[0].setDisabled(FIVE_KM_DISABLED_IMAGE_URL);
			images[1].setDisabled(TEN_KM_DISABLED_IMAGE_URL);
			images[2].setDisabled(TWENTY_ONE_KM_DISABLED_IMAGE_URL);
			images[3].setDisabled(FORTY_TWO_KM_DISABLED_IMAGE_URL);
			
			DISTANCE userSetDistance = this.getUserDistance();
			
			switch (userSetDistance) 
			{
				case FIVE_M:
				{	
					images[0] = FIVE_KM_ENABLED_IMAGE_URL;
					break;
				}
				case TEN_M:
				{	
					images[1] = TEN_KM_ENABLED_IMAGE_URL;
					break;
				}
				case THIRTEEN_POINT_5_M:
				{	
					images[2] = TWENTY_ONE_KM_ENABLED_IMAGE_URL;
					break;
				}
				case TWENTY_SIX_POINT_FIVE_M:
				{	
					images[3] = FORTY_TWO_KM_ENABLED_IMAGE_URL;
					break;
				}
				default: // Custom
				{
					// All Image buttons are disabled.
					break;
				} 
			}
		
		return images;
		
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
	
	protected double getUserDefinedDistance() 
	{
		return _userDefinedDistance;
	}

	protected void setUserDefinedDistance(double _userDefinedDistance) 
	{
		this._userDefinedDistance = _userDefinedDistance;
	}
}
