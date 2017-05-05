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
import com.actiknow.famdent.adapter.ProgrammeAdapter;
import com.actiknow.famdent.model.Programme;
import com.actiknow.famdent.utils.SimpleDividerItemDecoration;
import com.actiknow.famdent.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProgrammeListActivity extends AppCompatActivity {
    ImageView ivBack;
    RecyclerView rvProgrammesList;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Programme> programmeList= new ArrayList<>();
    ProgrammeAdapter programmeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programme_list);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        ivBack = (ImageView) findViewById (R.id.ivBack);
        rvProgrammesList=(RecyclerView)findViewById(R.id.rvProgrammerList);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
    }

    private void initData() {
//        programmeList.add (new Programme (1, "Orthodontics Workshop", "Dr Nikita Jain", "27/04/2017", "15:22"));
//        programmeList.add (new Programme (2, "Esthetic Implants", "Dr Rahul Jain", "27/05/2017", "15:22"));
//        programmeList.add (new Programme (3, "Basic Endodontics", "Dr Sumit", "27/05/2017", "15:22"));
//        programmeList.add (new Programme (4, "Smile Designing", "Dr Karman Singh", "27/05/2017", "15:22"));
//        programmeList.add (new Programme (5, "Orthodontics Workshop", "Dr Nikita Jain", "27/04/2017", "15:22"));
//        programmeList.add (new Programme (6, "Aesthetic Implants", "Dr Rahul Jain", "27/05/2017", "15:22"));
//        programmeList.add (new Programme (7, "Basic Endodontics", "Dr Rahul Jain", "27/05/2017", "15:22"));
//        programmeList.add (new Programme (8, "Smile Designing", "Dr Karman Singh", "27/05/2017", "15:22"));

        programmeAdapter = new ProgrammeAdapter (this, programmeList);
        rvProgrammesList.setAdapter (programmeAdapter);
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

/*
    private void getProgramListFromServer () {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_initializing), false);
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_EVENT, true);
            StringRequest strRequest = new StringRequest (Request.Method.GET, AppConfigURL.URL_EVENT,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        JSONArray jsonArray = jsonObj.getJSONArray (AppConfigTags.EVENTS);
                                        Log.e ("sud", "" + jsonArray);
                                        for (int i = 0; i < jsonArray.length (); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject (i);
                                            Log.e ("sud", "sud");
                                            Programme programme = new Programme ();
                                            programme.setId (jsonObject.getInt ("event_id"));
                                            programme.setProgram_name (jsonObject.getString (AppConfigTags.EVENT_NAME));
                                            programme.setDoctor_name (jsonObject.getString (AppConfigTags.EVENT_SPEAKERS));
                                            programme.setDate (jsonObject.getString (AppConfigTags.EVENT_DATE));
                                            programme.setTime (jsonObject.getString (AppConfigTags.EVENT_TIME));
                                            programmeList.add (programme);
                                        }
                                    }
                                    programmeAdapter = new ProgrammeAdapter (ProgrammeListActivity.this, programmeList);
                                    rvProgrammesList.setAdapter (programmeAdapter);
                                    rvProgrammesList.setHasFixedSize (true);
                                    rvProgrammesList.setLayoutManager (new LinearLayoutManager (ProgrammeListActivity.this, LinearLayoutManager.VERTICAL, false));
                                    rvProgrammesList.addItemDecoration (new SimpleDividerItemDecoration (ProgrammeListActivity.this));
                                    rvProgrammesList.setItemAnimator (new DefaultItemAnimator ());
                                    Utils.setTypefaceToAllViews (ProgrammeListActivity.this, ivBack);
                                    progressDialog.dismiss ();
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
                                    e.printStackTrace ();
                                }
                            } else {
                                progressDialog.dismiss ();
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            progressDialog.dismiss ();
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_USER_LOGIN_KEY, visitorDetailsPref.getStringPref (ProgrammeListActivity.this, VisitorDetailsPref.VISITOR_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            strRequest.setRetryPolicy (new DefaultRetryPolicy (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Utils.sendRequest (strRequest, 30);
        } else {
            progressDialog.dismiss ();
        }
    }
*/

    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
