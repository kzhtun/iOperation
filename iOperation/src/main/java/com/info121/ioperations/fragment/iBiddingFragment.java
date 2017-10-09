package com.info121.ioperations.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.info121.ioperations.adapter.iBiddingAdapter;
import com.info121.ioperations.iOpMainViewPagerActivity;
import com.info121.ioperations.util.Constant;
import com.info121.ioperations.util.Parser;
import com.info121.ioperations.util.Util;
import com.info121.model.iBid_Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

public final class iBiddingFragment extends Fragment {
    private TextView username;
    private static final String KEY_CONTENT = "TestFragment:Content";
    private Context context;
    private ListView LViOP;
    private LinearLayout LLNoData;
    private TextView TVHeader;
    private Spinner spinFilterList;

    private ArrayList<iBid_Model> items = new ArrayList<iBid_Model>();
    private ArrayList<iBid_Model> search = new ArrayList<iBid_Model>();

    private iBiddingAdapter iBid_adapter;
    private String SupplierCode;
    private String Status;

    private ProgressDialog progress;

    private Button btHome;
    public Button btnAccept;
    public Button btnReject;
    private Button btnSearch;
    private EditText etSearchBox;
    private EditText etDate;
    private String MultipleJobNo;

    private int whichPage = 1;
    private int pageSize = 0;

    //paging things
//    private Button btnFirst;
    private TextView btnPrevious;
    private TextView btnNext;
    //    private Button btnLast;
    private Button btnGo;
    private EditText etTextPageGo;
    private int posSpin = 0;

    //buttons navigation
    private Button btnUpdateServer;
    private Button btniOperation;
    private Button btniBidding;
    private Button btnFeedback;

    private Calendar myCalendar;
    private ImageView ivCalender;
    private int year, month, day;
    private LinearLayout LLLoading;
    private static int status = 0;

    private TextView mNotiCount;
    private ImageView mNotiImage;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    public static iBiddingFragment thisFragment;
    private int spinCurrentPosition;

    public static iBiddingFragment newInstance(String content) {
        iBiddingFragment fragment = new iBiddingFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisFragment = this;

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }


    public static boolean isActive = false;

    @Override
    public void onResume() {
        super.onResume();
        isActive = true;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_ibidding_layout, container, false);
        context = getActivity();
        items = new ArrayList<iBid_Model>();
        LViOP = (ListView) v.findViewById(R.id.listView_iBiddingFragment);
        LLLoading = (LinearLayout) v.findViewById(R.id.LLRootLoading_iBiddingFragment);
        etDate = (EditText) v.findViewById(R.id.etDate_iBiddingFragment);
        ivCalender = (ImageView) v.findViewById(R.id.ivCalender_iBiddingFragment);
        //populateItemDummyData();

        LLNoData = (LinearLayout) v.findViewById(R.id.LLNoDataAssigment_iBiddingFragment);
        etSearchBox = (EditText) v.findViewById(R.id.editTextiBiddingSearch);
        etSearchBox.clearFocus();
        LViOP.requestFocus();

//        LViOP.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                if (spinCurrentPosition == 3)
//                    BidDetailDialog.show(context, getActivity(), items.get(position), thisFragment, false);
//                else
//                    BidDetailDialog.show(context, getActivity(), items.get(position), thisFragment, true);
//            }
//        });

        btHome = (Button) v.findViewById(R.id.btHome_iBiddingFragment);
        btHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment bidingFragment = new HomeFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, bidingFragment);
                fragmentTransaction.commit();

            }
        });


        iBid_adapter = new iBiddingAdapter(context, items, getActivity().getSupportFragmentManager());

        LViOP.setAdapter(iBid_adapter);
//        TVHeader = (TextView) v.findViewById(R.id.textViewiOperationInfoHeader);

        String Driver = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, "");
        String Company = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, "");
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

//        TVHeader.setText("Driver : " + Driver + "          Company : " + Company + "            Date : " + mydate + "");
        spinFilterList = (Spinner) v.findViewById(R.id.spinneriBidding);
        List<String> list = new ArrayList<String>();
        list.add(getActivity().getResources().getString(R.string.ibidding_new_list));
        list.add(getActivity().getResources().getString(R.string.ibidding_completed_list));
        list.add(getActivity().getResources().getString(R.string.ibidding_rejected_list));
        list.add(getActivity().getResources().getString(R.string.ibidding_close_list));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                R.layout.spinner_item,R.id.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinFilterList.setAdapter(dataAdapter);


