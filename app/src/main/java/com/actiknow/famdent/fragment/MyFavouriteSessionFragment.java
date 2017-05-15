package com.actiknow.famdent.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.adapter.SessionAdapter;
import com.actiknow.famdent.helper.DatabaseHandler;
import com.actiknow.famdent.model.Session;
import com.actiknow.famdent.utils.AppConfigTags;
import com.actiknow.famdent.utils.AppConfigURL;
import com.actiknow.famdent.utils.Constants;
import com.actiknow.famdent.utils.NetworkConnection;
import com.actiknow.famdent.utils.SimpleDividerItemDecoration;
import com.actiknow.famdent.utils.Utils;
import com.actiknow.famdent.utils.VisitorDetailsPref;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

public class MyFavouriteSessionFragment extends Fragment {
    ImageView ivBack;
    RecyclerView rvSession;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Session> sessionList = new ArrayList<> ();
    List<String> list = new ArrayList<> ();
    SessionAdapter sessionAdapter;
    ImageView ivFilter;

    TextView tvNoResult;

    DatabaseHandler db;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate (R.layout.fragment_my_favourite_session, container, false);
        initView (rootView);
        initData ();
        initListener ();
        getOfflineFavouriteSessions ();
//        getSessionListFromServer ();
        return rootView;
    }

    private void initView (View rootView) {
        ivBack = (ImageView) rootView.findViewById (R.id.ivBack);
        tvNoResult = (TextView) rootView.findViewById (R.id.tvNoResult);
        rvSession = (RecyclerView) rootView.findViewById (R.id.rvScientificSession);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById (R.id.swipeRefreshLayout);
        ivFilter = (ImageView) rootView.findViewById (R.id.ivFilter);
    }

    private void initData () {
        db = new DatabaseHandler (getActivity ());
        swipeRefreshLayout.setColorSchemeColors (getResources ().getColor (R.color.colorPrimaryDark));
        swipeRefreshLayout.setRefreshing (true);
        sessionAdapter = new SessionAdapter (getActivity (), sessionList);
        rvSession.setAdapter (sessionAdapter);
        rvSession.setHasFixedSize (true);
        rvSession.setLayoutManager (new LinearLayoutManager (getActivity (), LinearLayoutManager.VERTICAL, false));
        rvSession.addItemDecoration (new SimpleDividerItemDecoration (getActivity ()));
        rvSession.setItemAnimator (new DefaultItemAnimator ());

        Utils.setTypefaceToAllViews (getActivity (), tvNoResult);
    }

    private void getOfflineFavouriteSessions () {
        Utils.showLog (Log.DEBUG, AppConfigTags.TAG, "Getting all the sessions from local database", true);
        sessionList.clear ();
        ArrayList<Session> offlineSessions = db.getAllFavouriteSessions ();

        for (Session session : offlineSessions) {
            sessionList.add (session);
        }
        sessionAdapter.notifyDataSetChanged ();
        if (offlineSessions.size () > 0) {
            swipeRefreshLayout.setRefreshing (false);
        } else {
            swipeRefreshLayout.setRefreshing (false);
            tvNoResult.setVisibility (View.VISIBLE);
        }
    }

    private void initListener () {
        swipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh () {
                getOfflineFavouriteSessions ();
            }
        });
    }

    private void getSessionListFromServer () {
        if (NetworkConnection.isNetworkAvailable (getActivity ())) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_SESSION_FAVOURITE_LIST, true);
            StringRequest strRequest = new StringRequest (Request.Method.GET, AppConfigURL.URL_SESSION_FAVOURITE_LIST,
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
                                            session.setId (jsonObject.getInt (AppConfigTags.SESSION_ID));
                                            session.setProgram_name (jsonObject.getString (AppConfigTags.SESSION_NAME));
                                            session.setDoctor_name (jsonObject.getString (AppConfigTags.SESSION_SPEAKERS));
                                            session.setDate (jsonObject.getString (AppConfigTags.SESSION_DATE));
                                            session.setTime (jsonObject.getString (AppConfigTags.SESSION_TIME));
                                            session.setLocation (jsonObject.getString (AppConfigTags.SESSION_LOCATION));
                                            session.setCategory (jsonObject.getString (AppConfigTags.SESSION_CATEGORY));
                                            sessionList.add (session);
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
                                    Utils.setTypefaceToAllViews (getActivity (), ivBack);
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
                    params.put (AppConfigTags.HEADER_VISITOR_LOGIN_KEY, visitorDetailsPref.getStringPref (getActivity (), VisitorDetailsPref.VISITOR_LOGIN_KEY));
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
}

