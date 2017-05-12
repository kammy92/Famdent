package com.actiknow.famdent.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.model.EventSpeaker;
import com.actiknow.famdent.utils.Utils;

import java.util.ArrayList;


/**
 * Created by actiknow on 4/28/17.
 */

public class ProgrammeSpeakerFragment extends Fragment {
    public static ArrayList<EventSpeaker> eventSpeakerList2;
    TextView tvSpeakerName;
    TextView tvSpeakerQualification;
    TextView tvExperience;
    ImageView ivSpeaker;

    public static ProgrammeSpeakerFragment newInstance (int position, ArrayList<EventSpeaker> eventSpeakerList) {
        ProgrammeSpeakerFragment fragment = new ProgrammeSpeakerFragment ();
        Bundle args = new Bundle ();
        args.putInt ("position", position);
        fragment.setArguments (args);
        eventSpeakerList2 = eventSpeakerList;
        return fragment;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate (R.layout.fragment_programme_speaker, container, false);
        initView (rootView);
        initData ();
        initListener ();
        return rootView;
    }

    private void initView (View v) {
        tvSpeakerName = (TextView) v.findViewById (R.id.tvSpeakerName);
        tvSpeakerQualification = (TextView) v.findViewById (R.id.tvSpeakerQualification);
        tvExperience = (TextView) v.findViewById (R.id.tvExperience);
        ivSpeaker = (ImageView) v.findViewById (R.id.ivSpeaker);
    }

    private void initData () {
        EventSpeaker eventSpeaker = eventSpeakerList2.get (getArguments ().getInt ("position"));
        Utils.setTypefaceToAllViews (getActivity (), tvSpeakerName);
        Log.e ("NAME", "" + eventSpeaker.getName ());
        tvSpeakerName.setText (eventSpeaker.getName ());
        tvSpeakerQualification.setText (eventSpeaker.getQualification ());
        tvExperience.setText (eventSpeaker.getExperience ());
    }

    private void initListener () {
    }
}