// 		((TextView) v.findViewById(R.id.TextViewiOPBookNo)).setText("Book No");
// 		((TextView) v.findViewById(R.id.TextViewiOPChild1)).setText("Bid No");
//		((TextView) v.findViewById(R.id.TextViewiOPChild2)).setText("Date");
//		((TextView) v.findViewById(R.id.TextViewiOPChild3)).setText("Type");
//		((TextView) v.findViewById(R.id.TextViewiOPChild4)).setText("Guest");
//		((TextView) v.findViewById(R.id.TextViewiOPChild5)).setText("A");
//		((TextView) v.findViewById(R.id.TextViewiOPChild6)).setText("C");
//		((TextView) v.findViewById(R.id.TextViewiOPChild7)).setText("I");
//		((TextView) v.findViewById(R.id.TextViewiOPChild8)).setText("Flight");
//		((TextView) v.findViewById(R.id.TextViewiOPChild9)).setText("Flight Time");
//		((TextView) v.findViewById(R.id.TextViewiOPChild10)).setText("Pickup Time");
//		//((TextView) v.findViewById(R.id.TextViewiOPChild11)).setText("Pickup Place");
//		((TextView) v.findViewById(R.id.TextViewiOPChild12)).setText("Hotel");
//		((TextView) v.findViewById(R.id.TextViewiOPChild13)).setText("Booking Remarks");
//		((TextView) v.findViewById(R.id.TextViewiOPChild14)).setText("Service Date");
//		((TextView) v.findViewById(R.id.TextViewiOPChild15)).setText("Agent");
//		((TextView) v.findViewById(R.id.TextViewiOPChild16)).setText("Select");
//		((TextView) v.findViewById(R.id.TextViewiOPChild17)).setText("Status");
//		try {
//			((TextView) v.findViewById(R.id.TextViewiOPChild20)).setText("TPTRemarks");
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

        SupplierCode = getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.SUPPLIER_KEY, "");
        Status = "Bid New";


        //new getSupplierBidListAsyncTask().execute();

        btnAccept = (Button) v.findViewById(R.id.buttonBidAccepted);

        btnAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean check = true;

                for (int i = 0; i < iBid_adapter.getRis().size(); i++) {
                    if (iBid_adapter.getRis().get(i).isSelected() == true && check) {
                        check = false;
                    }
                }

                if (check)
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Please ticks at least one item in bids list");
                else {
                    MultipleJobNo = "";
                    for (int i = 0; i < iBid_adapter.getRis().size(); i++) {
                        if (iBid_adapter.getRis().get(i).isSelected())
                            MultipleJobNo = MultipleJobNo + iBid_adapter.items.get(i).getBidNo() + "|";
                    }
                    MultipleJobNo = MultipleJobNo.substring(0, MultipleJobNo.length() - 1);


                    new updateBidAcceptedAsyncTask().execute();
                }


                if (iBiddingAdapter.selectedItem == -1)
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Please ticked select for one item in jobs list");
                else
                    new updateBidAcceptedAsyncTask().execute();

            }
        });

        btnReject = (Button) v.findViewById(R.id.buttonBidRejected);


        btnReject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = true;

                for (int i = 0; i < iBid_adapter.getRis().size(); i++) {
                    if (iBid_adapter.getRis().get(i).isSelected() == true && check) {
                        check = false;
                    }
                }

                if (check)
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Please ticks at least one item in bids list");
                else {
                    MultipleJobNo = "";
                    for (int i = 0; i < iBid_adapter.getRis().size(); i++) {
                        if (iBid_adapter.getRis().get(i).isSelected())
                            MultipleJobNo = MultipleJobNo + iBid_adapter.items.get(i).getBidNo() + "|";
                    }
                    MultipleJobNo = MultipleJobNo.substring(0, MultipleJobNo.length() - 1);


                    new updateBidRejectedAsyncTask().execute();
                }

                if (iBiddingAdapter.selectedItem == -1)
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Please ticked select for one item in jobs list");
                else
                    new updateBidRejectedAsyncTask().execute();

            }
        });
        spinFilterList.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                posSpin = position;
                if (position == 0) {
                    btnAccept.setVisibility(View.VISIBLE);
                    btnReject.setVisibility(View.VISIBLE);
                    spinCurrentPosition = 0;
                    whichPage = 1;
                    new getSupplierBidListAsyncTask().execute();
                } else if (position == 1) {
                    btnAccept.setVisibility(View.VISIBLE);
                    btnReject.setVisibility(View.VISIBLE);
                    spinCurrentPosition = 1;
                    whichPage = 1;
                    new getSupplierBidAcceptedListAsyncTask().execute();
                } else if (position == 2) {
                    btnAccept.setVisibility(View.VISIBLE);
                    btnReject.setVisibility(View.VISIBLE);
                    spinCurrentPosition = 2;
                    whichPage = 1;
                    new getSupplierBidRejectedListAsyncTask().execute();
                } else if (position == 3) {
                    btnAccept.setVisibility(View.GONE);
                    btnReject.setVisibility(View.GONE);
                    spinCurrentPosition = 3;
                    whichPage = 1;
                    new getSupplierBidCloseListAsyncTask().execute();
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        etSearchBox.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(etSearchBox) {
            @Override
            public boolean onDrawableClick() {
                // TODO : insert code to perform on clicking of the RIGHT drawable image...
                etSearchBox.setText("");
//                iBid_adapter.notifyDataSetChanged();
//                LViOP.invalidate();
//                LViOP.invalidateViews();
                if (spinFilterList.getSelectedItemPosition() == 0)
                    new getSupplierBidSearchListAsyncTask().execute();
                else if (spinFilterList.getSelectedItemPosition() == 1)
                    new getSupplierBidAcceptedSearchListAsyncTask().execute();
                else if (spinFilterList.getSelectedItemPosition() == 2)
                    new getSupplierBidRejectedSearchListAsyncTask().execute();
                else if (spinFilterList.getSelectedItemPosition() == 3)
                    new getSupplierBidCloseSearchListAsyncTask().execute();
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
                iBid_adapter.notifyDataSetChanged();
                LViOP.invalidate();
                LViOP.invalidateViews();

                if (spinFilterList.getSelectedItemPosition() == 0)
                    new getSupplierBidSearchListAsyncTask().execute();
                else if (spinFilterList.getSelectedItemPosition() == 1)
                    new getSupplierBidAcceptedSearchListAsyncTask().execute();
                else if (spinFilterList.getSelectedItemPosition() == 2)
                    new getSupplierBidRejectedSearchListAsyncTask().execute();
                else if (spinFilterList.getSelectedItemPosition() == 3)
                    new getSupplierBidCloseSearchListAsyncTask().execute();
            }
        });
