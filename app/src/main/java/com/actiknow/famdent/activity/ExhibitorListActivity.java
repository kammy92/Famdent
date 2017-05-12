package com.actiknow.famdent.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.adapter.ExhibitorAdapter;
import com.actiknow.famdent.helper.DatabaseHandler;
import com.actiknow.famdent.model.Exhibitor;
import com.actiknow.famdent.model.StallDetail;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.AppConfigURL;
import com.actiknow.famdent.utils.Constants;
import com.actiknow.famdent.utils.NetworkConnection;
import com.actiknow.famdent.utils.SetTypeFace;
import com.actiknow.famdent.utils.SimpleDividerItemDecoration;
import com.actiknow.famdent.utils.Utils;
import com.actiknow.famdent.utils.VisitorDetailsPref;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ExhibitorListActivity extends AppCompatActivity {
    RecyclerView rvExhibitor;
    ImageView ivBack;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Exhibitor> exhibitorList = new ArrayList<> ();
    List<Exhibitor> tempExhibitorList = new ArrayList<> ();
    ExhibitorAdapter exhibitorAdapter;
    String[] category;

    ImageView ivFilter;
    ImageView ivSort;
    TextView tvTitle;
    SearchView searchView;

    TextView tvNoResult;

    List<StallDetail> stallDetailList = new ArrayList<> ();

    DatabaseHandler db;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_exhibitor_list);
        initView ();
        initData ();
        initListener ();

//        getExhibitorList ();
        getOfflineExhibitorList ();
    }

    private void getOfflineExhibitorList () {
        Utils.showLog (Log.DEBUG, AppConfigTags.TAG, "Getting all the exhibitors from local database", true);
        exhibitorList.clear ();
        ArrayList<Exhibitor> offlineExhibitor = db.getAllExhibitorList ();
        for (Exhibitor exhibitor : offlineExhibitor)
            exhibitorList.add (exhibitor);
        exhibitorAdapter.notifyDataSetChanged ();
        swipeRefreshLayout.setRefreshing (false);
    }

    private void initView () {
        rvExhibitor = (RecyclerView) findViewById (R.id.rvExhibitorList);
        ivBack = (ImageView) findViewById (R.id.ivBack);
        ivFilter = (ImageView) findViewById (R.id.ivFilter);
        tvNoResult = (TextView) findViewById (R.id.tvNoResult);

        ivSort = (ImageView) findViewById (R.id.ivSort);
        tvTitle = (TextView) findViewById (R.id.tvTitle);
        searchView = (SearchView) findViewById (R.id.searchView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById (R.id.swipeRefreshLayout);
        Utils.setTypefaceToAllViews (this, rvExhibitor);
    }

    private void initData () {
        db = new DatabaseHandler (getApplicationContext ());

        category = new String[] {"Air Abrasion", "Curing Lights", "Disposable Needles"};

        swipeRefreshLayout.setRefreshing (true);

//        exhibitorList.add (new Exhibitor (1, "http://seeklogo.com/images/1/3M-logo-079FB52BC8-seeklogo.com.png", "3M ESPE", "Hall 1", "Stall 28"));
//        exhibitorList.add (new Exhibitor (2, "http://mudrsoc.com/wp-content/uploads/2017/01/Dentsply-Logo-Black.jpg", "DENTSPLY SIRONA", "Hall 1", "Stall 31"));
//        exhibitorList.add (new Exhibitor (3, "http://www.cldental.com.au/mobile/images/equipment/compressors/durrlogo.jpg", "DUERR DENTEL INDIA", "Hall 1", "Stall 45"));
//        exhibitorList.add (new Exhibitor (4, "", "UNICORN DENMART", "Hall 1", "Stall 29"));
//        exhibitorList.add (new Exhibitor (5, "", "CAPRI", "Hall 1", "Stall 38"));
//        exhibitorList.add (new Exhibitor (6, "", "ACETON INDIA", "Hall 1", "Stall 17"));
//        exhibitorList.add (new Exhibitor (7, "", "SKANRAY TECHNOLOGY", "Hall 1", "Stall 32"));

        swipeRefreshLayout.setColorSchemeColors (getResources ().getColor (R.color.colorPrimaryDark));
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
                swipeRefreshLayout.setRefreshing (true);
                getOfflineExhibitorList ();
//                getExhibitorList ();
            }
        });
        ivBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        ivFilter.setOnClickListener (new View.OnClickListener () {


            @Override
            public void onClick (View view) {
                boolean wrapInScrollView = true;
                final MaterialDialog dialog = new MaterialDialog.Builder (ExhibitorListActivity.this)
                        .customView (R.layout.dialog_filter, wrapInScrollView)
                        .positiveText ("APPLY")
                        .negativeText ("CANCEL")
                        .canceledOnTouchOutside (false)
                        .cancelable (false)
                        .onPositive (new MaterialDialog.SingleButtonCallback () {
                            @Override
                            public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ivFilter.setImageResource (R.drawable.ic_filter_checked);
                            }
                        })
                        .typeface (SetTypeFace.getTypeface (ExhibitorListActivity.this, Constants.font_name), SetTypeFace.getTypeface (ExhibitorListActivity.this, Constants.font_name))
                        .show ();


                //Spinner spCategory=(Spinner)dialog.findViewById(R.id.spCategoryType);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExhibitorListActivity.this, android.R.layout.simple_spinner_item, category);
                //spCategory.setAdapter(adapter);


                LinearLayout llCategory = (LinearLayout) dialog.findViewById (R.id.llCategory);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams (
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                Utils.setTypefaceToAllViews (ExhibitorListActivity.this, llCategory);

                for (int i = 0; i < category.length; i++) {
                    CheckBox checkBox = new CheckBox (ExhibitorListActivity.this);
                    checkBox.setLayoutParams (lparams);
                    checkBox.setText (category[i]);
                    llCategory.addView (checkBox);

                }
            }
        });

        searchView.setOnSearchClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
