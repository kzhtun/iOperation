package com.info121.ioperation.util;

import android.util.Log;

import com.info121.model.City_Model;
import com.info121.model.Country_Model;
import com.info121.model.ReAssign_Model;
import com.info121.model.iBid_Model;
import com.info121.model.iMessage;
import com.info121.model.iNewMessage;
import com.info121.model.iOP_Model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {


    public static String initXMLParser(String str) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        String result = null;

        xpp.setInput(new StringReader(str));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            } else if (eventType == XmlPullParser.START_TAG) {
                System.out.println("Start tag " + xpp.getName());
            } else if (eventType == XmlPullParser.END_TAG) {
                System.out.println("End tag " + xpp.getName());
            } else if (eventType == XmlPullParser.TEXT) {
                result = xpp.getText();
                System.out.println("Text " + result);
            }
            eventType = xpp.next();
        }
        System.out.println("End document");

        return result;
    }


    public static String getJSONSaveFeedbackResult(String jsontext) throws Exception {

        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("response");

        return txtObj.getJSONObject(0).getString("response");
    }

    public static String getJSONSaveSupplierResult(String jsontext) throws Exception {

        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("response");

        return txtObj.getJSONObject(0).getString("response");
    }

    public static String getJSONAcceptRejectResult(String jsontext) throws Exception {

        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("response");

        return txtObj.getJSONObject(0).getString("response");
    }


    public static String getJSONisMainContactPerson(String jsontext) throws Exception {

        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("validation");

        return txtObj.getJSONObject(0).getString("ismain");
    }


    public static String getJSONValidateSupplierResult(String jsontext) throws Exception {

        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("validation");

        return txtObj.getJSONObject(0).getString("valid");
    }

    public static String getJSONValidateUserResult(String jsontext) throws Exception {

        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("validation");

        return txtObj.getJSONObject(0).getString("valid");
    }

    public static ArrayList<ReAssign_Model> getJSONReAssignUserListResult(String jsontext) throws Exception {

        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("suppleruserlisttoassign");

        ArrayList<ReAssign_Model> rmlist = new ArrayList<ReAssign_Model>();

        for (int i = 0; i < txtObj.length(); i++)
            rmlist.add(new ReAssign_Model(txtObj.getJSONObject(i).getString("username"), txtObj.getJSONObject(i).getString("telno")));

        return rmlist;
    }

    public static String getJSONValidateUserSupplierResult(String jsontext) throws Exception {

        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("validation");

        return txtObj.getJSONObject(0).getString("supplier");
    }

    public static ArrayList<Country_Model> getJSONCountryResult(String jsontext) throws Exception {
        ArrayList<Country_Model> temp = new ArrayList<Country_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("country");
        for (int i = 0; i < txtObj.length(); i++) {
            temp.add(new Country_Model(txtObj.getJSONObject(i).getString("countrycode"), txtObj.getJSONObject(i).getString("country")));
        }
        return temp;
    }

    public static ArrayList<City_Model> getJSONCityResult(String jsontext) throws Exception {
        ArrayList<City_Model> temp = new ArrayList<City_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("city");
        for (int i = 0; i < txtObj.length(); i++) {
            temp.add(new City_Model(txtObj.getJSONObject(i).getString("citycode"), txtObj.getJSONObject(i).getString("city")));
        }
        return temp;
    }


    public static ArrayList<iOP_Model> getJSONSupplierJobAssignListResultNew(String jsontext, String filter) throws Exception {
        ArrayList<iOP_Model> temp = new ArrayList<iOP_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("assignedjoblist");
        for (int i = 0; i < txtObj.length(); i++) {
            iOP_Model tempModel = new iOP_Model();
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setClient(txtObj.getJSONObject(i).getString("client"));
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setAlight(txtObj.getJSONObject(i).getString("alight"));
            tempModel.setPickup(txtObj.getJSONObject(i).getString("pickup"));
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setAmount(txtObj.getJSONObject(i).getString("price"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setPhone(txtObj.getJSONObject(i).getString("phone"));
            tempModel.setDescription(txtObj.getJSONObject(i).getString("description"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));

            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));

            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));

            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            try {
                tempModel.setWelcometext1(txtObj.getJSONObject(i).getString("welcometext1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tempModel.setWelcometext2(txtObj.getJSONObject(i).getString("welcometext2"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e("STATUS", "" + txtObj.getJSONObject(i).getString("status"));
            Log.e("filter", "" + filter);
            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase(filter) == 0)
                temp.add(tempModel);
        }
        return temp;
    }


    public static ArrayList<iOP_Model> getJSONSupplierJobAssignListResult(String jsontext, String filter) throws Exception {

        ArrayList<iOP_Model> temp = new ArrayList<iOP_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("assignedjoblist");
        for (int i = 0; i < txtObj.length(); i++) {


            iOP_Model tempModel = new iOP_Model();
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setArrDep(txtObj.getJSONObject(i).getString("arrdepdate"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setName(txtObj.getJSONObject(i).getString("client"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setSelect(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));


            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            try {
                tempModel.setWelcometext1(txtObj.getJSONObject(i).getString("welcometext1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tempModel.setWelcometext2(txtObj.getJSONObject(i).getString("welcometext2"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase(filter) == 0)
                temp.add(tempModel);
        }
        return temp;
    }

    public static int getJSONSupplierJobAssignListResultSize(String jsontext, String filter) throws Exception {
        int count = 0;
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("assignedjoblist");
        for (int i = 0; i < txtObj.length(); i++) {
            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase(filter) == 0)
                count = count + 1;
        }

        return count;
    }

    public static ArrayList<iOP_Model> getJSONSupplierShowServedListResult(String jsontext, String filter) throws Exception {
        ArrayList<iOP_Model> temp = new ArrayList<iOP_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("showandservedjoblist");
        for (int i = 0; i < txtObj.length(); i++) {


            iOP_Model tempModel = new iOP_Model();
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setArrDep(txtObj.getJSONObject(i).getString("arrdepdate"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setName(txtObj.getJSONObject(i).getString("client"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setSelect(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));
            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            try {
                tempModel.setWelcometext1(txtObj.getJSONObject(i).getString("welcometext1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tempModel.setWelcometext2(txtObj.getJSONObject(i).getString("welcometext2"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase(filter) == 0)
                temp.add(tempModel);
        }
        return temp;
    }

    public static ArrayList<iOP_Model> getJSONSupplierShowServedListResultNew(String jsontext, String filter) throws Exception {
        ArrayList<iOP_Model> temp = new ArrayList<iOP_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        Log.e("rootObj", "" + rootObj);
        JSONArray txtObj = rootObj.getJSONArray("showandservedjoblist");
        for (int i = 0; i < txtObj.length(); i++) {
            iOP_Model tempModel = new iOP_Model();
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setClient(txtObj.getJSONObject(i).getString("client"));
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setAlight(txtObj.getJSONObject(i).getString("alight"));
            tempModel.setPickup(txtObj.getJSONObject(i).getString("pickup"));
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setAmount(txtObj.getJSONObject(i).getString("price"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setPhone(txtObj.getJSONObject(i).getString("phone"));
            tempModel.setDescription(txtObj.getJSONObject(i).getString("description"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));

            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));

            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));

            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            Log.e("STATUS", "" + txtObj.getJSONObject(i).getString("status"));
            Log.e("FILTER", "" + filter);
            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase("JOB SERVED") == 0) {
                temp.add(tempModel);
                Log.e("IF", "@@@@@@@@@@@@@@@@@@");
            } else {
                Log.e("ELSE", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
        }
        Log.e("TEMP", "" + temp);
        return temp;
    }


    public static int getJSONSupplierShowServedListResultSize(String jsontext, String filter) throws Exception {
        int count = 0;
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("showandservedjoblist");
        for (int i = 0; i < txtObj.length(); i++) {
            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase("JOB SERVED") == 0)
                count = count + 1;
        }

        return count;
    }

    public static ArrayList<iOP_Model> getJSONSupplierNoShowServedListResultNew(String jsontext, String filter) throws Exception {
        ArrayList<iOP_Model> temp = new ArrayList<iOP_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("noshowandservedjoblist");
        for (int i = 0; i < txtObj.length(); i++) {
            iOP_Model tempModel = new iOP_Model();
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setClient(txtObj.getJSONObject(i).getString("client"));
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setAlight(txtObj.getJSONObject(i).getString("alight"));
            tempModel.setPickup(txtObj.getJSONObject(i).getString("pickup"));
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setAmount(txtObj.getJSONObject(i).getString("price"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setPhone(txtObj.getJSONObject(i).getString("phone"));
            tempModel.setDescription(txtObj.getJSONObject(i).getString("description"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));

            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));

            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));

            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            Log.e("STATUS", "" + txtObj.getJSONObject(i).getString("status"));
            Log.e("FILTER", "" + filter);
            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase("JOB SERVED") == 0) {
                temp.add(tempModel);
                Log.e("IF", "@@@@@@@@@@@@@@@@@@");
            } else {
                Log.e("ELSE", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
        }
        return temp;
    }

    public static ArrayList<iOP_Model> getJSONSupplierNoShowServedListResult(String jsontext, String filter) throws Exception {

        ArrayList<iOP_Model> temp = new ArrayList<iOP_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("noshowandservedjoblist");
        Log.e("NoShowSize", "" + txtObj.length());
        for (int i = 0; i < txtObj.length(); i++) {

            iOP_Model tempModel = new iOP_Model();
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setArrDep(txtObj.getJSONObject(i).getString("arrdepdate"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setName(txtObj.getJSONObject(i).getString("client"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setSelect(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));
            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            try {
                tempModel.setWelcometext1(txtObj.getJSONObject(i).getString("welcometext1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tempModel.setWelcometext2(txtObj.getJSONObject(i).getString("welcometext2"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e("STATUSMMMM", "" + txtObj.getJSONObject(i).getString("status"));
            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase(filter) == 0)
                temp.add(tempModel);
        }
        return temp;
    }

    public static ArrayList<iOP_Model> getJSONGetSupplierRejectedJobsListResultNew(String jsontext, String filter) throws Exception {
        ArrayList<iOP_Model> temp = new ArrayList<iOP_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("rejectedjoblist");
        for (int i = 0; i < txtObj.length(); i++) {
            iOP_Model tempModel = new iOP_Model();
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setClient(txtObj.getJSONObject(i).getString("client"));
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setAlight(txtObj.getJSONObject(i).getString("alight"));
            tempModel.setPickup(txtObj.getJSONObject(i).getString("pickup"));
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setAmount(txtObj.getJSONObject(i).getString("price"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setPhone(txtObj.getJSONObject(i).getString("phone"));
            tempModel.setDescription(txtObj.getJSONObject(i).getString("description"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));

            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));

            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));

            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            try {
                tempModel.setWelcometext1(txtObj.getJSONObject(i).getString("welcometext1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tempModel.setWelcometext2(txtObj.getJSONObject(i).getString("welcometext2"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase(filter) == 0)
                temp.add(tempModel);
        }
        return temp;

    }

    public static ArrayList<iOP_Model> getJSONGetSupplierRejectedJobsListResult(String jsontext, String filter) throws Exception {
        ArrayList<iOP_Model> temp = new ArrayList<iOP_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("rejectedjoblist");
        for (int i = 0; i < txtObj.length(); i++) {


            iOP_Model tempModel = new iOP_Model();
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setArrDep(txtObj.getJSONObject(i).getString("arrdepdate"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setName(txtObj.getJSONObject(i).getString("client"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setSelect(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));
            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            try {
                tempModel.setWelcometext1(txtObj.getJSONObject(i).getString("welcometext1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tempModel.setWelcometext2(txtObj.getJSONObject(i).getString("welcometext2"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase(filter) == 0)
                temp.add(tempModel);
        }
        return temp;
    }

    public static int getJSONSupplierNoShowServedListResultSize(String jsontext, String filter) throws Exception {
        int count = 0;
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("noshowandservedjoblist");
        for (int i = 0; i < txtObj.length(); i++) {
            if (txtObj.getJSONObject(i).getString("status").compareToIgnoreCase(filter) == 0)
                count = count + 1;
        }

        return count;
    }

    public static ArrayList<iOP_Model> getJSONSupplierJobAcceptedListResult(String jsontext) throws Exception {
        ArrayList<iOP_Model> temp = new ArrayList<iOP_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("joblist");
        for (int i = 0; i < txtObj.length(); i++) {

            //if(txtObj.getJSONObject(i).getString("status").compareToIgnoreCase("JOB ACCEPTED")==0){

            iOP_Model tempModel = new iOP_Model();
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setArrDep(txtObj.getJSONObject(i).getString("arrdepdate"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setName(txtObj.getJSONObject(i).getString("client"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setSelect(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));
            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            try {
                tempModel.setWelcometext1(txtObj.getJSONObject(i).getString("welcometext1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tempModel.setWelcometext2(txtObj.getJSONObject(i).getString("welcometext2"));
            } catch (Exception e) {
                e.printStackTrace();
            }


            temp.add(tempModel);
            //}
        }
        return temp;
    }


    public static ArrayList<iBid_Model> getJSONSupplierBidNewListResultNew(String jsontext) throws Exception {
        ArrayList<iBid_Model> temp = new ArrayList<iBid_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidnewlist");
        for (int i = 0; i < txtObj.length(); i++) {
            iBid_Model tempModel = new iBid_Model();
            tempModel.setBidNo(txtObj.getJSONObject(i).getString("bidno"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setClient(txtObj.getJSONObject(i).getString("client"));
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setAlight(txtObj.getJSONObject(i).getString("alight"));
            Log.e("BID REJECT", "===============START====================");
            Log.e("ALIGHT", "" + txtObj.getJSONObject(i).getString("alight"));
            tempModel.setPickup(txtObj.getJSONObject(i).getString("pickup"));
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setPrice(txtObj.getJSONObject(i).getString("price"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setPhone(txtObj.getJSONObject(i).getString("phone"));
            Log.e("PHONE", "" + txtObj.getJSONObject(i).getString("phone"));
            tempModel.setDescription(txtObj.getJSONObject(i).getString("description"));
            Log.e("DESC", "" + txtObj.getJSONObject(i).getString("description"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            Log.e("BID REJECT", "==============ENND=====================");
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));

            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));

            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));
            temp.add(tempModel);
        }
        return temp;
    }

    public static ArrayList<iBid_Model> getJSONSupplierBidNewListResult(String jsontext) throws Exception {
        ArrayList<iBid_Model> temp = new ArrayList<iBid_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidnewlist");
        for (int i = 0; i < txtObj.length(); i++) {

            iBid_Model tempModel = new iBid_Model();
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setArrDep(txtObj.getJSONObject(i).getString("arrdepdate"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setName(txtObj.getJSONObject(i).getString("client"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setSelect(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setBidNo(txtObj.getJSONObject(i).getString("bidno"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            temp.add(tempModel);
        }
        return temp;
    }


    public static int getJSONSupplierBidNewListResultSize(String jsontext) throws Exception {

        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidnewlist");

        return txtObj.length();
    }


    public static ArrayList<iBid_Model> getJSONSupplierBidAcceptedListResultNew(String jsontext) throws Exception {
        ArrayList<iBid_Model> temp = new ArrayList<iBid_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidacceptlist");
        for (int i = 0; i < txtObj.length(); i++) {
            iBid_Model tempModel = new iBid_Model();
            tempModel.setBidNo(txtObj.getJSONObject(i).getString("bidno"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setClient(txtObj.getJSONObject(i).getString("client"));
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setAlight(txtObj.getJSONObject(i).getString("alight"));
            Log.e("BID REJECT", "===============START====================");
            Log.e("ALIGHT", "" + txtObj.getJSONObject(i).getString("alight"));
            tempModel.setPickup(txtObj.getJSONObject(i).getString("pickup"));
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setPrice(txtObj.getJSONObject(i).getString("price"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setPhone(txtObj.getJSONObject(i).getString("phone"));
            Log.e("PHONE", "" + txtObj.getJSONObject(i).getString("phone"));
            tempModel.setDescription(txtObj.getJSONObject(i).getString("description"));
            Log.e("DESC", "" + txtObj.getJSONObject(i).getString("description"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            Log.e("BID REJECT", "==============ENND=====================");
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));

            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));

            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));
            temp.add(tempModel);
        }
        return temp;
    }


    public static ArrayList<iBid_Model> getJSONSupplierBidAcceptedListResult(String jsontext) throws Exception {
        ArrayList<iBid_Model> temp = new ArrayList<iBid_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidacceptlist");
        for (int i = 0; i < txtObj.length(); i++) {

            iBid_Model tempModel = new iBid_Model();
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setArrDep(txtObj.getJSONObject(i).getString("arrdepdate"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setName(txtObj.getJSONObject(i).getString("client"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setSelect(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setBidNo(txtObj.getJSONObject(i).getString("bidno"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            temp.add(tempModel);
        }
        return temp;
    }

    public static int getJSONSupplierBidAcceptedListResultSize(String jsontext) throws Exception {

        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidacceptlist");

        return txtObj.length();
    }

    public static ArrayList<iBid_Model> getJSONSupplierBidRejectedListResultNew(String jsontext) throws Exception {
        ArrayList<iBid_Model> temp = new ArrayList<iBid_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidrejectlist");
        for (int i = 0; i < txtObj.length(); i++) {
            iBid_Model tempModel = new iBid_Model();
            tempModel.setBidNo(txtObj.getJSONObject(i).getString("bidno"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setClient(txtObj.getJSONObject(i).getString("client"));
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setAlight(txtObj.getJSONObject(i).getString("alight"));
            Log.e("BID REJECT", "===============START====================");
            Log.e("ALIGHT", "" + txtObj.getJSONObject(i).getString("alight"));
            tempModel.setPickup(txtObj.getJSONObject(i).getString("pickup"));
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setPrice(txtObj.getJSONObject(i).getString("price"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setPhone(txtObj.getJSONObject(i).getString("phone"));
            Log.e("PHONE", "" + txtObj.getJSONObject(i).getString("phone"));
            tempModel.setDescription(txtObj.getJSONObject(i).getString("description"));
            Log.e("DESC", "" + txtObj.getJSONObject(i).getString("description"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            Log.e("BID REJECT", "==============ENND=====================");
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));

            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));

            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));
            temp.add(tempModel);
        }
        return temp;
    }


    public static ArrayList<iBid_Model> getJSONSupplierBidRejectedListResult(String jsontext) throws Exception {
        ArrayList<iBid_Model> temp = new ArrayList<iBid_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidrejectlist");
        for (int i = 0; i < txtObj.length(); i++) {
            iBid_Model tempModel = new iBid_Model();
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setArrDep(txtObj.getJSONObject(i).getString("arrdepdate"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setName(txtObj.getJSONObject(i).getString("client"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setSelect(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setBidNo(txtObj.getJSONObject(i).getString("bidno"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            temp.add(tempModel);
        }
        return temp;
    }

    public static int getJSONSupplierBidRejectedListResultSize(String jsontext) throws Exception {

        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidrejectlist");

        return txtObj.length();
    }

    public static ArrayList<iBid_Model> getJSONSupplierBidCloseListResultNew(String jsontext) throws Exception {
        ArrayList<iBid_Model> temp = new ArrayList<iBid_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidcloselist");
        for (int i = 0; i < txtObj.length(); i++) {
            iBid_Model tempModel = new iBid_Model();
            tempModel.setBidNo(txtObj.getJSONObject(i).getString("bidno"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setClient(txtObj.getJSONObject(i).getString("client"));
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setAlight(txtObj.getJSONObject(i).getString("alight"));
            Log.e("BID REJECT", "===============START====================");
            Log.e("ALIGHT", "" + txtObj.getJSONObject(i).getString("alight"));
            tempModel.setPickup(txtObj.getJSONObject(i).getString("pickup"));
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setPrice(txtObj.getJSONObject(i).getString("price"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setPhone(txtObj.getJSONObject(i).getString("phone"));
            Log.e("PHONE", "" + txtObj.getJSONObject(i).getString("phone"));
            tempModel.setDescription(txtObj.getJSONObject(i).getString("description"));
            Log.e("DESC", "" + txtObj.getJSONObject(i).getString("description"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            Log.e("BID REJECT", "==============ENND=====================");
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));

            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));

            //tempModel.setStatus("  ");
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));
            temp.add(tempModel);
        }
        return temp;
    }


    public static ArrayList<iBid_Model> getJSONSupplierBidCloseListResult(String jsontext) throws Exception {
        ArrayList<iBid_Model> temp = new ArrayList<iBid_Model>();
        //jsontext = initXMLParser(jsontext);
        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidcloselist");
        for (int i = 0; i < txtObj.length(); i++) {

            iBid_Model tempModel = new iBid_Model();
            tempModel.setA(txtObj.getJSONObject(i).getString("noofadult"));
            tempModel.setAgent(txtObj.getJSONObject(i).getString("agent"));
            tempModel.setArrDep(txtObj.getJSONObject(i).getString("arrdepdate"));
            tempModel.setBookNo(txtObj.getJSONObject(i).getString("bookno"));
            tempModel.setBookingRemarks(txtObj.getJSONObject(i).getString("BookingRemarks"));
            tempModel.setC(txtObj.getJSONObject(i).getString("noofchild"));
            tempModel.setDate(txtObj.getJSONObject(i).getString("assigndate"));
            tempModel.setDriver(txtObj.getJSONObject(i).getString("driver"));
            tempModel.setFlight(txtObj.getJSONObject(i).getString("flightno"));
            try {
                tempModel.setFlightTime(txtObj.getJSONObject(i).getString("flighttime"));
            } catch (Exception e1) {

            }
            tempModel.setHotel(txtObj.getJSONObject(i).getString("hotel"));
            tempModel.setI(txtObj.getJSONObject(i).getString("noofinfant"));
            tempModel.setJobNo(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setName(txtObj.getJSONObject(i).getString("client"));
            tempModel.setPickupTime(txtObj.getJSONObject(i).getString("pickuptime"));
            tempModel.setSelect(txtObj.getJSONObject(i).getString("jobno"));
            tempModel.setStatus(txtObj.getJSONObject(i).getString("status"));
            try {
                tempModel.setTPTRemarks(txtObj.getJSONObject(i).getString("TPTremarks"));
            } catch (Exception e) {

            }
            tempModel.setType(txtObj.getJSONObject(i).getString("type"));
            tempModel.setBidNo(txtObj.getJSONObject(i).getString("bidno"));
            tempModel.setRecordscount(txtObj.getJSONObject(i).getString("recordscount"));

            temp.add(tempModel);
        }
        return temp;
    }


    public static List<iNewMessage> getJSONDriverNewMessages(String jsontext) throws Exception {
        List<iNewMessage> temp = new ArrayList<>();

        JSONObject rootObj = new JSONObject(jsontext);

        JSONArray msgObj = rootObj.getJSONArray("result").getJSONObject(0).getJSONObject("data").getJSONArray("listOfMessages");

        for (int i = 0; i < msgObj.length(); i++) {
            iNewMessage iMsg = new iNewMessage();

            iMsg.setJobNo(msgObj.getJSONObject(i).getString("jobNo"));
            iMsg.setMsgHdr(msgObj.getJSONObject(i).getString("msgHdr"));
            iMsg.setMsgBody(msgObj.getJSONObject(i).getString("msgBody"));
            iMsg.setSendBy(msgObj.getJSONObject(i).getString("sendBy"));
            iMsg.setSendDate(msgObj.getJSONObject(i).getString("sendDate"));

            temp.add(iMsg);
        }

        return temp;
    }

    public static ArrayList<iMessage> getJSONDriverAllMessages(String jsontext) throws Exception {
        ArrayList<iMessage> temp = new ArrayList<>();

        JSONObject rootObj = new JSONObject(jsontext);

        JSONArray msgObj = rootObj.getJSONArray("result").getJSONObject(0).getJSONObject("data").getJSONArray("listOfMessages");

        for (int i = 0; i < msgObj.length(); i++) {
            iMessage iMsg = new iMessage();

            iMsg.setRow(msgObj.getJSONObject(i).getString("row"));
            iMsg.setMsgId(msgObj.getJSONObject(i).getString("msgId"));
            iMsg.setJobNo(msgObj.getJSONObject(i).getString("jobNo"));
            iMsg.setMsgHdr(msgObj.getJSONObject(i).getString("msgHdr"));
            iMsg.setSendBy(msgObj.getJSONObject(i).getString("sendBy"));
            iMsg.setSendDate(msgObj.getJSONObject(i).getString("sendDate"));
            iMsg.setIsRead(msgObj.getJSONObject(i).getString("isRead"));

            temp.add(iMsg);
        }

        return temp;
    }

    public static int getJSONSupplierBidCloseListResultSize(String jsontext) throws Exception {

        JSONObject rootObj = new JSONObject(jsontext);
        JSONArray txtObj = rootObj.getJSONArray("bidcloselist");

        return txtObj.length();
    }

//	public static AccountDetailModel AccountDetails(String jsontext) throws Exception {
//		
//		AccountDetailModel temp = new AccountDetailModel();
//		
//		JSONObject rootObj = new JSONObject(jsontext);
//		JSONObject trackObj = rootObj.getJSONObject("results");
//		
//		temp.setGender(trackObj.getString("gender"));
//		temp.setEmail(trackObj.getString("email"));
//		temp.setUsername(trackObj.getString("username"));
//		
//		try {
//			JSONArray imgtrackObj = trackObj.getJSONArray("images");
//			
//			for(int i=0;i<imgtrackObj.length();i++){
//
//			}
//			
//			JSONObject json_data = imgtrackObj.getJSONObject(0);
//			
//			temp.setImages(json_data.getString("image"));
//		} catch (Exception e) {
//			Util.modifiedLogTrace(e.getStackTrace().toString());
//		}
//		
//	return temp;
//    }


}
