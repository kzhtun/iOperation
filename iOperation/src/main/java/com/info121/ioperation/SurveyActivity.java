package com.info121.ioperation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;

public class SurveyActivity extends AppCompatActivity {
    private SurveyMonkey s = new SurveyMonkey();
    public static final int SM_REQUEST_CODE = 0;
    public static final String SURVEY_HASH = "XJ7K6QC";
    public static final String APP_NAME = "iOperation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s.onStart(this, APP_NAME, SM_REQUEST_CODE, SURVEY_HASH);
        s.startSMFeedbackActivityForResult(this, SM_REQUEST_CODE, SURVEY_HASH);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            finish();


    }
}
