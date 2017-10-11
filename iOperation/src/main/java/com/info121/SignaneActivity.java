package com.info121;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.info121.ioperation.R;
import com.info121.ioperation.util.Util;
import com.info121.ioperation.util.Utils;

public class SignaneActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tvText, mZoom;
    ImageView ivPlus, ivMinus, ivClose, ivEdit;
    Float txtSize = 100.0f;
    private PowerManager.WakeLock wl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signane);

        tvText = (TextView) findViewById(R.id.tvDisplayName);
        ivClose = (ImageView) findViewById(R.id.ivclose);
        ivMinus = (ImageView) findViewById(R.id.ivMinus);
        ivPlus = (ImageView) findViewById(R.id.ivPlus);
        ivEdit = (ImageView) findViewById(R.id.ivEdit);
        mZoom = (TextView) findViewById(R.id.zoom);


        ivClose.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);

        String name = Utils.ReadSharePrefrence(SignaneActivity.this, "name");
        tvText.setText(name);

        if (name.equalsIgnoreCase("")) {
            Util.WriteSharePrefrence(SignaneActivity.this, "name", "MR. JAMES HAROLE FRANCO");
            tvText.setText("MR. JAMES HAROLE FRANCO");
        }

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();

// screen and CPU will stay awake during this section


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        wl.release();

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.ivclose:
                finish();

                break;

            case R.id.ivPlus:

                if (txtSize <= 200) {
                    txtSize = txtSize + 10;
                    tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
                    Log.d("test", "plus t" + txtSize);

                    mZoom.setText(((txtSize - 10) / 2) + "%");
                }

                break;

            case R.id.ivMinus:
                if (txtSize >= 15.0) {
                    txtSize = txtSize - 10;
                    tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
                    Log.d("test", "minus " + txtSize);

                    mZoom.setText(((txtSize - 10) / 2) + "%");
                }

                break;


            case R.id.ivEdit:

                Util.WriteSharePrefrence(SignaneActivity.this, "isclick", "1");
                final Dialog dialog = new Dialog(SignaneActivity.this);
                dialog.setContentView(R.layout.passengar_name_edit_dialog);
                dialog.setTitle("Change Name");


                final EditText editTextName = (EditText) dialog.findViewById(R.id.edtname);
                Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = editTextName.getText().toString();

                        tvText.setText(name);
                        Utils.WriteSharePrefrence(SignaneActivity.this, "name", name);
                        dialog.dismiss();

                    }
                });
                dialog.show();

                break;
        }
    }
}
