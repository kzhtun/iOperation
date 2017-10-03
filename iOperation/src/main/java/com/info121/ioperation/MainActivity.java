package com.info121.ioperation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.ioperation.fragment.BillingFragment;
import com.info121.ioperation.fragment.CalenderFragment;
import com.info121.ioperation.fragment.HomeFragment;
import com.info121.ioperation.fragment.iBiddingFragment;
import com.info121.ioperation.fragment.iOperationFragment;
import com.info121.ioperation.util.Constant;
import com.info121.ioperation.util.Util;
import com.info121.model.EventCalendarModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


    public static Context context;
    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private String userId;
    private ArrayList<EventCalendarModel> eventCalendarModelsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        userId = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).getString(Util.LOGIN_KEY, "");
        eventCalendarModelsArray = new ArrayList<>();

        // Initializing Toolbar and setting it as the actionbar
//        getSupportActionBar().setTitle("");
        // Initializing Toolbar and setting it as the actionbar
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        context = this;
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        String status = Util.ReadSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION);
        Log.e("STATUS M", "" + status);

        if (status.equalsIgnoreCase("ioperation")) {

            tx.replace(R.id.frame, new iOperationFragment());
            Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION, "");
            tx.commit();


        }


        if (status.equalsIgnoreCase("ibiding")) {

            tx.replace(R.id.frame, new iBiddingFragment());
            Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION, "");
            tx.commit();
        }
        if (status.equalsIgnoreCase("")) {
            tx.replace(R.id.frame, new HomeFragment());
            Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION, "");
            tx.commit();
        }
//        if(!status.equalsIgnoreCase("")) {
//            switch (status) {
//                case "ioperation":
//                    tx.replace(R.id.frame, new iOperationFragment());
//                    Util.WriteSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION,"");
//                    break;
//                case "ibiding":
//                    tx.replace(R.id.frame, new iBiddingFragment());
//                    Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION, "");
//                    break;
//            }
//        }else{
//            tx.replace(R.id.frame, new HomeFragment());
//        }
//        tx.commit();


        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

