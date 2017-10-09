package com.info121.ioperations.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.info121.constant.Constants;
import com.info121.ioperations.ChooseTaskActivity;
import com.info121.ioperations.R;
import com.info121.ioperations.adapter.iOperationAdapter;
import com.info121.ioperations.dialog.ReAssignDialog;
import com.info121.ioperations.dialog.UpdateJobServerDialog;
import com.info121.ioperations.iOpMainViewPagerActivity;
import com.info121.ioperations.util.Constant;
import com.info121.ioperations.util.Parser;
import com.info121.ioperations.util.Util;
import com.info121.model.iNewMessage;
import com.info121.model.iOP_Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

public final class iOperationFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    android.support.v4.app.FragmentTransaction fragmentTransaction;

    public static iOperationFragment thisFragment;
    public static boolean isActive = false;
    private static int status = 0;
    public Button btnReject;
    public Button btnReAssign;
    SharedPreferences sp;
    int count;
    private TextView username;
    private Context context;
    private ListView LViOP;
    private LinearLayout LLNoData;
    private TextView TVHeader;
    private Spinner spinFilterList;
    private iOperationAdapter iOP_adapter;
    private ArrayList<iOP_Model> items = new ArrayList<iOP_Model>();
    private ArrayList<iOP_Model> search = new ArrayList<iOP_Model>();
    private ProgressDialog progress;
    private String SupplierCode;
    private Button btnAccept;
    private Button btnSearch;
    private EditText etSearchBox;
    private EditText etDate;
    private String MultipleJobNo;
    private boolean isAssigned = true;
    private int whichPage = 1;
    private int pageSize = 0;
    private String filterStatus;
    private int positionSpin;
    //paging things
//    private Button btnFirst;
    private TextView btnPrevious;
    private TextView btnNext;
    //    private Button btnLast;
    private Button btnGo;
    private EditText etTextPageGo;
    //buttons navigation
    private Button btnUpdateServer;
    private Button btniOperation;
    private Button btniBidding;
    private Button btnFeedback;
    private Button btHome;
    private Calendar myCalendar;
    private DatePicker datePicker;
    private int year, month, day;
    private ImageView ivCalender;
    private LinearLayout LLLoading;
    private String mContent = "???";

    private List<iNewMessage> mMessageList;
    private TextView mNotiCount;
    private ImageView mNotiImage;

    public static iOperationFragment newInstance(String content) {

        iOperationFragment fragment = new iOperationFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }

        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE);


        count = 0;
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

//    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int month, int day) {
//            // TODO Auto-generated method stub
//            // arg1 = year
//            // arg2 = month
//            // arg3 = day
//            int m = month + 1;
//            if (view.isShown()) {
//                updateDate(day, m, year);
//            }
//
//
//        }
//    };

//    private void updateDate(int day, int month, int year) {
//        etDate.setText("" + day + "/" + month + "/" + year);
////            Toast.makeText(getActivity(), ""+day+"/"+month+"/"+year, Toast.LENGTH_SHORT).show();
//        searchRecordDateWise();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_ioperation_layout, container, false);
        context = getActivity();
        thisFragment = this;
        items = new ArrayList<iOP_Model>();
        LViOP = (ListView) v.findViewById(R.id.listViewiOperation);
        LLNoData = (LinearLayout) v.findViewById(R.id.LLNoDataAssigment);
        etDate = (EditText) v.findViewById(R.id.etDate_iOperatinFragment);
        ivCalender = (ImageView) v.findViewById(R.id.ivCalender_iOperation);
        LLLoading = (LinearLayout) v.findViewById(R.id.LLRootLoading_iOperationFragment);
        btHome = (Button) v.findViewById(R.id.btHome_iOperationFragment);
        //populateItemDummyData();



        Log.d("msg", "size  " + items.size());


        iOP_adapter = new iOperationAdapter(context, items, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
        iOP_adapter.resetCheck();
        LViOP.setAdapter(iOP_adapter);

        btHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment bidingFragment = new HomeFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, bidingFragment);
                fragmentTransaction.commit();
            }
        });


        etSearchBox = (EditText) v.findViewById(R.id.editTextiOperationSearch);
        etSearchBox.clearFocus();
        etSearchBox.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(etSearchBox) {
            @Override
            public boolean onDrawableClick() {
                // TODO : insert code to perform on clicking of the RIGHT drawable image...
                etSearchBox.setText("");
                if (spinFilterList.getSelectedItemPosition() == 0) {
                    filterStatus = "Job Assigned";
                    new getSupplierAcceptedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 1) {
                    filterStatus = "Job Served - Confirm";
                    new getSupplierShowServedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 2) {
                    filterStatus = "Job Served - No Show";
                    new getSupplierNoShowServedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 3) {
                    filterStatus = "Job Rejected";
                    new getJobSupplierRejectedJobsListAsyncTask().execute();
                }
                return true;
            }
        });

        etSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (spinFilterList.getSelectedItemPosition() == 0) {
                    positionSpin = 0;
                    whichPage = 1;
                    filterStatus = "Job Assigned";
                    new getSupplierAcceptedJobsSearchListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 1) {
                    positionSpin = 1;
                    whichPage = 1;
                    filterStatus = "Job Served - Confirm";
                    new getSupplierShowServedJobsSearchListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 2) {
                    positionSpin = 2;
                    whichPage = 1;
                    filterStatus = "Job Served - No Show";
                    new getSupplierNoShowServedJobsSearchListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 3) {
                    positionSpin = 3;
                    whichPage = 1;
                    filterStatus = "Job Rejected";
//                    new getJobSupplierRejectedJobsListAsyncTask().execute();
                    new getJobSupplierRejectedJobsDateSearchListAsyncTask().execute();

                }
            }
        });


//        LViOP.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                ListItemSelected(position);
//                Intent intent = new Intent(getActivity(), WelcomeIoperationActivity.class);
//                startActivity(intent);
////                iOP_adapter.perform_click(position);
////                if (filterStatus.compareToIgnoreCase("Job Assigned") == 0)
////                    JobDetailDialog.show(context, getActivity(), items.get(position), thisFragment, true);
////                else
////                    JobDetailDialog.show(context, getActivity(), items.get(position), thisFragment, false);
//
//            }
//        });

//        LViOP.setOnItemLongClickListener(new OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View v,
//                                           int pos, long id) {
//                ListItemSelected(pos);
//                return false;
//            }
//        });


        String Driver = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, "");
        String Company = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, "");
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

