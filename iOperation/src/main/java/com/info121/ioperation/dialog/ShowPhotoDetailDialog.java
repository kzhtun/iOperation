package com.info121.ioperation.dialog;

import java.io.File;
import java.io.FileOutputStream;

import com.info121.ioperation.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


public class ShowPhotoDetailDialog extends Dialog {

	private static ShowPhotoDetailDialog error = null;
	Context context;
	
	private Activity act;
	private Bitmap bmp;
	private ImageView imgPhoto;
	
	int TAKE_PHOTO_CODE = 0;

	protected ShowPhotoDetailDialog(final Context context, final Activity act, Bitmap bmp) {
		
		super(context);
		this.context = context;
		this.act = act;
		this.bmp = bmp;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.take_photo_dialog);
		getWindow().setBackgroundDrawable(new ColorDrawable(0));
		
		imgPhoto = (ImageView) findViewById(R.id.imageViewShowPhoto);
		imgPhoto.setImageBitmap(bmp);
		
		findViewById(R.id.ButtonDialogOKToContinue).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
		
	            
	            File f = null;
	            try {
					FileOutputStream fos = act.openFileOutput("MyFile.png", Context.MODE_WORLD_WRITEABLE);
					fos.close();
				    f = new File(act.getFilesDir() + File.separator + "MyFile.png");
				
				} catch (Exception e) {

				}
	            

	            Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE"); 
	            //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
	            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
	            
	            act.startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
	            dismiss();
				 
			}
		});

		
		findViewById(R.id.ButtonDialogPhotoClose).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	
	
	}
	

	public static void show(Context context, Activity act, Bitmap bmp) {
		error = new ShowPhotoDetailDialog(context, act,bmp);
		error.show();
	}
	


}
