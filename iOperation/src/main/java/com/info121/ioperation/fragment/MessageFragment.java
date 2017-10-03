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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.info121.constant.Constants;
import com.info121.ioperation.R;
import com.info121.ioperation.adapter.MessageAdapter;
import com.info121.ioperation.util.Parser;
import com.info121.ioperation.util.Util;
import com.info121.model.iMessage;
import com.info121.model.iNewMessage;
import com.info121.model.iOP_Model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment{
    private List<iNewMessage> mMessageList;
    private String mDriver;
    private Context mContext;
    private Button btHome;
    private ProgressBar progressBar;
    private ProgressDialog mProgressDialog;

    private RecyclerView mRecyclerView;
    private TextView mNoData;


    private OnFragmentInteractionListener mListener;
    List<iMessage> listTemp;

    public MessageFragment() {
        // Required empty public constructor
    }


    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_message, container, false);

        mContext = getActivity();

        mDriver = mContext.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, mContext.MODE_PRIVATE).getString(Util.LOGIN_KEY, "");

        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        btHome = (Button) v.findViewById(R.id.btHome_iOperationFragment);
        mNoData = (TextView) v.findViewById(R.id.no_data);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.message_list);
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

        new getAllMessageAsyncTask().execute();

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


    private class getAllMessageAsyncTask extends AsyncTask<Void, Void, String> {
        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected String doInBackground(Void... voids) {
            Hashtable<String, String> parameter = new Hashtable<String, String>();

            try {
                parameter = new Hashtable<String, String>();
                parameter.put("Dtfrom", "");
                parameter.put("Dtto", "");
                parameter.put("Username", mDriver);
                parameter.put("PageSize", "0");
                parameter.put("PagesPerSet", "0");
                parameter.put("PageNo", "0");

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }

            return Util.connectSOAP(Constants.uRLiOpsGetDriverAllMessages, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetDriverAllMessages, Constants.APIDotNetMethodNameGetDriverAllMessages, parameter);

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
                    listTemp  = Parser.getJSONDriverAllMessages(result);

                    if(listTemp.size() > 0) {
                        mRecyclerView.setHasFixedSize(false);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        mRecyclerView.setAdapter(new MessageAdapter(getActivity(), listTemp));

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

    public void onMessageDelete(int i){
        listTemp.remove(i);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
