package com.info121.ioperation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.constant.StaticLocationData;
import com.info121.ioperation.dialog.ShowPhotoDetailDialog;
import com.info121.ioperation.fragment.iOperationFragment;
import com.info121.ioperation.util.BitmapHelper;
import com.info121.ioperation.util.GPSTracker;
import com.info121.ioperation.util.Parser;
import com.info121.ioperation.util.Util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class NoShowClientActivity extends RootWithIdleCheckActivity implements OnClickListener {

    private Button btnContinue, buttonBack;
    private TextView TVLocation;
    private TextView TVName;
    private TextView TVDate;
    private TextView TVStart;
    private ProgressDialog progress;
    private Context context;
    private EditText etReason;

    private List<String> listreason;
    private Spinner spinListJobNo;
    private ArrayAdapter<String> dataAdapter;

    private String addressText = "";
    private String jobno;
    private String passenger_name;
    private ImageView imgCamera;
    private int posSpin = 0;

    int TAKE_PHOTO_CODE = 0;
    public static int countphoto = 0;
    String dir;
    public int count = 1;
    public String current = null;
    private Bitmap mBitmap;
    private String feedBack = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.no_show_client_layout);
        context = this;

        jobno = getIntent().getStringExtra(Util.JOBNO_KEY);
        passenger_name = getIntent().getStringExtra(Util.PASSENGER_KEY);

        btnContinue = (Button) findViewById(R.id.buttonNoShowContinue);
        btnContinue.setOnClickListener(this);

        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);

//		TVLocation = (TextView)findViewById(R.id.textViewNoShowLocation);
        TVName = (TextView) findViewById(R.id.textViewNoShowName);
