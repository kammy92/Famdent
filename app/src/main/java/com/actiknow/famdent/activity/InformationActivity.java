package com.actiknow.famdent.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.utils.Utils;

public class InformationActivity extends AppCompatActivity {
    ImageView ivBack;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_information);
        initView ();
        initData ();
        initListener ();
    }

    private void initView () {
        ivBack = (ImageView) findViewById (R.id.ivBack);
        Utils.setTypefaceToAllViews (this, ivBack);
    }

    private void initData () {
    }

    private void initListener () {
        ivBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
