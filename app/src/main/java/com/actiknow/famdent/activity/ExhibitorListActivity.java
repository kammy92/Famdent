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
import com.actiknow.famdent.adapter.ExhibitorAdapter;
import com.actiknow.famdent.model.Exhibitor;
import com.actiknow.famdent.utils.SimpleDividerItemDecoration;
import com.actiknow.famdent.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ExhibitorListActivity extends AppCompatActivity {
    RecyclerView rvExhibitor;
    ImageView ivBack;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Exhibitor> exhibitorList = new ArrayList<> ();
    ExhibitorAdapter exhibitorAdapter;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_exhibitor_list);
        initView ();
        initData ();
        initListener ();

    }

    private void initView () {
        rvExhibitor = (RecyclerView) findViewById (R.id.rvExhibitorList);
        ivBack = (ImageView) findViewById (R.id.ivBack);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById (R.id.swipeRefreshLayout);
        Utils.setTypefaceToAllViews (this, rvExhibitor);
    }

    private void initData () {
        exhibitorList.add (new Exhibitor (1, "http://seeklogo.com/images/1/3M-logo-079FB52BC8-seeklogo.com.png", "3M ESPE", "Hall 1", "Stall 28"));
        exhibitorList.add (new Exhibitor (2, "http://mudrsoc.com/wp-content/uploads/2017/01/Dentsply-Logo-Black.jpg", "DENTSPLY SIRONA", "Hall 1", "Stall 31"));
        exhibitorList.add (new Exhibitor (3, "http://www.cldental.com.au/mobile/images/equipment/compressors/durrlogo.jpg", "DUERR DENTEL INDIA", "Hall 1", "Stall 45"));
        exhibitorList.add (new Exhibitor (4, "", "UNICORN DENMART", "Hall 1", "Stall 29"));
        exhibitorList.add (new Exhibitor (5, "", "CAPRI", "Hall 1", "Stall 38"));
        exhibitorList.add (new Exhibitor (6, "", "ACETON INDIA", "Hall 1", "Stall 17"));
        exhibitorList.add (new Exhibitor (7, "", "SKANRAY TECHNOLOGY", "Hall 1", "Stall 32"));

        exhibitorAdapter = new ExhibitorAdapter (this, exhibitorList);
        rvExhibitor.setAdapter (exhibitorAdapter);
        rvExhibitor.setHasFixedSize (true);
        rvExhibitor.setLayoutManager (new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false));
        rvExhibitor.addItemDecoration (new SimpleDividerItemDecoration (this));
        rvExhibitor.setItemAnimator (new DefaultItemAnimator ());
    }

    private void initListener () {
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
