package com.info121.ioperation.dialog;


import com.info121.ioperation.R;
import com.info121.ioperation.RootApplication;
import com.info121.ioperation.util.Waiter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class ContinueOrNotDialog extends Dialog {

	private static ContinueOrNotDialog error = null;

	Context context;
	
	private Activity act;
    private TextView tvCount;
    public static Activity activityStatic;

	protected ContinueOrNotDialog(final Context context, final Activity act) {
		
		super(context);
		this.context = context;
		this.act = act;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.continueornot_dialog);
		getWindow().setBackgroundDrawable(new ColorDrawable(0));
		tvCount = (TextView)findViewById(R.id.TextViewDialogCountDownIdle);
		
		final Waiter waiter=new Waiter(10*1000,act,2,tvCount); //10 seconds
        waiter.start();
	
		
		findViewById(R.id.ButtonDialogOKToContinue).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((RootApplication )act.getApplication()).startWaiter(act,context);
				waiter.stopIT();
				dismiss();
			}
		});

	
	
	}
	
	
//
//	@Override
//	public void onDetachedFromWindow() {
//		super.onDetachedFromWindow();
//		((RootApplication )act.getApplication()).startWaiter(act);
//	}



	public static void show(Context context, Activity act) {
//		
//		if(error == null){
//			error = new ContinueOrNotDialog(act, act);
//			error.show();
//		}else{
//			if(!error.isShowing()){
//			error = new ContinueOrNotDialog(act, act);
//			error.show();
//			}
//		}
		
		
	
			try {
				if(!error.isShowing()){
				error = new ContinueOrNotDialog(activityStatic, activityStatic);
				error.show();
				}
			} catch (Exception e) {
				error = new ContinueOrNotDialog(activityStatic, activityStatic);
				error.show();
			}
		
		
		
	}



}
