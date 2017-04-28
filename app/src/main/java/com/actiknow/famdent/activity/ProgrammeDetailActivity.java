package com.actiknow.famdent.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.fragment.ProgrammeSpeakerFragment;
import com.actiknow.famdent.utils.Utils;
import com.actiknow.famdent.utils.WrappingViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by actiknow on 4/28/17.
 */

public class ProgrammeDetailActivity extends AppCompatActivity {
    private WrappingViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;

    ViewPagerAdapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_programme_detail);
        initView ();
        initData ();
    }

    private void initView () {
        dotsLayout = (LinearLayout) findViewById (R.id.layoutDots);
        viewPager = (WrappingViewPager) findViewById (R.id.viewpager);

    }

    private void initData () {
        setUpViewPager (viewPager);
        Utils.setTypefaceToAllViews (this, viewPager);
    }

    private void setUpViewPager (ViewPager viewPager) {
        adapter = new ViewPagerAdapter (getSupportFragmentManager ());
        adapter.addFragment (new ProgrammeSpeakerFragment (), "OVERVIEW");
        adapter.addFragment (new ProgrammeSpeakerFragment (), "COMPS");
        adapter.addFragment (new ProgrammeSpeakerFragment (), "ACCESS/POSSESSION");
        viewPager.setAdapter (adapter);
        addBottomDots (0);
        viewPager.addOnPageChangeListener (viewPagerPageChangeListener);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<> ();
        private final List<String> mFragmentTitleList = new ArrayList<> ();

        public ViewPagerAdapter (FragmentManager manager) {
            super (manager);
        }

        @Override
        public Fragment getItem (int position) {
            return mFragmentList.get (position);
        }

        @Override
        public int getCount () {
            return mFragmentList.size ();
        }

        public void addFragment (Fragment fragment, String title) {
            mFragmentList.add (fragment);
            mFragmentTitleList.add (title);
        }

        @Override
        public CharSequence getPageTitle (int position) {
            return mFragmentTitleList.get (position);
        }
    }


    private void addBottomDots (int currentPage) {
        dots = new TextView[3];

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


    private int getItem (int i) {
        return viewPager.getCurrentItem () + i;
    }


}
