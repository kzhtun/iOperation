package com.info121.ioperation.fragment;

import com.info121.ioperation.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class RootFragment extends Fragment {

    private static Context context;
	
	private LinearLayout LLLoading;
	private LinearLayout LLMainContainer;
	private View rootview;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	context = getActivity();
        rootview = inflater.inflate(R.layout.root_fragment_layout, container, false);
        LLLoading = (LinearLayout) rootview.findViewById(R.id.LLRootLoading);
		LLLoading.setVisibility(View.GONE);
		LLMainContainer = (LinearLayout) rootview.findViewById(R.id.LLRootMainContainer);
        return rootview;
    }
    
    
    protected void showLoading(){
		LLLoading.setVisibility(View.VISIBLE);
	}
	
	protected void dismissLoading(){
		LLLoading.setVisibility(View.GONE);
	}
	
	protected View getViewParent(){
		return rootview;
	}

	protected void addToParentLayout(int layoutid){
		View child = getActivity().getLayoutInflater().inflate(layoutid,null);
		LLMainContainer.addView(child);
	}
	
	protected String getStringFromResource(int resid){
		return getResources().getString(resid);

	}

}