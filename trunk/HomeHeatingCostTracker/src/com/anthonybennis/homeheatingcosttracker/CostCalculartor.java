package com.anthonybennis.homeheatingcosttracker;

import java.text.DecimalFormat;

import android.content.Context;

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
	public static String calculateCost(long startTime, Context context)
	{
		String roundedCostOfOilUsed = CostCalculartor.getCurrencySymbol(context) + "0.00";
		
		DecimalFormat decimalFormat;
		double oilBurnTime = CostCalculartor.getOilBurnPercentage(context);
		final double oilPerHour100PerCent = 2.27; // Litres. Fixed value.
		double oilCostPerLitre = CostCalculartor.getOilCostPerLitre(context);
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
		int indexOfDecimalPoint = roundedCostOfOilUsed.indexOf('.'); // TODO AB Refactor for European use of ','
			
		if (indexOfDecimalPoint == 0)
		{
			roundedCostOfOilUsed = "0" + roundedCostOfOilUsed;
		}
			
		roundedCostOfOilUsed = CostCalculartor.getCurrencySymbol(context) + roundedCostOfOilUsed;
		
		return roundedCostOfOilUsed;
	}
	
	/**
	 * 
	 * @return
	 */
	private static double getOilCostPerLitre(Context context)
	{
		double oilCostPerLitre = PreferencesUtil.loadPreferenceAsDoubleg(PreferencesUtil.COST_PER_LITRE, context);
		return oilCostPerLitre;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	private static double getOilBurnPercentage(Context context)
	{
		double burnPercentage = 0.65;	
		String insulationRating = PreferencesUtil.loadPreference(PreferencesUtil.BER, context);
		
		/*
		 * TODO AB: Refactor 
		 * remove String literals from here and improve how we refer to array values.
		 */
		if (insulationRating.equals("Good Insulation"))
		{
			burnPercentage = 0.60; // Burns less often.
		}
		else if (insulationRating.equals("Poor Insulation"))
		{
			burnPercentage = 0.70; // Burns more often.
		}
		else
		{
			burnPercentage = 0.65; // Default (Average insulation).
		}
		
		return burnPercentage;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	protected static String getCurrencySymbol(Context context)
	{
		String currenceySymbol = PreferencesUtil.loadPreference(PreferencesUtil.CURRENCY_SYMBOL, context);
		return currenceySymbol;
	}
}
