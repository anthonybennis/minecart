package com.anthonybennis.runplanner.client.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.anthonybennis.runplanner.client.logic.PlanItem.PACE;
import com.anthonybennis.runplanner.client.storage.Persistance;
import com.anthonybennis.runplanner.client.utils.RunPlannerDate;

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
	private RunPlannerDate _raceDate;
	private static final String PLAN_SEPERATPOR = "€";
	/**
	 * Constructor
	 * @param raceDate
	 */
	public PlanGenerator(RunPlannerDate raceDate)
	{
		_raceDate = raceDate;
	}
	
	/**
	 * 
	 * @param distance
	 * @param endDate
	 * @param experience
	 * @return
	 */
	public List<PlanItem> generatePlan(int distance, int experience)
	{
		List<PlanItem> planList = new ArrayList<PlanItem>();
		
		switch (experience)
		{
			case 0:
			{
				if (distance == 5)
				{
					planList = this.createBeginner5KMRacePlan(distance, _raceDate, experience);	
				}
				else if (distance == 10)
				{
					planList = this.createBeginner10KMRacePlan(distance, _raceDate, experience);
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
	private List<PlanItem> createBeginner5KMRacePlan(int distance, RunPlannerDate endDate, int experience)
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
		
		return planList;
	}
	

	/**
	 * 
	 * @param distance
	 * @param endDate
	 * @param experience
	 * @return
	 */
	private List<PlanItem> createBeginner10KMRacePlan(int distance, RunPlannerDate endDate, int experience)
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
		Date lastDaysDate;
		
		if (planList != null && planList.size() >0)
		{
			planList.get(planList.size()-1);
		}
		
		if (lastPlanItem == null)
		{
			lastDaysDate = this.calculatePlansStartDate();
		}
		else
		{
			lastDaysDate = lastPlanItem.getDate();
		}
		
		int number = planList.size();
		lastDaysDate = this.advanceDateOneDay(lastDaysDate);	
		PlanItem planItem = new PlanItem(number,lastDaysDate, walkRunMix, pace, comment);
		planList.add(planItem);
		
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
	public boolean isThereSuffecientTimeToPlan(int distance, RunPlannerDate endDate, int experience)
	{
		/*
		 * Calculate time to race
		 */
		int daysUntilRace = _raceDate.calculateNumberOfDaysFromToday();
		int recommendedDays = this.getRecommendedDaysNeeded(distance, endDate, experience);
		
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
	private int getRecommendedDaysNeeded(int distance, RunPlannerDate endDate, int experience)
	{
		int recommendedDays = 365;
		
		/*
		 * Beginner
		 */
		if (distance == 5 && experience == 0)
		{
			recommendedDays = 84; // Beginner 5KM: 12 weeks/84 days
		}
		else if (distance == 10 && experience == 0)
		{
			
		}
		else if (distance == 21 && experience == 0)
		{
			
		}
		else if (distance == 42 && experience == 0)
		{
			
		}
		/*
		 * Intermediate.
		 */
		else if (distance == 5 && experience == 1)
		{
			
		}
		else if (distance == 10 && experience == 1)
		{
			
		}
		else if (distance == 21 && experience == 1)
		{
			
		}
		else if (distance == 42 && experience == 1)
		{
			
		}
		/*
		 * Advanced.
		 */
		else if (distance == 5 && experience == 2)
		{
			
		}
		else if (distance == 10 && experience == 2)
		{
			
		}
		else if (distance == 21 && experience == 2)
		{
			
		}
		else if (distance == 42 && experience == 2)
		{
			
		}
		
		return recommendedDays; 
	}
	
	/**
	 * TODO AB calculatePlansStartDate
	 * We always start the plan on a Monday
	 * @return
	 */
	private Date calculatePlansStartDate()
	{
		Date startDate = null;
		
		/*
		 * The plan is 8 weeks.
		 * Generate plan for 8 weeks only. Not from "today" (Day plan is generated).
		 * If plan does not start for some time, advice user of the distances they
		 *  need to be doing before starting the plan. Inform user of start date.
		 * 
		 * The plan  starts on a Monday. If today (the day the plan is generated),
		 * is not on a Monday, we ramp up by duplicating the first week of the plan,
		 * by adding extra days dynamically.
		 * 
		 */
		
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
	
	private String convertPlanToString(List<PlanItem> plan)
	{
		StringBuilder planAsString = new StringBuilder();
		
		for (PlanItem planItem : plan) 
		{
			planAsString.append(planItem.toString());
			planAsString.append(PLAN_SEPERATPOR);
		}
		
		return planAsString.toString();
	}
	
	private List<PlanItem> convertStringToPlan(String planAsSttring)
	{
		List<PlanItem> plan = new ArrayList<PlanItem>();
		String[] planSplit = planAsSttring.split(PLAN_SEPERATPOR);
		
		PlanItem item;
		for (String string : planSplit) 
		{
			item = PlanItem.createFromString(string);
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
	 */
	public static void main(String[] args)
	{
		RunPlannerDate runPlannerDate = new RunPlannerDate();
		runPlannerDate.setDay(1);
		runPlannerDate.setMonth(9);
		runPlannerDate.setYear(2012);
		
		PlanGenerator gen = new PlanGenerator(runPlannerDate);
		List<PlanItem> plan = gen.generatePlan(5, 0);
		gen.savePlan(plan);
		gen.loadPlan();
	}
}