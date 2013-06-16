package com.anthonybennis.largebookmarkwidget;


/**
 * 
 * @author Anthony
 */
public class BrowserListActivity 
{
	
	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle savedInstanceState) 
//	{
//		super.onCreate(savedInstanceState);
//	    this.setContentView(R.layout.config);
//	     
//	    String[] projection = new String[] {Browser.BookmarkColumns._ID, 
//	                                     Browser.BookmarkColumns.TITLE, 
//	                                     Browser.BookmarkColumns.URL};
//	        
//	    String[] displayFields = new String[] {Browser.BookmarkColumns.TITLE, 
//	                                     Browser.BookmarkColumns.URL};
//	        
//	    int[] displayViews = new int[] { android.R.id.text1, android.R.id.text2 };
//
//	    Cursor cur = managedQuery(android.provider.Browser.BOOKMARKS_URI, projection, null, null, null);
//	    this.setListAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cur, displayFields, displayViews));
//	}
	
//	public void getBrowserHist()  
//	{
//        Cursor mCur = managedQuery(Browser.BOOKMARKS_URI,
//                Browser.HISTORY_PROJECTION, null, null, null);
//        mCur.moveToFirst();
//        if (mCur.moveToFirst() && mCur.getCount() > 0) {
//            while (mCur.isAfterLast() == false) 
//            {
////                Log.v("titleIdx", mCur
////                        .getString(Browser.HISTORY_PROJECTION_TITLE_INDEX));
////                Log.v("urlIdx", mCur
////                        .getString(Browser.HISTORY_PROJECTION_URL_INDEX));
//                mCur.moveToNext();
//           }
//      }
//	}
}
