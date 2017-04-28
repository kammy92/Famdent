package com.actiknow.famdent.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.fragment.MyFavouriteExhibitorFragment;
import com.actiknow.famdent.fragment.MyFavouriteProgrammesFragment;
import com.actiknow.famdent.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by l on 28/04/2017.
 */

public class MyFavouriteActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ivBack = (ImageView) findViewById (R.id.ivBack);

    }

    private void initData() {
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
        Utils.setTypefaceToAllViews (this, ivBack);
    }

    private void initListener() {
        ivBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyFavouriteExhibitorFragment (), "EXHIBITOR");
        adapter.addFragment(new MyFavouriteProgrammesFragment (), "PROGRAMMES");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}




