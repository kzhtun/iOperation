package com.info121.ioperations.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.info121.ioperations.R;

/**
 * Created by archirayan1 on 3/30/2016.
 */



public class CalenderFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calender,container,false);
        return v;
    }
}
