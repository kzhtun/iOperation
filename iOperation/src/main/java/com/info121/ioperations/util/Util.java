package com.info121.ioperations.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.info121.ioperations.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Util {

    public static String TAG = "iOP APP LOGERROR";
    public static boolean DEBUG_MODE = true;

    public static String SHARED_PREFERENCES_KEY = "com.info121.ioperation.sharedpreferences.key";
    public static String SERVER_SHARED_PREFERENCES_KEY = "com.info121.ioperation.server.sharedpreferences.key";
    public static String EXIT_KEY = "com.info121.ioperation.exit.key";
    public static String LOGIN_KEY = "com.info121.ioperation.login.key";
    public static String SUPPLIER_KEY = "com.info121.ioperation.supplier.key";
    public static String HANDPHONE_KEY = "com.info121.ioperation.handphone.key";
    public static String PASSWORD_KEY = "com.info121.ioperation.password.key";

    public static String SERVER_LAST_UPDATED_KEY = "com.info121.ioperation.last.updated";
    public static String SERVER_ONE_KEY = "com.info121.ioperation.server.one";
    public static String SERVER_TWO_KEY = "com.info121.ioperation.server.two";

    public static String JOBNO_KEY = "com.info121.ioperation.jobno.key";
    public static String PASSENGER_KEY = "com.info121.ioperation.passenger.key";
    public static String EXIT_NOSHOW_KEY = "com.info121.ioperation.exit.noshow.key";
    public static String EXIT_SHOW_KEY = "com.info121.ioperation.exit.show.key";

    public static String PASSENGERNAME1_KEY = "com.info121.ioperation.passengername1.key";
    public static String PASSENGERNAME2_KEY = "com.info121.ioperation.passengername2.key";

    public static String gcm_register_id = "";

    /**
     * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
     */
    public static final String SERVER_URL = null;

    /**
     * Google API project id registered to use GCM.
     */
    public static final String SENDER_ID = "216657472162";

    /**
     * Intent used to display a message in the screen.
     */
    public static final String DISPLAY_MESSAGE_ACTION =
            "com.info121.ioperation.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }

    public static void modifiedLogTrace(String msg) {
        if (DEBUG_MODE) Log.d(TAG, msg);
    }


    public static void showAlertCommonDialogWithOKBtn(Context ctx, String title, String message) {

        try {

            AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            return;
                        }
                    });

            alertDialog.show();

        } catch (Exception e) {
            Util.modifiedLogTrace(e.getStackTrace().toString());
        }


    }


    public static void showAlertCommonDialogWithOKandCancelBtn(Context ctx, String title, String message) {

        try {

            AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            return;
                        }
                    });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            return;
                        }

                    });
            alertDialog.show();

        } catch (Exception e) {
            Util.modifiedLogTrace(e.getStackTrace().toString());
        }


    }


    public static void showCustomDialog(Context ctx, String msg) {
        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom1_dialog_layout);
        dialog.setTitle(ctx.getResources().getString(R.string.app_name));
        TextView text = (TextView) dialog.findViewById(R.id.textViewRegisterTnC);
        text.setText(msg);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonRegisterClose);
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public static String OpenConnection(Context context, String url) {
        String result = "";

        result = getHttpData(url);

        if (result == null) {
            return null;
        } else {
            return result;
        }

    }

    public static String getHttpData(String url) {

        url = url.replaceAll(" ", "%20");
        String result = null;
        int timeOutMS = 1000 * 90;
        try {
            HttpParams my_httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(my_httpParams, 90000);
            HttpConnectionParams.setSoTimeout(my_httpParams, timeOutMS);
            DefaultHttpClient client = new DefaultHttpClient(my_httpParams);
            URI uri = new URI(url);

            HttpGet httpGetRequest = new HttpGet(uri);
            HttpResponse response = client.execute(httpGetRequest);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                response.getEntity().writeTo(os);
                result = os.toString();

            }

        } catch (SocketTimeoutException e) {
            Util.modifiedLogTrace(e.getStackTrace().toString());
            return "SocketTimeoutException";
        } catch (ClientProtocolException e) {
            Util.modifiedLogTrace(e.getStackTrace().toString());
        } catch (IOException e) {
            Util.modifiedLogTrace(e.getStackTrace().toString());
            return "IOException";
        } catch (URISyntaxException e) {
            Util.modifiedLogTrace(e.getStackTrace().toString());
        } catch (Exception e) {
            Util.modifiedLogTrace(e.getStackTrace().toString());
        }
        return result;
    }


    public static String postDataConn(String url, ArrayList<BasicNameValuePair> vp) {

        String result = null;
        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            for (int i = 0; i < vp.size(); i++) {
                postParameters.add(vp.get(i));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                    postParameters);

            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();

        } catch (Exception e) {
            Util.modifiedLogTrace(e.getStackTrace().toString());
            result = "Unknown Error";
        }

        return result;

    }


    public static String connectSOAP(String url, String soapNamespace, String soapAction, String soapMethod, Hashtable<String, String> HT) {

        String result = null;
        Log.d("msg","url "+url);

        SoapObject request = new SoapObject(soapNamespace, soapMethod);

        if (HT != null) {
            Enumeration<String> en = HT.keys();
            while (en.hasMoreElements()) {

                Object k = en.nextElement();
                Object v = HT.get(k);
                request.addProperty(k.toString(), v);
                System.out.println("key = " + k.toString() + "; value = " + v);
            }
        }




        SoapSerializationEnvelope envelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER11);

        //envelope.bodyOut = request;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(url,60000);
        //envelope.getResponse();


        try {

            androidHttpTransport.call(soapAction, envelope);

            if (envelope.bodyIn instanceof SoapFault) {

                result = ((SoapFault) envelope.bodyIn).faultstring;
                Log.e("RESULT1", "" + result);


            } else {

                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                result = resultsRequestSOAP.getProperty(0).toString();
                Log.e("RESULT1", "" + result);
            }



        } catch (Exception e) {
            e.printStackTrace();
            Log.d("msg","error  "+e.getMessage());
        }
        Log.e("RM",""+result);
        return result;






    }




    public static String connectSOAPUploadFile(String url, String soapNamespace, String soapAction, String soapMethod, Hashtable<String, String> HT, String pathPhoto, String pathSignature) {

        String result = null;

        SoapObject request = new SoapObject(soapNamespace, soapMethod);

        if (HT != null) {
            Enumeration<String> en = HT.keys();
            while (en.hasMoreElements()) {

                Object k = en.nextElement();
                Object v = HT.get(k);
                request.addProperty(k.toString(), v);
                System.out.println("key = " + k + "; value = " + v);
            }
        }

        SoapSerializationEnvelope envelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER11);

        //envelope.bodyOut = request;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);

        try {
            androidHttpTransport.callUploadFile(soapAction, envelope, pathPhoto, pathSignature);

            if (envelope.bodyIn instanceof SoapFault) {
                result = ((SoapFault) envelope.bodyIn).faultstring;
                Log.e("RESULT2", "" + result);
            } else {

                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                result = resultsRequestSOAP.getProperty(0).toString();
                Log.e("RESULT2", "" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }



    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    /**
     * Register this account/device pair within the server.
     *
     * @return whether the registration succeeded or not.
     */
    public static boolean register(final Context context, final String regId) {
        Log.i(TAG, "registering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL + "/register";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register it in the
        // demo server. As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                displayMessage(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
                post(serverUrl, params);

                // KZHTUN Remove GCM Related
                // GCMRegistrar.setRegisteredOnServer(context, true);

                String message = context.getString(R.string.server_registered);
                Util.displayMessage(context, message);
                return true;
            } catch (IOException e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i, e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return false;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        Util.displayMessage(context, message);
        return false;

    }

    /**
     * Unregister this account/device pair within the server.
     */
    public static void unregister(final Context context, final String regId) {
        Log.i(TAG, "unregistering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        try {
            post(serverUrl, params);

            // KZHTUN Remove GCM Related
            // GCMRegistrar.setRegisteredOnServer(context, false);

            String message = context.getString(R.string.server_unregistered);
            Util.displayMessage(context, message);
        } catch (IOException e) {
            // At this point the device is unregistered from GCM, but still
            // registered in the server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
            Util.displayMessage(context, message);
        }
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params   request parameters.
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint, Map<String, String> params)
            throws IOException {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void setTextViewFontSizeBasedOnScreenDensity(
            Activity activity, TextView tv, double size) {

        DisplayMetrics dMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        final float WIDE = activity.getResources().getDisplayMetrics().widthPixels;
        int valueWide = (int) (WIDE / size / (dMetrics.scaledDensity));
        tv.setTextSize(valueWide);
    }

    public static void setEditTextFontSizeBasedOnScreenDensity(
            Activity activity, EditText et, double size) {

        DisplayMetrics dMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        final float WIDE = activity.getResources().getDisplayMetrics().widthPixels;
        int valueWide = (int) (WIDE / size / (dMetrics.scaledDensity));
        et.setTextSize(valueWide);
    }

    public static void setTextViewFontSizeBasedOnScreenDensity(
            Activity activity, TextView tv) {

        DisplayMetrics dMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        final float WIDE = activity.getResources().getDisplayMetrics().widthPixels;
        int valueWide = (int) (WIDE / 19.0f / (dMetrics.scaledDensity));
        tv.setTextSize(valueWide);
    }

    public static void setEditTextFontSizeBasedOnScreenDensity(
            Activity activity, EditText et) {

        DisplayMetrics dMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        final float WIDE = activity.getResources().getDisplayMetrics().widthPixels;
        int valueWide = (int) (WIDE / 19.0f / (dMetrics.scaledDensity));
        et.setTextSize(valueWide);
    }

    public static void closeKeyboard(Activity c, IBinder windowToken) {
        c.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
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




}
