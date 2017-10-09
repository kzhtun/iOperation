package com.info121.ioperations.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;


import com.info121.ioperations.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by archirayan on 4/29/2016.
 */
public class Utils {

    Context context;
    SharedPreferences sp;
    Location getLocation;

    public Utils(Context context) {
        this.context = context;
       // sp = context.getSharedPreferences(Constant.Prefrence, Context.MODE_PRIVATE);
    }


    public void setSharedPrefrence(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public String getSharedPrefrence(String key) {

        String defValue = "";
        String resposne = sp.getString(key, defValue);
        return resposne;

    }

    public void clearSharedPreferenceData() {
        sp.edit().clear().commit();

    }

    public static EditText EditTextFont(Context context,EditText editText)
    {
        Typeface face= Typeface.createFromAsset(context.getAssets(), "font/Calibri.ttf");
        editText.setTypeface(face);

        return editText;
    }

    public static String getResponseofPost(String URL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public static String getResponseofGet(String URL) {
        URL url;
        String response = "";
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            url = new URL(URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            Log.d("URL - ResponseCode", URL + " - " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private static String getPostDataString(HashMap<String, String> params) throws
            UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public String MakeServiceCall(String URLSTR) {
        StringBuilder response = null;


        try {
            response = new StringBuilder();
            URL url = new URL(URLSTR);
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()), 8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null) {
                    response.append(strLine);
                }
                input.close();
            }
        } catch (Exception e) {

        }
        return response.toString();

    }


    private boolean appInstalledOrNot(String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network",
                                    "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }



    public static void WriteSharePrefrence(Context context, String key,
                                           String value) {
        @SuppressWarnings("static-access")
//        SharedPreferences write_Data = context.getSharedPreferences(
//                Constant.SHRED_PR.SHARE_PREF, context.MODE_PRIVATE);
//        Editor editor = write_Data.edit();
//        editor.putString(key, values);
//        editor.apply();

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String ReadSharePrefrence(Context context, String key) {


//        SharedPreferences read_data = context.getSharedPreferences(
//                Constant.SHRED_PR.SHARE_PREF, context.MODE_PRIVATE);
//
//        return read_data.getString(key, "");

        String data;
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        data = preferences.getString(key, "");
        editor.commit();
        return data;
    }

    public  static void ClearPref(Context context)
    {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();

    }

    public static String getBase64ImageString(Bitmap bitmap) {
//        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        String imgResult = Base64.encodeToString(ba, Base64.DEFAULT);
        return imgResult;
    }


    public static String getAddressFromLatLng(Context context, double latitude, double longitude) {
        String address = "";
        try {
            Geocoder geo = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                address = "No address found";

            } else {
                if (addresses.size() > 0) {
                    address = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail

        }
        return address;
    }

    public void saveImageToCameraFolder(Bitmap img, String path) throws IOException {

        final String[] title = path.split("/");
        final String imageName = title[title.length - 1];

        Boolean isSDPresent = isExternalStorageAvailable();
        Log.e("isSDPresent", "" + isSDPresent);
        if (isSDPresent) {
            // yes SD-card is present
            final File root = new File(Environment.getExternalStorageDirectory()
                    + "/Pictures/" + context.getString(R.string.app_name));
//            File dir = new File(sdCard.getAbsolutePath() + "/Pictures");
            if (!root.exists()) root.mkdir();
            final File sourceFile = new File(path);
            final File destinationFile = new File(Environment.getExternalStorageDirectory()
                    + "/Pictures/" + context.getString(R.string.app_name), imageName);
            copyFile(sourceFile, destinationFile);
        } else {
            final File root = new File(Environment.getDataDirectory()
                    + "/Pictures/" + context.getString(R.string.app_name));
//            File dir = new File(sdCard.getAbsolutePath() + "/Pictures");
            if (!root.exists()) root.mkdir();
            final File sourceFile = new File(path);
            final File destinationFile = new File(Environment.getDataDirectory() + "/Pictures/" + context.getString(R.string.app_name), imageName);
            copyFile(sourceFile, destinationFile);
        }

    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());

            String[] paths = {destFile.getPath()};
            String[] mediaType = {"image/jpeg"};
            MediaScannerConnection.scanFile(context, paths, mediaType, null);
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }

    }

    public boolean isExternalStorageAvailable() {

        String state = Environment.getExternalStorageState();
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        if (mExternalStorageAvailable == true
                && mExternalStorageWriteable == true) {
            return true;
        } else {
            return false;
        }
    }

}

