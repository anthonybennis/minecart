package com.anthonybennis.runplanner.client.storage;

import com.google.gwt.storage.client.Storage;

/**
 * 
 * @author abennis
 */
public class Persistance 
{
	 private static final Storage STORE = Storage.getLocalStorageIfSupported();
	 
	 /*
	  * Keys
	  */
	 public static final String TARGET_DATE = "RUNPLANNER_TARGET_DATE";
	 public static final String TARGET_DISTANCE = "RUNPLANNER_DISTANCE";
	 public static final String DISTANCE_UNIT = "RUNPLANNER_DISTANCE_UNIT";
	 public static final String AUDIO = "RUNPLANNER_AUDIO";
	 
	 /*
	  * TODO Store calender entries as Date;Distance;Note comma seperated values.
	  * use comma separated values
	  * store(PLAN, "DATE;DISTANCE;NOTE,DATE;DISTANCE;NOTE,DATE;DISTANCE;NOTE")
	  */
	 
	 
	 /**
	  * 
	  */
	 public static void store(String key, String value)
	 {
		 if (STORE != null)
		 {
			 STORE.setItem(key, value);
		 }
	 }
	 
	 /**
	  * TODO AB need method to get all and individual calander entries.
	  * 
	  * @param key
	  * @return
	  */
	 public static String get(String key)
	 {
		 String value = "?";
		 
		 if (STORE != null)
		 {
			 value = STORE.getItem(key);
		 }
		 
		 return value;
	 }
}