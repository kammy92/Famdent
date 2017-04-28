package com.actiknow.famdent.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actiknow.famdent.R;


/**
 * Created by actiknow on 4/28/17.
 */

public class ProgrammeSpeakerFragment extends Fragment {
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate (R.layout.fragment_programme_speaker, container, false);
        initView(rootView);
        initData ();
        initListener();
        return rootView;
    }

    private void initView(View rootView) {
    }

    private void initData() {
    }

    private void initListener() {
    }
}
