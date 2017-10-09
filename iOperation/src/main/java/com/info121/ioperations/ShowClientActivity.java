package com.info121.ioperations;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.ioperations.dialog.ShowPhotoDetailDialog;
import com.info121.ioperations.fragment.iOperationFragment;
import com.info121.ioperations.util.BitmapHelper;
import com.info121.ioperations.util.GPSTracker;
import com.info121.ioperations.util.Parser;
import com.info121.ioperations.util.Util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class ShowClientActivity extends RootWithIdleCheckActivity implements OnClickListener {

    public ProgressDialog progress;
    public LinearLayout mContent;
    public LinearLayout LLSignInfo;
    public TextView tvPassengerName;
    public EditText etAcknowledgement;
    public signature mSignature;
    public Button mClear, mGetSign, mCancel;
    public static String tempDir;
    public int count = 1;
    public String current = null;
    private Bitmap mBitmap;
    public View mView;
    public File mypath;
    private String uniqueId;
    private Context context;
    public String filePath;
    private String jobno, acknowledgement;
    private String passenger_name;

    private ImageView imgCamera, ivPicture;

    int TAKE_PHOTO_CODE = 0;
    public static int countphoto = 0;
    String dir;
    private String signature = "";
    private Button btBack;
    private String addressText = "";

    private Button mRateMe;
    private String emailText;
    private int stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_client_layout);


        context = this;
        LLSignInfo = (LinearLayout) findViewById(R.id.LLTextinfoSign);
        LLSignInfo.setOnClickListener(this);
        tvPassengerName = (TextView) findViewById(R.id.TextViewShowPassengerName);
        etAcknowledgement = (EditText) findViewById(R.id.editTextShowAcknowledgement);
//        etAcknowledgement.setText("RARA");
        btBack = (Button) findViewById(R.id.buttonBack_ShowActivity);
        btBack.setOnClickListener(this);
//		imgCamera = (ImageView)findViewById(R.id.imageViewShowCamera);
//		imgCamera.setOnClickListener(this);
        ivPicture = (ImageView) findViewById(R.id.ivShowCameraPic);
        ivPicture.setOnClickListener(this);
        jobno = getIntent().getStringExtra(Util.JOBNO_KEY);
        passenger_name = getIntent().getStringExtra(Util.PASSENGER_KEY);
        acknowledgement = etAcknowledgement.getText().toString();
        tvPassengerName.setText(passenger_name);

        mGetSign = (Button) findViewById(R.id.buttonShowContinue);
        mGetSign.setOnClickListener(this);

        tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);

        prepareDirectory();
        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".jpg";
        mypath = new File(directory, current);


        mContent = (LinearLayout) findViewById(R.id.LLShowSignature);


        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mClear = (Button) findViewById(R.id.clear);

        mRateMe = (Button) findViewById(R.id.btn_rate_me);

        //dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";


        mRateMe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_rating);
                dialog.setTitle("Thank you for sharing your expirence. \n Your email will be used to send future promotions");


                final EditText mEmail = (EditText) dialog.findViewById(R.id.email);
                final RatingBar mRatingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
                final Button mSubmit = (Button) dialog.findViewById(R.id.submit);

                mSubmit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Toast.makeText(ShowClientActivity.this, String.valueOf(mRatingBar.getRating()), Toast.LENGTH_SHORT);

                        stars = Math.round(mRatingBar.getRating());
                        emailText = mEmail.getText().toString();

                        if (emailText.length() == 0) {
                            mEmail.setError("Please enter valid email address.");
                            mEmail.requestFocus();
                        } else {
                            if (isValidEmaillId(emailText)) {
                                dialog.dismiss();
                            } else {
                                mEmail.setError("Please enter valid email address.");
                                mEmail.requestFocus();
                            }
                        }


                    }
                });

                dialog.show();

            }
        });


        dir = getDir("directory", Context.MODE_PRIVATE).getPath();