//        iBid_adapter.notifyDataSetChanged();
//        LViOP.invalidate();
//        LViOP.invalidateViews();

//        btnSearch = (Button) v.findViewById(R.id.buttoniBiddingSearch);

//        btnSearch.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                iBid_adapter.notifyDataSetChanged();
//                LViOP.invalidate();
//                LViOP.invalidateViews();
//
//                if (spinFilterList.getSelectedItemPosition() == 0)
//                    new getSupplierBidSearchListAsyncTask().execute();
//                else if (spinFilterList.getSelectedItemPosition() == 1)
//                    new getSupplierBidAcceptedSearchListAsyncTask().execute();
//                else if (spinFilterList.getSelectedItemPosition() == 2)
//                    new getSupplierBidRejectedSearchListAsyncTask().execute();
//                else if (spinFilterList.getSelectedItemPosition() == 3)
//                    new getSupplierBidCloseSearchListAsyncTask().execute();
//
//
//            }
//        });


//		btnFirst = (Button) v.findViewById(R.id.ButtoniBidFirst);
//		btnFirst.setOnClickListener(new OnClickListener() {
//			   @Override
//			   public void onClick(View v) {
//
//				    whichPage = 1;
//				    if(posSpin == 0){
//	 	        		new getSupplierBidListAsyncTask().execute();
//	 	        	}else if(posSpin == 1){
//	 	        		new getSupplierBidAcceptedListAsyncTask().execute();
//	 	        	}else if(posSpin == 2){
//	 	        		new getSupplierBidRejectedListAsyncTask().execute();
//	 	        	}else if(posSpin == 3){
//	 	        		new getSupplierBidCloseListAsyncTask().execute();
//	 	        	}
//					btnPrevious.setVisibility(View.GONE);
//
//			   }
//		});

        btnPrevious = (TextView) v.findViewById(R.id.ButtoniBidPrevious);
        btnPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                whichPage = whichPage - 1;
                if (posSpin == 0) {
                    new getSupplierBidListAsyncTask().execute();
                } else if (posSpin == 1) {
                    new getSupplierBidAcceptedListAsyncTask().execute();
                } else if (posSpin == 2) {
                    new getSupplierBidRejectedListAsyncTask().execute();
                } else if (posSpin == 3) {
                    new getSupplierBidCloseListAsyncTask().execute();
                }

            }
        });

        btnNext = (TextView) v.findViewById(R.id.ButtoniBidNext);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                whichPage = whichPage + 1;
                if (posSpin == 0) {
                    new getSupplierBidListAsyncTask().execute();
                } else if (posSpin == 1) {
                    new getSupplierBidAcceptedListAsyncTask().execute();
                } else if (posSpin == 2) {
                    new getSupplierBidRejectedListAsyncTask().execute();
                } else if (posSpin == 3) {
                    new getSupplierBidCloseListAsyncTask().execute();
                }

            }
        });