//                Toast.makeText (ExhibitorListActivity.this, "karman open", Toast.LENGTH_SHORT).show ();
//                ivFilter.setVisibility (View.GONE);
                ivBack.setVisibility (View.GONE);
//                ivSort.setVisibility (View.GONE);
                tvTitle.setVisibility (View.GONE);
            }
        });

        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit (String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange (String newText) {
                tempExhibitorList.clear ();
                for (Exhibitor exhibitor : exhibitorList) {
                    List<StallDetail> tempStallDetailList = exhibitor.getStallDetailList ();
                    StallDetail stallDetail = tempStallDetailList.get (0);
                    if (exhibitor.getExhibitor_name ().toUpperCase ().contains (newText.toUpperCase ()) ||
                            exhibitor.getExhibitor_name ().toLowerCase ().contains (newText.toLowerCase ()) ||
                            stallDetail.getStall_number ().toLowerCase ().contains (newText.toLowerCase ()) ||
                            stallDetail.getStall_number ().toUpperCase ().contains (newText.toUpperCase ())) {
                        tempExhibitorList.add (exhibitor);
                    }
                }
                exhibitorAdapter = new ExhibitorAdapter (ExhibitorListActivity.this, tempExhibitorList);
                rvExhibitor.setAdapter (exhibitorAdapter);
                rvExhibitor.setHasFixedSize (true);
                rvExhibitor.setLayoutManager (new LinearLayoutManager (ExhibitorListActivity.this, LinearLayoutManager.VERTICAL, false));
                rvExhibitor.addItemDecoration (new SimpleDividerItemDecoration (ExhibitorListActivity.this));
                rvExhibitor.setItemAnimator (new DefaultItemAnimator ());
                return true;
            }
        });

        searchView.setOnCloseListener (new SearchView.OnCloseListener () {
            @Override
            public boolean onClose () {
//                Toast.makeText (ExhibitorListActivity.this, "karman close", Toast.LENGTH_SHORT).show ();
//                ivFilter.setVisibility (View.VISIBLE);
                ivBack.setVisibility (View.VISIBLE);
//                ivSort.setVisibility (View.VISIBLE);
                tvTitle.setVisibility (View.VISIBLE);
                return false;
            }
        });
    }

    private void getExhibitorList () {
        if (NetworkConnection.isNetworkAvailable (this)) {
            tvNoResult.setVisibility (View.GONE);
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_EXHIBITOR_LIST, true);
            StringRequest strRequest = new StringRequest (Request.Method.GET, AppConfigURL.URL_EXHIBITOR_LIST,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            exhibitorList.clear ();
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean is_error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! is_error) {
                                        JSONArray jsonArrayExhibitor = jsonObj.getJSONArray (AppConfigTags.EXHIBITOR);
                                        for (int i = 0; i < jsonArrayExhibitor.length (); i++) {
                                            JSONObject jsonObjectExhibitor = jsonArrayExhibitor.getJSONObject (i);
                                            Exhibitor exhibitor = new Exhibitor (
                                                    jsonObjectExhibitor.getInt (AppConfigTags.EXHIBITOR_ID),
                                                    jsonObjectExhibitor.getString (AppConfigTags.EXHIBITOR_LOGO),
                                                    jsonObjectExhibitor.getString (AppConfigTags.EXHIBITOR_NAME));

                                            JSONArray jsonArrayStallDetails = jsonObjectExhibitor.getJSONArray (AppConfigTags.STALL_DETAILS);
                                            exhibitor.clearStallDetailList ();
                                            for (int j = 0; j < jsonArrayStallDetails.length (); j++) {
                                                JSONObject jsonObjectStallDetail = jsonArrayStallDetails.getJSONObject (j);
                                                StallDetail stallDetail = new StallDetail (
                                                        jsonObjectStallDetail.getString (AppConfigTags.STALL_NAME),
                                                        jsonObjectStallDetail.getString (AppConfigTags.HALL_NUMBER),
                                                        jsonObjectStallDetail.getString (AppConfigTags.STALL_NUMBER)
                                                );
                                                exhibitor.setStallDetailInList (stallDetail);
                                            }
                                            exhibitorList.add (i, exhibitor);
                                            exhibitorAdapter.notifyDataSetChanged ();
                                            }
                                        if (jsonArrayExhibitor.length () > 0) {
                                            swipeRefreshLayout.setRefreshing (false);
                                        } else {
                                            swipeRefreshLayout.setRefreshing (false);
                                            tvNoResult.setVisibility (View.VISIBLE);
                                        }
                                    } else {
                                        swipeRefreshLayout.setRefreshing (false);
                                        tvNoResult.setVisibility (View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                    swipeRefreshLayout.setRefreshing (false);
                                    tvNoResult.setVisibility (View.VISIBLE);
                                }
                            } else {
                                swipeRefreshLayout.setRefreshing (false);
                                tvNoResult.setVisibility (View.VISIBLE);
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            NetworkResponse response = error.networkResponse;
                            if (response != null && response.data != null) {
                                Utils.showLog (Log.ERROR, AppConfigTags.ERROR, new String (response.data), true);

                            }
                            swipeRefreshLayout.setRefreshing (false);
                            tvNoResult.setVisibility (View.VISIBLE);
                        }
                    }) {

                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    VisitorDetailsPref visitorDetailsPref = VisitorDetailsPref.getInstance ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (ExhibitorListActivity.this, visitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            Utils.sendRequest (strRequest, 5);
        } else {
            swipeRefreshLayout.setRefreshing (false);
            tvNoResult.setVisibility (View.VISIBLE);
        }
    }


