package com.anthonybennis.largebookmarkwidget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class LaunchURLActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.openBrowser();
	}

	public void openBrowser()
	{
		String url = "http://www.anthonybennis.com";
		
		if (!url.startsWith("https://") && !url.startsWith("http://"))
		{
		    url = "http://" + url;
		}
		
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
}