//	        File newdir = new File(dir); 
//	        newdir.mkdirs();


        GPSTracker gps = new GPSTracker(ShowClientActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

           addressText = getCompleteAddressString(latitude, longitude);
          //  addressText = getCompleteAddressString( 1.330980, 103.865060);
            // \n is for new line
            // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
            addressText = "No Location.";
        }
    }


    public static boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {

                strAdd = addresses.get(0).getFeatureName();
                strAdd += ", " + addresses.get(0).getThoroughfare();
                strAdd += ", " + addresses.get(0).getLocality();
                strAdd += ", " + addresses.get(0).getCountryName();

            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return strAdd;
    }


    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate = (c.get(Calendar.YEAR) * 10000) +
                ((c.get(Calendar.MONTH) + 1) * 100) +
                (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:", String.valueOf(todaysDate));
        return (String.valueOf(todaysDate));

    }

    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:", String.valueOf(currentTime));
        return (String.valueOf(currentTime));

    }


    private boolean prepareDirectory() {
        try {
            if (makedirs()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean makedirs() {

        File f = null;
        try {
            FileOutputStream fos = openFileOutput(current, Context.MODE_WORLD_WRITEABLE);
            fos.close();
            // f = new File(getFilesDir() + File.separator + current);

        } catch (Exception e) {

        }

        File tempdir = new File(getFilesDir() + File.separator + current);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory()) {
            File[] files = tempdir.listFiles();
            for (File file : files) {
                if (!file.delete()) {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }


    public class signature extends View {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
            this.setBackgroundColor(Color.WHITE);
        }

        public String save(View v) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            String encode = "";

            mBitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(mBitmap);
            try {
//                mBitmap = BitmapHelper.decodeBitmap(mBitmap, false);
                FileOutputStream mFileOutStream = new FileOutputStream(mypath);
                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
//                String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
//                Log.v("url", "url: " + url);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                encode = Base64.encodeToString(byteArray, Base64.DEFAULT);

//                ShowPhotoDetailDialog.show(context, ShowClientActivity.this, mBitmap);
                filePath = saveToInternalStorage(mBitmap);

                boolean result;
                FTPClient ftpClient = null;

                try {
                    ftpClient = new FTPClient();
                    ftpClient.connect(InetAddress.getByName(Constants.BASE_PHOTO_URL));


                    int reply = ftpClient.getReplyCode();

                    if (ftpClient.login("ipos", "iposftp")) {

                        ftpClient.enterLocalPassiveMode(); // important!
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);


                        FileInputStream in1 = new FileInputStream(new File(filePath));
                        String signaturefilename = "signature_" + passenger_name + "_" + jobno + ".jpg";
                        result = ftpClient.storeFile(signaturefilename, in1);
                        in1.close();
                    }
                } catch (Exception e) {
                    Log.v("log_tag", e.toString());
                }


                //In case you want to delete the file
                //boolean deleted = mypath.delete();
                //Log.v("log_tag","deleted: " + mypath.toString() + deleted);
                //If you want to convert the image to string use base64 converter


            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }
            return filePath;
        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);
            LLSignInfo.setVisibility(View.GONE);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonBack_ShowActivity:
                finish();
                break;

            case R.id.buttonShowContinue:
//                signature = mSignature.save(mSignature);

                new processShowAsyncTask().execute();

                break;

            case R.id.LLTextinfoSign:
                LLSignInfo.setVisibility(View.GONE);
                break;
            case R.id.ivShowCameraPic:
                if (imageReturned != null) {
                    ShowPhotoDetailDialog.show(context, this, imageReturned);
                } else {

//            String file = dir+"/cache.jpg";
//            Log.v("my file",file);
                    savefile = new File(dir, "cache2.jpg");


//            try {
//            	
//            	Log.v("result",String.valueOf(savefile.createNewFile()));
//            } catch (Exception e) {
//            	e.printStackTrace();
//            	
//            }       

                    outputFileUri = Uri.fromFile(savefile);

                    File f = null;
                    try {
                        FileOutputStream fos = openFileOutput("MyFile.jpg", Context.MODE_WORLD_WRITEABLE);
                        fos.close();
                        f = new File(getFilesDir() + File.separator + "MyFile.jpg");

                    } catch (Exception e) {

                    }


                    int outputX = 0;
                    int outputY = 0;
                    try {
                        Camera camera = Camera.open();
                        Camera.Parameters params = camera.getParameters();
                        List<Camera.Size> sizes = params.getSupportedPictureSizes();

                        outputX = 0;
                        outputY = 0;
                        double pixel = 0;
                        for (Camera.Size tempsize : sizes) {
                            pixel = (tempsize.width * tempsize.height) / 1000000;

                            if (pixel < 1.3) {
                                outputX = tempsize.width;
                                outputY = tempsize.height;
                                break;
                            }
                        }
                        camera.release();
                        Toast.makeText(this, "pixel:" + String.valueOf(pixel) + ", " + String.valueOf(outputX) + "X" + String.valueOf(outputY), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {

                    }


                    Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    //cameraIntent.putExtra("crop", "true");
                    if (outputX != 0 && outputY != 0) {
                        cameraIntent.putExtra("outputX", outputX);
                        cameraIntent.putExtra("outputY", outputY);
                    }
                    cameraIntent.putExtra("aspectX", 1);
                    cameraIntent.putExtra("aspectY", 1);
                    cameraIntent.putExtra("scale", true);
                    cameraIntent.putExtra("outputFormat",
                            Bitmap.CompressFormat.JPEG.toString());
                    startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);

                }

                break;

        }
    }

    Bitmap imageReturned;
    File savefile;
    Uri outputFileUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                //imageReturned = (Bitmap) data.getExtras().get("data");

//				Uri selectedImage = outputFileUri;
//	            getContentResolver().notifyChange(selectedImage, null);
//	            
//	            ContentResolver cr = getContentResolver();
//	            Bitmap bitmap;
                try {
//	            	imageReturned = android.provider.MediaStore.Images.Media
//	                 .getBitmap(cr, selectedImage);


                    try {
                        InputStream is = openFileInput("MyFile.jpg");
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        //options.inSampleSize = 4;
                        imageReturned = BitmapFactory.decodeStream(is, null, options);
                    } catch (IOException e) {

                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                            .show();

                }

                new processImageCaptured().execute();

            }

        }

    }


    private class processImageCaptured extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(context, "Processing image",
                    "Loading . . .", true);

        }

        @Override
        protected String doInBackground(Void... v) {

            try {

                //imageReturned = BitmapHelper.decodeInputSream(openFileInput("MyFile.png"), false);

                imageReturned = BitmapHelper.decodeBitmap(imageReturned, false);

                //imageReturned = BitmapHelper.decodeFile(savefile, false);


            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            ivPicture.setImageBitmap(imageReturned);


        }
    }


    public class processShowAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(context, "Please Wait",
                    "Loading . . .", true);
        }

        @Override
        protected String doInBackground(Void... v) {

            uploadingFTPImage();
            String filename = "photo_" + passenger_name + "_" + jobno + "Raaaaj.jpg";
            String signname = "signature_" + passenger_name + "_" + jobno + ".jpg";

            Log.d("fileNAAME", filename + " ___ " + signname);
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("jobno", jobno);
                parameter.put("PassengerName", passenger_name);
                parameter.put("FeedBack", etAcknowledgement.getText().toString());
                parameter.put("PhotoPhotoFileName", "photo_" + passenger_name + "_" + jobno + ".jpg");
                parameter.put("SignaturePhotoFileName", "signature_" + passenger_name + "_" + jobno + ".jpg");
                parameter.put("Location", addressText);
                // KZHTUN on 03102017
                parameter.put("email", emailText);
                parameter.put("rate", String.valueOf(stars));

                parameter.put("IOpsUserName", getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAPUploadFile(Constants.uRLiOpsSaveShowPassenger, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsSaveShowPassenger, Constants.APIDotNetMethodNameSaveShowPassenger, parameter, getFilesDir() + File.separator + "MyFile.jpg", signature);
        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            if (result != null) {
//        		Util.showAlertCommonDialogWithOKBtn(iOpMainViewPagerActivity.thisactivity, "Success","Show data sent to the server");
//    			SharedPreferences preferences = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
//				SharedPreferences.Editor editor = preferences.edit();
//				editor.putBoolean(Util.EXIT_SHOW_KEY, true);
//				editor.commit();
//    			finish();
//    			iOperationFragment.thisFragment.CallTheJob();

                try {

                    String temp = Parser.getJSONAcceptRejectResult(result);
                    Util.showAlertCommonDialogWithOKBtn(iOpMainViewPagerActivity.thisactivity, "Success", "Show data sent to the server");
                    SharedPreferences preferences = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(Util.EXIT_SHOW_KEY, true);
                    editor.commit();
                    finish();
                    iOperationFragment.thisFragment.CallTheJob();
                } catch (Exception e) {
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Operation Failed");
                }
            } else {

                Util.showAlertCommonDialogWithOKBtn(context, "Error ", "Operation Failed");

            }
        }
    }


    public boolean uploadingFTPImage() {

        boolean result = false;
        FTPClient ftpClient = null;

        try {
            ftpClient = new FTPClient();
            ftpClient.connect(InetAddress.getByName(Constants.BASE_PHOTO_URL));


            int reply = ftpClient.getReplyCode();

            if (ftpClient.login("ipos", "iposftp")) {

                ftpClient.enterLocalPassiveMode(); // important!
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);

                if (imageReturned != null) {
                    String data = getFilesDir() + File.separator + "MyFile.jpg";
                    FileInputStream in = new FileInputStream(new File(data));
                    String filename = "photo_" + passenger_name + "_" + jobno + ".jpg";
                    result = ftpClient.storeFile(filename, in);
                    in.close();


                    String filePath = mSignature.save(mSignature);

//                    String data1 = getFilesDir() + File.separator + "MyFile.jpg";
//                    File file = savebitmap(mBitmap);
//                    Toast.makeText(context, "" + file.length(), Toast.LENGTH_SHORT).show();
//                    Log.d("filePaaath", filePath);
//                    Toast.makeText(context, "RAj" + filePath, Toast.LENGTH_SHORT).show();
//                    FileInputStream in1 = new FileInputStream(new File(filePath));
//                    String signaturefilename = "signature_" + passenger_name + "_" + jobno + ".jpg";
//                    result = ftpClient.storeFile(signaturefilename, in1);
//                    in.close();
                }

                if (result)
                    Log.v("upload result", "succeeded");

//                if (LLSignInfo.getVisibility() == View.GONE) {


//                }
                reply = ftpClient.getReplyCode();
                if (result)
                    Log.v("upload result", "succeeded");
                else {
                    Log.v("upload result", "failed");
                }
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            Log.v("count", "error");
            e.printStackTrace();
        }
        return result;
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }
}