//		btnLast = (Button) v.findViewById(R.id.ButtoniBidLast);
//		btnLast.setOnClickListener(new OnClickListener() {
//			   @Override
//			   public void onClick(View v) {
//
//				   whichPage = pageSize;
//				   if(posSpin == 0){
//	 	        		new getSupplierBidListAsyncTask().execute();
//	 	        	}else if(posSpin == 1){
//	 	        		new getSupplierBidAcceptedListAsyncTask().execute();
//	 	        	}else if(posSpin == 2){
//	 	        		new getSupplierBidRejectedListAsyncTask().execute();
//	 	        	}else if(posSpin == 3){
//	 	        		new getSupplierBidCloseListAsyncTask().execute();
//	 	        	}
//			   }
//		});

        btnGo = (Button) v.findViewById(R.id.ButtoniBidGo);
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
                            if (posSpin == 0) {
                                new getSupplierBidListAsyncTask().execute();
                            } else if (posSpin == 1) {
                                new getSupplierBidAcceptedListAsyncTask().execute();
                            } else if (posSpin == 2) {
                                new getSupplierBidRejectedListAsyncTask().execute();
                            } else if (posSpin == 3) {
                                new getSupplierBidCloseListAsyncTask().execute();
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "page number is out of range");
                }

            }
        });

        etTextPageGo = (EditText) v.findViewById(R.id.editTextiBidPage);


