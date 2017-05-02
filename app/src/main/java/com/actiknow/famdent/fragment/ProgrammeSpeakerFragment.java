package com.actiknow.famdent.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.model.ProgrammeSpeaker;
import com.actiknow.famdent.utils.Utils;


/**
 * Created by actiknow on 4/28/17.
 */

public class ProgrammeSpeakerFragment extends Fragment {
    public static ProgrammeSpeaker programmeSpeaker2;

    TextView tvSpeakerName;
    TextView tvSpeakerQualification;
    TextView tvExperience;
    ImageView ivSpeaker;

//    private static final String ARG_TEXT = "text";


    public static ProgrammeSpeakerFragment newInstance (ProgrammeSpeaker programmeSpeaker) {
        ProgrammeSpeakerFragment fragment = new ProgrammeSpeakerFragment ();
//        Bundle args = new Bundle ();
//        args.putString (ARG_TEXT, "");
//        fragment.setArguments (args);
        programmeSpeaker2 = programmeSpeaker;
        return fragment;
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate (R.layout.fragment_programme_speaker, container, false);
        initView(rootView);
        initData ();
        initListener();
        return rootView;
    }

    private void initView (View v) {
        tvSpeakerName = (TextView) v.findViewById (R.id.tvSpeakerName);
        tvSpeakerQualification = (TextView) v.findViewById (R.id.tvSpeakerQualification);
        tvExperience = (TextView) v.findViewById (R.id.tvExperience);
        ivSpeaker = (ImageView) v.findViewById (R.id.ivSpeaker);
    }

    private void initData() {
        Utils.setTypefaceToAllViews (getActivity (), tvSpeakerName);
        tvSpeakerName.setText (programmeSpeaker2.getName ());
        tvSpeakerQualification.setText (programmeSpeaker2.getQualification ());
        tvExperience.setText (programmeSpeaker2.getExperience ());
    }

    private void initListener() {
    }
}