//        TVHeader = (TextView) v.findViewById(R.id.textViewiOperationInfoHeader);
//        TVHeader.setText("Driver : " + Driver + "  Company : " + Company + "  Date : " + mydate + "");
        spinFilterList = (Spinner) v.findViewById(R.id.spinneriOperation);
        List<String> list = new ArrayList<String>();
        list.add(getActivity().getResources().getString(R.string.ioperation_filter1_job_assigned));
        list.add(getActivity().getResources().getString(R.string.ioperation_filter2_jobserve_confirm));
        list.add(getActivity().getResources().getString(R.string.ioperation_filter3_jobserve_noshow));
        list.add(getActivity().getResources().getString(R.string.ioperation_filter4_jobsupplierrejected));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                R.layout.spinner_item, R.id.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinFilterList.setAdapter(dataAdapter);
        spinFilterList.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {

                if (position == 0) {

                    positionSpin = 0;
                    whichPage = 1;
                    filterStatus = "Job Assigned";
                    new getSupplierAcceptedJobsListAsyncTask().execute();
                } else if (position == 1) {
                    positionSpin = 1;
                    whichPage = 1;
                    filterStatus = "Job Served - Confirm";
                    new getSupplierShowServedJobsListAsyncTask().execute();
                } else if (position == 2) {
                    positionSpin = 2;
                    whichPage = 1;
                    filterStatus = "Job Served - No Show";
                    new getSupplierNoShowServedJobsListAsyncTask().execute();
                } else if (position == 3) {
                    positionSpin = 3;
                    whichPage = 1;
                    filterStatus = "Job Rejected";
                    new getJobSupplierRejectedJobsListAsyncTask().execute();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

// 		((TextView) v.findViewById(R.id.TextViewiOPBookNo)).setText("Book No");
// 		((TextView) v.findViewById(R.id.TextViewiOPChild1)).setText("Job No");
//		((TextView) v.findViewById(R.id.TextViewiOPChild2)).setText("Date");
//		((TextView) v.findViewById(R.id.TextViewiOPChild3)).setText("Type");
//		((TextView) v.findViewById(R.id.TextViewiOPChild4)).setText("Guest");
//		((TextView) v.findViewById(R.id.TextViewiOPChild5)).setText("A");
//		((TextView) v.findViewById(R.id.TextViewiOPChild6)).setText("C");
//		((TextView) v.findViewById(R.id.TextViewiOPChild7)).setText("I");
//		((TextView) v.findViewById(R.id.TextViewiOPChild8)).setText("Flight");
//		((TextView) v.findViewById(R.id.TextViewiOPChild9)).setText("Flight Time");
//		((TextView) v.findViewById(R.id.TextViewiOPChild10)).setText("Pickup Time");
//		((TextView) v.findViewById(R.id.TextViewiOPChild11)).setText("Hotel");
//		((TextView) v.findViewById(R.id.TextViewiOPChild12)).setText("TPT Remarks");
//		((TextView) v.findViewById(R.id.TextViewiOPChild13)).setText("Booking Remarks");
//		((TextView) v.findViewById(R.id.TextViewiOPChild14)).setText("Arr/Dep");
//		((TextView) v.findViewById(R.id.TextViewiOPChild15)).setText("Agent");
//		((TextView) v.findViewById(R.id.TextViewiOPChild16)).setText("Driver");
//		((TextView) v.findViewById(R.id.TextViewiOPChild17)).setText("Select");
//		((TextView) v.findViewById(R.id.TextViewiOPChild18)).setText("Status");
//		((TextView) v.findViewById(R.id.TextViewiOPChild19)).setText("ReAssign");
//		((TextView) v.findViewById(R.id.TextViewiOPChild20)).setText("Serve");

        SupplierCode = getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.SUPPLIER_KEY, "");

        btnAccept = (Button) v.findViewById(R.id.buttonJobAccepted);

        btnAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = true;

                for (int i = 0; i < iOP_adapter.getRis().size(); i++) {
                    if (iOP_adapter.getRis().get(i).isSelected() == true && check) {
                        check = false;
                    }
                }

                if (check)
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Please ticks at least one item in jobs list");
                else {
                    MultipleJobNo = "";
                    for (int i = 0; i < iOP_adapter.getRis().size(); i++) {
                        if (iOP_adapter.getRis().get(i).isSelected())
                            MultipleJobNo = MultipleJobNo + iOperationAdapter.items.get(i).getJobNo() + "|";
                    }
                    MultipleJobNo = MultipleJobNo.substring(0, MultipleJobNo.length() - 1);


                    new updateJobAcceptedAsyncTask().execute();
                }
            }
        });

        btnReject = (Button) v.findViewById(R.id.buttonJobRejected);


        btnReject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = true;

                for (int i = 0; i < iOP_adapter.getRis().size(); i++) {
                    if (iOP_adapter.getRis().get(i).isSelected() == true && check) {
                        check = false;
                    }
                }

                if (check)
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Please ticks at least one item in jobs list");
                else {
                    MultipleJobNo = "";
                    for (int i = 0; i < iOP_adapter.getRis().size(); i++) {
                        if (iOP_adapter.getRis().get(i).isSelected())
                            MultipleJobNo = MultipleJobNo + iOperationAdapter.items.get(i).getJobNo() + "|";
                    }
                    MultipleJobNo = MultipleJobNo.substring(0, MultipleJobNo.length() - 1);


                    new updateJobRejectedAsyncTask().execute();
                }

//				   if(iOperationAdapter.selectedItem == -1)
//				   Util.showAlertCommonDialogWithOKBtn(context, "Error","Please ticked select for one item in jobs list");
//				   else
//				   new updateJobRejectedAsyncTask().execute();

            }
        });

        btnReAssign = (Button) v.findViewById(R.id.ButtonJobReassign);

        btnReAssign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = true;

                for (int i = 0; i < iOP_adapter.getRis().size(); i++) {
                    if (iOP_adapter.getRis().get(i).isSelected() == true && check) {
                        check = false;
                    }
                }

                if (check)
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Please ticks at least one item in jobs list");
                else {
                    MultipleJobNo = "";
                    for (int i = 0; i < iOP_adapter.getRis().size(); i++) {
                        if (iOP_adapter.getRis().get(i).isSelected())
                            MultipleJobNo = MultipleJobNo + iOperationAdapter.items.get(i).getJobNo() + "|";
                    }
                    MultipleJobNo = MultipleJobNo.substring(0, MultipleJobNo.length() - 1);


                    ReAssignDialog.show(context, getActivity(), MultipleJobNo, thisFragment);
                }


            }
        });


//        btnSearch = (Button) v.findViewById(R.id.buttonSearchiOperation);

//        btnSearch.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (spinFilterList.getSelectedItemPosition() == 0) {
//                    filterStatus = "Job Assigned";
//                    new getSupplierAcceptedJobsSearchListAsyncTask().execute();
//                } else if (spinFilterList.getSelectedItemPosition() == 1) {
//                    filterStatus = "JOB SERVED";
//                    new getSupplierShowServedJobsSearchListAsyncTask().execute();
//                } else if (spinFilterList.getSelectedItemPosition() == 2) {
//                    filterStatus = "JOB SERVED";
//                    new getSupplierNoShowServedJobsSearchListAsyncTask().execute();
//                } else if (spinFilterList.getSelectedItemPosition() == 3) {
//
//                    filterStatus = "JOB REJECTED";
//                    new getJobSupplierRejectedJobsListAsyncTask().execute();
//                }
//
//
//            }
//        });

//        btnFirst = (Button) v.findViewById(R.id.ButtoniOpFirst);
//        btnFirst.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                whichPage = 1;
//
//                btnPrevious.setVisibility(View.GONE);
//
//                if (spinFilterList.getSelectedItemPosition() == 0) {
//                    filterStatus = "Job Assigned";
//                    new getSupplierAcceptedJobsListAsyncTask().execute();
//                } else if (spinFilterList.getSelectedItemPosition() == 1) {
//                    filterStatus = "JOB SERVED";
//                    new getSupplierShowServedJobsListAsyncTask().execute();
//                } else if (spinFilterList.getSelectedItemPosition() == 2) {
//                    filterStatus = "JOB SERVED";
//                    new getSupplierNoShowServedJobsListAsyncTask().execute();
//                } else if (spinFilterList.getSelectedItemPosition() == 3) {
//
//                    filterStatus = "JOB REJECTED";
//                    new getJobSupplierRejectedJobsListAsyncTask().execute();
//                }
//
//
//            }
//        });

        btnPrevious = (TextView) v.findViewById(R.id.ButtoniOpPrevious);
        btnPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                whichPage = whichPage - 1;
                if (spinFilterList.getSelectedItemPosition() == 0) {
                    filterStatus = "Job Assigned";
                    new getSupplierAcceptedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 1) {
                    filterStatus = "Job Served - Confirm";
                    new getSupplierShowServedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 2) {
                    filterStatus = "Job Served - No Show";
                    new getSupplierNoShowServedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 3) {

                    filterStatus = "Job Rejected";
                    new getJobSupplierRejectedJobsListAsyncTask().execute();
                }


            }
        });

        btnNext = (TextView) v.findViewById(R.id.ButtoniOpNext);
        btnNext.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                whichPage = whichPage + 1;
                if (spinFilterList.getSelectedItemPosition() == 0) {
                    filterStatus = "Job Assigned";
                    new getSupplierAcceptedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 1) {

                    filterStatus = "Job Served - Confirm";
                    new getSupplierShowServedJobsListAsyncTask().execute();

                } else if (spinFilterList.getSelectedItemPosition() == 2) {
                    filterStatus = "Job Served - No Show";
                    new getSupplierNoShowServedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 3) {

                    filterStatus = "Job Rejected";
                    new getJobSupplierRejectedJobsListAsyncTask().execute();
                }


            }
        });

