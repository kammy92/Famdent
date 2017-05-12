package com.actiknow.famdent.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.adapter.EventAdapter;
import com.actiknow.famdent.helper.DatabaseHandler;
import com.actiknow.famdent.model.Event;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.AppConfigURL;
import com.actiknow.famdent.utils.Constants;
import com.actiknow.famdent.utils.NetworkConnection;
import com.actiknow.famdent.utils.SimpleDividerItemDecoration;
import com.actiknow.famdent.utils.Utils;
import com.actiknow.famdent.utils.VisitorDetailsPref;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventListActivity extends AppCompatActivity {
    ImageView ivBack;
    RecyclerView rvProgrammesList;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Event> eventList = new ArrayList<> ();
    List<Event> tempEventList = new ArrayList<> ();
    EventAdapter eventAdapter;
    TextView tvNoResult;

    ImageView ivFilter;
    ImageView ivSort;
    TextView tvTitle;
    SearchView searchView;
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_event_list);
        initView();
        initData();
        initListener();
        getOfflineEventList ();
//        getProgramListFromServer ();
    }

    private void initView() {
        ivBack = (ImageView) findViewById (R.id.ivBack);
        rvProgrammesList=(RecyclerView)findViewById(R.id.rvProgrammerList);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        tvNoResult = (TextView) findViewById (R.id.tvNoResult);

        ivFilter = (ImageView) findViewById (R.id.ivFilter);
        ivSort = (ImageView) findViewById (R.id.ivSort);
        tvTitle = (TextView) findViewById (R.id.tvTitle);
        searchView = (SearchView) findViewById (R.id.searchView);
    }

    private void initData() {
        db = new DatabaseHandler (getApplicationContext ());
        swipeRefreshLayout.setRefreshing (true);

//        eventList.add (new Event (1, "Orthodontics Workshop", "Dr Nikita Jain", "27/04/2017", "15:22"));
//        eventList.add (new Event (2, "Esthetic Implants", "Dr Rahul Jain", "27/05/2017", "15:22"));
//        eventList.add (new Event (3, "Basic Endodontics", "Dr Sumit", "27/05/2017", "15:22"));
//        eventList.add (new Event (4, "Smile Designing", "Dr Karman Singh", "27/05/2017", "15:22"));
//        eventList.add (new Event (5, "Orthodontics Workshop", "Dr Nikita Jain", "27/04/2017", "15:22"));
//        eventList.add (new Event (6, "Aesthetic Implants", "Dr Rahul Jain", "27/05/2017", "15:22"));
//        eventList.add (new Event (7, "Basic Endodontics", "Dr Rahul Jain", "27/05/2017", "15:22"));
//        eventList.add (new Event (8, "Smile Designing", "Dr Karman Singh", "27/05/2017", "15:22"));

        swipeRefreshLayout.setColorSchemeColors (getResources ().getColor (R.color.colorPrimaryDark));

        eventAdapter = new EventAdapter (this, eventList);
        rvProgrammesList.setAdapter (eventAdapter);
        rvProgrammesList.setHasFixedSize (true);
        rvProgrammesList.setLayoutManager (new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false));
        rvProgrammesList.addItemDecoration (new SimpleDividerItemDecoration (this));
        rvProgrammesList.setItemAnimator (new DefaultItemAnimator ());

        Utils.setTypefaceToAllViews (this, ivBack);
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh () {
                swipeRefreshLayout.setRefreshing (true);
                getOfflineEventList ();
//                getProgramListFromServer ();
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
                tempEventList.clear ();
                for (Event event : eventList) {
                    if (event.getProgram_name ().toUpperCase ().contains (newText.toUpperCase ()) ||
                            event.getProgram_name ().toLowerCase ().contains (newText.toLowerCase ()) ||
                            event.getDoctor_name ().toLowerCase ().contains (newText.toLowerCase ()) ||
                            event.getDoctor_name ().toUpperCase ().contains (newText.toUpperCase ())) {
                        tempEventList.add (event);
                    }
                }
                eventAdapter = new EventAdapter (EventListActivity.this, tempEventList);
                rvProgrammesList.setAdapter (eventAdapter);
                rvProgrammesList.setHasFixedSize (true);
                rvProgrammesList.setLayoutManager (new LinearLayoutManager (EventListActivity.this, LinearLayoutManager.VERTICAL, false));
                rvProgrammesList.addItemDecoration (new SimpleDividerItemDecoration (EventListActivity.this));
                rvProgrammesList.setItemAnimator (new DefaultItemAnimator ());
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

    private void getOfflineEventList () {
        Utils.showLog (Log.DEBUG, AppConfigTags.TAG, "Getting all the events from local database", true);
        eventList.clear ();
        ArrayList<Event> offlineEvents = db.getAllEventList ();
        for (Event event : offlineEvents)
            eventList.add (event);
        eventAdapter.notifyDataSetChanged ();
        swipeRefreshLayout.setRefreshing (false);
    }

    private void getProgramListFromServer () {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_EVENT_LIST, true);
            StringRequest strRequest = new StringRequest (Request.Method.GET, AppConfigURL.URL_EVENT_LIST,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            eventList.clear ();
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        JSONArray jsonArrayEvent = jsonObj.getJSONArray (AppConfigTags.EVENTS);
                                        for (int i = 0; i < jsonArrayEvent.length (); i++) {
                                            JSONObject jsonObject = jsonArrayEvent.getJSONObject (i);
                                            Event event = new Event (
                                                    jsonObject.getInt (AppConfigTags.EVENT_ID),
                                                    jsonObject.getString (AppConfigTags.EVENT_NAME),
                                                    jsonObject.getString (AppConfigTags.EVENT_SPEAKERS),
                                                    jsonObject.getString (AppConfigTags.EVENT_DATE),
                                                    jsonObject.getString (AppConfigTags.EVENT_TIME));
                                            eventList.add (event);
                                        }
                                        eventAdapter.notifyDataSetChanged ();
                                        if (jsonArrayEvent.length () > 0) {
                                            swipeRefreshLayout.setRefreshing (false);
                                        } else {
                                            swipeRefreshLayout.setRefreshing (false);
                                            tvNoResult.setVisibility (View.VISIBLE);
                                        }
                                    } else {
                                        swipeRefreshLayout.setRefreshing (false);
                                        tvNoResult.setVisibility (View.VISIBLE);
                                    }
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
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    VisitorDetailsPref visitorDetailsPref = VisitorDetailsPref.getInstance ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (EventListActivity.this, visitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            Utils.sendRequest (strRequest, 30);
        } else {
            // getOfflineData();
            swipeRefreshLayout.setRefreshing (false);
            tvNoResult.setVisibility (View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
