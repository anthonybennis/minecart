package com.anthonybennis.runplanner.client.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.anthonybennis.runplanner.client.DistancePanelManager;
import com.anthonybennis.runplanner.client.DistancePanelManager.DISTANCE;
import com.anthonybennis.runplanner.client.logic.PlanItem.PACE;
import com.anthonybennis.runplanner.client.storage.Persistance;
import com.anthonybennis.runplanner.client.utils.RunPlannerDate;
import com.google.gwt.user.datepicker.client.CalendarUtil;

/**
 * Training Plan
 * 1. Number of weeks (Differed from table size) Distance per day calculated from RWRWR
 * 2. No / $ characters in description.
 * 2. Table Structure:
 * 
 * [Day Number][ddmmyyyy][RunWalkArray W1 R1][Comment]
 * [Day Number][ddmmyyyy][RunWalkArray W1 R1][Comment]
 * [Day Number][ddmmyyyy][RunWalkArray W1 R1][Comment]
 * [Day Number][ddmmyyyy][RunWalkArray W1 R1][Comment]
 * 
 * 
 * Storage String example: 
 * 1/22082012/W1R1W1R1W1/First day. Take it easy.$
 * 2/23082012/W2R2W2R2W2/Let's start to buid up distance...$
 * 
 * First tokenise by $. Each $ represents a day.
 * Then each day is broken up by '/' (We don't use commas to separate as description may contain them. XML would take up too much memory
 * 
 * 100 days of schedule is 6KB in Windows. Should have plenty of Storage space?
 * 
 * 5km: http://running.about.com/od/racetraining/a/5Kadvbeginner.htm
 * 10km: http://running.about.com/od/racetraining/a/10Kbeginner.htm
 * 
 * 
 * @author abennis
 */
public class PlanGenerator
{
	private static final String PLAN_SEPERATPOR = "€";
	private DistancePanelManager.DISTANCE _distance;
	private int _experience; 
	private RunPlannerDate _raceDate;
	
	/**
	 * Constructor
	 * @param raceDate
	 */
	public PlanGenerator(DistancePanelManager.DISTANCE distance, int experience, RunPlannerDate raceDate)
	{
		_distance = distance;
		_experience = experience;
		_raceDate = raceDate;
	}
	
	/**
	 * 
	 * @param distance
	 * @param endDate
	 * @param experience
	 * @return
	 */
	public List<PlanItem> generatePlan()
	{
		List<PlanItem> planList = new ArrayList<PlanItem>();
		
		switch (_experience)
		{
			case 0:
			{
				if (_distance == DISTANCE.FIVE_KM)
				{
					planList = this.createBeginner5KMRacePlan();	
				}
				else if (_distance == DISTANCE.TEN_KM)
				{
					planList = this.createBeginner10KMRacePlan();
				}
				
				break;
			}
			case 1:
			{
				break;
			}
			case 2:
			{
				break;
			}
			default:
			{
				break;
			}
		}
		
		return planList;
	}
	