//        btnLast = (Button) v.findViewById(R.id.ButtoniOpLast);
//        btnLast.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                whichPage = pageSize;
//                if (spinFilterList.getSelectedItemPosition() == 0) {
//                    filterStatus = "Job Assigned";
//                    new getSupplierAcceptedJobsListAsyncTask().execute();
//                } else if (spinFilterList.getSelectedItemPosition() == 1) {
//                    filterStatus = "JOB SERVED";
//                    new getSupplierShowServedJobsListAsyncTask().execute();
//                } else if (spinFilterList.getSelectedItemPosition() == 2) {
//                    filterStatus = "JOB SERVED";
//                    new getSupplierNoShowServedJobsListAsyncTask().execute();
//                } else if (spinFilterList.getSelectedItemPosition() == 3) {
//
//                    filterStatus = "JOB REJECTED";
//                    new getJobSupplierRejectedJobsListAsyncTask().execute();
//                }
//            }
//        });

        btnGo = (Button) v.findViewById(R.id.ButtoniOpGo);
        btnGo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (etTextPageGo.getText().toString().compareTo("") == 0) {
                        Util.showAlertCommonDialogWithOKBtn(context, "Error", "Please insert page number");
                    } else {
                        int temp = Integer.parseInt(etTextPageGo.getText().toString());
                        if ((temp < 1 || temp > pageSize)) {
                            Util.showAlertCommonDialogWithOKBtn(context, "Error", "page number is out of range");
                        } else {

                            whichPage = Integer.parseInt(etTextPageGo.getText().toString());
                            if (spinFilterList.getSelectedItemPosition() == 0) {
                                filterStatus = "Job Assigned";
                                new getSupplierAcceptedJobsListAsyncTask().execute();
                            } else if (spinFilterList.getSelectedItemPosition() == 1) {

                                filterStatus = "Job Served - Confirm";
                                new getSupplierShowServedJobsListAsyncTask().execute();

                            } else if (spinFilterList.getSelectedItemPosition() == 2) {

                                filterStatus = "Job Served - No Show";
                                new getSupplierNoShowServedJobsListAsyncTask().execute();

                            } else if (spinFilterList.getSelectedItemPosition() == 3) {

                                filterStatus = "Job Rejected";
                                new getJobSupplierRejectedJobsListAsyncTask().execute();

                            }

                        }
                    }
                } catch (NumberFormatException e) {
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Please insert page no based on the range");
                }

            }
        });

        etTextPageGo = (EditText) v.findViewById(R.id.editTextiOpPage);


        btnUpdateServer = (Button) v.findViewById(R.id.ButtonShortcutiOpServerInfo);
        btnUpdateServer.setText("xxx.xxx." + Constants.SERVER_URL.substring(8, Constants.SERVER_URL.length()));
        btnUpdateServer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateJobServerDialog.show(context, getActivity(), thisFragment, true);

            }
        });

        btniOperation = (Button) v.findViewById(R.id.ButtonShortcutiOpJobAssignments);
        btniOperation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                iOpMainViewPagerActivity.pager.setCurrentItem(0);

            }
        });

        btniBidding = (Button) v.findViewById(R.id.ButtonShortcutiOpJobBidding);
        btniBidding.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                iOpMainViewPagerActivity.pager.setCurrentItem(1);

            }
        });

        btnFeedback = (Button) v.findViewById(R.id.ButtonShortcutiOpFeedback);
        btnFeedback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                iOpMainViewPagerActivity.pager.setCurrentItem(2);

            }
        });


