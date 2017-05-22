package com.actiknow.famdent.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.adapter.SessionAdapter;
import com.actiknow.famdent.helper.DatabaseHandler;
import com.actiknow.famdent.model.Banner;
import com.actiknow.famdent.model.Session;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.AppConfigURL;
import com.actiknow.famdent.utils.Constants;
import com.actiknow.famdent.utils.NetworkConnection;
import com.actiknow.famdent.utils.SetTypeFace;
import com.actiknow.famdent.utils.SimpleDividerItemDecoration;
import com.actiknow.famdent.utils.TypefaceSpan;
import com.actiknow.famdent.utils.Utils;
import com.actiknow.famdent.utils.VisitorDetailsPref;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionListActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {
    ImageView ivBack;
    RecyclerView rvSession;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Session> sessionList = new ArrayList<> ();
    List<Session> tempSessionList = new ArrayList<> ();

    List<String> sessionCategories = new ArrayList<> ();

    SessionAdapter sessionAdapter;

    TextView tvNoResult;

    ImageView ivFilter;
    ImageView ivSort;
    TextView tvTitle;
    SearchView searchView;

    String category = "";

    DatabaseHandler db;
    private SliderLayout slider;

//    Dialog dialog;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_session_list);
        initView ();
        initData ();
        initListener ();
        initSlider ();
        selectSessionCategoryDialog ();
//        getOfflineSessionList ();
    }

    private void getOfflineSessionList () {
        Utils.showLog (Log.DEBUG, AppConfigTags.TAG, "Getting all the sessions from local database", true);
        sessionList.clear ();
        ArrayList<Session> offlineSessions = db.getAllSessionList ();

        for (Session session : offlineSessions) {
            if (category.length () > 0) {
                if (session.getCategory ().equalsIgnoreCase (category)) {
                    sessionList.add (session);
                }
            } else {
                sessionList.add (session);
            }
        }
        sessionAdapter.notifyDataSetChanged ();
        swipeRefreshLayout.setRefreshing (false);
    }

    private void initView () {
        ivBack = (ImageView) findViewById (R.id.ivBack);
        tvNoResult = (TextView) findViewById (R.id.tvNoResult);
        rvSession = (RecyclerView) findViewById (R.id.rvSession);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById (R.id.swipeRefreshLayout);

        ivFilter = (ImageView) findViewById (R.id.ivFilter);
        ivSort = (ImageView) findViewById (R.id.ivSort);
        tvTitle = (TextView) findViewById (R.id.tvTitle);
        searchView = (SearchView) findViewById (R.id.searchView);
        slider = (SliderLayout) findViewById (R.id.slider);
    }

    private void initData () {
        db = new DatabaseHandler (getApplicationContext ());
//        dialog = Utils.showBigBannerDialog (this);
        sessionCategories.add ("International Speakers");
        sessionCategories.add ("Live Dentistry Arena");
        sessionCategories.add ("Famdent Awards Exceptional Speakers");
        sessionCategories.add ("Power Of 10");
        sessionCategories.add ("How To Series");
        sessionCategories.add ("Most Challenging Cases");
        sessionCategories.add ("2 Ways To Get There!!");

        swipeRefreshLayout.setColorSchemeColors (getResources ().getColor (R.color.colorPrimaryDark));

        sessionAdapter = new SessionAdapter (SessionListActivity.this, sessionList);
        rvSession.setAdapter (sessionAdapter);
        rvSession.setHasFixedSize (true);
        rvSession.setLayoutManager (new LinearLayoutManager (SessionListActivity.this, LinearLayoutManager.VERTICAL, false));
        rvSession.addItemDecoration (new SimpleDividerItemDecoration (SessionListActivity.this));
        rvSession.setItemAnimator (new DefaultItemAnimator ());
    
        searchView.setQueryHint (Html.fromHtml ("<font color = #ffffff>" + "Search" + "</font>"));
        Utils.setTypefaceToAllViews (this, ivBack);
    }

    private void initListener () {
        swipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh () {
                swipeRefreshLayout.setRefreshing (true);
                getOfflineSessionList ();
//                getSessionListFromServer ();
            }
        });
        ivBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
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
                tempSessionList.clear ();
                for (Session session : sessionList) {
                    if (session.getDoctor_name ().toUpperCase ().contains (newText.toUpperCase ()) ||
                            session.getDoctor_name ().toLowerCase ().contains (newText.toLowerCase ()) ||
                            session.getProgram_name ().toLowerCase ().contains (newText.toLowerCase ()) ||
                            session.getProgram_name ().toUpperCase ().contains (newText.toUpperCase ()) ||
                            session.getLocation ().toUpperCase ().contains (newText.toUpperCase ()) ||
                            session.getLocation ().toLowerCase ().contains (newText.toLowerCase ())) {
                        tempSessionList.add (session);
                    }
                }
                sessionAdapter = new SessionAdapter (SessionListActivity.this, tempSessionList);
                rvSession.setAdapter (sessionAdapter);
                rvSession.setHasFixedSize (true);
                rvSession.setLayoutManager (new LinearLayoutManager (SessionListActivity.this, LinearLayoutManager.VERTICAL, false));
                rvSession.addItemDecoration (new SimpleDividerItemDecoration (SessionListActivity.this));
                rvSession.setItemAnimator (new DefaultItemAnimator ());
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

