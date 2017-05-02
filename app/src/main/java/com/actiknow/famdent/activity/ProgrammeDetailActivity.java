package com.actiknow.famdent.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.fragment.ProgrammeSpeakerFragment;
import com.actiknow.famdent.model.ProgrammeDetail;
import com.actiknow.famdent.model.ProgrammeSpeaker;
import com.actiknow.famdent.utils.Constants;
import com.actiknow.famdent.utils.SetTypeFace;
import com.actiknow.famdent.utils.Utils;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by actiknow on 4/28/17.
 */

public class ProgrammeDetailActivity extends AppCompatActivity {
    TextView tvDate;
    TextView tvTime;
    TextView tvDuration;
    TextView tvCost;
    LinearLayout llTopics;
    ProgrammeDetail programmeDetail;
    ViewPagerAdapter adapter;
    ImageView ivFavourite;
    ImageView ivBack;
    CoordinatorLayout clMain;
    //    private WrappingViewPager viewPager;
    private ViewPager viewPager2;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener () {
        @Override
        public void onPageSelected (int position) {
            addBottomDots (position);
        }

        @Override
        public void onPageScrolled (int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged (int arg0) {
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_programme_detail);
        initView ();
        initData ();
        initListener ();
    }

    private void initListener () {
        ivBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        ivFavourite.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (programmeDetail.isFavourite ()) {
                    MaterialDialog dialog = new MaterialDialog.Builder (ProgrammeDetailActivity.this)
                            .content (R.string.dialog_text_remove_favourite)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (ProgrammeDetailActivity.this), SetTypeFace.getTypeface (ProgrammeDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    programmeDetail.setFavourite (false);
                                    ivFavourite.setImageResource (R.drawable.ic_star_border);
                                    Utils.showSnackBar (ProgrammeDetailActivity.this, clMain, "Programme removed from favourites", Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_undo), new View.OnClickListener () {
                                        @Override
                                        public void onClick (View v) {
                                            programmeDetail.setFavourite (true);
                                            ivFavourite.setImageResource (R.drawable.ic_star);
                                        }
                                    });
                                }
                            }).build ();
                    dialog.show ();
                } else {
                    MaterialDialog dialog = new MaterialDialog.Builder (ProgrammeDetailActivity.this)
                            .content (R.string.dialog_text_add_favourite)
                            .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                            .contentColor (getResources ().getColor (R.color.app_text_color_dark))
                            .negativeColor (getResources ().getColor (R.color.app_text_color_dark))
                            .typeface (SetTypeFace.getTypeface (ProgrammeDetailActivity.this), SetTypeFace.getTypeface (ProgrammeDetailActivity.this))
                            .canceledOnTouchOutside (false)
                            .cancelable (false)
                            .positiveText (R.string.dialog_action_yes)
                            .negativeText (R.string.dialog_action_no)
                            .onPositive (new MaterialDialog.SingleButtonCallback () {
                                @Override
                                public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    programmeDetail.setFavourite (true);
                                    ivFavourite.setImageResource (R.drawable.ic_star);
                                    Utils.showSnackBar (ProgrammeDetailActivity.this, clMain, "Programme added to favourites", Snackbar.LENGTH_LONG, null, null);
                                }
                            }).build ();
                    dialog.show ();
                }
            }
        });

    }

    private void initView () {
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
        dotsLayout = (LinearLayout) findViewById (R.id.layoutDots);
//        viewPager = (WrappingViewPager) findViewById (R.id.viewpager);
        viewPager2 = (ViewPager) findViewById (R.id.viewpager);
        ivBack = (ImageView) findViewById (R.id.ivBack);
        ivFavourite = (ImageView) findViewById (R.id.ivFavourite);
        llTopics = (LinearLayout) findViewById (R.id.llTopics);
        tvDate = (TextView) findViewById (R.id.tvDate);
        tvTime = (TextView) findViewById (R.id.tvTime);
        tvDuration = (TextView) findViewById (R.id.tvDuration);
        tvCost = (TextView) findViewById (R.id.tvCost);
    }

    private void initData () {
        Utils.setTypefaceToAllViews (this, tvCost);

        ArrayList<ProgrammeSpeaker> programmeSpeakerList = new ArrayList<> ();
        programmeSpeakerList.add (new ProgrammeSpeaker (1, "Karman Singh", "MBBS, MD", "9 Years"));
        programmeSpeakerList.add (new ProgrammeSpeaker (2, "Rahul Jain", "MBBS, MD", "4 Years"));
        programmeSpeakerList.add (new ProgrammeSpeaker (3, "Sudhanshu Sharma", "MBBS, MD", "6 Years"));

        ArrayList<String> topicList = new ArrayList<> ();
        topicList.add ("Impaction");
        topicList.add ("Implantology");
        topicList.add ("TMU Surgery");

        programmeDetail = new ProgrammeDetail (1, true, "asd", "24/05/2017", "13:00", "3 Hours", "10,000", programmeSpeakerList, topicList);

        if (programmeDetail.isFavourite ()) {
            ivFavourite.setImageResource (R.drawable.ic_star);
        } else {
            ivFavourite.setImageResource (R.drawable.ic_star_border);
        }

        for (int i = 0; i < programmeDetail.getTopicList ().size (); i++) {
            ArrayList<String> topicListTemp = programmeDetail.getTopicList ();
            TextView tv = new TextView (this);
            tv.setText ("\u25B8 " + topicListTemp.get (i));
            tv.setTextSize (18);
            tv.setTypeface (SetTypeFace.getTypeface (this, Constants.font_name));
            tv.setTextColor (getResources ().getColor (R.color.app_text_color_dark));
            llTopics.addView (tv);
        }

        tvDate.setText ("Date: " + programmeDetail.getDate ());
        tvTime.setText ("Time: " + programmeDetail.getTime ());
        tvDuration.setText ("Duration: " + programmeDetail.getDuration ());
        tvCost.setText ("The Seminar costs Rs " + programmeDetail.getFees ());

        setUpViewPager (viewPager2);

    }

    private void setUpViewPager (ViewPager viewPager) {
        ArrayList<ProgrammeSpeaker> programmeSpeakerList = programmeDetail.getProgrammeSpeakerList ();
        adapter = new ViewPagerAdapter (getSupportFragmentManager ());
        for (int i = 0; i < programmeSpeakerList.size (); i++) {
            ProgrammeSpeaker programmeSpeaker = programmeSpeakerList.get (i);
            Log.e ("kammy", " " + programmeSpeaker.getId ());
            Log.e ("kammy", " " + programmeSpeaker.getName ());
            adapter.addFragment (new ProgrammeSpeakerFragment ());
        }

        addBottomDots (0);
        viewPager.addOnPageChangeListener (viewPagerPageChangeListener);
        viewPager.setAdapter (adapter);
    }

    private void addBottomDots (int currentPage) {
        ArrayList<ProgrammeSpeaker> programmeSpeakerList = programmeDetail.getProgrammeSpeakerList ();

        dots = new TextView[programmeSpeakerList.size ()];

        dotsLayout.removeAllViews ();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView (this);
            dots[i].setText (Html.fromHtml ("&#8226;"));
            dots[i].setTextSize (35);
            dots[i].setTextColor (getResources ().getColor (R.color.text_color_grey_dark));
            dotsLayout.addView (dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor (getResources ().getColor (R.color.colorPrimary));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<> ();

        public ViewPagerAdapter (FragmentManager manager) {
            super (manager);
        }

        @Override
        public Fragment getItem (int position) {
            ArrayList<ProgrammeSpeaker> programmeSpeakerList = programmeDetail.getProgrammeSpeakerList ();
            ProgrammeSpeaker programmeSpeaker = programmeSpeakerList.get (position);
            Log.e ("karman", "speaker name " + programmeSpeaker.getName ());
            return ProgrammeSpeakerFragment.newInstance (programmeSpeaker);
        }

        @Override
        public int getCount () {
            return mFragmentList.size ();
        }

        public void addFragment (Fragment fragment) {
            mFragmentList.add (fragment);
        }
    }

}