//		   Intent intent = new Intent(context, WelcomeIoperationActivity.class);
//		   intent.putExtra(Util.JOBNO_KEY, "121");
//		   intent.putExtra(Util.PASSENGER_KEY, "MR BROWN");
//		   SharedPreferences preferences = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
//		   SharedPreferences.Editor editor = preferences.edit();
//		   editor.putBoolean(Util.EXIT_NOSHOW_KEY, false);
//		   editor.putBoolean(Util.EXIT_SHOW_KEY, false);
//		   editor.commit();
//		   context.startActivity(intent);


        // Get current date by calender
        Util.closeKeyboard(getActivity(), etSearchBox.getWindowToken());
        myCalendar = Calendar.getInstance();
        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        month = myCalendar.get(Calendar.MONTH);
        year = myCalendar.get(Calendar.YEAR);

        ivCalender.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager methodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Process to get Current Date
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if (view.isShown()) {
                                    Calendar c = Calendar.getInstance();
                                    c.set(year, monthOfYear, dayOfMonth);
                                    String myFormat = "dd/MM/yyyy"; //In which you need put here
                                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                                    etDate.setText(sdf.format(c.getTime()));

//                                    etDate.setText(dayOfMonth + "/"
//                                            + (monthOfYear + 1) + "/" + year);
                                    searchRecordDateWise();
                                }
                            }
                        }, year, month, day);

                dpd.show();

            }
        });


        etDate.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(etDate) {
            @Override
            public boolean onDrawableClick() {
                // TODO : insert code to perform on clicking of the RIGHT drawable image...
                etDate.setText("");
                if (spinFilterList.getSelectedItemPosition() == 0) {
                    filterStatus = "Job Assigned";
                    new getSupplierAcceptedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 1) {
                    filterStatus = "Job Served - Confirm";
                    new getSupplierShowServedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 2) {
                    filterStatus = "Job Served - No Show";
                    new getSupplierNoShowServedJobsListAsyncTask().execute();
                } else if (spinFilterList.getSelectedItemPosition() == 3) {
                    filterStatus = "Job Rejected";
                    new getJobSupplierRejectedJobsListAsyncTask().execute();
                }
                return true;
            }
        });



        // KZHTUN on 20170925
        mNotiCount = (TextView) v.findViewById(R.id.notification_count);
        mNotiImage = (ImageView) v.findViewById(R.id.notification_icon);

        mNotiCount.setText(String.valueOf(Constants.unreadCount));

        mNotiImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageFragment messageFragment = new MessageFragment();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, messageFragment);
                fragmentTransaction.commit();
            }
        });



        return v;
    }

    private void searchRecordDateWise() {

        sp.edit().putString("search",etDate.getText().toString()).commit();

        Log.e("LABEL UPDATE", "" + etDate.getText().toString());
        status = 1;
        Log.e("status", "" + status);
        if (spinFilterList.getSelectedItemPosition() == 0) {
//            Toast.makeText(getActivity(), "" + spinFilterList.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
            filterStatus = "Job Assigned";
            new getSupplierAcceptedJobsSearchListAsyncTask().execute();
        } else if (spinFilterList.getSelectedItemPosition() == 1) {
//            Toast.makeText(getActivity(), "" + spinFilterList.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
            filterStatus = "Job Served - Confirm";
            new getSupplierShowServedJobsSearchListAsyncTask().execute();
        } else if (spinFilterList.getSelectedItemPosition() == 2) {
//            Toast.makeText(getActivity(), "" + spinFilterList.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
            filterStatus = "Job Served - No Show";
            new getSupplierNoShowServedJobsSearchListAsyncTask().execute();
        } else if (spinFilterList.getSelectedItemPosition() == 3) {
//            Toast.makeText(getActivity(), "" + spinFilterList.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
            filterStatus = "Job Rejected";
            new getJobSupplierRejectedJobsDateSearchListAsyncTask().execute();
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    @Override
    public void onResume() {
        super.onResume();
        isActive = true;
        resetListItemSelected();
//
        try {
            Util.closeKeyboard(getActivity(), etSearchBox.getWindowToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            Util.closeKeyboard(getActivity(), etSearchBox.getWindowToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
    }

    public void ListItemSelected(int pos) {

        try {
            iOP_adapter.isSelected = true;
            iOP_adapter.selectedIndex = pos;
            LViOP.invalidateViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetListItemSelected() {

        try {

            iOP_adapter.isSelected = false;
            iOP_adapter.selectedIndex = 0;
            LViOP.invalidateViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void showLoading() {
        LLLoading.setVisibility(View.VISIBLE);
    }

    protected void dismissLoading() {
        LLLoading.setVisibility(View.GONE);
    }


    public void CallTheJob() {
        whichPage = 1;
        new getSupplierAcceptedJobsListAsyncTask().execute();
        btnUpdateServer.setText("xxx.xxx." + Constants.SERVER_URL.substring(8, Constants.SERVER_URL.length()));
    }

    private class getSupplierAcceptedJobsListAsyncTask extends AsyncTask<Void, Void, String> {

        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected void onPreExecute() {

            count++;
            showLoading();
            etTextPageGo.setText("");
            btnReAssign.setVisibility(View.VISIBLE);

            if (whichPage == 1) {
//                btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            } else if (whichPage == pageSize) {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
//                btnLast.setVisibility(View.GONE);
            } else {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }


            if (pageSize == 1) {
//                btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
//                btnLast.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
            } else {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected String doInBackground(Void... v) {

            Hashtable<String, String> parameter = new Hashtable<String, String>();


            try {
                parameter = new Hashtable<String, String>();
                parameter.put("suppliercode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
                parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

                String temp = Util.connectSOAP(Constants.uRLiOpsIsSupplierMainContactPerson, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsIsSupplierMainContactPerson, Constants.APIDotNetMethodNameIsSupplierMainContactPerson, parameter);
                if (temp.compareToIgnoreCase("yes") == 0) {
                    isMainPerson = true;
                } else {
                    isMainPerson = false;
                }

                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", String.valueOf(whichPage));


            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }


            return Util.connectSOAP(Constants.uRLiOpsGetSupplierAssignedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierAssignedJobsList, Constants.APIDotNetMethodNameGetSupplierAssignedJobsList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null && isTimeout == false) {
                try {
                    // store json list in shared prefrence
                    Log.e("assignedjoblist", "" + result);

                    Util.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.KEY_JOBLIST, result);
                    items = new ArrayList<iOP_Model>();

                    ArrayList<iOP_Model> listTemp = Parser.getJSONSupplierJobAssignListResultNew(result, filterStatus);
                    pageSize = Integer.parseInt(listTemp.get(0).getRecordscount());
                    int isEven = (pageSize % 10);
                    pageSize = pageSize / 10;
                    if (!(isEven == 0) || pageSize == 0)
                        pageSize = pageSize + 1;

                    if (listTemp.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);

                        for (int i = 0; i < listTemp.size(); i++)
                            items.add(listTemp.get(i));

                        iOP_adapter = new iOperationAdapter(context, items, getActivity(), isAssigned, thisFragment, isMainPerson, getActivity().getSupportFragmentManager());

                        LViOP.setAdapter(iOP_adapter);
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                        iOP_adapter.resetCheck();
                    }

                    etTextPageGo.setHint("Page " + whichPage + " of " + pageSize);

                    if (whichPage == 1) {
//                        btnFirst.setVisibility(View.GONE);
                        btnPrevious.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
//                        btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    } else if (whichPage == pageSize) {
//                        btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.GONE);
//                        btnLast.setVisibility(View.GONE);
                    } else {
//                        btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
//                        btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {

                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }

                   // Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                if (items.size() <= 0) {
                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);
                } else {
                    LViOP.setVisibility(View.VISIBLE);
                    LLNoData.setVisibility(View.GONE);
                }

               // Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }

        }
    }

    private class getSupplierAcceptedJobsSearchListAsyncTask extends AsyncTask<Void, Void, String> {

        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected void onPreExecute() {
            count++;
            showLoading();
//            Toast.makeText(getActivity(), " - " + count + "getSupplierAcceptedJobsSearchListAsyncTask", Toast.LENGTH_LONG).show();

            etTextPageGo.setText("");
            btnReAssign.setVisibility(View.VISIBLE);
            etTextPageGo.setVisibility(View.GONE);
//            btnFirst.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
//            btnLast.setVisibility(View.GONE);
            btnGo.setVisibility(View.GONE);


        }

        @Override
        protected String doInBackground(Void... v) {


            Hashtable<String, String> parameter = new Hashtable<String, String>();


            try {
                parameter = new Hashtable<String, String>();
                parameter.put("suppliercode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
                parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

                String temp = Util.connectSOAP(Constants.uRLiOpsIsSupplierMainContactPerson, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsIsSupplierMainContactPerson, Constants.APIDotNetMethodNameIsSupplierMainContactPerson, parameter);
                if (temp.compareToIgnoreCase("yes") == 0) {
                    isMainPerson = true;
                } else {
                    isMainPerson = false;
                }


                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", "1");

                String resultForSize = Util.connectSOAP(Constants.uRLiOpsGetSupplierAssignedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierAssignedJobsList, Constants.APIDotNetMethodNameGetSupplierAssignedJobsList, parameter);
                pageSize = Parser.getJSONSupplierJobAssignListResultSize(resultForSize, filterStatus);
                pageSize = pageSize / 10;
                pageSize = pageSize + 1;


                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsGetSupplierAssignedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierAssignedJobsList, Constants.APIDotNetMethodNameGetSupplierAssignedJobsList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null && isTimeout == false) {
                try {


                    Log.d("msg", "ioperationfragment res " + result);

                    Log.e("RESULT>>", result);
                    // store json list in shared prefrence
                    items = new ArrayList<iOP_Model>();

                    sp.edit().putString("result",result.toString()).commit();
                    sp.edit().putString("filter",filterStatus).commit();
                    ArrayList<iOP_Model> listTemp = Parser.getJSONSupplierJobAssignListResultNew(result, filterStatus);
                    Log.e("DATE STATUS", "" + status);


                    if (etSearchBox.getText().toString().trim().compareToIgnoreCase("") == 0 && status == 0) {
                        if (listTemp.size() <= 0) {
                            LViOP.setVisibility(View.GONE);
                            LLNoData.setVisibility(View.VISIBLE);
                        } else {
                            LViOP.setVisibility(View.VISIBLE);
                            LLNoData.setVisibility(View.GONE);
                        }

                        for (int i = 0; i < listTemp.size(); i++)
                            items.add(listTemp.get(i));

                        iOP_adapter = new iOperationAdapter(context, listTemp, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                    } else {
                        search = new ArrayList<iOP_Model>();

                        for (int i = 0; i < listTemp.size(); i++) {
                            Log.e("MEHUl>>>>>", "" + status);
                            if (status == 1) {
                                Log.e("DATE>>", "" + etDate.getText().toString());
                                if (listTemp.get(i).isContainSearchFor(etDate.getText().toString().trim()))
                                    search.add(listTemp.get(i));

                            } else {
                                Log.e("SEARCH>>", "" + etSearchBox.getText().toString());
                                if (listTemp.get(i).isContainSearchFor(etSearchBox.getText().toString().trim()))
                                    search.add(listTemp.get(i));
                            }

                        }

                        Log.e("SEARCH SIZE", "" + search.size());
                        if (search.size() <= 0) {
                            LViOP.setVisibility(View.GONE);
                            LLNoData.setVisibility(View.VISIBLE);
                        } else {
                            LViOP.setVisibility(View.VISIBLE);
                            LLNoData.setVisibility(View.GONE);
                        }


                        for (int i = 0; i < search.size(); i++)
                            items.add(search.get(i));

                        iOP_adapter = new iOperationAdapter(context, search, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                        if (status == 1) {
                            status = 0;
                        }
                    }

                } catch (Exception e) {

                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }
                 //   Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                if (items.size() <= 0) {
                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);
                } else {
                    LViOP.setVisibility(View.VISIBLE);
                    LLNoData.setVisibility(View.GONE);
                }

              //  Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }

        }
    }

    private class getSupplierShowServedJobsListAsyncTask extends AsyncTask<Void, Void, String> {

        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected void onPreExecute() {
            count++;
            showLoading();
//            Toast.makeText(getActivity(), " - "+count+  "getSupplierShowServedJobsListAsyncTask", Toast.LENGTH_LONG).show();
            etTextPageGo.setText("");
            btnReAssign.setVisibility(View.GONE);

            if (whichPage == 1) {
//                btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            } else if (whichPage == pageSize) {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
//                btnLast.setVisibility(View.GONE);
            } else {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }


            if (pageSize == 1) {
//                btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
//                btnLast.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
            }

        }

        @Override
        protected String doInBackground(Void... v) {

            Hashtable<String, String> parameter = new Hashtable<String, String>();


            try {
                parameter = new Hashtable<String, String>();
                parameter.put("suppliercode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
                parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

                String temp = Util.connectSOAP(Constants.uRLiOpsIsSupplierMainContactPerson, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsIsSupplierMainContactPerson, Constants.APIDotNetMethodNameIsSupplierMainContactPerson, parameter);
                if (temp.compareToIgnoreCase("yes") == 0) {
                    isMainPerson = true;
                } else {
                    isMainPerson = false;
                }

                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            Log.e("VIHI", "" + Util.connectSOAP(Constants.uRLIOPS_GetSupplierShowAndServedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_GetSupplierShowAndServedJobsList, Constants.APIDotNetMethodNameIOPS_GetSupplierShowAndServedJobsList, parameter));
            return Util.connectSOAP(Constants.uRLIOPS_GetSupplierShowAndServedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_GetSupplierShowAndServedJobsList, Constants.APIDotNetMethodNameIOPS_GetSupplierShowAndServedJobsList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {

            dismissLoading();
            Log.e("res", "IOPS_GetSupplierShowAndServedJobsList   " + result);
            if (result != null && isTimeout == false) {
                try {

                    // store json list in shared prefrence
                    Util.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.KEY_JOBLIST, result);
                    items = new ArrayList<iOP_Model>();

                    ArrayList<iOP_Model> listTemp = Parser.getJSONSupplierShowServedListResultNew(result, filterStatus);
                    Log.e("listTemp", "" + listTemp);
                    Log.e("SIZEE", "" + listTemp.size());

                    Log.e("COUNT", "" + listTemp.get(0).getRecordscount());
                    pageSize = Integer.parseInt(listTemp.get(0).getRecordscount());
                    Log.e("PAGESIZE", "" + pageSize);
                    int isEven = (pageSize % 10);
                    Log.e("isEven", "" + isEven);
                    pageSize = pageSize / 10;
                    Log.e("PAGESIZE", "" + pageSize);
                    if (!(isEven == 0) || pageSize == 0)
                        pageSize = pageSize + 1;
                    Log.e("PAGESIZE", "" + pageSize);
                    Log.e("VIHU", "" + listTemp.size());
                    for (int i = 0; i < listTemp.size(); i++)
                        items.add(listTemp.get(i));

                    Log.e("ITEM SIZE", "" + items.size());
                    iOP_adapter = new iOperationAdapter(context, items, getActivity(), isAssigned, thisFragment, isMainPerson, getActivity().getSupportFragmentManager());
                    LViOP.setAdapter(iOP_adapter);
                    iOP_adapter.notifyDataSetChanged();
                    LViOP.invalidate();

                    LViOP.invalidateViews();
//                    iOP_adapter.resetCheck();

//                    etTextPageGo.setHint("Page " + whichPage + " of " + pageSize);

                    if (whichPage == 1) {
//                        btnFirst.setVisibility(View.GONE);
                        btnPrevious.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
//                        btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    } else if (whichPage == pageSize) {
//                        btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.GONE);
//                        btnLast.setVisibility(View.GONE);
                    } else {
//                        btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
//                        btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    }

                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }

                } catch (Exception e) {

                    Log.e("EXCEPTION", "" + e);
//                    Toast.makeText(getActivity(), "exception : " + e, Toast.LENGTH_SHORT).show();
                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }

                 //   Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                if (items.size() <= 0) {
                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);
                } else {
                    LViOP.setVisibility(View.VISIBLE);
                    LLNoData.setVisibility(View.GONE);
                }

              //  Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }

        }
    }

    private class getSupplierShowServedJobsSearchListAsyncTask extends AsyncTask<Void, Void, String> {

        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected void onPreExecute() {
            count++;
            showLoading();
//            Toast.makeText(getActivity(), " - " + count + "getSupplierShowServedJobsSearchListAsyncTask", Toast.LENGTH_LONG).show();
            etTextPageGo.setText("");
            btnReAssign.setVisibility(View.GONE);
            etTextPageGo.setVisibility(View.GONE);
//            btnFirst.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
//            btnLast.setVisibility(View.GONE);
            btnGo.setVisibility(View.GONE);


        }

        @Override
        protected String doInBackground(Void... v) {

            Hashtable<String, String> parameter = new Hashtable<String, String>();


            try {
                parameter = new Hashtable<String, String>();
                parameter.put("suppliercode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
                parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

                String temp = Util.connectSOAP(Constants.uRLiOpsIsSupplierMainContactPerson, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsIsSupplierMainContactPerson, Constants.APIDotNetMethodNameIsSupplierMainContactPerson, parameter);
                if (temp.compareToIgnoreCase("yes") == 0) {
                    isMainPerson = true;
                } else {
                    isMainPerson = false;
                }


                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", "1");

                String resultForSize = Util.connectSOAP(Constants.uRLIOPS_GetSupplierShowAndServedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_GetSupplierShowAndServedJobsList, Constants.APIDotNetMethodNameIOPS_GetSupplierShowAndServedJobsList, parameter);
                pageSize = Parser.getJSONSupplierShowServedListResultSize(resultForSize, filterStatus);
                pageSize = pageSize / 10;
                pageSize = pageSize + 1;


                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }


            return Util.connectSOAP(Constants.uRLIOPS_GetSupplierShowAndServedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_GetSupplierShowAndServedJobsList, Constants.APIDotNetMethodNameIOPS_GetSupplierShowAndServedJobsList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {

            dismissLoading();
            Log.e("res", "job list  --> IOPS_GetSupplierShowAndServedJobsList  " + result);
            if (result != null && isTimeout == false) {
                try {

                    items = new ArrayList<iOP_Model>();

                    ArrayList<iOP_Model> listTemp = Parser.getJSONSupplierShowServedListResultNew(result, filterStatus);

                    if (etSearchBox.getText().toString().trim().compareToIgnoreCase("") == 0 && status == 0) {

                        if (listTemp.size() <= 0) {
                            LViOP.setVisibility(View.GONE);
                            LLNoData.setVisibility(View.VISIBLE);
                        } else {
                            LViOP.setVisibility(View.VISIBLE);
                            LLNoData.setVisibility(View.GONE);
                        }
                        Log.e("VIHAAN", "" + listTemp.size());
                        Log.e("ITEM1", "" + items.size());
                        for (int i = 0; i < listTemp.size(); i++)
                            items.add(listTemp.get(i));
                        Log.e("ITEM2", "" + items.size());

                        iOP_adapter = new iOperationAdapter(context, listTemp, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();

                    } else {

                        search = new ArrayList<iOP_Model>();

                        for (int i = 0; i < listTemp.size(); i++) {
                            if (status == 1) {
                                Log.e("DATE>>", "" + etDate.getText().toString());
                                if (listTemp.get(i).isContainSearchFor(etDate.getText().toString().trim()))
                                    search.add(listTemp.get(i));

                            } else {

                                if (listTemp.get(i).isContainSearchFor(etSearchBox.getText().toString().toLowerCase().trim()))
                                    search.add(listTemp.get(i));
                            }
                        }

                        if (search.size() <= 0) {
                            LViOP.setVisibility(View.GONE);
                            LLNoData.setVisibility(View.VISIBLE);
                        } else {
                            LViOP.setVisibility(View.VISIBLE);
                            LLNoData.setVisibility(View.GONE);
                        }

                        for (int i = 0; i < search.size(); i++)
                            items.add(search.get(i));

                        iOP_adapter = new iOperationAdapter(context, search, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                        if (status == 1) {
                            status = 0;
                        }
                    }

                } catch (Exception e) {

                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }

                //    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                if (items.size() <= 0) {
                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);
                } else {
                    LViOP.setVisibility(View.VISIBLE);
                    LLNoData.setVisibility(View.GONE);
                }

              //  Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }

        }
    }

    private class getSupplierNoShowServedJobsListAsyncTask extends AsyncTask<Void, Void, String> {

        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected void onPreExecute() {
            count++;
            showLoading();

//            Toast.makeText(getActivity(), " - " + count + "getSupplierNoShowServedJobsListAsyncTask", Toast.LENGTH_LONG).show();
            etTextPageGo.setText("");
            btnReAssign.setVisibility(View.GONE);

            if (whichPage == 1) {
//                btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            } else if (whichPage == pageSize) {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
//                btnLast.setVisibility(View.GONE);
            } else {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }


            if (pageSize == 1) {
//                btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
//                btnLast.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
            } else {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected String doInBackground(Void... v) {

            Hashtable<String, String> parameter = new Hashtable<String, String>();


            try {
                parameter = new Hashtable<String, String>();
                parameter.put("suppliercode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
                parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

                String temp = Util.connectSOAP(Constants.uRLiOpsIsSupplierMainContactPerson, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsIsSupplierMainContactPerson, Constants.APIDotNetMethodNameIsSupplierMainContactPerson, parameter);
                if (temp.compareToIgnoreCase("yes") == 0) {
                    isMainPerson = true;
                } else {
                    isMainPerson = false;
                }

                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }


            return Util.connectSOAP(Constants.uRLIOPS_GetSupplierNoShowAndServedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_GetSupplierNoShowAndServedJobsList, Constants.APIDotNetMethodNameIOPS_GetSupplierNoShowAndServedJobsList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            Log.e("res", "IOPS_GetSupplierNoShowAndServedJobsList    " + result);
            if (result != null && isTimeout == false) {
                try {
                    // store json list in shared prefrence
                    Util.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.KEY_JOBLIST, result);
                    items = new ArrayList<iOP_Model>();

                    ArrayList<iOP_Model> listTemp = Parser.getJSONSupplierNoShowServedListResultNew(result, filterStatus);

                    pageSize = Integer.parseInt(listTemp.get(0).getRecordscount());
                    int isEven = (pageSize % 10);
                    pageSize = pageSize / 10;
                    if (!(isEven == 0) || pageSize == 0)
                        pageSize = pageSize + 1;

                    for (int i = 0; i < listTemp.size(); i++)
                        items.add(listTemp.get(i));

                    iOP_adapter = new iOperationAdapter(context, items, getActivity(), isAssigned, thisFragment, isMainPerson, getActivity().getSupportFragmentManager());

                    LViOP.setAdapter(iOP_adapter);
                    iOP_adapter.notifyDataSetChanged();
                    LViOP.invalidate();

                    LViOP.invalidateViews();
                    iOP_adapter.resetCheck();

                    etTextPageGo.setHint("Page " + whichPage + " of " + pageSize);

                    if (whichPage == 1) {
//                        btnFirst.setVisibility(View.GONE);
                        btnPrevious.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
//                        btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    } else if (whichPage == pageSize) {
//                        btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.GONE);
//                        btnLast.setVisibility(View.GONE);
                    } else {
//                        btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
//                        btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    }

                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Log.e("Exception", "" + e);
//                    Toast.makeText(getActivity(), " - " + e, Toast.LENGTH_SHORT).show();
                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }

                //    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                if (items.size() <= 0) {
                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);
                } else {
                    LViOP.setVisibility(View.VISIBLE);
                    LLNoData.setVisibility(View.GONE);
                }

            //    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }

        }
    }

    private class getSupplierNoShowServedJobsSearchListAsyncTask extends AsyncTask<Void, Void, String> {

        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected void onPreExecute() {
            count++;
            showLoading();
//            Toast.makeText(getActivity(), " - " + count + "getSupplierNoShowServedJobsSearchListAsyncTask", Toast.LENGTH_LONG).show();
            etTextPageGo.setText("");
            btnReAssign.setVisibility(View.GONE);
            etTextPageGo.setVisibility(View.GONE);
//            btnFirst.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
//            btnLast.setVisibility(View.GONE);
            btnGo.setVisibility(View.GONE);


        }

        @Override
        protected String doInBackground(Void... v) {

            Hashtable<String, String> parameter = new Hashtable<String, String>();


            try {
                parameter = new Hashtable<String, String>();
                parameter.put("suppliercode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
                parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

                String temp = Util.connectSOAP(Constants.uRLiOpsIsSupplierMainContactPerson, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsIsSupplierMainContactPerson, Constants.APIDotNetMethodNameIsSupplierMainContactPerson, parameter);
                if (temp.compareToIgnoreCase("yes") == 0) {
                    isMainPerson = true;
                } else {
                    isMainPerson = false;
                }


                parameter.put("SupplierCode", SupplierCode);
//                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "999999999");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", "1");

                String resultForSize = Util.connectSOAP(Constants.uRLIOPS_GetSupplierNoShowAndServedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_GetSupplierNoShowAndServedJobsList, Constants.APIDotNetMethodNameIOPS_GetSupplierNoShowAndServedJobsList, parameter);
                pageSize = Parser.getJSONSupplierNoShowServedListResultSize(resultForSize, filterStatus);
                pageSize = pageSize / 10;
                pageSize = pageSize + 1;


                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }


            return Util.connectSOAP(Constants.uRLIOPS_GetSupplierNoShowAndServedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_GetSupplierNoShowAndServedJobsList, Constants.APIDotNetMethodNameIOPS_GetSupplierNoShowAndServedJobsList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null && isTimeout == false) {
                try {
                    // store json list in shared prefrence
                    Util.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.KEY_JOBLIST, result);

                    items = new ArrayList<iOP_Model>();

                    ArrayList<iOP_Model> listTemp = Parser.getJSONSupplierNoShowServedListResultNew(result, filterStatus);

                    if (etSearchBox.getText().toString().trim().compareToIgnoreCase("") == 0 && status == 0) {

                        if (listTemp.size() <= 0) {
                            LViOP.setVisibility(View.GONE);
                            LLNoData.setVisibility(View.VISIBLE);
                        } else {
                            LViOP.setVisibility(View.VISIBLE);
                            LLNoData.setVisibility(View.GONE);
                        }

                        for (int i = 0; i < listTemp.size(); i++)
                            items.add(listTemp.get(i));

                        iOP_adapter = new iOperationAdapter(context, listTemp, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();

                    } else {

                        search = new ArrayList<iOP_Model>();

                        for (int i = 0; i < listTemp.size(); i++) {
                            if (status == 1) {
                                Log.e("DATE>>", "" + etDate.getText().toString());
                                if (listTemp.get(i).isContainSearchFor(etDate.getText().toString().trim()))
                                    search.add(listTemp.get(i));

                            } else {
                                if (listTemp.get(i).isContainSearchFor(etSearchBox.getText().toString().toLowerCase().trim()))
                                    search.add(listTemp.get(i));
                            }
                        }

                        if (search.size() <= 0) {
                            LViOP.setVisibility(View.GONE);
                            LLNoData.setVisibility(View.VISIBLE);
                        } else {
                            LViOP.setVisibility(View.VISIBLE);
                            LLNoData.setVisibility(View.GONE);
                        }

                        for (int i = 0; i < search.size(); i++)
                            items.add(search.get(i));

                        iOP_adapter = new iOperationAdapter(context, search, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                        if (status == 1) {
                            status = 0;
                        }
                    }

                } catch (Exception e) {
                    Log.e("EXCEPTION", "" + e);
//                    Toast.makeText(getActivity(), "exception : " + e, Toast.LENGTH_SHORT).show();

                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }

                //    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");
                }

            } else {

                if (items.size() <= 0) {
                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);
                } else {
                    LViOP.setVisibility(View.VISIBLE);
                    LLNoData.setVisibility(View.GONE);
                }

             //   Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }

        }
    }

    private class getJobSupplierRejectedJobsListAsyncTask extends AsyncTask<Void, Void, String> {

        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected void onPreExecute() {
            count++;
            showLoading();

//            Toast.makeText(getActivity(), " - " + count + "getJobSupplierRejectedJobsListAsyncTask", Toast.LENGTH_LONG).show();
            etTextPageGo.setText("");
            btnReAssign.setVisibility(View.GONE);

            if (whichPage == 1) {
//                btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            } else if (whichPage == pageSize) {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
//                btnLast.setVisibility(View.GONE);
            } else {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }


            if (pageSize == 1) {
//                btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
//                btnLast.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
            } else {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected String doInBackground(Void... v) {

            Hashtable<String, String> parameter = new Hashtable<String, String>();


            try {
                parameter = new Hashtable<String, String>();
                parameter.put("suppliercode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
                parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

                String temp = Util.connectSOAP(Constants.uRLiOpsIsSupplierMainContactPerson, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsIsSupplierMainContactPerson, Constants.APIDotNetMethodNameIsSupplierMainContactPerson, parameter);
                if (temp.compareToIgnoreCase("yes") == 0) {
                    isMainPerson = true;
                } else {
                    isMainPerson = false;
                }

                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }


            return Util.connectSOAP(Constants.uRLiOpsGetSupplierRejectedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierRejectedJobsList, Constants.APIDotNetMethodNameGetSupplierRejectedJobsList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null && isTimeout == false) {
                try {
                    // store json list in shared prefrence
                    Util.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.KEY_JOBLIST, result);

                    items = new ArrayList<iOP_Model>();

                    ArrayList<iOP_Model> listTemp = Parser.getJSONGetSupplierRejectedJobsListResultNew(result, filterStatus);

                    pageSize = Integer.parseInt(listTemp.get(0).getRecordscount());
                    int isEven = (pageSize % 10);
                    pageSize = pageSize / 10;
                    if (!(isEven == 0) || pageSize == 0)
                        pageSize = pageSize + 1;

                    for (int i = 0; i < listTemp.size(); i++)
                        items.add(listTemp.get(i));

                    iOP_adapter = new iOperationAdapter(context, items, getActivity(), isAssigned, thisFragment, isMainPerson, getActivity().getSupportFragmentManager());

                    LViOP.setAdapter(iOP_adapter);
                    iOP_adapter.notifyDataSetChanged();
                    LViOP.invalidate();

                    LViOP.invalidateViews();
                    iOP_adapter.resetCheck();

                    etTextPageGo.setHint("Page " + whichPage + " of " + pageSize);

                    if (whichPage == 1) {
//                        btnFirst.setVisibility(View.GONE);
                        btnPrevious.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
//                        btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    } else if (whichPage == pageSize) {
//                        btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.GONE);
//                        btnLast.setVisibility(View.GONE);
                    } else {
//                        btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
//                        btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    }

                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Log.e("Exception", "" + e);
//                    Toast.makeText(getActivity(), " - " + e, Toast.LENGTH_SHORT).show();
                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }

                //    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                if (items.size() <= 0) {
                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);
                } else {
                    LViOP.setVisibility(View.VISIBLE);
                    LLNoData.setVisibility(View.GONE);
                }

            //    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }

        }
    }

    private class updateJobAcceptedAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            count++;
            showLoading();
//            Toast.makeText(getActivity(), " - " + count + "updateJobAcceptedAsyncTask", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("JobNo", MultipleJobNo);
                parameter.put("IOPSUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }

            return Util.connectSOAP(Constants.uRLiOpsUpdateJobAcceptedStatus, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateJobAcceptedStatus, Constants.APIDotNetMethodNameUpdateJobAcceptedStatus, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();

            //Util.showAlertCommonDialogWithOKBtn(context, "result",MultipleJobNo);

            if (result != null) {
                try {

                    String temp = Parser.getJSONAcceptRejectResult(result);
                    //Util.showAlertCommonDialogWithOKBtn(context, "Success","Job #" + MultipleJobNo + " Accepted Succesfully");
                    items = new ArrayList<iOP_Model>();
                    search = new ArrayList<iOP_Model>();


                    try {

                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("Job #" + MultipleJobNo + " Accepted Succesfully");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                        getActivity().finish();
                                        Intent intent = new Intent(ChooseTaskActivity.context, iOpMainViewPagerActivity.class);
                                        intent.putExtra(Constants.intentKey_iOp, 1);
                                        startActivity(intent);
                                        return;
                                    }
                                });

                        alertDialog.show();

                    } catch (Exception e) {
                        Util.modifiedLogTrace(e.getStackTrace().toString());
                    }


                } catch (Exception e) {
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Operation Failed");
                }


            } else {

                Util.showAlertCommonDialogWithOKBtn(context, "Error ", "Operation Failed");

            }

        }
    }

    private class updateJobRejectedAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            count++;
            showLoading();
//            Toast.makeText(getActivity(), " - " + count + "updateJobRejectedAsyncTask", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("JobNo", MultipleJobNo);
                parameter.put("IOPSUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsUpdateJobRejectedStatus, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateJobRejectedStatus, Constants.APIDotNetMethodNameUpdateJobRejectedStatus, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {

                    String temp = Parser.getJSONAcceptRejectResult(result);
                    Util.showAlertCommonDialogWithOKBtn(context, "Success", "Job #" + MultipleJobNo + " Rejected Succesfully");
                    items = new ArrayList<iOP_Model>();
                    search = new ArrayList<iOP_Model>();


                    try {

                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("Job #" + MultipleJobNo + " Rejected Succesfully");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                        getActivity().finish();
                                        Intent intent = new Intent(ChooseTaskActivity.context, iOpMainViewPagerActivity.class);
                                        intent.putExtra(Constants.intentKey_iOp, 1);
                                        startActivity(intent);
                                        return;
                                    }
                                });

                        alertDialog.show();

                    } catch (Exception e) {
                        Util.modifiedLogTrace(e.getStackTrace().toString());
                    }


                } catch (Exception e) {
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Operation Failed");
                }


            } else {

                Util.showAlertCommonDialogWithOKBtn(context, "Error ", "Operation Failed");

            }

        }
    }

    private class checkIsMainPersonAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            count++;
            showLoading();

//            Toast.makeText(getActivity(), " - " + count + "checkIsMainPersonAsyncTask", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("suppliercode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
                parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsIsSupplierMainContactPerson, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsIsSupplierMainContactPerson, Constants.APIDotNetMethodNameIsSupplierMainContactPerson, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {

                    String temp = Parser.getJSONAcceptRejectResult(result);

                    if (temp.compareToIgnoreCase("yes") == 0) {


                    } else {


                    }

                } catch (Exception e) {

                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Operation Failed");
                }


            } else {

                Util.showAlertCommonDialogWithOKBtn(context, "Error ", "Operation Failed");

            }

        }
    }

    private class getSupplierAcceptedJobsDateSearchListAsyncTask extends AsyncTask<Void, Void, String> {

        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected void onPreExecute() {
            count++;
            showLoading();
//            Toast.makeText(getActivity(), " - " + count + " MEHUL", Toast.LENGTH_LONG).show();

            btnReAssign.setVisibility(View.VISIBLE);
            etTextPageGo.setVisibility(View.GONE);
//            btnFirst.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
//            btnLast.setVisibility(View.GONE);
            btnGo.setVisibility(View.GONE);


        }

        @Override
        protected String doInBackground(Void... v) {

            Hashtable<String, String> parameter = new Hashtable<String, String>();


            try {
                parameter = new Hashtable<String, String>();
                parameter.put("suppliercode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
                parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

                String temp = Util.connectSOAP(Constants.uRLiOpsIsSupplierMainContactPerson, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsIsSupplierMainContactPerson, Constants.APIDotNetMethodNameIsSupplierMainContactPerson, parameter);
                if (temp.compareToIgnoreCase("yes") == 0) {
                    isMainPerson = true;
                } else {
                    isMainPerson = false;
                }

//
                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", "1");

                String resultForSize = Util.connectSOAP(Constants.uRLiOpsGetSupplierAssignedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierAssignedJobsList, Constants.APIDotNetMethodNameGetSupplierAssignedJobsList, parameter);
                pageSize = Parser.getJSONSupplierJobAssignListResultSize(resultForSize, filterStatus);
                pageSize = pageSize / 10;
                pageSize = pageSize + 1;


                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsGetSupplierAssignedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierAssignedJobsList, Constants.APIDotNetMethodNameGetSupplierAssignedJobsList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null && isTimeout == false) {
                try {
                    Log.e("RESULT>>", result);
                    Log.d("msg", "res " + result);
                    // store json list in shared prefrence
                    items = new ArrayList<iOP_Model>();

                    ArrayList<iOP_Model> listTemp = Parser.getJSONSupplierJobAssignListResultNew(result, filterStatus);
                    Log.e("DATE STATUS", "" + status);


                    if (etDate.getText().toString().trim().compareToIgnoreCase("") == 0) {
                        if (listTemp.size() <= 0) {
                            LViOP.setVisibility(View.GONE);
                            LLNoData.setVisibility(View.VISIBLE);
                        } else {
                            LViOP.setVisibility(View.VISIBLE);
                            LLNoData.setVisibility(View.GONE);
                        }

                        for (int i = 0; i < listTemp.size(); i++)

                            items.add(listTemp.get(i));

                        iOP_adapter = new iOperationAdapter(context, listTemp, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                    } else {
                        search = new ArrayList<iOP_Model>();

                        for (int i = 0; i < listTemp.size(); i++) {
                            Log.e("MEHUl>>>>>", "" + status);
                            Log.e("DATE>>", "" + etDate.getText().toString().trim());
                            if (listTemp.get(i).isContainSearchFor(etDate.getText().toString().trim()))
                                search.add(listTemp.get(i));
                        }

                        Log.e("SEARCH SIZE", "" + search.size());
                        if (search.size() <= 0) {
                            LViOP.setVisibility(View.GONE);
                            LLNoData.setVisibility(View.VISIBLE);
                        } else {
                            LViOP.setVisibility(View.VISIBLE);
                            LLNoData.setVisibility(View.GONE);
                        }


                        for (int i = 0; i < search.size(); i++)
                            items.add(search.get(i));

                        iOP_adapter = new iOperationAdapter(context, search, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                    }
                } catch (Exception e) {

                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }
                    Util.showAlertCommonDialogWithOKBtn(context, "Error2", "Unable to get data from server. Check your internet connection and please try again.");
                }
            } else {
                Log.d("msg", "res else " + result);
                if (items.size() <= 0) {
                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);
                } else {
                    LViOP.setVisibility(View.VISIBLE);
                    LLNoData.setVisibility(View.GONE);
                }

                Util.showAlertCommonDialogWithOKBtn(context, "Error2", "Unable to get data from server. Check your internet connection and please try again.");

            }
        }
    }



    private class getJobSupplierRejectedJobsDateSearchListAsyncTask extends AsyncTask<Void, Void, String> {

        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected void onPreExecute() {
            count++;
            showLoading();

//            Toast.makeText(getActivity(), " - " + count + "getJobSupplierRejectedJobsListAsyncTask", Toast.LENGTH_LONG).show();
            etTextPageGo.setText("");
            btnReAssign.setVisibility(View.GONE);

            if (whichPage == 1) {
//                btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            } else if (whichPage == pageSize) {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
//                btnLast.setVisibility(View.GONE);
            } else {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }


            if (pageSize == 1) {
//                btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
//                btnLast.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
            } else {
//                btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//                btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected String doInBackground(Void... v) {

            Hashtable<String, String> parameter = new Hashtable<String, String>();


            try {
                parameter = new Hashtable<String, String>();
                parameter.put("suppliercode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
                parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

                String temp = Util.connectSOAP(Constants.uRLiOpsIsSupplierMainContactPerson, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsIsSupplierMainContactPerson, Constants.APIDotNetMethodNameIsSupplierMainContactPerson, parameter);
                if (temp.compareToIgnoreCase("yes") == 0) {
                    isMainPerson = true;
                } else {
                    isMainPerson = false;
                }

                parameter.put("SupplierCode", SupplierCode);
                parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }


            return Util.connectSOAP(Constants.uRLiOpsGetSupplierRejectedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierRejectedJobsList, Constants.APIDotNetMethodNameGetSupplierRejectedJobsList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null && isTimeout == false) {
                try {
                    // store json list in shared prefrence
                    Util.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.KEY_JOBLIST, result);

                    items = new ArrayList<iOP_Model>();

                    ArrayList<iOP_Model> listTemp = Parser.getJSONGetSupplierRejectedJobsListResultNew(result, filterStatus);

                    if (etSearchBox.getText().toString().trim().compareToIgnoreCase("") == 0 && status == 0) {

                        if (listTemp.size() <= 0) {
                            LViOP.setVisibility(View.GONE);
                            LLNoData.setVisibility(View.VISIBLE);
                        } else {
                            LViOP.setVisibility(View.VISIBLE);
                            LLNoData.setVisibility(View.GONE);
                        }

                        for (int i = 0; i < listTemp.size(); i++)
                            items.add(listTemp.get(i));

                        iOP_adapter = new iOperationAdapter(context, listTemp, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();

                    } else {

                        search = new ArrayList<iOP_Model>();

                        for (int i = 0; i < listTemp.size(); i++) {
                            if (status == 1) {
                                Log.e("DATE>>", "" + etDate.getText().toString());
                                if (listTemp.get(i).isContainSearchFor(etDate.getText().toString().trim()))
                                    search.add(listTemp.get(i));

                            } else {
                                if (listTemp.get(i).isContainSearchFor(etSearchBox.getText().toString().toLowerCase().trim()))
                                    search.add(listTemp.get(i));
                            }
                        }

                        if (search.size() <= 0) {
                            LViOP.setVisibility(View.GONE);
                            LLNoData.setVisibility(View.VISIBLE);
                        } else {
                            LViOP.setVisibility(View.VISIBLE);
                            LLNoData.setVisibility(View.GONE);
                        }

                        for (int i = 0; i < search.size(); i++)
                            items.add(search.get(i));

                        iOP_adapter = new iOperationAdapter(context, search, getActivity(), isAssigned, thisFragment, getActivity().getSupportFragmentManager());
                        iOP_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                        if (status == 1) {
                            status = 0;
                        }
                    }

                } catch (Exception e) {
                    Log.e("Exception", "" + e);
                    if (items.size() <= 0) {
                        LViOP.setVisibility(View.GONE);
                        LLNoData.setVisibility(View.VISIBLE);
                    } else {
                        LViOP.setVisibility(View.VISIBLE);
                        LLNoData.setVisibility(View.GONE);
                    }
                    Util.showAlertCommonDialogWithOKBtn(context, "Error1", "Unable to get data from server. Check your internet connection and please try again.");
                }
            } else {

                if (items.size() <= 0) {
                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);
                } else {
                    LViOP.setVisibility(View.VISIBLE);
                    LLNoData.setVisibility(View.GONE);
                }
                Util.showAlertCommonDialogWithOKBtn(context, "Error1", "Unable to get data from server. Check your internet connection and please try again.");
            }
        }
    }

}