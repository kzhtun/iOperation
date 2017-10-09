package com.info121.ioperations;

import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import com.info121.ioperations.util.Waiter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

@ReportsCrashes(formKey = "", // will not be used
        mailTo = "archirayan39@gmail.com", // my email here
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)


public class RootApplication extends Application {

	 
    private static final String TAG=RootApplication.class.getName();
    private Waiter waiter;  //Thread which controls idle time
    private Activity act;
    private Context thisctx;
    private final static int WAIT_IDLE=10*60; //in seconds
	
    @Override
    public void onCreate() {
        super.onCreate();
       // ACRA.init(this);
        Log.d("iOperation", "onCreate()");
        
        Log.d(TAG, "Starting application"+this.toString());
       
        // output debug to LogCat, with tag LittleFluffyLocationLibrary

        // KZHTUN remove Location Related
        //    LocationLibrary.showDebugOutput(true);

        // in most cases the following initialising code using defaults is probably sufficient:
        //
        // LocationLibrary.initialiseLibrary(getBaseContext(), "com.your.package.name");
        //
        // however for the purposes of the test app, we will request unrealistically frequent location broadcasts
        // every 1 minute, and force a location update if there hasn't been one for 2 minutes.
//        LocationLibrary.initialiseLibrary(getBaseContext(), 60 * 1000, 2 * 60 * 1000, "com.info121.ioperation");
    }
    
    

    public void touch()
    {
        waiter.touch();
        if(waiter==null){
   		     waiter=new Waiter(WAIT_IDLE*1000,act,1); //1 mins
	         waiter.start();
   	    }else{
	        if(!waiter.isAlive()){
	         waiter=new Waiter(WAIT_IDLE*1000,act,1); //1 mins
	         waiter.start();
	        }
   	     }
    }
    
    public void startWaiter(Activity act, Context thisctx){
    	 this.act = act;
    	 this.thisctx = thisctx;
    	 if(waiter==null){

    		 waiter=new Waiter(WAIT_IDLE*1000,act,1); //1 mins
	         waiter.start();

    	 }else{

	    	 if(!waiter.isAlive()){
	    	 waiter=new Waiter(WAIT_IDLE*1000,act,1); //1 mins
	         waiter.start();

	    	 }
    	 }

    }
    
}