//        dialog.setOnCancelListener (new DialogInterface.OnCancelListener () {
//            @Override
//            public void onCancel (DialogInterface dialog) {
//                selectSessionCategoryDialog ();
//            }
//        });
    }

    private void getSessionListFromServer () {
        swipeRefreshLayout.setRefreshing (true);
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_SESSION_LIST, true);
            StringRequest strRequest = new StringRequest (Request.Method.GET, AppConfigURL.URL_SESSION_LIST,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            sessionList.clear ();
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        JSONArray jsonArray = jsonObj.getJSONArray (AppConfigTags.SESSIONS);
                                        for (int i = 0; i < jsonArray.length (); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject (i);
                                            Session session = new Session ();

                                            if (category.length () > 0) {
                                                if (jsonObject.getString (AppConfigTags.SESSION_CATEGORY).equalsIgnoreCase (category)) {
                                                    session.setId (jsonObject.getInt (AppConfigTags.SESSION_ID));
                                                    session.setProgram_name (jsonObject.getString (AppConfigTags.SESSION_NAME));
                                                    session.setDoctor_name (jsonObject.getString (AppConfigTags.SESSION_SPEAKERS));
                                                    session.setDate (jsonObject.getString (AppConfigTags.SESSION_DATE));
                                                    session.setTime (jsonObject.getString (AppConfigTags.SESSION_TIME));
                                                    session.setLocation (jsonObject.getString (AppConfigTags.SESSION_LOCATION));
                                                    session.setCategory (jsonObject.getString (AppConfigTags.SESSION_CATEGORY));
                                                    sessionList.add (session);
                                                }
                                            } else {
                                                session.setId (jsonObject.getInt (AppConfigTags.SESSION_ID));
                                                session.setProgram_name (jsonObject.getString (AppConfigTags.SESSION_NAME));
                                                session.setDoctor_name (jsonObject.getString (AppConfigTags.SESSION_SPEAKERS));
                                                session.setDate (jsonObject.getString (AppConfigTags.SESSION_DATE));
                                                session.setTime (jsonObject.getString (AppConfigTags.SESSION_TIME));
                                                session.setLocation (jsonObject.getString (AppConfigTags.SESSION_LOCATION));
                                                session.setCategory (jsonObject.getString (AppConfigTags.SESSION_CATEGORY));
                                                sessionList.add (session);
                                            }

//                                            Log.e ("karman category name", category);
//                                            Log.e ("karman session category", session.getCategory ());

//                                            if (session.getCategory ().equalsIgnoreCase (category)){
//                                            }
                                        }
                                        sessionAdapter.notifyDataSetChanged ();
                                        if (jsonArray.length () > 0) {
                                            swipeRefreshLayout.setRefreshing (false);
                                        } else {
                                            swipeRefreshLayout.setRefreshing (false);
                                            tvNoResult.setVisibility (View.VISIBLE);
                                        }
                                    } else {
                                        swipeRefreshLayout.setRefreshing (false);
                                        tvNoResult.setVisibility (View.VISIBLE);
                                    }
                                    Utils.setTypefaceToAllViews (SessionListActivity.this, ivBack);
                                } catch (Exception e) {
                                    swipeRefreshLayout.setRefreshing (false);
                                    tvNoResult.setVisibility (View.VISIBLE);
                                    e.printStackTrace ();
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
                            swipeRefreshLayout.setRefreshing (false);
                            tvNoResult.setVisibility (View.VISIBLE);
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    VisitorDetailsPref visitorDetailsPref = VisitorDetailsPref.getInstance ();
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (SessionListActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            strRequest.setRetryPolicy (new DefaultRetryPolicy (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Utils.sendRequest (strRequest, 30);
        } else {
            swipeRefreshLayout.setRefreshing (false);
            tvNoResult.setVisibility (View.VISIBLE);
        }
    }

    private void applyFilterInList (String category) {
        List<Session> sessionList1 = sessionList;
        sessionList.clear ();
        for (Session session : sessionList1) {
            if (session.getCategory ().toUpperCase ().contains (category.toUpperCase ()) ||
                    session.getCategory ().toLowerCase ().contains (category.toLowerCase ())) {
                Log.e ("karman", "in if");
                sessionList.add (session);
            }
        }
        sessionAdapter.notifyDataSetChanged ();
    }

    private void selectSessionCategoryDialog () {
        new MaterialDialog.Builder (this)
                .title ("Select Category")
//                .positiveColor (getResources ().getColor (R.color.app_text_color_dark))
                .contentColor (getResources ().getColor (R.color.app_text_color_dark))
//                .positiveText (R.string.dialog_action_show_all)
                .items (sessionCategories)
                .typeface (SetTypeFace.getTypeface (this), SetTypeFace.getTypeface (this))
                .canceledOnTouchOutside (true)
                .cancelable (true)
                .itemsCallback (new MaterialDialog.ListCallback () {
                    @Override
                    public void onSelection (MaterialDialog dialog, View view, int which, CharSequence text) {
//                        Utils.showToast (SessionListActivity.this, "tt" + text.toString (), false);
                        category = text.toString ();
//                        getSessionListFromServer ();
                        getOfflineSessionList ();
                    }
                })
//                .onPositive (new MaterialDialog.SingleButtonCallback () {
//                    @Override
//                    public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        category = "";
//                        getSessionListFromServer ();
//                    }
//                })
                .cancelListener (new DialogInterface.OnCancelListener () {
                    @Override
                    public void onCancel (DialogInterface dialog) {
                        category = "";
                        getOfflineSessionList ();
//                        getSessionListFromServer ();
                    }
                })
                .show ();
    }

    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }
    
    private void initSlider () {
        for (int i = 0; i < db.getAllSessionBanners ().size (); i++) {
            Banner banner = db.getAllSessionBanners ().get (i);
            SpannableString s = new SpannableString (banner.getTitle ());
            s.setSpan (new TypefaceSpan (this, Constants.font_name), 0, s.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            
            DefaultSliderView defaultSliderView = new DefaultSliderView (this);
            defaultSliderView
                    .image (banner.getImage ())
                    .setScaleType (BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener (this);
            
            defaultSliderView.bundle (new Bundle ());
            defaultSliderView.getBundle ().putString ("url", banner.getUrl ());
            slider.addSlider (defaultSliderView);
        }
        
        slider.setIndicatorVisibility (PagerIndicator.IndicatorVisibility.Visible);
        slider.setPresetTransformer (SliderLayout.Transformer.Default);
        slider.setCustomAnimation (new DescriptionAnimation ());
        slider.setDuration (5000);
        slider.addOnPageChangeListener (this);
        slider.setCustomIndicator ((PagerIndicator) findViewById (R.id.custom_indicator));
        slider.setPresetIndicator (SliderLayout.PresetIndicators.Center_Bottom);
    }
    
    
    @Override
    public void onSliderClick (BaseSliderView slider) {
        Uri uri = Uri.parse ("http://" + slider.getBundle ().get ("url"));
        Intent intent = new Intent (Intent.ACTION_VIEW, uri);
        startActivity (intent);
    }
    
    @Override
    public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {
    }
    
    @Override
    public void onPageSelected (int position) {
    }
    
    @Override
    public void onPageScrollStateChanged (int state) {
    }
    
}