//		btnUpdateServer = (Button) v.findViewById(R.id.ButtonShortcutiOpServerInfo);
//		btnUpdateServer.setText("xxx.xxx."+Constants.SERVER_URL.substring(8, Constants.SERVER_URL.length()));
//		btnUpdateServer.setOnClickListener(new OnClickListener() {
//			   @Override
//			   public void onClick(View v) {
//
//				   UpdateBidServerDialog.show(context, getActivity(), thisFragment, true);
//
//			   }
//		});

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

        Util.closeKeyboard(getActivity(), etSearchBox.getWindowToken());

        myCalendar = Calendar.getInstance();
        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        month = myCalendar.get(Calendar.MONTH);
        year = myCalendar.get(Calendar.YEAR);

        ivCalender.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
                if (spinFilterList.getSelectedItemPosition() == 0)
                    new getSupplierBidSearchListAsyncTask().execute();
                else if (spinFilterList.getSelectedItemPosition() == 1)
                    new getSupplierBidAcceptedSearchListAsyncTask().execute();
                else if (spinFilterList.getSelectedItemPosition() == 2)
                    new getSupplierBidRejectedSearchListAsyncTask().execute();
                else if (spinFilterList.getSelectedItemPosition() == 3)
                    new getSupplierBidCloseSearchListAsyncTask().execute();
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
        status = 1;
        if (spinFilterList.getSelectedItemPosition() == 0)
            new getSupplierBidSearchListAsyncTask().execute();
        else if (spinFilterList.getSelectedItemPosition() == 1)
            new getSupplierBidAcceptedSearchListAsyncTask().execute();
        else if (spinFilterList.getSelectedItemPosition() == 2)
            new getSupplierBidRejectedSearchListAsyncTask().execute();
        else if (spinFilterList.getSelectedItemPosition() == 3)
            new getSupplierBidCloseSearchListAsyncTask().execute();

        iBid_adapter.notifyDataSetChanged();
        LViOP.invalidate();
        LViOP.invalidateViews();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }


    private int mod(int x, int y) {
        int result = x % y;
        return result < 0 ? result + y : result;
    }


    protected void showLoading() {
        LLLoading.setVisibility(View.VISIBLE);
    }

    protected void dismissLoading() {
        LLLoading.setVisibility(View.GONE);
    }


    private class getSupplierBidListAsyncTask extends AsyncTask<Void, Void, String> {

        private boolean isTimeout = false;

        @Override
        protected void onPreExecute() {

            showLoading();
            etTextPageGo.setText("");

            if (whichPage == 1) {
//        		btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
//	    		btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            } else if (whichPage == pageSize) {
//        		btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
//        		btnLast.setVisibility(View.GONE);
            } else {
//        		 btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//        		 btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }


            if (pageSize == 1) {
//        		 btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
//        		 btnLast.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
            } else {

//         		 btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//        		 btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {

                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }

            return Util.connectSOAP(Constants.uRLiOpsGetSupplierBidNewList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidNewList, Constants.APIDotNetMethodNameGetSupplierBidNewList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                Log.e("RESULT", "" + result);
                try {
                    // store json list in shared prefrence
                    Util.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.KEY_JSONLIST, result);

                    items = new ArrayList<iBid_Model>();
                    ArrayList<iBid_Model> listTemp = Parser.getJSONSupplierBidNewListResultNew(result);

                    pageSize = Integer.parseInt(listTemp.get(0).getRecordscount());

                    int isEven = (pageSize % 10);
                    pageSize = pageSize / 10;
                    if (!(isEven == 0) || pageSize == 0)
                        pageSize = pageSize + 1;


                    for (int i = 0; i < listTemp.size(); i++)
                        items.add(listTemp.get(i));
                    iBid_adapter = new iBiddingAdapter(context, items, getActivity().getSupportFragmentManager());

                    LViOP.setAdapter(iBid_adapter);
                    iBid_adapter.notifyDataSetChanged();

                    LViOP.invalidate();
                    LViOP.invalidateViews();

                    etTextPageGo.setHint("Page " + whichPage + " of " + pageSize);

                    if (whichPage == 1) {
//                		btnFirst.setVisibility(View.GONE);
                        btnPrevious.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
//        	    		btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    } else if (whichPage == pageSize) {
//                		btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.GONE);
//                		btnLast.setVisibility(View.GONE);
                    } else {
//                		 btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
//                		 btnLast.setVisibility(View.VISIBLE);
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

                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);

                   // Util.showAlertCommonDialogWithOKBtn(context, "Error","Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {


                LViOP.setVisibility(View.GONE);
                LLNoData.setVisibility(View.VISIBLE);

               // Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }


        }
    }


    private class getSupplierBidSearchListAsyncTask extends AsyncTask<Void, Void, String> {

        private boolean isTimeout = false;

        @Override
        protected void onPreExecute() {

            showLoading();
            etTextPageGo.setText("");

//        		 btnFirst.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
//        		 btnLast.setVisibility(View.GONE);
            btnGo.setVisibility(View.GONE);
            etTextPageGo.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
//                parameter.put("PageSize", "999999999");
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", "1");

                String resultForSize = Util.connectSOAP(Constants.uRLiOpsGetSupplierBidNewList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidNewList, Constants.APIDotNetMethodNameGetSupplierBidNewList, parameter);
                pageSize = Parser.getJSONSupplierBidNewListResultSize(resultForSize);
                pageSize = pageSize / 10;
                pageSize = pageSize + 1;

                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
//                parameter.put("PageSize", "999999999");
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "10");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }

            return Util.connectSOAP(Constants.uRLiOpsGetSupplierBidNewList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidNewList, Constants.APIDotNetMethodNameGetSupplierBidNewList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {
                    items = new ArrayList<iBid_Model>();

                    ArrayList<iBid_Model> listTemp = Parser.getJSONSupplierBidNewListResultNew(result);

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
                        iBid_adapter = new iBiddingAdapter(context, listTemp, getActivity().getSupportFragmentManager());
                        LViOP.setAdapter(iBid_adapter);
                        iBid_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();

                    } else {

                        search = new ArrayList<iBid_Model>();

                        for (int i = 0; i < listTemp.size(); i++) {
                            if (status == 1) {
                                Log.e("DATE>>", "" + etDate.getText().toString());
                                if (listTemp.get(i).isContainSearchFor(etDate.getText().toString().trim()))
                                    search.add(listTemp.get(i));

                            } else {
                                Log.e("SEARCH>>", "" + etSearchBox.getText().toString());
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

                        if (status == 1) {
                            status = 0;
                        }
                        iBid_adapter = new iBiddingAdapter(context, search, getActivity().getSupportFragmentManager());
                        LViOP.setAdapter(iBid_adapter);
                        iBid_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();

                    }

                } catch (Exception e) {

                    Log.e("EXCEPTION",""+e);
//                    Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);

                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {


                LViOP.setVisibility(View.GONE);
                LLNoData.setVisibility(View.VISIBLE);
//                Toast.makeText(getActivity(), "msg", Toast.LENGTH_SHORT).show();
                Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }


        }
    }


    private class getSupplierBidAcceptedListAsyncTask extends AsyncTask<Void, Void, String> {

        private boolean isTimeout = false;

        @Override
        protected void onPreExecute() {

            showLoading();
            etTextPageGo.setText("");

            if (whichPage == 1) {
//        		btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
//	    		btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            } else if (whichPage == pageSize) {
//        		btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
//        		btnLast.setVisibility(View.GONE);
            } else {
//        		 btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//        		 btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }


            if (pageSize == 1) {
//        		 btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
//        		 btnLast.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
            } else {

//         		 btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//        		 btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected String doInBackground(Void... v) {

            Hashtable<String, String> parameter = null;
            try {

                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsGetSupplierBidAcceptList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidAcceptList, Constants.APIDotNetMethodNameGetSupplierBidAcceptList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {
                    // store json list in shared prefrence
                    Util.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.KEY_JSONLIST, result);
                    items = new ArrayList<iBid_Model>();

                    ArrayList<iBid_Model> listTemp = Parser.getJSONSupplierBidAcceptedListResultNew(result);

                    pageSize = Integer.parseInt(listTemp.get(0).getRecordscount());
                    int isEven = (pageSize % 10);
                    pageSize = pageSize / 10;
                    if (!(isEven == 0) || pageSize == 0)
                        pageSize = pageSize + 1;


                    for (int i = 0; i < listTemp.size(); i++)
                        items.add(listTemp.get(i));
                    iBid_adapter = new iBiddingAdapter(context, items, getActivity().getSupportFragmentManager());

                    LViOP.setAdapter(iBid_adapter);
                    iBid_adapter.notifyDataSetChanged();
                    LViOP.invalidate();
                    LViOP.invalidateViews();

                    etTextPageGo.setHint("Page " + whichPage + " of " + pageSize);

                    if (whichPage == 1) {
//                		btnFirst.setVisibility(View.GONE);
                        btnPrevious.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
//        	    		btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    } else if (whichPage == pageSize) {
//                		btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.GONE);
//                		btnLast.setVisibility(View.GONE);
                    } else {
//                		 btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
//                		 btnLast.setVisibility(View.VISIBLE);
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

                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);

                    Util.showAlertCommonDialogWithOKBtn(context, "Error","Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                LViOP.setVisibility(View.GONE);
                LLNoData.setVisibility(View.VISIBLE);

                Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }


        }
    }

    private class getSupplierBidAcceptedSearchListAsyncTask extends AsyncTask<Void, Void, String> {

        private boolean isTimeout = false;

        @Override
        protected void onPreExecute() {

            showLoading();
            etTextPageGo.setText("");

            etTextPageGo.setVisibility(View.GONE);
//        		 btnFirst.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
//        		 btnLast.setVisibility(View.GONE);
            btnGo.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(Void... v) {

            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
//                parameter.put("PageSize", "999999999");
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", "1");

                String resultForSize = Util.connectSOAP(Constants.uRLiOpsGetSupplierBidAcceptList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidAcceptList, Constants.APIDotNetMethodNameGetSupplierBidAcceptList, parameter);
                pageSize = Parser.getJSONSupplierBidAcceptedListResultSize(resultForSize);
                pageSize = pageSize / 10;
                pageSize = pageSize + 1;

                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsGetSupplierBidAcceptList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidAcceptList, Constants.APIDotNetMethodNameGetSupplierBidAcceptList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {
                    items = new ArrayList<iBid_Model>();

                    ArrayList<iBid_Model> listTemp = Parser.getJSONSupplierBidAcceptedListResultNew(result);

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

                        iBid_adapter = new iBiddingAdapter(context, listTemp, getActivity().getSupportFragmentManager());
                        iBid_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();

                    } else {

                        search = new ArrayList<iBid_Model>();

                        for (int i = 0; i < listTemp.size(); i++) {
                            if (status == 1) {
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


                        iBid_adapter = new iBiddingAdapter(context, search, getActivity().getSupportFragmentManager());
                        LViOP.setAdapter(iBid_adapter);
                        iBid_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                        if (status == 1) {
                            status = 0;
                        }
                    }

                } catch (Exception e) {

                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);

                    Util.showAlertCommonDialogWithOKBtn(context, "Error","Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                LViOP.setVisibility(View.GONE);
                LLNoData.setVisibility(View.VISIBLE);

                Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }


        }
    }


    private class getSupplierBidRejectedListAsyncTask extends AsyncTask<Void, Void, String> {
        private boolean isTimeout = false;

        @Override
        protected void onPreExecute() {

            showLoading();
            etTextPageGo.setText("");

            if (whichPage == 1) {
//        		btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
//	    		btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            } else if (whichPage == pageSize) {
//        		btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
//        		btnLast.setVisibility(View.GONE);
            } else {
//        		 btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//        		 btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }


            if (pageSize == 1) {
//        		 btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
//        		 btnLast.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
            } else {

//         		 btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//        		 btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {


                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsGetSupplierBidRejectedList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidRejectedList, Constants.APIDotNetMethodNameGetSupplierBidRejectedList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {
                    // store json list in shared prefrence
                    Util.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.KEY_JSONLIST, result);
                    items = new ArrayList<iBid_Model>();

                    ArrayList<iBid_Model> listTemp = Parser.getJSONSupplierBidRejectedListResultNew(result);

                    pageSize = Integer.parseInt(listTemp.get(0).getRecordscount());
                    int isEven = (pageSize % 10);
                    pageSize = pageSize / 10;
                    if (!(isEven == 0) || pageSize == 0)
                        pageSize = pageSize + 1;


                    for (int i = 0; i < listTemp.size(); i++)
                        items.add(listTemp.get(i));
                    iBid_adapter = new iBiddingAdapter(context, items, getActivity().getSupportFragmentManager());

                    LViOP.setAdapter(iBid_adapter);
                    iBid_adapter.notifyDataSetChanged();
                    LViOP.invalidate();
                    LViOP.invalidateViews();

                    etTextPageGo.setHint("Page " + whichPage + " of " + pageSize);

                    if (whichPage == 1) {
//                		btnFirst.setVisibility(View.GONE);
                        btnPrevious.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
//        	    		btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    } else if (whichPage == pageSize) {
//                		btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.GONE);
//                		btnLast.setVisibility(View.GONE);
                    } else {
//                		 btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
//                		 btnLast.setVisibility(View.VISIBLE);
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

                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);

                    Util.showAlertCommonDialogWithOKBtn(context, "Error","Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                LViOP.setVisibility(View.GONE);
                LLNoData.setVisibility(View.VISIBLE);

                Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }


        }
    }


    private class getSupplierBidRejectedSearchListAsyncTask extends AsyncTask<Void, Void, String> {
        private boolean isTimeout = false;

        @Override
        protected void onPreExecute() {

            showLoading();
            etTextPageGo.setText("");
            etTextPageGo.setVisibility(View.GONE);
//        		 btnFirst.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
//        		 btnLast.setVisibility(View.GONE);
            btnGo.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
//                parameter.put("PageSize", "999999999");
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", "1");

                String resultForSize = Util.connectSOAP(Constants.uRLiOpsGetSupplierBidRejectedList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidRejectedList, Constants.APIDotNetMethodNameGetSupplierBidRejectedList, parameter);
                pageSize = Parser.getJSONSupplierBidRejectedListResultSize(resultForSize);
                pageSize = pageSize / 10;
                pageSize = pageSize + 1;

                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsGetSupplierBidRejectedList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidRejectedList, Constants.APIDotNetMethodNameGetSupplierBidRejectedList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {
                    items = new ArrayList<iBid_Model>();

                    ArrayList<iBid_Model> listTemp = Parser.getJSONSupplierBidRejectedListResultNew(result);

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

                        iBid_adapter = new iBiddingAdapter(context, listTemp, getActivity().getSupportFragmentManager());
                        iBid_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();

                    } else {

                        search = new ArrayList<iBid_Model>();

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

                        iBid_adapter = new iBiddingAdapter(context, search, getActivity().getSupportFragmentManager());
                        LViOP.setAdapter(iBid_adapter);
                        iBid_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                        if (status == 1) {
                            status = 0;
                        }
                    }

                } catch (Exception e) {

                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);

                    Util.showAlertCommonDialogWithOKBtn(context, "Error","Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                LViOP.setVisibility(View.GONE);
                LLNoData.setVisibility(View.VISIBLE);

                Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }


        }
    }


    private class getSupplierBidCloseListAsyncTask extends AsyncTask<Void, Void, String> {
        private boolean isTimeout = false;

        @Override
        protected void onPreExecute() {

            showLoading();
            etTextPageGo.setText("");

            if (whichPage == 1) {
//        		btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
//	    		btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            } else if (whichPage == pageSize) {
//        		btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
//        		btnLast.setVisibility(View.GONE);
            } else {
//        		 btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//        		 btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);
            }


            if (pageSize == 1) {
//        		 btnFirst.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
//        		 btnLast.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
            } else {

//         		 btnFirst.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
//        		 btnLast.setVisibility(View.VISIBLE);
                btnGo.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {

                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsGetSupplierBidCloseList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidCloseList, Constants.APIDotNetMethodNameGetSupplierBidCloseList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {
                    // store json list in shared prefrence
                    Util.WriteSharePrefrence(getActivity(), Constant.SHRED_PR.KEY_JSONLIST, result);
                    items = new ArrayList<iBid_Model>();

                    ArrayList<iBid_Model> listTemp = Parser.getJSONSupplierBidCloseListResultNew(result);

                    pageSize = Integer.parseInt(listTemp.get(0).getRecordscount());
                    int isEven = (pageSize % 10);
                    pageSize = pageSize / 10;
                    if (!(isEven == 0) || pageSize == 0)
                        pageSize = pageSize + 1;

                    for (int i = 0; i < listTemp.size(); i++)
                        items.add(listTemp.get(i));
                    iBid_adapter = new iBiddingAdapter(context, items, getActivity().getSupportFragmentManager());

                    LViOP.setAdapter(iBid_adapter);
                    iBid_adapter.notifyDataSetChanged();
                    LViOP.invalidate();
                    LViOP.invalidateViews();

                    etTextPageGo.setHint("Page " + whichPage + " of " + pageSize);

                    if (whichPage == 1) {
//                		btnFirst.setVisibility(View.GONE);
                        btnPrevious.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
//        	    		btnLast.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                    } else if (whichPage == pageSize) {
//                		btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnGo.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.GONE);
//                		btnLast.setVisibility(View.GONE);
                    } else {
//                		 btnFirst.setVisibility(View.VISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
//                		 btnLast.setVisibility(View.VISIBLE);
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

                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);

                    Util.showAlertCommonDialogWithOKBtn(context, "Error","Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                LViOP.setVisibility(View.GONE);
                LLNoData.setVisibility(View.VISIBLE);

                Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");

            }


        }
    }


    private class getSupplierBidCloseSearchListAsyncTask extends AsyncTask<Void, Void, String> {
        private boolean isTimeout = false;

        @Override
        protected void onPreExecute() {

            showLoading();
            etTextPageGo.setText("");
            etTextPageGo.setVisibility(View.GONE);
//        		 btnFirst.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
//        		 btnLast.setVisibility(View.GONE);
            btnGo.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
//                parameter.put("PageSize", "999999999");
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", "1");

                String resultForSize = Util.connectSOAP(Constants.uRLiOpsGetSupplierBidCloseList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidCloseList, Constants.APIDotNetMethodNameGetSupplierBidCloseList, parameter);
                pageSize = Parser.getJSONSupplierBidCloseListResultSize(resultForSize);
                pageSize = pageSize / 10;
                pageSize = pageSize + 1;

                parameter = new Hashtable<String, String>();
                parameter.put("SupplierCode", SupplierCode);
                //parameter.put("Status", Status);
//                parameter.put("PageSize", "999999999");
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1");
                parameter.put("PageNo", String.valueOf(whichPage));

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsGetSupplierBidCloseList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidCloseList, Constants.APIDotNetMethodNameGetSupplierBidCloseList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {
                    items = new ArrayList<iBid_Model>();

                    ArrayList<iBid_Model> listTemp = Parser.getJSONSupplierBidCloseListResult(result);

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

                        iBid_adapter = new iBiddingAdapter(context, listTemp, getActivity().getSupportFragmentManager());
                        iBid_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();

                    } else {

                        search = new ArrayList<iBid_Model>();

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

                        iBid_adapter = new iBiddingAdapter(context, search, getActivity().getSupportFragmentManager());
                        LViOP.setAdapter(iBid_adapter);
                        iBid_adapter.notifyDataSetChanged();
                        LViOP.invalidate();
                        LViOP.invalidateViews();
                    }
                    if (status == 1) {
                        status = 0;
                    }

                } catch (Exception e) {

                    LViOP.setVisibility(View.GONE);
                    LLNoData.setVisibility(View.VISIBLE);

                    Util.showAlertCommonDialogWithOKBtn(context, "Error","Unable to get data from server. Check your internet connection and please try again.");
                }


            } else {

                LViOP.setVisibility(View.GONE);
                LLNoData.setVisibility(View.VISIBLE);
                Util.showAlertCommonDialogWithOKBtn(context, "Error", "Unable to get data from server. Check your internet connection and please try again.");
            }
        }
    }


    private class updateBidAcceptedAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            showLoading();

        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("BidNo", MultipleJobNo);
            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsUpdateBidAcceptedStatus, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateBidAcceptedStatus, Constants.APIDotNetMethodNameUpdateBidAcceptedStatus, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {

                    String temp = Parser.getJSONAcceptRejectResult(result);
                    //Util.showAlertCommonDialogWithOKBtn(context, "Success","Job #" + iBiddingAdapter.items.get(iBiddingAdapter.selectedItem).getJobNo() + " Accepted Succesfully");


                    items = new ArrayList<iBid_Model>();
                    search = new ArrayList<iBid_Model>();


                    try {

                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("Bid #" + MultipleJobNo + " Accepted Succesfully");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//        								    getActivity().finish();
                                        Intent intent = new Intent(ChooseTaskActivity.context, iOpMainViewPagerActivity.class);
                                        intent.putExtra(Constants.intentKey_iOp, 2);
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


    private class updateBidRejectedAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            showLoading();

        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("BidNo", MultipleJobNo);
            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsUpdateBidRejectedStatus, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateBidRejectedStatus, Constants.APIDotNetMethodNameUpdateBidRejectedStatus, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {

                    String temp = Parser.getJSONAcceptRejectResult(result);
                    //Util.showAlertCommonDialogWithOKBtn(context, "Success","Job #" + iBiddingAdapter.items.get(iBiddingAdapter.selectedItem).getJobNo() + " Rejected Succesfully");


                    items = new ArrayList<iBid_Model>();
                    search = new ArrayList<iBid_Model>();


                    try {

                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("Bid #" + MultipleJobNo + " Rejected Succesfully");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//        								    getActivity().finish();
                                        Intent intent = new Intent(ChooseTaskActivity.context, iOpMainViewPagerActivity.class);
                                        intent.putExtra(Constants.intentKey_iOp, 2);
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


    public void CallTheJob() {
        whichPage = 1;
        if (posSpin == 0) {
            new getSupplierBidListAsyncTask().execute();
        } else if (posSpin == 1) {
            new getSupplierBidAcceptedListAsyncTask().execute();
        } else if (posSpin == 2) {
            new getSupplierBidRejectedListAsyncTask().execute();
        }

        btnUpdateServer.setText("xxx.xxx." + Constants.SERVER_URL.substring(8, Constants.SERVER_URL.length()));

    }

}