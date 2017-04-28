package com.actiknow.famdent.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.adapter.ProgrammeAdapter;
import com.actiknow.famdent.model.Programme;
import com.actiknow.famdent.utils.SimpleDividerItemDecoration;
import com.actiknow.famdent.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProgrammeListActivity extends AppCompatActivity {
    ImageView ivBack;
    RecyclerView rvProgrammesList;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Programme> programmeList= new ArrayList<>();
    ProgrammeAdapter programmeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programme_list);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        ivBack = (ImageView) findViewById (R.id.ivBack);
        rvProgrammesList=(RecyclerView)findViewById(R.id.rvProgrammerList);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
    }

    private void initData() {

        programmeList.add (new Programme (1, "ORTHODONTICS WORKSHOP", "DR NIKITA JAIN", "27/04/2017", "15:22"));
        programmeList.add (new Programme (2, "ESTHETIC IMPLANTS", "DR RAHUL JAIN", "27/05/2017", "15:22"));
        programmeList.add (new Programme (3, "BASIC ENDODONTICS", "DR SUMIT", "27/05/2017", "15:22"));
        programmeList.add (new Programme (4, "SMILE DESIGNING", "DR KARMAN SINGH", "27/05/2017", "15:22"));
        programmeList.add (new Programme (1, "ORTHODONTICS WORKSHOP", "DR NIKITA JAIN", "27/04/2017", "15:22"));
        programmeList.add (new Programme (2, "ESTHETIC IMPLANTS", "DR RAHUL JAIN", "27/05/2017", "15:22"));
        programmeList.add (new Programme (3, "BASIC ENDODONTICS", "DR RAHUL JAIN", "27/05/2017", "15:22"));
        programmeList.add (new Programme (4, "SMILE DESIGNING", "DR KARMAN SINGH", "27/05/2017", "15:22"));


        programmeAdapter = new ProgrammeAdapter (this, programmeList);
        rvProgrammesList.setAdapter (programmeAdapter);
        rvProgrammesList.setHasFixedSize (true);
        rvProgrammesList.setLayoutManager (new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false));
        rvProgrammesList.addItemDecoration (new SimpleDividerItemDecoration (this));
        rvProgrammesList.setItemAnimator (new DefaultItemAnimator ());

        Utils.setTypefaceToAllViews (this, ivBack);
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh () {
                swipeRefreshLayout.setRefreshing (false);
            }
        });
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
