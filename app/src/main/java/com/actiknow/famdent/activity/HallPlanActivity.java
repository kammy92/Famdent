package com.actiknow.famdent.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.utils.TouchImageView;
import com.actiknow.famdent.utils.Utils;

public class HallPlanActivity extends AppCompatActivity {
    ImageView ivBack;
    TouchImageView ivHallPlan;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_hall_plan);
        initView ();
        initData ();
        initListener ();
    }

    private void initView () {
        ivBack = (ImageView) findViewById (R.id.ivBack);
        ivHallPlan = (TouchImageView) findViewById (R.id.ivHallPlan);
        Utils.setTypefaceToAllViews (this, ivBack);
    }

    private void initData () {
        ivHallPlan.setZoom (1);
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
