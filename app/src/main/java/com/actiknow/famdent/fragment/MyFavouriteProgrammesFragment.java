package com.actiknow.famdent.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actiknow.famdent.R;
import com.actiknow.famdent.adapter.ProgrammeAdapter;
import com.actiknow.famdent.model.Programme;
import com.actiknow.famdent.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by l on 28/04/2017.
 */

public class MyFavouriteProgrammesFragment extends Fragment {
    RecyclerView rvProgrammesList;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Programme> programmeList= new ArrayList<>();
    ProgrammeAdapter programmeAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate (R.layout.fragment_my_favourite_programmes, container, false);
        initView (rootView);
        initData ();
        initListener ();
        return rootView;
    }

    private void initView(View rootView) {
        rvProgrammesList=(RecyclerView)rootView.findViewById(R.id.rvProgrammerList);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
    }

    private void initData() {
        programmeList.add(new Programme(1,"ORTHODONTICS WORKSHOP","DR NIKITA JAIN","27/04/2017","15:22"));
        programmeList.add(new Programme(2,"ESTHETIC IMPLANTS","DR RAHUL JAIN","27/05/2017","15:22"));
        programmeList.add(new Programme(3,"BASIC ENDODONTICS","DR SUMIT","27/05/2017","15:22"));
        programmeList.add(new Programme(4,"SMILE DESIGNING","DR KARMAN SINGH","27/05/2017","15:22"));
        programmeList.add(new Programme(5,"ORTHODONTICS WORKSHOP","DR NIKITA JAIN","27/04/2017","15:22"));
        programmeList.add(new Programme(6,"ESTHETIC IMPLANTS","DR RAHUL JAIN","27/05/2017","15:22"));
        programmeList.add(new Programme(7,"BASIC ENDODONTICS","DR RAHUL JAIN","27/05/2017","15:22"));
        programmeList.add(new Programme(8,"SMILE DESIGNING","DR KARMAN SINGH","27/05/2017","15:22"));

        programmeAdapter = new ProgrammeAdapter (getActivity(), programmeList);
        rvProgrammesList.setAdapter (programmeAdapter);
        rvProgrammesList.setHasFixedSize (true);
        rvProgrammesList.setLayoutManager (new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvProgrammesList.addItemDecoration (new SimpleDividerItemDecoration (getActivity()));
        rvProgrammesList.setItemAnimator (new DefaultItemAnimator());
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh () {
                swipeRefreshLayout.setRefreshing (false);
            }
        });
    }
}