//        View header=navigationView.inflateHeaderView(R.layout.header);
        TextView uname = (TextView) navigationView.findViewById(R.id.usernameHeader);
        TextView email = (TextView) navigationView.findViewById(R.id.emailHeader);
        SharedPreferences preferences = context.getSharedPreferences(Util.SERVER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

        uname.setText(getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();
                Fragment fragment;
                android.support.v4.app.FragmentTransaction fragmentTransaction;
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.home:
//                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        fragment = new HomeFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.incomming:
//                        Toast.makeText(getApplicationContext(), "Incomming", Toast.LENGTH_SHORT).show();
                        fragment = new iBiddingFragment();
//                        fragment = new IncommingFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        return true;

                    // For rest of the options we just show a toast on click
                    case R.id.todo:
//                        Toast.makeText(getApplicationContext(), "todo", Toast.LENGTH_SHORT).show();
                        fragment = new iOperationFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.view_jobs:
//                        Toast.makeText(getApplicationContext(), "View Jobs", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), "Incomming", Toast.LENGTH_SHORT).show();
                        fragment = new iBiddingFragment();
//                        fragment = new IncommingFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.calender:
                        Toast.makeText(getApplicationContext(), "Calender", Toast.LENGTH_SHORT).show();
                        fragment = new CalenderFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();


                        return true;
                    case R.id.billing:
                        Toast.makeText(getApplicationContext(), "Billing", Toast.LENGTH_SHORT).show();
                        fragment = new BillingFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        return true;
//                    case R.id.feedback:
//                        Toast.makeText(getApplicationContext(), "FeedBack", Toast.LENGTH_SHORT).show();
//                        fragment = new FeedbackFragment();
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.frame, fragment);
//                        fragmentTransaction.commit();
//                        return true;
//                    case R.id.change_password:
//                        Toast.makeText(getApplicationContext(), "Change Password", Toast.LENGTH_SHORT).show();
//                        fragment = new ForgotPasswordFragment();
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.frame, fragment);
//                        fragmentTransaction.commit();
//                        return true;
                    case R.id.exit:
                        Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT).show();
                        showLogOutDialog(MainActivity.this, "Exit", "logout from the app?");
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_launcher, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


        new AddEventInCalendar().execute();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void showLogOutDialog(Context ctx, String title, String message) {

        try {

            AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            SharedPreferences preferences = context.getSharedPreferences(Util.SERVER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

                            String updated_time = preferences.getString(Util.SERVER_LAST_UPDATED_KEY, "-");
                            String server_one = preferences.getString(Util.SERVER_ONE_KEY, Constants.SERVER_URL);
                            String server_two = preferences.getString(Util.SERVER_TWO_KEY, Constants.SERVER_URL);

                            SharedPreferences settings = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE);
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


                            finish();
                            Intent intent = new Intent(getApplicationContext(), iOPLoginActivity.class);
                            startActivity(intent);

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


    @Override
    public void onBackPressed() {

    }


    class AddEventInCalendar extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            Uri baseUri;
            if (Build.VERSION.SDK_INT >= 8) {
                baseUri = Uri.parse("content://com.android.calendar/events");
            } else {
                baseUri = Uri.parse("content://calendar/events");
            }
            ContentResolver resolver = getContentResolver();

            deleteEvent(resolver, baseUri, 1);

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {


            Log.d("msg", "home screeen userid " + userId);
            Hashtable<String, String> parameter = new Hashtable<>();
            parameter.put("username", userId);


            return Util.connectSOAP(Constants.uRLIOPS_GetJobsForCalendar, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_GetJobsForCalendar, Constants.APIDotNetMethodNameGetJobsForCalendar, parameter);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (dialog != null) {
                dialog.dismiss();
            }


            Log.d("msg", " calendar event res  " + s);

            try {
                JSONObject jsonObjectMaib = new JSONObject(s);
                JSONArray jsonArrayjobsforcalendar = jsonObjectMaib.getJSONArray("jobsforcalendar");

                for (int i = 0; i < jsonArrayjobsforcalendar.length(); i++) {

                    EventCalendarModel eventCalendarModel = new EventCalendarModel();


                    JSONObject jsonObjectjobsforcalendar = jsonArrayjobsforcalendar.getJSONObject(i);
                    String jobdate = jsonObjectjobsforcalendar.getString("jobdate");
                    String jobtime = jsonObjectjobsforcalendar.getString("jobtime");
                    String description = jsonObjectjobsforcalendar.getString("description");

                    String eventid = jsonObjectjobsforcalendar.getString("eventid");
                    String location = jsonObjectjobsforcalendar.getString("location");
                    String title = jsonObjectjobsforcalendar.getString("title");
                    String mins = jsonObjectjobsforcalendar.getString("mins");


                    eventCalendarModel.setDescription(description);
                    eventCalendarModel.setJobdate(jobdate);
                    eventCalendarModel.setJobtime(jobtime);
                    eventCalendarModel.setEventid(eventid);
                    eventCalendarModel.setMins(mins);
                    eventCalendarModel.setTitle(title);
                    eventCalendarModel.setLocaton(location);


                    Long startTime = null;
                    eventCalendarModelsArray.add(eventCalendarModel);
                    String startDate = jobdate;
                    try {
                        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        Date date = dt1.parse(jobdate);
//                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);

                        startTime = date.getTime();
                        Log.d("msg", "start time " + startTime);

                    } catch (Exception e) {
                        Log.e("Exception", ">>> " + e.getMessage());
                        Toast.makeText(MainActivity.this, "EXC " + e, Toast.LENGTH_SHORT).show();
                    }


                    Uri baseUri;
                    if (Build.VERSION.SDK_INT >= 8) {
                        baseUri = Uri.parse("content://com.android.calendar/events");
                    } else {
                        baseUri = Uri.parse("content://calendar/events");
                    }


                    ContentResolver cr = getApplicationContext().getContentResolver();
                    final ContentValues event = new ContentValues();
                    event.put(CalendarContract.Events.CALENDAR_ID, 1);

                    event.put(CalendarContract.Events.TITLE, title);
                    event.put(CalendarContract.Events.DESCRIPTION, description);
                    event.put(CalendarContract.Events.EVENT_LOCATION, location);

                    event.put(CalendarContract.Events.DTSTART, startTime);
                    event.put(CalendarContract.Events.DTEND, startTime);
                    event.put(CalendarContract.Events.ALL_DAY, 0);   // 0 for false, 1 for true
                    event.put(CalendarContract.Events.HAS_ALARM, 1); // 0 for false, 1 for true

                    String timeZone = TimeZone.getDefault().getID();
                    event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);


                    Uri uri = cr.insert(baseUri, event);

                    //   context.getContentResolver().insert(baseUri, event);

                    // get the event ID that is the last element in the Uri


                    Uri baseUriRemider;
                    if (Build.VERSION.SDK_INT >= 8) {
                        baseUriRemider = Uri.parse("content://com.android.calendar/reminders");
                    } else {
                        baseUriRemider = Uri.parse("content://calendar/reminders");
                    }
                    Log.d("msg", "base uri " + baseUriRemider.getLastPathSegment());
                    long eventID = Long.parseLong(uri.getLastPathSegment());

                    ContentValues reminders = new ContentValues();
                    reminders.put(CalendarContract.Reminders.EVENT_ID, eventID);
                    reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                    reminders.put(CalendarContract.Reminders.MINUTES, mins);

                    Uri uri2 = cr.insert(baseUriRemider, reminders);
                    Log.d("msg", "base uri 2 " + uri2.getLastPathSegment());


                    //   context.getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, reminders);

//                    Calendar cal = Calendar.getInstance();
//                    Intent intent = new Intent(Intent.ACTION_EDIT);
//                    intent.setType("vnd.android.cursor.item/event");
//                    intent.putExtra("beginTime", startTime);
//                    intent.putExtra("allDay", true);
//                    intent.putExtra("rrule", "FREQ=YEARLY");
//                    intent.putExtra("endTime", startTime+60*60*1000*2);
//                    intent.putExtra("title", "A Test Event from android app"+i);
//                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", ">>>  error mainactivity" + e.getMessage());
            }


        }

        private void deleteEvent(ContentResolver resolver, Uri eventsUri, int calendarId) {
            Cursor cursor;
            if (android.os.Build.VERSION.SDK_INT <= 7) { //up-to Android 2.1
                cursor = resolver.query(eventsUri, new String[]{"_id"}, "Calendars._id=" + calendarId, null, null);
            } else { //8 is Android 2.2 (Froyo) (http://developer.android.com/reference/android/os/Build.VERSION_CODES.html)
                cursor = resolver.query(eventsUri, new String[]{"_id"}, "calendar_id=" + calendarId, null, null);
            }
            while (cursor.moveToNext()) {
                long eventId = cursor.getLong(cursor.getColumnIndex("_id"));
                int countDelete = resolver.delete(ContentUris.withAppendedId(eventsUri, eventId), null, null);
                Log.d("msg", "count delete " + countDelete);
            }
            cursor.close();
        }


    }
}
