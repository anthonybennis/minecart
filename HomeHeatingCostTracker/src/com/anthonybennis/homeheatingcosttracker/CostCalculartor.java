package com.anthonybennis.homeheatingcosttracker;

import java.text.DecimalFormat;

/**
 * 
 * @author abennis
 */
public class CostCalculartor 
{
	/**
	 * 
	 * @param startTime
	 * @return
	 */
	public static String calculateCost(long startTime)
	{
		String roundedCostOfOilUsed = "€0.00";
		
		DecimalFormat decimalFormat;
		double oilBurnTime = 0.65; // TODO - Increase for bad BERs
		double oilPerHour100PerCent = 2.27; // Litres
		double oilCostPerLitre = 0.81;// Euro  TODO -(Get current price from settings)
		double oilUsed =  (oilPerHour100PerCent*oilBurnTime); // For example, €1.20 per hour.
		double hourlyOilCost = (oilUsed*oilCostPerLitre);
		double secondOilCost = (hourlyOilCost/3600);
		double currentCostOfOilUsed = 0;
		
		long currentTime;
		long durationInMilliseconds;
		long durationInSeconds;

		currentTime= System.currentTimeMillis();
		durationInMilliseconds = (currentTime - startTime);
		durationInSeconds = (durationInMilliseconds/1000);
		currentCostOfOilUsed = durationInSeconds*secondOilCost;
		decimalFormat = new DecimalFormat("##.00");
		roundedCostOfOilUsed = decimalFormat.format(currentCostOfOilUsed);
		int indexOfDecimalPoint = roundedCostOfOilUsed.indexOf('.');
			
		if (indexOfDecimalPoint == 0)
		{
			roundedCostOfOilUsed = "0" + roundedCostOfOilUsed;
		}
			
		roundedCostOfOilUsed = "€" + roundedCostOfOilUsed;
		
		return roundedCostOfOilUsed;
	}
}
