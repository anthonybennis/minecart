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
		double oilBurnTime = CostCalculartor.getOilBurnPercentage();
		final double oilPerHour100PerCent = 2.27; // Litres. Fixed value.
		double oilCostPerLitre = CostCalculartor.getOilCostPerLitre();
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
	
	/**
	 * 
	 * @return
	 */
	private static double getOilCostPerLitre()
	{
		double oilCostPerLitre = 0.85; // Default (December 2013 prices for Cork).
				
		// TODO AB: Read from settings.
				
		return oilCostPerLitre;
	}
	
	private static double getOilBurnPercentage()
	{
		// TODO Get BER rating.
		return 0.65; // TODO - Increase for bad BERs
	}
}
