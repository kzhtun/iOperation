package com.info121.ioperation.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.info121.constant.Constants;
import com.info121.ioperation.dialog.ContinueOrNotDialog;
import com.info121.ioperation.iOPLoginActivity;

public class Waiter extends Thread
{
    private static final String TAG=Waiter.class.getName();
    private long lastUsed;
    private long period;
    private boolean stop;
    private Activity context;
    private int mode; // mode 1 - check idle, mode 2 - check continue or not
    private TextView tvCount;
   
    
    
    public Waiter(long period,Activity ctx, int mode)
    {
    	context = ctx;
        this.period=period;
        stop=false;
        this.mode = mode;
        
    }
    
    public Waiter(long period,Activity ctx, int mode, TextView tvCount)
    {
    	context = ctx;
        this.period=period;
        stop=false;
        this.mode = mode;
        this.tvCount = tvCount;
    }

    long idle;
    public void run()
    {
        idle=0;
        this.touch();
        do
        {
            idle=System.currentTimeMillis()-lastUsed;
            if(mode==1)
            	Log.d(TAG, "Application is idle for "+idle +" ms");
            else
            	 Log.d(TAG, "Waiting continue or not for "+idle +" ms");
            
            try
            {
            	context.runOnUiThread(new Runnable() {

                    public void run() {
                    	
                    	try {
                    		tvCount.setText(String.valueOf(Math.round((10-(idle/1000))) + " seconds remaining"));
						} catch (Exception e) {
							
						}
                    }
                });
            	
                Thread.sleep(1000); //check every 1 seconds
            }
            catch (InterruptedException e)
            {
                Log.d(TAG, "Waiter interrupted!");
            }
            if(idle > period)
            {
            	stop=true;
                idle=0;
                
                //do something here - e.g. call popup or so
 
              if(mode==1){
                
                context.runOnUiThread(new Runnable() {

                    public void run() {
                    	
                    	try {
							ContinueOrNotDialog.show(context.getBaseContext(), context);
						} catch (Exception e) {
							
						}
                    }
                });
                
              }else{
            	  
 
			  	SharedPreferences preferences = context.getSharedPreferences(Util.SERVER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
				
				String updated_time = preferences.getString(Util.SERVER_LAST_UPDATED_KEY, "-");
				String server_one = preferences.getString(Util.SERVER_ONE_KEY, Constants.SERVER_URL);
				String server_two = preferences.getString(Util.SERVER_TWO_KEY, Constants.SERVER_URL);
				
				SharedPreferences settings = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE);
		    	settings.edit().clear().commit();
		    	
		        preferences = context.getSharedPreferences(Util.SERVER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString(Util.SERVER_LAST_UPDATED_KEY, updated_time);
				editor.putString(Util.SERVER_ONE_KEY, server_one);
				editor.putString(Util.SERVER_TWO_KEY, server_two);
				editor.commit();
				
				Constants.SERVER_URL = server_one;
				Constants.BASE_URL = "http://" + Constants.SERVER_URL;
				Constants.BASE_PHOTO_URL = server_two;
				
				Constants.reload();
				
				Intent intent = new Intent(context, iOPLoginActivity.class);
			    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    context.startActivity(intent);
            	  
            	  
              }
               
            }
        }
        while(!stop);
        Log.d(TAG, "Finishing Waiter thread");
    }

    public synchronized void touch()
    {
        lastUsed=System.currentTimeMillis();
    }

    public synchronized void forceInterrupt()
    {
        this.interrupt();
    }

    //soft stopping of thread
    public synchronized void stopIT()
    {
        stop=true;
    }

    public synchronized void setPeriod(long period)
    {
        this.period=period;
    }

}