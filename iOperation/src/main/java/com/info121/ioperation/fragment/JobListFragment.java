package com.info121.ioperation.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.info121.constant.Constants;
import com.info121.ioperation.R;
import com.info121.ioperation.adapter.JobAdapter;
import com.info121.ioperation.adapter.MessageAdapter;
import com.info121.ioperation.util.Parser;
import com.info121.ioperation.util.Util;
import com.info121.model.Job;
import com.info121.model.iMessage;
import com.info121.model.iNewMessage;

import java.util.Hashtable;
import java.util.List;

public class JobListFragment extends Fragment{
    private List<Job> mJobList;
    private String mDriver;
    private Context mContext;
    private Button btHome;
    private ProgressBar progressBar;
    private ProgressDialog mProgressDialog;

    private RecyclerView mRecyclerView;
    private TextView mNoData;


    private OnFragmentInteractionListener mListener;
    List<Job> listTemp;

    public JobListFragment() {
        // Required empty public constructor
    }


    public static JobListFragment newInstance(String param1, String param2) {
        JobListFragment fragment = new JobListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_job_list, container, false);

        mContext = getActivity();

        mDriver = mContext.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, mContext.MODE_PRIVATE).getString(Util.LOGIN_KEY, "");

        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        btHome = (Button) v.findViewById(R.id.btHome_iOperationFragment);
        mNoData = (TextView) v.findViewById(R.id.no_data);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.job_list);
        mProgressDialog = new ProgressDialog(getActivity());

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment bidingFragment = new HomeFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, bidingFragment);
                fragmentTransaction.commit();
            }
        });

        new getAllJobsAsyncTask().execute();

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class getAllJobsAsyncTask extends AsyncTask<Void, Void, String> {
        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected String doInBackground(Void... voids) {
            Hashtable<String, String> parameter = new Hashtable<String, String>();

            try {
                parameter = new Hashtable<String, String>();
                parameter.put("driver", mDriver);

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }

            return Util.connectSOAP(Constants.uRLiOpsGetDriverAllJobs, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetDriverAllJobs, Constants.APIDotNetMethodNameGetDriverAllJobs, parameter);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgressDialog();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mProgressDialog.hide();

            if (result != null) {
                try {
                    mJobList  = Parser.getJSONDriverAllJobs(result);

                    if(mJobList.size() > 0) {
                        mRecyclerView.setHasFixedSize(false);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        mRecyclerView.setAdapter(new JobAdapter(getActivity(), mJobList));

                        mNoData.setVisibility(View.GONE);
                    }else{
                        mNoData.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Util.modifiedLogTrace(e.getStackTrace().toString());
                }
            }
        }
    }


    private void showProgressDialog() {
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
        mProgressDialog.show();
    }

//    public void onMessageDelete(int i){
//        listTemp.remove(i);
//        mRecyclerView.getAdapter().notifyDataSetChanged();
//    }
}