	/**
	 * Creates a Hard coded plan for beginners 5KM race
	 * Warning: You should be able to do at least 20 miles in a week.
	 * It's Advanced beginner!
	 * 
	 * TODO AB convert to KM
	 * 
	 * @return List of Plan Items.
	 */
	private List<PlanItem> createBeginner5KMRacePlan()
	{
		List<PlanItem> planList = new ArrayList<PlanItem>();
		
		/*
		 * Creates 84 day Plan:
		 * Week 1:
		 */
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(.5,1.5,.5),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.SLOW, ""); // Sunday 
		// Week 2: 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(.5,1.5,.5),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.SLOW, ""); // Sunday 
		// Week 3: 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(.5,2,.5),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(2.5), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.SLOW, ""); // Sunday 
		// Week 4:
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(2),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(3), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.SLOW, ""); // Sunday 
		// Week 5:
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(2.5),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(3), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(3), PACE.SLOW, ""); // Sunday 
		// Week 6:
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(3),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(3.5), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(3), PACE.SLOW, ""); // Sunday 
		// Week 7:
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(3.5),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(4), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(3.5), PACE.SLOW, ""); // Sunday 
		// Week 8:
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(3),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(4), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(4), PACE.SLOW, ""); // Sunday 
		// Week 9: RACE WEEK
		// TODO THIS NEEDS TO BE DYNAMICALLY GENERATED!
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(3),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(5), PACE.FAST, ""); // RACE DAY 
		
		return planList;
	}
	

	/**
	 * 
	 * @param distance
	 * @param endDate
	 * @param experience
	 * @return
	 */
	private List<PlanItem> createBeginner10KMRacePlan()
	{
		List<PlanItem> planList = new ArrayList<PlanItem>();
		
		/*
		 * Creates 84 day Plan:
		 * Week 1:
		 */
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(.5,1.5,.5),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.SLOW, ""); // Sunday 
		// Week 2: 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(.5,2,.5),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(2.5), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.SLOW, ""); // Sunday 
		// Week 3:
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(2),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(3), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.SLOW, ""); // Sunday 
		// Week 4:
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(2.5),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(3), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(3), PACE.SLOW, ""); // Sunday 
		// Week 5:
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(3),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(3.5), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(3), PACE.SLOW, ""); // Sunday 
		// Week 6:
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(3.5),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(4), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(3.5), PACE.SLOW, ""); // Sunday 
		// Week 7:
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(3),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1,1,1), PACE.MIX, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(1.5), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(4), PACE.COMFORTABLE, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(4), PACE.SLOW, ""); // Sunday 
		// Week 8: RACE WEEK
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Monday
		this.addPlanItemToMix(planList, new WalkRunMix(3),PACE.COMFORTABLE, ""); // Tuesday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Wednesday 
		this.addPlanItemToMix(planList, new WalkRunMix(2), PACE.FAST, ""); // Thursday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Friday 
		this.addPlanItemToMix(planList, new WalkRunMix(), PACE.REST, ""); // Saturday
		this.addPlanItemToMix(planList, new WalkRunMix(5), PACE.FAST, ""); // RACE DAY 
		// TODO AB Double this plan for half marathon.
		
		return planList;
	}
	
	
	/**
	 * 
	 * @param planList
	 * @param sevenDayWalkRunMix
	 * @return
	 */
	private List<PlanItem> addPlanItemToMix(List<PlanItem> planList, WalkRunMix walkRunMix, PACE pace, String comment)
	{
		PlanItem lastPlanItem = null;
		Date lastDaysDate = null;
		
		if (planList != null && planList.size() >0) 
		{
			lastPlanItem = planList.get(planList.size()-1);
			lastDaysDate = lastPlanItem.getDate();
			lastDaysDate = this.advanceDateOneDay(lastDaysDate); 
		}
		else // First PlanItem of Plan.
		{
			lastDaysDate = this.calculatePlansStartDate(_distance, _raceDate, _experience);
		}
		
		int number = planList.size();
			
	
		
		int daysLeftToDate = CalendarUtil.getDaysBetween(lastDaysDate, _raceDate.toDate());
		
		PlanItem planItem = null;
		if (daysLeftToDate > 7)
		{
			planItem = new PlanItem(number,lastDaysDate, walkRunMix, pace, comment);
		}
		else
		{
			/*
			 * TODO Generate Last week program dynamically, or from preset.
			 */
			planItem = new PlanItem(number,lastDaysDate, walkRunMix, pace, comment);
		}
		
		planList.add(planItem);
		System.out.println("Created PlanItem: " + planItem.toString());
		
		return planList;
	}
	
	/**
	 * TODO AB - We'll want to warn the user that there isn't enough time. But
	 * they may want the plan anyhow as they may already be in training.
	 * 
	 * Should only be called when generating plan for the first time.
	 * 
	 * @param distance
	 * @param endDate
	 * @param experience
	 * @return
	 */
	public static boolean isThereSuffecientTimeToPlan(DistancePanelManager.DISTANCE distance, RunPlannerDate endDate, int experience)
	{
		/*
		 * Calculate time to race
		 */
		int daysUntilRace = endDate.calculateNumberOfDaysFromToday();
		int recommendedDays = PlanGenerator.getRecommendedDaysNeeded(distance, endDate, experience);
		
		return recommendedDays >= daysUntilRace;
	}
	
	/**
	 * TODO AB - set recommended days for each level and distance.
	 * 
	 * @param distance
	 * @param endDate
	 * @param experience
	 * @return
	 */
	private static int getRecommendedDaysNeeded(DistancePanelManager.DISTANCE distance, RunPlannerDate endDate, int experience)
	{
		int recommendedDays = 365;
		
		/*
		 * Beginner
		 */
		if (distance == DISTANCE.FIVE_KM && experience == 0)
		{
			recommendedDays = 56; // Beginner 5KM: 8 weeks/56 days
		}
		else if (distance == DISTANCE.TEN_KM && experience == 0)
		{
			// TODO AB Calculate recoemmned days
		}
		else if (distance == DISTANCE.TWNETY_ONE_KM && experience == 0)
		{
			// TODO AB Calculate recoemmned days
		}
		else if (distance == DISTANCE.FORTY_TWO_KM && experience == 0)
		{
			// TODO AB Calculate recoemmned days
		}
		/*
		 * Intermediate.
		 */
		else if (distance == DISTANCE.FIVE_KM && experience == 1)
		{
			// TODO AB Calculate recoemmned days
		}
		else if (distance == DISTANCE.TEN_KM && experience == 1)
		{
			// TODO AB Calculate recoemmned days
		}
		else if (distance == DISTANCE.TWNETY_ONE_KM && experience == 1)
		{
			// TODO AB Calculate recoemmned days
		}
		else if (distance == DISTANCE.FORTY_TWO_KM && experience == 1)
		{
			// TODO AB Calculate recoemmned days
		}
		/*
		 * TODO FUTURE_ENHANCEMENT = Advanced.
		 */
		else if (distance == DISTANCE.FIVE_KM && experience == 2)
		{
			// TODO AB Calculate recoemmned days
		}
		else if (distance == DISTANCE.TEN_KM && experience == 2)
		{
			// TODO AB Calculate recoemmned days
		}
		else if (distance == DISTANCE.TWNETY_ONE_KM && experience == 2)
		{
			// TODO AB Calculate recoemmned days
		}
		else if (distance == DISTANCE.FORTY_TWO_KM && experience == 2)
		{
			// TODO AB Calculate recoemmned days
		}
		
		return recommendedDays; 
	}
	
	/**
	 * TODO AB calculatePlansStartDate
	 * We always start the plan on a Monday
	 * @return
	 */
	private Date calculatePlansStartDate(DistancePanelManager.DISTANCE distance, RunPlannerDate raceDate, int experience)
	{
		Date startDate = new Date();
		
		/*
		 * For example: The plan is 8 weeks.
		 * 
		 * Plan must start on a Monday. Every Plan has an extra week built in,
		 * so we can eat into this so that it starts on a Monday.
		 * 
		 * TODO Add extra week to all plans (Duplicate first week)
		 * TODO Reduce the List<PlanItems> by the number of days.
		 */
		int recommendedDaysNeeded = PlanGenerator.getRecommendedDaysNeeded(distance, raceDate, experience);

		recommendedDaysNeeded = -recommendedDaysNeeded;
		CalendarUtil.addDaysToDate(startDate, recommendedDaysNeeded); 
		
		/*
		 * If not Monday... keep going forward until we hit a Monday.
		 * All Plans assume they start on a Monday.
		 */
		startDate = RunPlannerDate.getNextMonday(startDate);
		
		return startDate;
	}

	/**
	 * TODO AB Refactor: move to date utils.
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Date advanceDateOneDay(Date date)
	{
		date.setDate(date.getDate() + 1);
		return date;
	}
	
	/**
	 * 
	 * @param plan
	 * @return
	 */
	private String convertPlanToString(List<PlanItem> plan)
	{
		StringBuilder planAsString = new StringBuilder();
		
		for (PlanItem planItem : plan) 
		{
			System.out.println("Saving PlanItem: " + planItem.toString());
			planAsString.append(planItem.toString());
			planAsString.append(PLAN_SEPERATPOR);
		}
		
		return planAsString.toString();
	}
	
	/**
	 * 
	 * @param planAsSttring
	 * @return
	 */
	private List<PlanItem> convertStringToPlan(String planAsSttring)
	{
		List<PlanItem> plan = new ArrayList<PlanItem>();
		String[] planSplit = planAsSttring.split(PLAN_SEPERATPOR);
		
		PlanItem item;
		for (String string : planSplit) 
		{
			item = PlanItem.createFromString(string);
			System.out.println("Loading PlanItem: " + item.toString());
			plan.add(item);
		}
		
		return plan;
	}
	
	/**
	 * Loads a previously generated plan
	 * @return
	 */
	public List<PlanItem> loadPlan()
	{
		List<PlanItem> plan = null;
		String loadedPlan = Persistance.get(Persistance.PLAN);
		plan = this.convertStringToPlan(loadedPlan);
		
		return plan;
	}
	
	/**
	 * 
	 * @param plan
	 */
	public void savePlan(List<PlanItem> plan)
	{
		String planAsString = this.convertPlanToString(plan);
		Persistance.store(Persistance.PLAN, planAsString);
	}
	
	
	/*
	 * For TESTING
	 * Can't test storage if it 
	 */
	public static void main(String[] args)
	{
		RunPlannerDate runPlannerDate = new RunPlannerDate();
		runPlannerDate.setDay(1);
		runPlannerDate.setMonth(9);
		runPlannerDate.setYear(2012);
		
		PlanGenerator gen = new PlanGenerator(DistancePanelManager.DISTANCE.FIVE_KM, 0,runPlannerDate);
		Date date = new Date();
		gen.calculatePlansStartDate(DistancePanelManager.DISTANCE.FIVE_KM, runPlannerDate, 0);
//		List<PlanItem> plan = gen.generatePlan();
//		gen.savePlan(plan);
//		gen.loadPlan();
	}
}