/*
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater ().inflate (R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService (Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem (R.id.action_search).getActionView ();
        if (null != searchView) {
            searchView.setSearchableInfo (searchManager.getSearchableInfo (getComponentName ()));
//            searchView.setIconifiedByDefault (false);
        }


//        final int searchBarId = searchView.getContext ().getResources ().getIdentifier ("android:id/search_bar", null, null);
//        LinearLayout searchBar = (LinearLayout) searchView.findViewById (searchBarId);

        EditText et = (EditText) searchView.findViewById (R.id.search_src_text);
//        et.getBackground ().setColorFilter (R.color.text_color_grey_dark,null);
//        et.setBackgroundColor (getResources ().getColor (R.color.text_color_grey_light)); // ← If you just want a color
//        et.setBackground (getResources ().getDrawable (R.drawable.layout_search_edittext));

//        et.setFocusableInTouchMode (true);
//        et.setFocusable (true);

        LinearLayout searchBar = (LinearLayout) searchView.findViewById (R.id.search_bar);
        searchBar.setLayoutTransition (new LayoutTransition ());

        searchView.setQueryHint ("Search ATM ID");
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener () {
            public boolean onQueryTextChange (String newText) {
//                etSearch.setText (newText);
                return true;
            }

            public boolean onQueryTextSubmit (String query) {
                //Here u can get the value "query" which is entered in the search box.
                return true;
            }
        };
        searchView.setOnQueryTextListener (queryTextListener);

        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.action_search:
//                if (etSearch.isShown ()) {
//                    etSearch.setVisibility (View.GONE);
//                    final Handler handler = new Handler ();
//                    handler.postDelayed (new Runnable () {
//                        @Override
//                        public void run () {
//                            etSearch.setText ("");
//                        }
//                    }, 1000);
//                } else {
//                    etSearch.setVisibility (View.VISIBLE);
//                }
                break;
        }
        Utils.hideSoftKeyboard (ExhibitorListActivity.this);
/**
 if (item != null && item.getItemId () == android.R.id.home) {
 if (mDrawerLayout.isDrawerOpen (mDrawerPanel)) {
 } else {
 mDrawerLayout.openDrawer (mDrawerPanel);
 }
 return true;
 }
 */
//        return super.onOptionsItemSelected (item);
//    }
//*/

    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
