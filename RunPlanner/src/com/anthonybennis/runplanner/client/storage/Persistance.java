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
	 public static final String TARGET_DATE = "TARGET_DATE";
	 public static final String TARGET_DISTANCE = "DISTANCE";
	 public static final String DISTANCE_UNIT = "DISTANCE_UNIT";
	 
	 /*
	  * TODO AB - How will we store calender entries?
	  * Maybe DATE is key?
	  * store(Date,String distance);
	  * or use comma seperated values? (Preffered approach)
	  * store(PLAN, "DATE,DISTANCE,DATE,DISTANCE,DATE,DISTANCE")
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