//		TVDate = (TextView)findViewById(R.id.textViewNoShowDate);
//		TVStart = (TextView)findViewById(R.id.textViewNoShowStart);
        etReason = (EditText) findViewById(R.id.editTextNoShow);

        imgCamera = (ImageView) findViewById(R.id.imageViewShowCamera);
        imgCamera.setOnClickListener(this);

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        TVName.setText("Welcome " + passenger_name);
//		TVDate.setText(mydate);
//		TVStart.setText("Start : 13:00PM Till 13:30PM");


        spinListJobNo = (Spinner) findViewById(R.id.spinnerFeedbackReferenceJobNo);
        listreason = new ArrayList<String>();
        listreason.add("No Show");
        listreason.add("Flight Delay");
        listreason.add("Flight Cancel");
        listreason.add("Others");
        dataAdapter = new ArrayAdapter<String>(context,
                R.layout.spinner_item, R.id.spinner_item, listreason);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinListJobNo.setAdapter(dataAdapter);


        spinListJobNo.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                posSpin = pos;

                if (pos == 3) {
                    etReason.setVisibility(View.VISIBLE);
                    etReason.setText("");
                } else {
                    etReason.setVisibility(View.GONE);
                    etReason.setText(listreason.get(pos));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        GPSTracker gps = new GPSTracker(NoShowClientActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            addressText = getCompleteAddressString(latitude, longitude);
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

//        try {
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//
//
//            LocationManager locationManager = new LocationManager(
//
//           Location loctemp = new Location("dummyprovider");
////            loctemp.setLatitude(Double.parseDouble(StaticLocationData.latitude));
////            loctemp.setLongitude(Double.parseDouble(StaticLocationData.longitude));
////            loctemp.setAccuracy(Float.parseFloat(StaticLocationData.accuracy));
//
//           // Location loc = loctemp;
//            List<Address> addresses = null;
//            // Call the synchronous getFromLocation() method by passing in the lat/long values.
//            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
//
//            if (addresses != null && addresses.size() > 0) {
//                Address address = addresses.get(0);
//                // Format the first line of address (if available), city, and country name.
//
//                if (address.getAddressLine(0) != null && address.getLocality() != null && address.getCountryName() != null) {
//
//                    addressText = String.format("%s, %s, %s",
//                            address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
//                            address.getLocality(),
//                            address.getCountryName());
//
//                } else if (address.getAddressLine(0) == null && address.getLocality() != null && address.getCountryName() != null) {
//
//                    addressText = String.format("%s, %s",
//                            address.getLocality(),
//                            address.getCountryName());
//                } else if (address.getAddressLine(0) != null && address.getLocality() == null && address.getCountryName() != null) {
//
//                    addressText = String.format("%s, %s",
//                            address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
//                            address.getCountryName());
//                } else {
//                    addressText = String.format("%s, %s, %s",
//                            address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
//                            address.getLocality(),
//                            address.getCountryName());
//                }
//
//
//                // Update the UI via a message handler.
////			    TVLocation.setText("Location : "+addressText);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
////			TVLocation.setText("Location : Unable to parse location. . .");
//            addressText = "no location";
//        }


    // by KZHTUN on 02102017

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);

                strAdd =  addresses.get(0).getFeatureName();
                strAdd += ", " + addresses.get(0).getSubLocality();
                strAdd += ", " + addresses.get(0).getLocality();
                strAdd += ", " + addresses.get(0).getCountryName();


            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return strAdd;
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonNoShowContinue:
                feedBack = etReason.getText().toString();
                new processNoShowAsyncTask().execute();
                break;

            case R.id.buttonBack:
                finish();
                break;

            case R.id.imageViewShowCamera:


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

                        for (Camera.Size tempsize : sizes) {
                            double pixel = (tempsize.width * tempsize.height) / 1000000;

                            if (pixel < 1.3) {
                                outputX = tempsize.width;
                                outputY = tempsize.height;
                                break;
                            }
                        }
                        camera.release();
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


//    public void checkIfLocationEnabled() {
//
//        try {
//            LocationManager lm = null;
//            boolean gps_enabled = false, network_enabled = false;
//            if (lm == null)
//                lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//            try {
//                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            } catch (Exception ex) {
//            }
//            try {
//                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//            } catch (Exception ex) {
//            }
//
//            if (!gps_enabled) {
//                addressText = "no location";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    private class processNoShowAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(context, "NoShow",
                    "Loading . . .", true);

        }

        @Override
        protected String doInBackground(Void... v) {

            uploadingFTPImage();

            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("jobno", jobno);
                parameter.put("PassengerName", passenger_name);

                if (posSpin == 3) {
                    parameter.put("Reason", listreason.get(posSpin));
                    parameter.put("FeedBack", etReason.getText().toString());
                } else
                    parameter.put("Reason", listreason.get(posSpin));
                //checkIfLocationEnabled();
                parameter.put("Location", addressText);
                parameter.put("IOpsUserName", getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("SignatureImgFileName", "");
                parameter.put("PhotoPhotoFileName", "photo_" + passenger_name + "_" + jobno + ".jpg");

            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsSaveNoShowPassenger, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsSaveNoShowPassenger, Constants.APIDotNetMethodNameSaveNoShowPassenger, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            if (result != null) {
                try {

                    String temp = Parser.getJSONAcceptRejectResult(result);
                    Util.showAlertCommonDialogWithOKBtn(iOpMainViewPagerActivity.thisactivity, "Success", "No Show data sent to the server");
                    SharedPreferences preferences = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(Util.EXIT_NOSHOW_KEY, true);
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
            imgCamera.setImageBitmap(imageReturned);


        }
    }


    public String ImageToBase64() throws IOException {
        byte[] bytes = fileToByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public byte[] fileToByteArray() throws IOException {

        File imagefile = new File(getFilesDir() + File.separator + "MyFile.jpg");
        byte[] data = new byte[(int) imagefile.length()];
        FileInputStream fis = new FileInputStream(imagefile);
        fis.read(data);
        fis.close();
        return data;
    }

    public boolean uploadingFTPImage() {

        boolean result = false;
//		try
//		{
//		    SimpleFTP ftp = new SimpleFTP();
//
//		    // Connect to an FTP server
//		    ftp.connect("dev.thecellcity.com", 32, "pasca", "pasca1309");
//
//		    // Set binary mode.
//		    ftp.bin();
//
//		    // Change to a new working directory on the FTP server.
//		    ftp.cwd("public_html");
//
//		    // Upload some files.
//		    //ftp.stor(new File("webcam.jpg"));
//		    //ftp.stor(new File("comicbot-latest.png"));
//
//		    // You can also upload from an InputStream, e.g.
//		    result = ftp.stor(new FileInputStream(new File(getFilesDir() + File.separator + "MyFile.png")), "photo_"+passenger_name+"_"+jobno+".png");
//		    result = ftp.stor(new FileInputStream(new File(mSignature.save(mSignature))), "signature_"+passenger_name+"_"+jobno+".png");
//		    //ftp.stor(someSocket.getInputStream(), "blah.dat");
//
//		    // Quit from the FTP server.
//		    ftp.disconnect();
//		}
//		catch (IOException e)
//		{
//		    e.printStackTrace();
//		}


        FTPClient ftpClient = null;

        try {
            ftpClient = new FTPClient();
            ftpClient.connect(InetAddress.getByName(Constants.BASE_PHOTO_URL), 21);

            int reply = ftpClient.getReplyCode();

            if (ftpClient.login("ipos", "iposftp")) {

                ftpClient.enterLocalPassiveMode(); // important!
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                if (imageReturned != null) {
                    String data = getFilesDir() + File.separator + "MyFile.jpg";
                    FileInputStream in = new FileInputStream(new File(data));
                    result = ftpClient.storeFile("photo_" + passenger_name + "_" + jobno + ".jpg", in);
                    in.close();
                }

//	            if(LLSignInfo.getVisibility() == View.GONE){
//	            String data = mSignature.save(mSignature);
//	            FileInputStream in = new FileInputStream(new File(data));
//	            result = ftpClient.storeFile("signature_"+passenger_name+"_"+jobno+".png", in);
//	            in.close();
//	            }


                reply = ftpClient.getReplyCode();

                if (result)
                    Log.v("upload result", "succeeded");
                ftpClient.logout();
                ftpClient.disconnect();

            }
        } catch (Exception e) {
            Log.v("count", "error");
            e.printStackTrace();
        }


        return result;

    }


}
