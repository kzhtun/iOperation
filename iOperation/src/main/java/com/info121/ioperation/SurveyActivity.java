package com.info121.ioperation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;

public class SurveyActivity extends AppCompatActivity {
    private SurveyMonkey s = new SurveyMonkey();
    public static final int SM_REQUEST_CODE = 0;
    public static final String SURVEY_HASH = "XJ7K6QC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s.startSMFeedbackActivityForResult(this, SM_REQUEST_CODE, SURVEY_HASH);
    }
}
