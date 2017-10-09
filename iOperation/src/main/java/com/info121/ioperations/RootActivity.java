package com.info121.ioperations;

import com.info121.ioperations.util.Util;

import android.os.Bundle;
import android.app.Activity;

public class RootActivity extends Activity {
	 private static final String TAG=RootApplication.class.getName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(!Util.isNetworkAvailable(this)){
			Util.showAlertCommonDialogWithOKBtn(this, "Error","No internet connection available");
		}
		
		//getApp().startWaiter(RootActivity.this,RootActivity.this);
	}

//	@Override
//	public void onUserInteraction() {
//		super.onUserInteraction();
//		
//		 getApp().touch();
//	        Log.d(TAG, "User interaction to "+this.toString());
//	}
//	
//	 public RootApplication getApp()
//	    {
//	        return (RootApplication )this.getApplication();
//	    }

	  
	

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}
	
	

}
